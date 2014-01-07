package util;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ApplicationContextUtils {
	private static ApplicationContext context;
	
	public static ApplicationContext getApplicationContext(){
		if(context == null){
			context = new ClassPathXmlApplicationContext(new String[]{"../applicationContext.xml"});
		}
		return context;
	}
	
	public static ApplicationContext getApplicationContext(ServletContext servletContext){
		if(context == null){
			context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		}
		return context;
	}
	
	public static ApplicationContext getApplicationContext(FacesContext facesContext){
		if(context == null){
			context = WebApplicationContextUtils
					.getWebApplicationContext((ServletContext) facesContext.getExternalContext().getContext());
		}
		return context;
	}
}
