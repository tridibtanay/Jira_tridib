import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class PANInformed {

	@Test
	public void main(String[] args) {

// Set path to chromedriver if not in system PATH
		System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		try {
// Step 1: Launch the URL
			driver.get("https://www.wipo.int/pat-informed/en");

// Step 2: Validate the header
			WebElement header = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'PAT-INFORMED DATABASE')]")));
			assertTrue(header.isDisplayed(), "Header is not displayed");

// Step 3: Click search button without entering data
			WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
			searchButton.click();

// Step 4: Validate the first participant and print it
			WebElement firstParticipant = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("(//div[@class='inn_result']//td[contains(@class,'participant')])[1]")));
			String participantName = firstParticipant.getText();
			System.out.println("Participant Name: " + participantName);
			assertTrue(!participantName.isEmpty(), "Participant name is empty");

// Step 5: Click the first patent value under "PATENTS"
			WebElement firstPatent = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("(//div[@class='inn_result']//td[contains(@class,'patents')])[1]//button")));
			firstPatent.click();

// Step 6: Count number of tables (info + text fallback)
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'inn_panel')]")));

			List<WebElement> tables = driver.findElements(By.xpath("//table"));
			System.out.println("Number of tables found: " + tables.size());

// Step 7: Validate dates
			boolean datesValidated = false;

			for (WebElement table : tables) {
				List<WebElement> dateElements = table
						.findElements(By.xpath(".//td[contains(text(),'date') or contains(text(),'Date')]"));
				if (dateElements.size() >= 2) {
					List<WebElement> values = table.findElements(By
							.xpath(".//td[preceding-sibling::td[contains(text(),'date') or contains(text(),'Date')]]"));
					if (values.size() >= 2) {
						String date1 = values.get(0).getText();
						String date2 = values.get(1).getText();
						System.out.println("Date 1: " + date1);
						System.out.println("Date 2: " + date2);

						assertTrue(date1.matches("\\d{4}-\\d{2}-\\d{2}") || date1.matches("\\d{2}/\\d{2}/\\d{4}"),
								"Invalid format for date 1");
						assertTrue(date2.matches("\\d{4}-\\d{2}-\\d{2}") || date2.matches("\\d{2}/\\d{2}/\\d{4}"),
								"Invalid format for date 2");

						datesValidated = true;
						break;
					}
				}
			}

			if (!datesValidated) {
// Fallback to text section (right-hand side panel)
				WebElement textPanel = driver.findElement(By.xpath("//div[@class='inn_panel']"));
				String panelText = textPanel.getText();

				String regexDate = "\\d{4}-\\d{2}-\\d{2}";
				java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regexDate).matcher(panelText);

				int dateCount = 0;
				while (matcher.find()) {
					System.out.println("Date found in text: " + matcher.group());
					dateCount++;
				}

				assertTrue(dateCount >= 2, "Less than 2 dates found in fallback text");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}
}
