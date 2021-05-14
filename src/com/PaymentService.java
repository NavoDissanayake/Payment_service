package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class PaymentService {
	
	private Connection connect() 
	{ 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 
 
			//Provide the correct database details: DBServer/DBName, username, password 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payment","root",""); 
		} 
		catch (Exception e) 
		{e.printStackTrace();} 
		return con; 
	} 
	
	public String insertPayment(String paydate, String description, 
			 String price, String type) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for inserting."; 
			 } 
			 // create a prepared statement
			 String query = " insert into payment (`payid`,`paydate`,`description`,`price`,`type`)"
					+ " values (?, ?, ?, ?, ?)"; 

			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, paydate); 
			 preparedStmt.setString(3, description); 
			 preparedStmt.setDouble(4, Double.parseDouble(price)); 
			 preparedStmt.setString(5, type); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readPayment(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 }  
			
	public String readPayment()
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for reading."; 
	 } 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Pay Date</th>" 
	 + "<th>Payment Description</th><th>Price</th>"
	 + "<th>Type</th>" 
	 + "<th>Update</th><th>Remove</th></tr>"; 
	 String query = "select * from payment"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String payid = Integer.toString(rs.getInt("payid")); 
	 String paydate = rs.getString("paydate"); 
	 String description = rs.getString("description"); 
	 String price = Double.toString(rs.getDouble("price")); 
	 String type = rs.getString("type");
	 
	// Add into the html table
	 output += "<tr><td>" + paydate + "</td>"; 
	 output += "<td>" + description + "</td>"; 
	 output += "<td>" + price + "</td>"; 
	 output += "<td>" + type + "</td>"; 
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update' "
	+ "class='btnUpdate btn btn-secondary' data-payid='" + payid + "'></td>"
	+ "<td><input name='btnRemove' type='button' value='Remove' "
	+ "class='btnRemove btn btn-danger' data-payid='" + payid + "'></td></tr>"; 
	 } 
	 con.close(); 
	 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	catch (Exception e) 
	 { 
	 output = "Error while reading the items."; 
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
	}
		
	public String updatePayment(String payid, String paydate, String description, 
			 String price, String type) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for updating."; 
			 } 
			 // Create the prepared statement
			 String query = "UPDATE payment SET paydate=?,description=?,price=?,type=? WHERE payid=?"; 
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setString(1, paydate); 
			 preparedStmt.setString(2, description); 
			 preparedStmt.setDouble(3, Double.parseDouble(price)); 
			 preparedStmt.setString(4, type); 
			 preparedStmt.setInt(5, Integer.parseInt(payid)); 
			// execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readPayment(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 }
			 
	public String deletePayment(String payid) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for deleting."; 
	 } 
	 // Create a prepared statement
	 String query = "delete from payment where payid=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	 // Binding values
	 preparedStmt.setInt(1, Integer.parseInt(payid));  
	 
	 //  Execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newItems = readPayment(); 
	 output = "{\"status\":\"success\", \"data\": \"" + 
	 newItems + "\"}"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
}
