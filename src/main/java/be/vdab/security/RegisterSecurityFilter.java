package be.vdab.security;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class RegisterSecurityFilter extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		CharacterEncodingFilter utf8filter = new CharacterEncodingFilter();
		utf8filter.setEncoding("UTF-8");
		insertFilters(servletContext, utf8filter);
	}

}
