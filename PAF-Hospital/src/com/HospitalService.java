package com;

import model.Hospital;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Hospitals")

public class HospitalService {
	Hospital hospitalObj = new Hospital();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readHospitals() {
		return hospitalObj.readHospitals();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertHospital(
			@FormParam("hname") String hname,
			@FormParam("no") String no, 
			@FormParam("street") String street,
			@FormParam("city") String city, 
			@FormParam("hos_charges") String hos_charges) {
		String output = hospitalObj.insertHospital(hname,no, street,city, hos_charges);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateHospital(String hospitalData) {
		// Convert the input string to a JSON object
		JsonObject hospitalObject = new JsonParser().parse(hospitalData).getAsJsonObject();

		// Read the values from the JSON object
		String hid = hospitalObject.get("hid").getAsString();
		String hname = hospitalObject.get("hname").getAsString();
		String no =hospitalObject.get("no").getAsString();
		String  street = hospitalObject.get("street").getAsString();
		String city = hospitalObject.get("city").getAsString();
		String hos_charges = hospitalObject.get("hos_charges").getAsString();
		

		String output = hospitalObj.updateHospital(hid, hname,no, street,city, hos_charges);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteHospital(String hospitalData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(hospitalData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String hid = doc.select("hid").text();
		String output = hospitalObj.deleteHospital(hid);
		return output;
	}
}
