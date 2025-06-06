package com.tenant.modules.Accounting.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.Selenium.WebClass;
import com.tenant.common.Utils.CommonMethods;
import com.tenant.modules.Accounting.pagefuncs.AccountingGeneralJournalEditFuncs;
import com.tenant.modules.Dashboard.pagefuncs.TenantDashboardFuncs;

public class GeneralJournalEdits  {

	WebClass webObj;
	WebDriver driver;
	TenantDashboardFuncs dashboard;
	AccountingGeneralJournalEditFuncs generaljournaledits;

	
	@BeforeClass(alwaysRun = true)
	public void initiateObjs() {
		webObj = new WebClass();
		driver = webObj.getDriver();
		dashboard = new TenantDashboardFuncs(driver);
//		CommonMethods.storeVariable.put("RELATIONSHIP_NAME", "ZAuto Relationship 010224120429"); 
	}
	
	@Test(groups = { "Regression" })
	public void General_Journal_edits_Search_for_data_and_edit() throws InterruptedException {
		try {
			if (driver == null)
				initiateObjs();

			driver.navigate().refresh();
//			ExtentTestNGITestListener.createNode("User should be able to search and edit values : General Journal Edits  ");

//			ExtentTestNGITestListener.createDescryption("Navigate to Accounting Menu");
			dashboard.clickOnAccountingMenu();
			dashboard.navigateToAccountingGeneralJournalEdits();
			generaljournaledits = new AccountingGeneralJournalEditFuncs(driver);
			generaljournaledits.validateGeneralJournalEditsHeader();
			generaljournaledits.validateGeneralJournalEdits(CommonMethods.storeVariable.get("RELATION_NAME"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Regression" })
	public void GeneralJournalEdits_Search_and_Edit() {
		try {
			if (driver == null)
				initiateObjs();

			driver.navigate().refresh();
//			ExtentTestNGITestListener.createNode("User should be able to search and edit values : General Journal Edits  ");

//			ExtentTestNGITestListener.createDescryption("Navigate to Accounting Menu");
			dashboard.clickOnAccountingMenu();
			dashboard.navigateToAccountingGeneralJournalEdits();
			generaljournaledits = new AccountingGeneralJournalEditFuncs(driver);
			generaljournaledits.validateGeneralJournalEditsHeader();
			generaljournaledits.Enter_RequireData();
			generaljournaledits.Validate_GeneralJournalEdits_Data();
			generaljournaledits.Edit_GeneralJournalEdits_Data();
			generaljournaledits.Validate_Edited_GeneralJournalEdits_Data();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups = { "Regression" })
	public void General_Journal_Edits_edited_JE_Validation()  {
		try {
			if (driver == null)
				initiateObjs();

			driver.navigate().refresh();
			dashboard.clickOnAccountingMenu();
			dashboard.navigateToAccountingGeneralJournalEdits();
			generaljournaledits = new AccountingGeneralJournalEditFuncs(driver);
			generaljournaledits.validateGeneralJournalEditsHeader();
			generaljournaledits.enter_Relationship(CommonMethods.storeVariable.get("RELATIONSHIP_NAME"));
			generaljournaledits.enter_AsOfStartDate(CommonMethods.storeVariable.get("AsOFDate"));
			generaljournaledits.enter_AsOfEndDate(CommonMethods.storeVariable.get("AsOFDate"));
			generaljournaledits.Search_Button();
			generaljournaledits.filterEntityDescription(CommonMethods.storeVariable.get("EntityDescription"));
			generaljournaledits.validateGLAccount("Receivable");
			generaljournaledits.Edit_GLAccount("Depreciation Expense");	
			generaljournaledits.validateGLAccountAfterEdit("Depreciation Expense");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

