package com.nanuvem.lom.lomgui;

import java.util.HashMap;
import java.util.Map;

public class WidgetStoreFacade {

	private static WidgetStoreFacade singleton;

	public static WidgetStoreFacade getInstance(){
		if(WidgetStoreFacade.singleton == null){
			WidgetStoreFacade.singleton = new WidgetStoreFacade();
		}
		return WidgetStoreFacade.singleton;
	}

	private Map<String, String> widgetsMapping;
	
	private WidgetStoreFacade() {
		widgetsMapping = new HashMap<String, String>();
		widgetsMapping.put("root", "UlRootWidget");
	}
	
	public String getWidget(String type) {
		return widgetsMapping.get(type);
	}
	
	public void setWidget(String type, String widgetName) {
		widgetsMapping.put(type, widgetName);
	}

}