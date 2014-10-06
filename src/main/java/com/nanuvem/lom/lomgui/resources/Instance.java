package com.nanuvem.lom.lomgui.resources;

import org.codehaus.jackson.JsonNode;

/**
 * This class represent a entity Instance
 * @author Delano Oliveira
 *
 */

public class Instance extends JSONBean {
	
	private Long id;
	private Integer version;
	private Long classID;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Long getClassID() {
		return classID;
	}
	public void setClassID(Long classID) {
		this.classID = classID;
	}
	public static Instance instanceFromJson(JsonNode instanceJSON) {
		Instance instance = new Instance();
		instance.setValuesFromJson(instanceJSON);
		return instance;
	}
	
}
