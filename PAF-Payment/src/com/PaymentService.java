package com;

import model.Payment;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payments")

public class PaymentService {
	Payment PaymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayments() {
		return PaymentObj.readPayments();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("appointmentId") String appointmentId,
			@FormParam("subtotal") String subtotal,
			@FormParam("tax") String tax, 
			@FormParam("amount") String amount) {
		String output = PaymentObj.insertPayment(appointmentId, subtotal,  tax, amount);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updatePayment(String PaymentData) {
		// Convert the input string to a JSON object
		JsonObject PaymentObject = new JsonParser().parse(PaymentData).getAsJsonObject();

		// Read the values from the JSON object
		String tId = PaymentObject.get("tId").getAsString();
		String appointmentId = PaymentObject.get("appointmentId").getAsString();
		String subtotal = PaymentObject.get("subtotal").getAsString();
		String tax = PaymentObject.get("tax").getAsString();
		String amount = PaymentObject.get("amount").getAsString();

		String output = PaymentObj.updatePayment(tId,appointmentId, subtotal,  tax, amount);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String PaymentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(PaymentData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String tId = doc.select("tId").text();
		String output = PaymentObj.deletePayment(tId);
		return output;
	}
}
