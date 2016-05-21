package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import checker.BonusStudiesChecker;
import config.AppConfiguration;

public class AppMain {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		
		BonusStudiesChecker checker = context.getBean(BonusStudiesChecker.class);
		checker.checkForStudies();
		
		context.close();
	}
}
