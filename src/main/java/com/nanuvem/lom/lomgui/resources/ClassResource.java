package com.nanuvem.lom.lomgui.resources;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.nanuvem.restest.TypedResource;

public class ClassResource extends TypedResource<Clazz> {

	private static final String CLASSES = "http://localhost:8080/lomgui/api/data/class";


	public ClassResource() {
		super(CLASSES);
	}

	@Override
	protected String toJson(Clazz clazz) {
		ObjectNode clazzNode = clazz.getJson();
		return clazzNode.toString();
	}

	@Override
	protected List<Clazz> toList(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			ArrayNode classesJSON = (ArrayNode) objectMapper.readTree(factory
					.createJsonParser(json));

			List<Clazz> classes = new ArrayList<Clazz>();
			for (JsonNode clazzJSON : classesJSON) {
				Clazz clazz = Clazz.clazzFromJson(clazzJSON);
				classes.add(clazz);
			}
			return classes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Clazz toObject(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			JsonNode clazzJSON = (ArrayNode) objectMapper.readTree(factory
					.createJsonParser(json));

			return Clazz.clazzFromJson(clazzJSON);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
