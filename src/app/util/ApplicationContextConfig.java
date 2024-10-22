package app.util;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class ApplicationContextConfig {

   
   
   @Bean
   public MessageSource getMessageResource()  {
       ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();

messageResource.setBasename("application"); 
       messageResource.setDefaultEncoding("UTF-8");
       return messageResource;
   }
   
   @Bean(name = "localeResolver")
   public LocaleResolver getLocaleResolver()  {
       CookieLocaleResolver resolver= new CookieLocaleResolver();
       resolver.setCookieDomain("myAppLocaleCookie");
       // 60 minutes
    
       resolver.setCookieMaxAge(60*60);
       return resolver;
   }
   

}
