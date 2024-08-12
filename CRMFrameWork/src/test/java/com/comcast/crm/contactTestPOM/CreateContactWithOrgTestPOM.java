package com.comcast.crm.contactTestPOM;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.comcast.crm.generifilecutility.ExcelUtility;
import com.comcast.crm.generifilecutility.FileUtility;
import com.comcast.crm.objectrepoUtility.ContactInfoPage;
import com.comcast.crm.objectrepoUtility.ContactPage;
import com.comcast.crm.objectrepoUtility.CreateNewContactPage;
import com.comcast.crm.objectrepoUtility.CreateNewOrganizationPage;
import com.comcast.crm.objectrepoUtility.HomePage;
import com.comcast.crm.objectrepoUtility.LoginPage;
import com.comcast.crm.objectrepoUtility.OrgLookUpIcon;
import com.comcast.crm.objectrepoUtility.OrganizationInfoPage;
import com.comcast.crm.objectrepoUtility.OrganizationPage;
import com.comcast.crm.webdriverutility.JavaUtility;
import com.comcast.crm.webdriverutility.WebDriverUtility;

public class CreateContactWithOrgTestPOM 
{
	static WebDriver driver;
	public static void main(String[] args) throws Throwable {

		WebDriverUtility wLib=new WebDriverUtility();
		FileUtility fLib=new FileUtility();
		JavaUtility jLib=new JavaUtility();
		ExcelUtility eLib=new ExcelUtility();

		String URL = fLib.getDataFromPropertiesFile("url");
		String BROWSER =fLib.getDataFromPropertiesFile("browser");
		String USERNAME =fLib.getDataFromPropertiesFile("username");
		String PASSWORD =fLib.getDataFromPropertiesFile("password");
		
		String lastName=eLib.getDataFromExcel("contact", 1, 2);

        int nn=jLib.getRandomNumber();
		if(BROWSER.equals("chrome"))
		{
			driver=new ChromeDriver();
		}
		else if(BROWSER.equals("edge"))
		{
			driver=new EdgeDriver();
		}
		wLib.preCondition(driver);
		wLib.waitForPageToLoad(driver);

		driver.get(URL);
		LoginPage lp=new LoginPage(driver);
		lp.loginToApp(USERNAME, PASSWORD);
		
        HomePage hp=new HomePage(driver);
		hp.getOrganizationLink().click();
		
		OrganizationPage op=new OrganizationPage(driver);
		op.getCreateNewOrgBtn().click();
		
		CreateNewOrganizationPage cnop=new CreateNewOrganizationPage(driver);
		cnop.getOrgNameEdit().sendKeys(eLib.getDataFromExcel("org", 1, 2)+nn);
		cnop.getSaveBtn().click();
		
		OrganizationInfoPage oip=new OrganizationInfoPage(driver);
		String Heading=oip.getHeaderMsg().getText();
		if(Heading.contains(eLib.getDataFromExcel("org", 1, 2)+nn))
		{
			System.out.println("verified");
		}
		else
		{
			System.out.println("not as expected");
		}
		
		String organizationname=oip.getVerifyOrgName().getText();
	
		if(organizationname.equals(eLib.getDataFromExcel("org", 1, 2)+nn))
		{
			System.out.println("organization name verified");
		}
		else
		{
			System.out.println("name is verified");
		}

		hp.getContactLink().click();
	
		ContactPage cp=new ContactPage(driver);
		cp.getCreateNewContactBtn().click();
		
		CreateNewContactPage cncp=new CreateNewContactPage(driver);
		cncp.getLastNameTxtFld().sendKeys(lastName+nn);
		cncp.getOrgLookUpIcon().click();
		
		
		//switch to child window

		Set<String> wid=driver.getWindowHandles();
		wLib.switchNewBrowserTab( driver , "module=Account&action");
		
		
		   Set<String> set = driver.getWindowHandles();
		   wLib.switchNewBrowserTab(driver, "module=Accounts&action");
		   OrgLookUpIcon olp=new OrgLookUpIcon(driver);
		   olp.selectOrg(eLib.getDataFromExcel("Org",1,2)+nn, driver);
		   wLib.switchToTabOnTitle(driver, "module=Contacts&action");
		   cncp.getSaveBtn().click();
		   ContactInfoPage cip=new ContactInfoPage(driver);
		   
		   String heading1=cip.getVerifyLastNameTxtFld().getText();
		   if(heading1.contains(lastName+nn))
		  {
		     System.out.println("contact is created");
		  }
			else
			{
			System.out.println("contact is not created");
			}
			String lastname1=cip.getVerifyLastNameTxtFld().getText();
			if(lastname1.equals(lastName+nn ))
			{
				
				System.out.println("last name is verified");
			}
			else
			{
			System.out.println("last name is not verified");
			}
		    String orglink=oip.getVerifyOrgName().getText();
			if(orglink.equals(eLib.getDataFromExcel("Org",1,2)+nn))
			{
				System.out.println("organization verified");
			}
	     	else
			{
				System.out.println("organization not verified");
			} 
			hp.getAdminImg().click();
			hp.logOut();
			wLib.postCondition(driver);
	}
}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
