package com.pos.JWT.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet result = new MessageDispatcherServlet();

        result.setApplicationContext(applicationContext);
        result.setTransformWsdlLocations(true);

        return new ServletRegistrationBean(result, "/login", "/register", "/Token");
    }


    // LOGIN
    @Bean(name = "login")
    public DefaultWsdl11Definition defaultWsdl11DefinitionLogin(XsdSchema loginSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("LoginPort");
        result.setLocationUri("/login");
        result.setTargetNamespace("http://com.pos.JWT/login");
        result.setSchema(loginSchema);

        return result;
    }

    @Bean
    public XsdSchema loginSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Login.xsd"));
    }


    // REGISTER
    @Bean(name = "register")
    public DefaultWsdl11Definition defaultWsdl11DefinitionRegister(XsdSchema registerSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("RegisterPort");
        result.setLocationUri("/register");
        result.setTargetNamespace("http://com.pos.JWT/register");
        result.setSchema(registerSchema);

        return result;
    }

    @Bean
    public XsdSchema registerSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Register.xsd"));
    }


    // REGISTER
    @Bean(name = "register")
    public DefaultWsdl11Definition defaultWsdl11DefinitionValidateToken(XsdSchema validateTokenSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("ValidateTokenPort");
        result.setLocationUri("/validateToken");
        result.setTargetNamespace("http://com.pos.JWT/validateToken");
        result.setSchema(validateTokenSchema);

        return result;
    }

    @Bean
    public XsdSchema validateTokenSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Token.xsd"));
    }
}