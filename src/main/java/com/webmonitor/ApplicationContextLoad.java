package com.webmonitor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/* Essa é uma classe auxiliar para carregar todo o contexto da applicação, 
 * util quando a injeção não pode ser instanciada diretamente numa classe*/
@Component
public class ApplicationContextLoad implements ApplicationContextAware {

	@Autowired
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
