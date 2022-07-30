package E2EFunction;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebPage {
	private Engine engineObj ;
	private String Url;
	
	public WebPage(Engine engineO ) throws IOException {
		engineObj = engineO;
		
	}
	public void Open(String url) {
		this.Url = url;
		engineObj.driver.get(url);
	}
	public  List<WebElement> find(String obid) {
		return engineObj.driver.findElements(engineObj.getByFromObjRepo(obid));
		
	}
}
