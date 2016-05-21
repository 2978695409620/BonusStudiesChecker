package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppConfiguration;
import scraper.WLUBonusChecker;

public class AppMain {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		
		WLUBonusChecker checker = context.getBean(WLUBonusChecker.class);
		checker.checkForStudies();
		
		context.close();
	}
}
