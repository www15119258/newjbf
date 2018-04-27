package com.cangzhitao.jbf.core.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import com.cangzhitao.jbf.core.util.PropertiesUtil;

public class JbfApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		ConfigurableEnvironment env = event.getEnvironment();
		PropertiesPropertySource proerty = null;
		proerty = new PropertiesPropertySource("core", PropertiesUtil.loadProperties(env.getActiveProfiles(), "core"));
		if(proerty!=null) {
			env.getPropertySources().addLast(proerty);
		}
		proerty = new PropertiesPropertySource("filemanage", PropertiesUtil.loadProperties(env.getActiveProfiles(), "filemanage"));
		if(proerty!=null) {
			env.getPropertySources().addLast(proerty);
		}
		/*proerty = new PropertiesPropertySource("security", PropertiesUtil.loadProperties(env.getActiveProfiles(), "security"));
		if(proerty!=null) {
			env.getPropertySources().addLast(proerty);
		}*/
	}

}
