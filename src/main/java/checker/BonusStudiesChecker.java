package checker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mail.Mailer;
import persistence.model.Study;
import persistence.repository.StudiesRepository;
import scraper.WLUBonusSiteScraper;

@Transactional
@Component
public class BonusStudiesChecker {

	@Autowired
	private StudiesRepository repository;
	
	@Autowired
	private WLUBonusSiteScraper scraper;
	
	@Autowired
	private Mailer mailer;
	
	private static final Logger log = LoggerFactory.getLogger(BonusStudiesChecker.class);
	
	//Checks for studies every 10 minutes
	@Scheduled(fixedDelay = 300000)
	public void checkForStudies() {
		ArrayList<String> studies = scraper.findStudies();
		
		for (String studyName: studies) {
			if (repository.countByName(studyName) == 0) {
				Study study = new Study();
				study.setName(studyName);
				
				log.info("Saving new study '{}' to database", study);
				repository.save(study);
				
				log.info("Notifying mail recipients");
				mailer.notifyRecipients(studyName);
			}
		}
	}
	
}
