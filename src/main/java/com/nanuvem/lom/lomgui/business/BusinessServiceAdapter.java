package com.nanuvem.lom.lomgui.business;

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

import com.nanuvem.lom.lomgui.resources.Clazz;

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
			LomBusinessFacade.getInstance().addClass(Clazz.clazzFromJson(clazzJson));
			return Response.created(null).build();
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

	
	private JsonNode jsonNodeFromString(String json) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonFactory factory = objectMapper.getJsonFactory();
		JsonNode jsonNode = (JsonNode) objectMapper.readTree(factory
				.createJsonParser(json));
		return jsonNode;
	}
	
}
