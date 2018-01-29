package app.utils;

import java.io.File;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class HttpUtils {

	private static WebDriver loggedWebDriver;
	

	public static Document getHtmlPageNoLogged(String url){
		Document doc = null;
		Connection connect = Jsoup.connect(url);
	
		try {
			doc = connect.ignoreContentType(true).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	
	public static Document getHtmlPageLogged(String url, String username, String password){
		Document doc = null;
		System.out.print(".");
		
		try {

			if (loggedWebDriver == null)
				initLoggedWebDriver(username, password);
			
			Thread.sleep(3000);
			
			loggedWebDriver.get(url);
		    
		    String pageSource = loggedWebDriver.getPageSource();
			doc = Jsoup.parse(pageSource);
			
		}
		catch (Exception e){
			loggedWebDriver = null;
			System.out.print("Errore durante il recupero della pagina");
		}
		
		return doc;
		
	}
//	
//	public static Document loginOnFantagazzetta(String username, String password) {
//		if (loggedWebDriver == null)
//			initLoggedWebDriver(username, password);
//		String pageSource = loggedWebDriver.getPageSource();
//		Document doc = Jsoup.parse(pageSource);
//		
//		return doc;
//	}
	
	

	private static WebDriver initLoggedWebDriver(String username, String password) {
		WebDriver driver = null;
		int i = 1;

		while (i <= 3){
			try {
				// 1 - Crea driver pronto per navigare
				driver = initDriver();
				
				// 2 - Esegui login su fantagazzetta
				String url = AppConstants.LOGIN_PAGE_URL;
				
				driver.get(url);
		//		champDriver.navigate().refresh();
		//		WebElement navBar   = driver.findElement(By.id("myNav"));
				
				WebElement loginBtnPage = driver.findElement(By.className("bbtnblu"));
				WebDriverWait wait = new WebDriverWait(driver,5);
			    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("iubenda-cs-close-btn")));

				WebElement cookieInfoCloseButton = driver.findElement(By.className("iubenda-cs-close-btn"));
				WebDriverWait wait2 = new WebDriverWait(driver,2);
				wait2.until(ExpectedConditions.elementToBeClickable(cookieInfoCloseButton));
				cookieInfoCloseButton.click();
			    
						 
			    WebElement id = driver.findElement(By.id("username"));
			    WebElement pass = driver.findElement(By.id("password"));
			    WebElement loginButtonModal = driver.findElement(By.id("accedi"));
		
			    
			    loginBtnPage.click();
			    wait = new WebDriverWait(driver,5);
			    wait.until(ExpectedConditions.elementToBeClickable(loginButtonModal));
			    
			    
			    id.sendKeys(username);
			    pass.sendKeys(password);
			    loginButtonModal.click();
			   
			    // 3 - Setta il Web Driver nel field della classe
			    loggedWebDriver = driver;
			    break;
			}
			catch (Exception e) {
				System.out.println("Errore durante la creazione del Driver loggato. Tentativo: " + i);
				loggedWebDriver = null;
				driver.close();
			}
			i++;
		}
	    
	    return driver;
	}


	
	private static void getLeagueName() {
		// Prende dalla sessione lo user e da DB recupera il nome della league
		
	}


//	private static WebDriver initDriver() {
//		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File("C:/driver/chromedriver.exe")).build(); 
//		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//
//		ChromeOptions options = new ChromeOptions();
////		options.addArguments("--headless", "--disable-gpu");
//		options.addArguments("--allow-file-access-from-files");
//		options.addArguments("--verbose");
//		options.addArguments("load-extension=C:\\Users\\Menesbatto\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\cfhdojbkjhnklbpkdaibdccddilifddb\\1.13.4_0");
//		capabilities.setVersion("");
//		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//		
//		
//		
//			long startTime = System.nanoTime();
//			System.out.println("1 CARICAMENTO DRIVER...");
//
//		WebDriver driver = new ChromeDriver(service, capabilities);
//		
//			long currentTime = System.nanoTime();
//			long duration = (currentTime - startTime);  //divide by 1000000 to get milliseconds.
//			System.out.println("DONE\t" + duration / 1000000);
//			System.out.println();
//			
////			WebDriverWait wait = new WebDriverWait(driver,2);
////			wait.until(ExpectedConditions.elementToBeClickable(loginButton));
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
////			WebDriverWait wait2 = new WebDriverWait(driver,20);
////			WebElement body   = driver.findElement(By.tagName("body"));
////			body.findElement(By.id("title-main"));
////			wait2.until(ExpectedConditions.textToBePresentInElement(body, "stata installata"));
//			
//
//			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "/w");
//		return driver;
//	}

	private static WebDriver initDriver() throws InterruptedException {
		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File("C:/driver/chromedriver.exe")).build(); 
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--allow-file-access-from-files");
		options.addArguments("--verbose");
		options.addArguments("load-extension=C:\\Users\\Menesbatto\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\cfhdojbkjhnklbpkdaibdccddilifddb\\1.13.5_0");
		capabilities.setVersion("");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		WebDriver driver = new ChromeDriver(service, capabilities);
		
		Thread.sleep(3000);
		
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "/w");
		
		return driver;
	}


	public static void closeDrivers(String username) {
		loggedWebDriver.close();
		loggedWebDriver = null;
		
	}

	
//	public static void shutdown(ChampEnum champ) {
//		WebDriver driver = getChampDriver(champ.getNextMatchesUrl());
//		driver.close();
//		String champName = getChampName(champ.getResultsUrl());
//		driversMap.remove(champName);
//	}
	
}
