package com.nanuvem.lom.lomgui.resources;

import org.codehaus.jackson.JsonNode;

public class Attribute extends JSONBean {
	
	private Long id;
	private Long classID;
	private String name;
	private String type;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClassID() {
		return classID;
	}
	public void setClassID(Long classID) {
		this.classID = classID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public static Attribute attributeFromJson(JsonNode attributeJSON) {
		Attribute attribute = new Attribute();
		attribute.setValuesFromJson(attributeJSON);
		return attribute;
	}
	
}
