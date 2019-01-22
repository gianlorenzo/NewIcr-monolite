package it.uniroma3.icr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
//import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;


@SpringBootApplication
public class IcrApplication extends SpringBootServletInitializer {

    @Value("${server.context-parameters.pathImage}")
    private String pathImage;

    @Value("${server.context-parameters.pathSample}")
    private String pathSample;

    @Value("${server.context-parameters.pathNegativeSample}")
    private String pathNegativeSample;

    @Value("${server.context-parameters.jsPath}")
    private String jsPath;


    public static void main(String[] args) throws Exception {
        SpringApplication.run(IcrApplication.class, args);
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }


    @Bean
    public ServletContextInitializer contextInitializer() {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext)
                    throws ServletException {
                servletContext.setInitParameter("pathImage", pathImage);
                servletContext.setInitParameter("pathSample", pathSample);
                servletContext.setInitParameter("pathNegativeSample", pathNegativeSample);
                servletContext.setInitParameter("jsPath",jsPath);

            }
        };
    }



}
		
	
