package com.nanuvem.lom.lomgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.nanuvem.lom.lomgui.resources.Attribute;
import com.nanuvem.lom.lomgui.resources.AttributeResource;
import com.nanuvem.lom.lomgui.resources.ClassResource;
import com.nanuvem.lom.lomgui.resources.Clazz;

public class ClassWidgetTest {
	
	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;
	
	private static ClassResource clazzResource;
	private static Clazz clazz;
	
	private static AttributeResource attributeResource;
	private static Attribute nameAttribute;
	
	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		
		clazzResource = new ClassResource();
		clazz = new Clazz((long) 1);
		clazz.setName("Cliente");
		clazzResource.post(clazz);
		
		attributeResource = new AttributeResource();
		nameAttribute = new Attribute();
		nameAttribute.setId((long) 0);
		nameAttribute.setName("name");
		nameAttribute.setClassID(clazz.getId());
		attributeResource.post(nameAttribute);
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
		attributeResource.delete(nameAttribute.getId().toString());
		clazzResource.delete(clazz.getId().toString());
	}
	
	@Test
	public void scenarioDefaultClassWidget() {
		driver.get("http://localhost:8080/lomgui/");

		String idClass = "class_" + clazz.getName();
		WebElement clientLi = ElementHelper.waitAndFindElementById(driver,
				idClass, DEFAULT_TIMEOUT);
		clientLi.click();
		
		String idAttributeName = "attribute_" + nameAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttributeName, DEFAULT_TIMEOUT);
		
		assertNotNull("Name Attribute not found", clientLi);
		assertEquals(nameAttribute.getName(), attributeElement.getText());
		
	}

}
