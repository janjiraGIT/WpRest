package com.wizepass.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wizepass.controller.DataController;
import com.wizepass.elasticsearch.EsResponse;
import com.wizepass.util.Constants;
import com.wizepass.util.DataStream;

@Path("/users")
public class RestEndPoint {
	// http://localhost:8081/WpRest/users/all
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers() throws JSONException {
		final DataController controller = new DataController();		
		final  List<Map<String,Object>>  usersInMapList  = controller.getUsers();
		final JSONArray usersInJsonArray = new JSONArray(usersInMapList);		
		return usersInJsonArray.toString();		
	}
	
	// http://localhost:8081/WpRest/users/mg
//	@GET
//	@Path("/mg")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getMg() throws JSONException {
//		final DataController controller = new DataController();		
//		final  List<Map<String,Object>>  mg  = controller.getMg();
//		final JSONArray usersInJsonArray = new JSONArray(mg);		
//		return usersInJsonArray.toString();		
//	}
	
	// http://127.0.0.1:8081/WpRest/users/pesote 
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserByName(@PathParam("name") String name) throws JSONException {
	final DataController controller = new DataController();		
	final  List<Map<String,Object>>  usersInMapList  = controller.getByName(name);
	final JSONArray usersInJsonArray = new JSONArray(usersInMapList);		
	return usersInJsonArray.toString();		
		}
	
	// http://127.0.0.1:8081/WpRest/users/registrationtokens
	@GET
	@Path("/registrationtokens")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRegTokens() {
	final DataController controller = new DataController();		
	final  List<Map<String,Object>>  regInList  = controller.getRegistrationToken();
	final JSONArray regInJsonArray = new JSONArray(regInList);		
	return regInJsonArray.toString();		
		}

	@POST
    @Path("/registrationtokens")
	@Produces(MediaType.APPLICATION_JSON)	
	@Consumes(MediaType.APPLICATION_JSON)
	public String postRegistrationTokens(InputStream jsonObj) throws IOException{
		final DataStream data = new DataStream();
		final String dataStr = data.readInputStream(jsonObj);
		XContentParser parser = XContentFactory.xContent(XContentType.JSON).createParser(dataStr);		
		XContentBuilder Xbuilder = XContentFactory.jsonBuilder().copyCurrentStructure(parser);		
		EsResponse.postNewDocument(Constants.INDEX, Constants.TYPE_REG, Xbuilder);
		JSONObject result = new JSONObject(dataStr);
		return result.toString();
	}
	
	// TODO : PUT for replace data by passing argument , recod_code 
	@PUT
    @Path("/registrationtokens/{id}")
	@Produces(MediaType.APPLICATION_JSON)	
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateRegistrationTokens(String id, InputStream jsonObj) throws IOException{
		final DataStream data = new DataStream();
		final String dataStr = data.readInputStream(jsonObj);
		XContentParser parser = XContentFactory.xContent(XContentType.JSON).createParser(dataStr);		
		XContentBuilder Xbuilder = XContentFactory.jsonBuilder().copyCurrentStructure(parser);		
		EsResponse.putNewDocument(Constants.INDEX, Constants.TYPE_REG, id, Xbuilder);
		JSONObject result = new JSONObject(dataStr);
		return result.toString();
	}
}
