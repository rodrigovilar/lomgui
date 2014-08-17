package com.nanuvem.lom.lomgui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.nanuvem.lom.lomgui.resources.Widget;

@Path("/widget")
public class WidgetService {

	@Context
	private HttpServletRequest servletRequest;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getWidgets() {
		ArrayNode noWidgets = JsonNodeFactory.instance.arrayNode();

		List<Widget> allWidgets = WidgetStoreFacade.getInstance().getAllWidgets();
		for (Widget widget : allWidgets) {
			noWidgets.add(widget.getJson());
		}

		return noWidgets.toString();
	}
	
	@POST
	public Response addWidget(String json){
		try {
			ObjectNode widgetJson = (ObjectNode) jsonNodeFromString(json);
			WidgetStoreFacade.getInstance().addWidget(Widget.widgetFromJson(widgetJson));
			return Response.created(null).build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
	}
	
	@GET
	@Produces("text/plain; charset=utf-8")
	@Path("/root")
	public Response getRootWidget() {
		
		String widgetFilename = WidgetStoreFacade.getInstance().getWidgetFromTarget("root").getFilename();
		String result = FileSystemUtil.getWidgetScript(servletRequest,
				widgetFilename);
		return Response.ok(result).build();
	}

	@POST
	@Path("/root")
	public Response setRootWidget(String json, @Context UriInfo uriInfo) {
		try {
			JsonNode jsonNode = jsonNodeFromString(json);
			Widget widget = WidgetStoreFacade.getInstance().getWidgetFromName(jsonNode.get("widget").getTextValue());
			saveWidgetByPath(uriInfo.getPath(), widget);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}
		
	}
	
	private void saveWidgetByPath(String path, Widget widget){
		String[] pathComponents = path.split("/");
		if(pathComponents.length < 2){
			return;
		}

		if(pathComponents[1].equals("root")){
			WidgetStoreFacade.getInstance().setWidgetToTarget("root", widget);
		}
	}
	
	private JsonNode jsonNodeFromString(String json) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonFactory factory = objectMapper.getJsonFactory();
		JsonNode jsonNode = (JsonNode) objectMapper.readTree(factory
				.createJsonParser(json));
		return jsonNode;
	}

}