package cei.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan( basePackages = { "cei.code", "cei.file", "cei.web" } )
public class WebConfiguration extends WebMvcConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger("--- cei web ---");
	
	@Value ( "${jsp.root:/WEB-INF/jsp/}" )
	String jspRoot;
	
	@Value ( "${freemarker.root:freemarker/}" )
	String freemarkerTemplates;

	@Bean
	public ViewResolver viewResolver() {
		
		log.info( "JSP path: {}", jspRoot );
		
		InternalResourceViewResolver view = new InternalResourceViewResolver();
		view.setPrefix( jspRoot );
		view.setSuffix(".jsp");
		view.setOrder(Integer.MAX_VALUE);
		return view;
	}

	@Bean
	public FreeMarkerConfigurer freemarkerConfig() {
		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		config.setTemplateLoaderPath( "classpath:" + freemarkerTemplates );

		log.info( "Freemarker template path: {}", freemarkerTemplates );

		//22. Sep. 2012 hellozkyz
		//Jar 파일안에 Freemarker Template 를 호출 할때
		config.setPreferFileSystemAccess(false);

		return config;
	}

	@Bean
	public ViewResolver viewResolverByFreemarker() {
		FreeMarkerViewResolver view = new FreeMarkerViewResolver();
		view.setCache( true );
		view.setContentType( "text/html; charset=utf-8" );
		view.setSuffix( ".ftl" );
		view.setOrder( 1 );
		return view;
	}

	public void addViewControllers(ViewControllerRegistry view) {
		view.addViewController("/").setViewName("index");
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info( "javascript directory /js/ registration!" );
		registry.addResourceHandler( "/js/**" ).addResourceLocations("/js/");
		
		log.info( "CSS directory /css/ registration!" );
		registry.addResourceHandler( "/css/**" ).addResourceLocations("/css/");

		log.info( "Image directory /img/ registration!" );
		registry.addResourceHandler( "/img/**" ).addResourceLocations("/img/");

		log.info( "/img/favicon.ico registration!" );
		registry.addResourceHandler( "/favicon.ico" ).addResourceLocations("/img/favicon.ico");
	}
}