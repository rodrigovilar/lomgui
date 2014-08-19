package com.nanuvem.lom.lomgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.nanuvem.lom.lomgui.resources.ClassResource;
import com.nanuvem.lom.lomgui.resources.Clazz;
import com.nanuvem.lom.lomgui.resources.Widget;
import com.nanuvem.lom.lomgui.resources.WidgetResource;

public class RootWidgetTest {

	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;

	private static ClassResource clazzResource;
	private static Clazz clazz;

	private static WidgetResource widgetResource;
	private static Widget widgetRootUl;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		clazzResource = new ClassResource();
		widgetResource = new WidgetResource();

		widgetRootUl = new Widget("UlRootWidget", "UlRootWidget");
		widgetResource.post(widgetRootUl);

		clazz = new Clazz((long) 0);
		clazz.setName("Cliente");
		clazzResource.post(clazz);
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
		clazzResource.delete(clazz.getId().toString());
		// widgetResource.delete(widgetRootUl.getName());
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
	public void scenarioChangeToTableRootWidget() throws Exception {
		final String idClasses = "classes";

		setRootWidget("TableRootWidget");

		driver.get("http://localhost:8080/lomgui/");
		WebElement tableElement = ElementHelper.waitAndFindElementById(driver,
				idClasses, DEFAULT_TIMEOUT);

		assertNotNull("Table Element not found", tableElement);
		assertEquals("tr", tableElement.getTagName());
	}

	@Test
	public void scenarioChangeToUlRootWidget() throws Exception {
		final String idClasses = "classes";

		setRootWidget("UlRootWidget");

		driver.get("http://localhost:8080/lomgui/");
		WebElement ulElement = ElementHelper.waitAndFindElementById(driver,
				idClasses, DEFAULT_TIMEOUT);

		assertNotNull("Ul Element not found", ulElement);
		assertEquals("ul", ulElement.getTagName());
	}

	private void setRootWidget(String widgetName) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://localhost:8080/lomgui/api/widget/root");
		JSONObject json = new JSONObject();
		try {
			json.put("widget", widgetName);
			post.setEntity(new StringEntity(json.toString()));
			post.setHeader("Content-type", "application/json");
			client.execute(post);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}