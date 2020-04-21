package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/paf_project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String appointmentId, String subtotal, String tax, String amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment(`tId`,`appointmentId`, `subtotal`, `tax`, `amount`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, appointmentId);
			preparedStmt.setString(3, subtotal);
			preparedStmt.setString(4, tax);
			preparedStmt.setString(5, amount);
		
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPayments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>tId</th><th>appointmentId</th><th>subtotal</th><th>tax</th><th>amount</th></tr>";
			String query = "select * from payment";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String tId = Integer.toString(rs.getInt("tId"));
				String appointmentId = Integer.toString(rs.getInt("appointmentId"));
				String subtotal = Float.toString(rs.getFloat("subtotal"));
				String tax = Float.toString(rs.getFloat("tax"));
				String amount = Integer.toString(rs.getInt("amount"));
				
				output += "<tr><td>" + tId + "</td>";
				output += "<td>" + appointmentId + "</td>";
				output += "<td>" + subtotal + "</td>";
				output += "<td>" + tax + "</td>";
				output += "<td>" + amount + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String tId,String appointmentId, String subtotal, String tax, String amount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payment SET appointmentId=?,subtotal=?,tax=?,amount=? WHERE tId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			
			preparedStmt.setInt(1, Integer.parseInt(appointmentId));
			preparedStmt.setFloat(2, Float.parseFloat(subtotal));
			preparedStmt.setFloat(3, Float.parseFloat(tax));
			preparedStmt.setInt(4, Integer.parseInt(amount));
			preparedStmt.setInt(5, Integer.parseInt(tId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deletePayment(String tId) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from payment where tId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(tId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Payment.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
