package com.nanuvem.lom.lomgui.business;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.nanuvem.lom.lomgui.resources.Attribute;
import com.nanuvem.lom.lomgui.resources.Clazz;
import com.nanuvem.lom.lomgui.resources.Instance;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

@Path("data")
public class BusinessServiceAdapter {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class")
	public String getClasses() {
		ArrayNode noClasses = JsonNodeFactory.instance.arrayNode();

		for (Clazz clazz : LomBusinessFacade.getInstance().getAllClasses()) {
			noClasses.add(clazz.getJson());
		}

		return noClasses.toString();
	}
	
	@POST
	@Path("/class")
	public Response addClass(String json) {
		try {
			ObjectNode clazzJson = (ObjectNode) jsonNodeFromString(json);
			Clazz clazz = LomBusinessFacade.getInstance().addClass(Clazz.clazzFromJson(clazzJson));
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(201);
			builder.entity(clazz.getJson().toString());
			return builder.build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}

	@DELETE
	@Path("/class")
	public Response deleteAllClasses() {
		LomBusinessFacade.getInstance().removeAllClasses();
		return Response.ok().build();
	}

	@DELETE
	@Path("/class/{id}")
	public Response deleteClass(@PathParam("id") Long id) {
		if(LomBusinessFacade.getInstance().removeClass(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class/{fullName}/attributes")
	public String getAttributesFromClass(@PathParam("fullName") String fullName) {
		Clazz clazz = LomBusinessFacade.getInstance().getClass(fullName);
		ArrayNode attributesNode = JsonNodeFactory.instance.arrayNode();
		if(clazz != null){
			List<Attribute> attributes = LomBusinessFacade.getInstance().getAttributesByClassID(clazz.getId());
			for(Attribute attribute : attributes){
				attributesNode.add(attribute.getJson());
			}
		}
		return attributesNode.toString();
	}
	
	@POST
	@Path("/class/{fullName}/attributes")
	public Response addAttributeToClass(@PathParam("fullName") String fullName, String json) {
		Clazz clazz = LomBusinessFacade.getInstance().getClass(fullName);
		if(clazz != null){
			try {
				ObjectNode attributeJson = (ObjectNode) jsonNodeFromString(json);
				Attribute attribute = Attribute.attributeFromJson(attributeJson);
				attribute.setClassID(clazz.getId());
				attribute = LomBusinessFacade.getInstance().addAttribute(attribute);
				ResponseBuilderImpl builder = new ResponseBuilderImpl();
				builder.status(201);
				builder.entity(attribute.getJson().toString());
				return builder.build();
			} catch (Exception e) {
				return Response.notAcceptable(null).build();
			}
		}
		return Response.notAcceptable(null).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/class/{fullName}/instances")
	public String getInstances(@PathParam("fullName") String fullName) {
		Clazz clazz = LomBusinessFacade.getInstance().getClass(fullName);
		ArrayNode instancesNode = JsonNodeFactory.instance.arrayNode();
		if(clazz != null){
			List<Instance> instances = LomBusinessFacade.getInstance().getInstancesByClassID(clazz.getId());
			for(Instance instance : instances){
				instancesNode.add(instance.getJson());
			}
		}
		return instancesNode.toString();
	}
	
	@POST
	@Path("/class/{fullName}/instances")
	public Response addInstanceToClass(@PathParam("fullName") String fullName, String json) {
		Clazz clazz = LomBusinessFacade.getInstance().getClass(fullName);
		if(clazz != null){
			try {
				ObjectNode instanceJson = (ObjectNode) jsonNodeFromString(json);
				Instance instance = Instance.instanceFromJson(instanceJson);
				instance.setClassID(clazz.getId());
				instance = LomBusinessFacade.getInstance().addInstance(instance);
				ResponseBuilderImpl builder = new ResponseBuilderImpl();
				builder.status(201);
				builder.entity(instance.getJson().toString());
				return builder.build();
			} catch (Exception e) {
				return Response.notAcceptable(null).build();
			}
		}
		return Response.notAcceptable(null).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/attribute")
	public String getAttributes() {
		ArrayNode noAttributes = JsonNodeFactory.instance.arrayNode();

		for (Attribute attribute : LomBusinessFacade.getInstance().getAllAttributes()) {
			noAttributes.add(attribute.getJson());
		}

		return noAttributes.toString();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/attribute")
	public Response addAttribute(String json) {
		try {
			ObjectNode attributeJson = (ObjectNode) jsonNodeFromString(json);
			Attribute attribute = LomBusinessFacade.getInstance().addAttribute(Attribute.attributeFromJson(attributeJson));
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(201);
			builder.entity(attribute.getJson().toString());
			return builder.build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/attribute/{id}")
	public Response deleteAttribute(@PathParam("id") Long id) {
		if(LomBusinessFacade.getInstance().removeAttribute(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/instance")
	public Response addInstance(String json) {
		try {
			ObjectNode instanceJson = (ObjectNode) jsonNodeFromString(json);
			Instance instance = LomBusinessFacade.getInstance().addInstance(Instance.instanceFromJson(instanceJson));
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(201);
			builder.entity(instance.getJson().toString());
			return builder.build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/attribute/{id}")
	public Response deleteInstance(@PathParam("id") Long id) {
		if(LomBusinessFacade.getInstance().removeInstance(id))
			return Response.ok().build();
		return Response.notAcceptable(null).build();
	}

	
	private JsonNode jsonNodeFromString(String json) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonFactory factory = objectMapper.getJsonFactory();
		JsonNode jsonNode = (JsonNode) objectMapper.readTree(factory
				.createJsonParser(json));
		return jsonNode;
	}
	
}
