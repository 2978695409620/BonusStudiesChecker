package checker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	
	private static final Logger log = LoggerFactory.getLogger(BonusStudiesChecker.class);
	
	public void checkForStudies() {
		ArrayList<String> studies = scraper.findStudies();
		
		for (String s: studies) {
			if (repository.countByName(s) == 0) {
				Study study = new Study();
				study.setName(s);
				log.info("Saving new study '{}' to database", study);
				repository.save(study);
			}
		}
	}
	
}
