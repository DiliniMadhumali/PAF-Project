package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Hospital {

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

	public String insertHospital(String hname, String no, String street, String city, String hos_charges) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = "insert into hospital(`hid`,`hname`,`no`,`street`,`city`,`hos_charges`)"
					+ "values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, hname);
			preparedStmt.setString(3, no);
			preparedStmt.setString(4, street);
			preparedStmt.setString(5, city);
			preparedStmt.setString(6, hos_charges);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Hospitals.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readHospitals() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>hid</th><th>hname</th><th>no</th><th>street</th><th>city</th><th>hos_charges</th></tr>";
			String query = "select * from hospital";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String hid = Integer.toString(rs.getInt("hid"));
				String hname = rs.getString("hname");
				String no = rs.getString("no");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String hos_charges = Double.toString(rs.getDouble("hos_charges"));

				// Add into the html table
				
				output += "<tr><td>" + hid + "</td>";
				output += "<td>" + hname + "</td>";
				output += "<td>" + no + "</td>";
				output += "<td>" + street + "</td>";
				output += "<td>" + city + "</td>";
				output += "<td>" + hos_charges + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Hospitals.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateHospital(String hid, String hname, String no, String street, String city, String hos_charges) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE hospital SET hname=?,no=?,street=?,city=?,hos_charges=? WHERE hid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, hname);
			preparedStmt.setString(2, no);
			preparedStmt.setString(3, street);
			preparedStmt.setString(4, city);
			preparedStmt.setString(5, hos_charges);

			preparedStmt.setInt(6, Integer.parseInt(hid));

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

	public String deleteHospital(String hid) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from hospital where hid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(hid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Hospital.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
