package com;

import model.Patient;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Patients")

public class PatientService {
	Patient PatientObj = new Patient();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPatients() {
		return PatientObj.readPatients();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPatient(@FormParam("uid") String uid,
			
			@FormParam("did") String did, 
			@FormParam("hid") String hid, 
			@FormParam("fpname") String fpname,
			@FormParam("lpname") String lpname,
			@FormParam("age") String age,
			@FormParam("gender") String gender,
			@FormParam("dob") String dob,
			@FormParam("phone") String phone,
			@FormParam("street") String street,
			@FormParam("city") String city) {
		String output = PatientObj.insertPatient(uid, did, hid, fpname, lpname, age, gender, dob, phone, street,
				city);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updatePatient(String PatientData) {
		// Convert the input string to a JSON object
		JsonObject PatientObject = new JsonParser().parse(PatientData).getAsJsonObject();

		// Read the values from the JSON object
		String uid = PatientObject.get("uid").getAsString();
		String pid = PatientObject.get("pid").getAsString();
		String did = PatientObject.get("did").getAsString();
		String hid = PatientObject.get("hid").getAsString();
		String fpname = PatientObject.get("fpname").getAsString();
		String lpname = PatientObject.get("lpname").getAsString();
		String age = PatientObject.get("age").getAsString();
		String gender = PatientObject.get("gender").getAsString();
		String dob = PatientObject.get("dob").getAsString();
		String phone = PatientObject.get("phone").getAsString();
		String street = PatientObject.get("street").getAsString();
		String city = PatientObject.get("city").getAsString();

		String output = PatientObj.updatePatient(uid, pid, did, hid, fpname, lpname, age, gender, dob, phone, street,
				city);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePatient(String PatientData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(PatientData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String pid = doc.select("pid").text();
		String output = PatientObj.deletePatient(pid);
		return output;
	}
}
