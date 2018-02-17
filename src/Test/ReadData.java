package Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ReadData{
	
	public FileInputStream fis = null;
	public String path;
	
	public void tc() throws IOException, InterruptedException
	{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\lib\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
	
		driver.get("https://www.facebook.com/");
	
		ArrayList<String> userName = readExcelData(0);
		ArrayList<String> password = readExcelData(1);
		
		for(int i=0;i<userName.size();i++)
		{
		driver.findElement(By.id("email")).sendKeys(userName.get(i));
		driver.findElement(By.id("pass")).sendKeys(password.get(i));
		
		driver.findElement(By.id("loginbutton")).click();
		driver.manage().window().maximize();
		Thread.sleep(6000);
		
		driver.findElement(By.tagName("textarea")).sendKeys("Hello World");
		Thread.sleep(6000);
		driver.findElement(By.xpath("//span[text()='Post']")).click();
		Thread.sleep(6000);
		
		driver.findElement(By.id("userNavigationLabel")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[text()='Log Out']")).click();
		} 
	}
	
	public ArrayList<String> readExcelData(int colNo) throws IOException
	{
		path = System.getProperty("user.dir")+"\\TestData\\credentials.xlsx";
		fis = new FileInputStream(path);

		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheet("credentials");
		
		Iterator<Row> rowIt = sh.iterator();
		rowIt.next();
		
		ArrayList<String> list = new ArrayList<String>();
		while(rowIt.hasNext())
		{
		
		list.add(rowIt.next().getCell(colNo).getStringCellValue());
		
		}
		//System.out.println("List:::::" +list);
		
		return list;
	}
		
	public static void main(String[] args) throws IOException, InterruptedException
	{
		
		ReadData data = new ReadData();
		data.tc();
	}	
}

