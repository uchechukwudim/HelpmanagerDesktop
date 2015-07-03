package com.HelpManager.uche;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseHelper {
	
	private final String Url = "jdbc:mysql://localhost:3306/HelpManager";
	private final String userName = "root";
	private final String password = "password";
	private Connection connection;
	
	public DatabaseHelper(){
		try {
			
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(Url,userName,password);
				
			} catch (Exception e) {}
	}
	
	
	public ResultSet ExecuteSql(String sql) throws SQLException
	{
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
		  
			return result;
	}
	
	public void ExecuteStatement(String sql) throws SQLException
	{
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
	}
	

	public int getResultCount(String sql) throws SQLException
	{
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultCount = statement.executeQuery();
		  int counter = 0;
		  
			while(resultCount.next())
				{
					counter++;
				}
			
			return counter;
	}
	//maybe code to process individual column here
	
	
	//Note: class to hold information from table.
	
	//close database
	public void closeConnection()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
