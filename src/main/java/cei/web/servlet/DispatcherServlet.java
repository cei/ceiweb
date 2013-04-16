package cei.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@SuppressWarnings("serial")
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	
	public DispatcherServlet() {
		super();
		this.setContextClass(AnnotationConfigWebApplicationContext.class);
	}

	public String getContextConfigLocation() {
		String contextConfigLocation = super.getContextConfigLocation();

		log.info("\n\n Additional CEILib ContextConfigLocation : cei.spring.config, " + contextConfigLocation + "\n\n");

		return "cei.spring.config, " + contextConfigLocation;
	}
}
