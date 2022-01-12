package com.POS.Book.interceptor;

import com.POS.Book.interceptor.exception.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

@Component
@Log4j2
public class ValidateTokenInterception implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        validateToken(token);

        return true;
    }


    private void validateToken(String token) {
        log.info("Checking token");

        if (token == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        try {
            log.info("Call SOAPEndpoint to validate token");
            SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage soapRequest = MessageFactory.newInstance().createMessage();
            SOAPPart soapPart = soapRequest.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();

            MimeHeaders headers = soapRequest.getMimeHeaders();
            headers.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE);
            headers.setHeader(HttpHeaders.AUTHORIZATION, token);

            SOAPBodyElement soapBodyElement = body.addBodyElement(
                    envelope.createName("Request-ValidateToken", "gs", "http://com.pos.JWT/Token"));
            SOAPElement soapElement = soapBodyElement.addChildElement(
                    envelope.createName("token", "gs", "http://com.pos.JWT/Token"));

            soapElement.addTextNode(token.substring(7));

            soapRequest.saveChanges();

            SOAPMessage soapResponse = connection.call(soapRequest, "http://localhost:8090/Token");

            if (!soapResponse.getSOAPBody().getFirstChild().getFirstChild().getLocalName().equals("sub")) {
                throw new UnauthorizedException("Unauthorized");
            }

            connection.close();
        } catch (SOAPException exception) {
            log.error(exception.getMessage());
            throw new UnauthorizedException("Unauthorized");
        }
    }
}
