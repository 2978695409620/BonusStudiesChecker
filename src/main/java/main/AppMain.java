package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppConfiguration;

public class AppMain {
	
	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	}
}
