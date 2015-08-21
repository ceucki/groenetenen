package be.vdab.web;

import javax.servlet.Filter;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import be.vdab.dao.CreateDAOBeans;
import be.vdab.datasource.CreateDateSourceBean;
import be.vdab.mail.CreateMailBeans;
import be.vdab.restservices.CreateRestControllerBeans;
import be.vdab.security.CreateSecurityFilter;
import be.vdab.services.CreateServiceBeans;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { CreateDateSourceBean.class, CreateDAOBeans.class, CreateServiceBeans.class,
				CreateMailBeans.class,CreateSecurityFilter.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {

		return new Class<?>[] { CreateControllerBeans.class, CreateRestControllerBeans.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {

		return new Filter[] { new OpenEntityManagerInViewFilter() };
	}

}
