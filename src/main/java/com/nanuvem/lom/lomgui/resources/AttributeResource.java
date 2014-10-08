package com.nanuvem.lom.lomgui.resources;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.nanuvem.restest.TypedResource;

public class AttributeResource extends TypedResource<Attribute>  {

	private static final String ATTRIBUTES = "http://localhost:8080/lomgui/api/data/attribute";


	public AttributeResource() {
		super(ATTRIBUTES);
	}

	@Override
	protected String toJson(Attribute attribute) {
		ObjectNode attributeNode = attribute.getJson();
		return attributeNode.toString();
	}

	@Override
	protected List<Attribute> toList(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			ArrayNode attributesJSON = (ArrayNode) objectMapper.readTree(factory
					.createJsonParser(json));

			List<Attribute> attributes = new ArrayList<Attribute>();
			for (JsonNode attributeJSON : attributesJSON) {
				Attribute attribute = Attribute.attributeFromJson(attributeJSON);
				attributes.add(attribute);
			}
			return attributes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Attribute toObject(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			JsonNode attributeJSON = (ArrayNode) objectMapper.readTree(factory
					.createJsonParser(json));

			return Attribute.attributeFromJson(attributeJSON);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
