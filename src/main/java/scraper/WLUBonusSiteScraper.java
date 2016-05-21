package scraper;

import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WLUBonusSiteScraper {

	@Value("${userID}")
	private String userID;
	
	@Value("${password}")
	private String password;
	
	@Value("${site.url}")
	private String siteUrl;
	
	private static final Logger log = LoggerFactory.getLogger(WLUBonusSiteScraper.class);
	
	public ArrayList<String> findStudies() {
		
		ArrayList<String> results = new ArrayList<String>();
		
		//Site urls and form data
		String loginPageUrl = siteUrl + "/Default.aspx?ReturnUrl=%2f";
		String studiesUrl = siteUrl + "/all_exp_participant.aspx";
		
		String viewStateKey = "__VIEWSTATE";
		String viewStateValue = "";
		
		String viewStateGeneratorKey = "__VIEWSTATEGENERATOR";
		String viewStateGeneratorValue = "";
		
		String eventValidationKey = "__EVENTVALIDATION";
		String eventValidationValue = "/wEdAAXgbYJg97p18OKhGOsqqg4rUIlPJ3shF6ZfHx5cHAdswX1Gsa4Qp9IFMNZyT1m/ORlOGPoKvJSxXl507+PWyULdk0IaRa81gSyF/t2E7n3iJQQ6kzdIXOQxbd+RTCSkYGCFK4jpQmHNMxPymazrnBSo";
		
		String userIdKey = "ctl00$ContentPlaceHolder1$userid";
		
		String passwordKey = "ctl00$ContentPlaceHolder1$pw";
		
		String defaultAuthButtonKey = "ctl00$ContentPlaceHolder1$default_auth_button";
		String defaultAuthButtonValue = "Log In";
		
		try {
			//Get login page and grabs view state
			log.info("Connecting to {}", siteUrl);
			Connection.Response res = Jsoup.connect(siteUrl)
					.method(Method.GET)
					.execute();
			
			Document mainPageDoc = Jsoup.parse(res.body());
			viewStateValue = mainPageDoc.getElementById(viewStateKey).val();
			viewStateGeneratorValue = mainPageDoc.getElementById(viewStateGeneratorKey).val();
			
			//Logs into the bonus studies site to generate cookie
			log.info("Logging into website: {}", loginPageUrl);
			Connection.Response loginRes = Jsoup.connect(loginPageUrl)
					.data(viewStateKey, viewStateValue, 
							viewStateGeneratorKey, viewStateGeneratorValue, 
							eventValidationKey, eventValidationValue, 
							userIdKey, userID, 
							passwordKey, password, 
							defaultAuthButtonKey, defaultAuthButtonValue)
					.cookies(res.cookies())
					.method(Method.POST)
					.execute();
			
			//Get studies list page and parse available studies
			log.info("Getting page: {}", studiesUrl);
			Connection.Response studiesRes = Jsoup.connect(studiesUrl)
					.cookies(res.cookies())
					.cookies(loginRes.cookies())
					.method(Method.GET)
					.execute();
			log.debug("Result from studies page: {}", studiesRes.body());
			
			Document studiesDoc = Jsoup.parse(studiesRes.body());
			
			Elements studies = studiesDoc.select("a[id$=HyperlinkStudentStudyInfo]");
			
			for (Element e: studies) {
				results.add(e.text());
			}
			
			log.info("Studies found: {}", results);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return results;
	}
}
