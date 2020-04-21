package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {

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

	public String insertPatient(String uid, String did, String hid, String fpname, String lpname, String age,
			String gender, String dob, String phone, String street, String city) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = "INSERT INTO `patient`(`uid`, `pid`, `did`, `hid`, `fpname`, `lpname`, `age`, `gender`, `dob`, `phone`, `street`, `city`) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, uid);
			preparedStmt.setInt(2, 0);
			preparedStmt.setString(3, did);
			preparedStmt.setString(4, hid);
			preparedStmt.setString(5, fpname);
			preparedStmt.setString(6, lpname);
			preparedStmt.setString(7, age);
			preparedStmt.setString(8, gender);
			preparedStmt.setString(9, dob);
			preparedStmt.setString(10, phone);
			preparedStmt.setString(11, street);
			preparedStmt.setString(12, city);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Patients.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPatients() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>uid</th><th>pid</th><th>did</th><th>hid</th><th>fpname</th><th>lpname</th><th>age</th><th>gender</th><th>dob</th><th>phone</th><th>street</th><th>city</th></tr>";
			String query = "select * from patient";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String uid = Integer.toString(rs.getInt("uid"));
				String pid = Integer.toString(rs.getInt("pid"));
				String did = Integer.toString(rs.getInt("did"));
				String hid = Integer.toString(rs.getInt("hid"));
				String fpname = rs.getString("fpname");
				String lpname = rs.getString("lpname");
				String age = Integer.toString(rs.getInt("age"));
				String gender = rs.getString("gender");
				String dob = rs.getString("dob");
				String phone = rs.getString("phone");
				String street = rs.getString("street");
				String city = rs.getString("city");

				// Add into the html table
				output += "<tr><td>" + uid + "</td>";
				output += "<td>" + pid + "</td>";
				output += "<td>" + did + "</td>";
				output += "<td>" + hid + "</td>";
				output += "<td>" + fpname + "</td>";
				output += "<td>" + lpname + "</td>";
				output += "<td>" + age + "</td>";
				output += "<td>" + gender + "</td>";
				output += "<td>" + dob + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + street + "</td>";
				output += "<td>" + city + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Doctors.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePatient(String uid, String pid, String did, String hid, String fpname, String lpname,
			String age, String gender, String dob, String phone, String street, String city) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE patient SET uid=?,did=?,hid=?,fpname=?,lpname=?,age=?,gender=?,dob=?,phone=?,street=?,city=?  WHERE pid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setInt(1, Integer.parseInt(uid));
			preparedStmt.setInt(2, Integer.parseInt(did));
			preparedStmt.setInt(3, Integer.parseInt(hid));
			preparedStmt.setString(4, fpname);
			preparedStmt.setString(5, lpname);
			preparedStmt.setInt(6, Integer.parseInt(age));
			preparedStmt.setString(7, gender);
			preparedStmt.setString(8, dob);
			preparedStmt.setString(9, phone);
			preparedStmt.setString(10, street);
			preparedStmt.setString(11, city);
			preparedStmt.setInt(12, Integer.parseInt(pid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Patient.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deletePatient(String pid) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from Patient where pid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Patient.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
