package com.pos.JWT.config;

import com.pos.JWT.controller.LoginController;
import com.pos.JWT.controller.RegisterController;
import com.pos.JWT.controller.TokenController;
import com.pos.JWT.controller.UserController;
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

        return new ServletRegistrationBean(result, "/login", "/register", "/Token", "/DeleteUser", "/ChangePassword",
                "/ChangeRole");
    }


    // LOGIN
    @Bean(name = "login")
    public DefaultWsdl11Definition defaultWsdl11DefinitionLogin(XsdSchema loginSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("LoginPort");
        result.setLocationUri("/login");
        result.setTargetNamespace(LoginController.NAMESPACE_URI);
        result.setSchema(loginSchema);

        return result;
    }

    @Bean
    public XsdSchema loginSchema() {
        return new SimpleXsdSchema(new ClassPathResource("XSDs/Login.xsd"));
    }


    // REGISTER
    @Bean(name = "register")
    public DefaultWsdl11Definition defaultWsdl11DefinitionRegister(XsdSchema registerSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("RegisterPort");
        result.setLocationUri("/register");
        result.setTargetNamespace(RegisterController.NAMESPACE_URI);
        result.setSchema(registerSchema);

        return result;
    }

    @Bean
    public XsdSchema registerSchema() {
        return new SimpleXsdSchema(new ClassPathResource("XSDs/Register.xsd"));
    }


    // REGISTER
    @Bean(name = "register")
    public DefaultWsdl11Definition defaultWsdl11DefinitionValidateToken(XsdSchema validateTokenSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("ValidateTokenPort");
        result.setLocationUri("/validateToken");
        result.setTargetNamespace(TokenController.NAMESPACE_URI);
        result.setSchema(validateTokenSchema);

        return result;
    }

    @Bean
    public XsdSchema validateTokenSchema() {
        return new SimpleXsdSchema(new ClassPathResource("XSDs/Token.xsd"));
    }


    // DELETE USER
    @Bean(name = "DeleteUser")
    public DefaultWsdl11Definition defaultWsdl11DefinitionDeleteUser(XsdSchema deleteUserSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("DeleteUserPort");
        result.setLocationUri("/DeleteUser");
        result.setTargetNamespace(UserController.DELETE_NAMESPACE_URI);
        result.setSchema(deleteUserSchema);

        return result;
    }

    @Bean
    public XsdSchema deleteUserSchema() {
        return new SimpleXsdSchema(new ClassPathResource("XSDs/User/Delete.xsd"));
    }


    // Change Role
    @Bean(name = "ChangeRole")
    public DefaultWsdl11Definition defaultWsdl11DefinitionChangeRole(XsdSchema changeRoleSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("ChangeRolePort");
        result.setLocationUri("/ChangeRole");
        result.setTargetNamespace(UserController.ROLE_NAMESPACE_URI);
        result.setSchema(changeRoleSchema);

        return result;
    }

    @Bean
    public XsdSchema changeRoleSchema() {
        return new SimpleXsdSchema(new ClassPathResource("XSDs/User/ChangeRole.xsd"));
    }


    // Change Password
    @Bean(name = "ChangePassword")
    public DefaultWsdl11Definition defaultWsdl11DefinitionChangePassword(XsdSchema changePasswordSchema) {
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("ChangePasswordPort");
        result.setLocationUri("/ChangePassword");
        result.setTargetNamespace(UserController.PASSWORD_NAMESPACE_URI);
        result.setSchema(changePasswordSchema);

        return result;
    }

    @Bean
    public XsdSchema changePasswordSchema() {
        return new SimpleXsdSchema(new ClassPathResource("XSDs/User/ChangePassword.xsd"));
    }
}