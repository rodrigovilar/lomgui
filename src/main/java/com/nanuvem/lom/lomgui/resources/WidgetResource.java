package com.nanuvem.lom.lomgui.resources;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.nanuvem.restest.TypedResource;

public class WidgetResource extends TypedResource<Widget>{

	private static final String WIDGETS = "http://localhost:8080/lomgui/api/widget";
	
	public WidgetResource() {
		super(WIDGETS);
	}

	@Override
	protected String toJson(Widget widget) {
		return widget.getJson().toString();
	}

	@Override
	protected List<Widget> toList(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			ArrayNode widgetsJSON = (ArrayNode) objectMapper.readTree(factory
					.createJsonParser(json));

			List<Widget> widgets = new ArrayList<Widget>();
			for (JsonNode widgetJSON : widgetsJSON) {
				Widget usuario = Widget.widgetFromJson(widgetJSON);
				widgets.add(usuario);
			}
			return widgets;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Widget toObject(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
