package com.nanuvem.lom.lomgui.business;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

public class Clazz {

	private Long id;
	private Integer version;
	private String name;
	private String namespace;

	public Clazz() {
		super();
	}
	
	public Clazz(Long id) {
		super();
		this.id = id;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public String getFullName() {
		if (namespace != null && !namespace.isEmpty()) {
			return getNamespace() + "." + getName();
		}
		return getName();
	}

	public ObjectNode getJson() {
		ObjectNode classNode = JsonNodeFactory.instance.objectNode();
		classNode.put("id", getId());
		classNode.put("name", getName());
		classNode.put("namespace", "");
		classNode.put("fullName", getFullName());
		return classNode;
	}
	
	public void setValuesFromJson(JsonNode clazzJSON) {
		if (clazzJSON.has("id")) {
			this.setId(clazzJSON.get("id").getLongValue());
		}
		if (clazzJSON.has("version")) {
			this.setVersion(clazzJSON.get("version").getIntValue());
		}
		if (clazzJSON.has("name")) {
			this.setName(clazzJSON.get("name").getTextValue());
		}
		if (clazzJSON.has("namespace")) {
			this.setNamespace(clazzJSON.get("namespace").getTextValue());
		}
	}

	public static Clazz clazzFromJson(JsonNode clazzJSON) {
		Clazz clazz = new Clazz();
		clazz.setValuesFromJson(clazzJSON);
		return clazz;
	}

}
