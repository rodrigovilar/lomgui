package com.nanuvem.lom.lomgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.nanuvem.lom.lomgui.business.Clazz;
import com.nanuvem.lom.lomgui.resources.ClassResource;

public class RootWidgetTest {

	private static final int DEFAULT_TIMEOUT = 10;
	private WebDriver driver;

	@Before
	public void setUp() {
		driver = new FirefoxDriver();
	}

	@After
	public void tearDown() {
		driver.close();
	}

	@Test
	public void scenarioRootWidget() {
		Clazz clazz = new Clazz((long) 0);
		clazz.setName("Cliente");
		ClassResource clazzResource = new ClassResource();
		clazzResource.post(clazz);

		driver.get("http://localhost:8080/lomgui/");

		WebElement clientLi = ElementHelper.waitAndFindElementById(driver,
				"class_Cliente", DEFAULT_TIMEOUT);

		assertNotNull("Client Class not found", clientLi);
		assertEquals("Cliente", clientLi.getText());
	}

}