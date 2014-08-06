package com.nanuvem.lom.lomgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.nanuvem.lom.lomgui.resources.ClassResource;
import com.nanuvem.lom.lomgui.resources.Clazz;

public class RootWidgetTest {

	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;
	private static ClassResource clazzResource;
	private static Clazz clazz;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		clazzResource = new ClassResource();
		clazz = new Clazz((long) 0);
		clazz.setName("Cliente");
		clazzResource.post(clazz);
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
		clazzResource.delete(clazz.getId().toString());
	}

	@Test
	public void scenarioDefaultRootWidget() {
		driver.get("http://localhost:8080/lomgui/");

		String idName = "class_" + clazz.getName();
		WebElement clientLi = ElementHelper.waitAndFindElementById(driver,
				idName, DEFAULT_TIMEOUT);

		assertNotNull("Client Class not found", clientLi);
		assertEquals(clazz.getName(), clientLi.getText());
	}

	@Test
	public void scenarioChangeToTableRootWidget() {
		final String idClasses = "classes";

		// change widget

		driver.get("http://localhost:8080/lomgui/");
		WebElement tableElement = ElementHelper.waitAndFindElementById(driver,
				idClasses, DEFAULT_TIMEOUT);

		assertNotNull("Table Element not found", tableElement);
		assertEquals("tr", tableElement.getTagName());
	}

	@Test
	public void scenarioChangeToUlRootWidget() {
		final String idClasses = "classes";

		// change widget

		driver.get("http://localhost:8080/lomgui/");
		WebElement ulElement = ElementHelper.waitAndFindElementById(driver,
				idClasses, DEFAULT_TIMEOUT);

		assertNotNull("Ul Element not found", ulElement);
		assertEquals("ul", ulElement.getTagName());
	}

}