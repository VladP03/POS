package com.facultate.POS.interceptor;

import com.facultate.POS.interceptor.exception.UnauthorizedException;
import com.pos.token.RequestValidateToken;
import com.pos.token.ResponseValidateToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceTransportException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.transport.HeadersAwareSenderWebServiceConnection;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;

import java.io.IOException;

@Log4j2
@Component
public class ValidateToken extends WebServiceGatewaySupport {

    public void validateToken(String token) {
        log.info("Checking token");

        if (token == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        log.info("Call SOAPEndpoint to validate token");
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setClassesToBeBound(RequestValidateToken.class, ResponseValidateToken.class);
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);

        final RequestValidateToken request = new RequestValidateToken();
        request.setToken(token.substring(7));

        log.info("Request token validation");
        try {
            ResponseValidateToken response = (ResponseValidateToken) webServiceTemplate
                    .marshalSendAndReceive("http://localhost:8090/user", request, new WebServiceMessageCallback() {
                        @Override
                        public void doWithMessage(WebServiceMessage message) throws IOException {
                            TransportContext context = TransportContextHolder.getTransportContext();
                            HeadersAwareSenderWebServiceConnection connection = (HeadersAwareSenderWebServiceConnection) context.getConnection();
                            connection.addRequestHeader("Authorization", token);
                        }
                    });
        } catch (WebServiceTransportException exception) {
            throw new UnauthorizedException("Unauthorized");
        }
    }

}
