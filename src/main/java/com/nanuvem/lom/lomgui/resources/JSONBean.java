package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.NullNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * This class automatically methods to get a JSON from a Bean and contrariwise. 
 * @author Delano Oliveira
 *
 */

public class JSONBean {

	public ObjectNode getJson() {
		ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();

		Class<?> cls = this.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {

			try {

				String value = BeanUtils.getProperty(this, field.getName());

				if (field.getType() == Boolean.class) {
					jsonNode.put(field.getName(), Boolean.parseBoolean(value));
				} else if (field.getType() == Double.class) {
					jsonNode.put(field.getName(), Double.parseDouble(value));
				} else if (field.getType() == Float.class) {
					jsonNode.put(field.getName(), Float.parseFloat(value));
				} else if (field.getType() == Integer.class) {
					jsonNode.put(field.getName(), Integer.parseInt(value));
				} else if (field.getType() == Long.class) {
					jsonNode.put(field.getName(), Long.parseLong(value));
				} else if (field.getType() == Byte.class) {
					jsonNode.put(field.getName(), Byte.parseByte(value));
				} else if (field.getType() == Character.class) {
					jsonNode.put(field.getName(), value.charAt(0));
				} else if (field.getType() == Short.class) {
					jsonNode.put(field.getName(), Short.parseShort(value));
				} else {
					jsonNode.put(field.getName(), value);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				jsonNode.put(field.getName(), NullNode.getInstance());
			}
		}

		return jsonNode;
	}

	public void setValuesFromJson(JsonNode jsonNode) {
		Class<?> cls = this.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			try {

				Object value = null;

				if (jsonNode.has(field.getName())) {

					if (field.getType() == Boolean.class) {
						value = jsonNode.get(field.getName()).getBooleanValue();
					} else if (field.getType() == Double.class) {
						value = jsonNode.get(field.getName()).getDoubleValue();
					} else if (field.getType() == Float.class) {
						value = jsonNode.get(field.getName()).getNumberValue()
								.floatValue();
					} else if (field.getType() == Integer.class) {
						value = jsonNode.get(field.getName()).getIntValue();
					} else if (field.getType() == Long.class) {
						value = jsonNode.get(field.getName()).getLongValue();
					} else if (field.getType() == Byte.class) {
						value = jsonNode.get(field.getName()).getBinaryValue();
					} else if (field.getType() == Character.class) {
						value = jsonNode.get(field.getName()).getTextValue()
								.charAt(0);
					} else if (field.getType() == Short.class) {
						value = jsonNode.get(field.getName()).getNumberValue()
								.shortValue();
					} else {
						value = jsonNode.get(field.getName()).getTextValue();
					}
				}
				BeanUtils.setProperty(this, field.getName(), value);

			} catch (Exception e) {
				System.err.println(e.getMessage());
				// do nothing
			}
		}
	}
}
