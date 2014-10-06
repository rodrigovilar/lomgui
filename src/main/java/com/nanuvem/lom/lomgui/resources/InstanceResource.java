package com.nanuvem.lom.lomgui.resources;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.nanuvem.restest.TypedResource;

public class InstanceResource extends TypedResource<Instance> {
	
	private static final String INSTANCES = "http://localhost:8080/lomgui/api/data/instance";


	public InstanceResource() {
		super(INSTANCES);
	}

	@Override
	protected String toJson(Instance instance) {
		ObjectNode instanceNode = instance.getJson();
		return instanceNode.toString();
	}

	@Override
	protected List<Instance> toList(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			ArrayNode instancesJSON = (ArrayNode) objectMapper.readTree(factory
					.createJsonParser(json));

			List<Instance> instances = new ArrayList<Instance>();
			for (JsonNode instanceJSON : instancesJSON) {
				Instance instance = Instance.instanceFromJson(instanceJSON);
				instances.add(instance);
			}
			return instances;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Instance toObject(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();
			JsonNode instanceJSON = (ArrayNode) objectMapper.readTree(factory
					.createJsonParser(json));

			return Instance.instanceFromJson(instanceJSON);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
