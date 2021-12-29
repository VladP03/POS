package com.pos.stateless;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean
    public ServletRegistrationBean messageDispatcherServletCalculator(ApplicationContext applicationContext) {
        MessageDispatcherServlet result = new MessageDispatcherServlet();

        result.setApplicationContext(applicationContext);
        result.setTransformWsdlLocations(true);

        return new ServletRegistrationBean(result, "/sample", "/data");
    }

    @Bean(name = "calculator")
    public DefaultWsdl11Definition defaultWsdl11DefinitionCalculator(XsdSchema calculatorSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("CalculatorPort");
        result.setLocationUri("/sample");
        result.setTargetNamespace("http://com.pos.stateless/calculator");
        result.setSchema((calculatorSchema));

        return result;
    }

    @Bean
    public XsdSchema calculatorSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Calculator.xsd"));
    }

    @Bean(name = "data")
    public DefaultWsdl11Definition defaultWsdl11DefinitionData(XsdSchema dataSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("DataPort");
        result.setLocationUri("/data");
        result.setTargetNamespace("http://com.pos.stateless/data");
        result.setSchema((dataSchema));

        return result;
    }

    @Bean
    public XsdSchema dataSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Data.xsd"));
    }
}
