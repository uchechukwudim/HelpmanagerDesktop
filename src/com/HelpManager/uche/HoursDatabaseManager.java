package com.HelpManager.uche;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HoursDatabaseManager extends BreakDatabaseManager{
	
	private DatabaseHelper db;
	
	public HoursDatabaseManager()
	{
		db = new DatabaseHelper();
	}
	
	public void createSchedulerTable(String WeekNumber)
	  {
		  Calendar date = Calendar.getInstance();
		  int year = date.get(Calendar.YEAR);
		  
		  String sql = "CREATE TABLE scheduleHours_week"+WeekNumber+"_"+year+"(" +
		  		"Id int NOT NULL  AUTO_INCREMENT," +
		  		"staff_id int NOT NULL," +
		  		"Monday_hours varchar(255)," +
		  		"Tuesday_hours varchar(255)," +
		  		"Wednesday_hours varchar(255)," +
		  		"Thursday_hours varchar(255)," +
		  		"Friday_hours varchar(255)," +
		  		"Saturday_hours varchar(255)," +
		  		"Sunday_hours varchar(255)," +
		  		"Total_hours DOUBLE," +
		  		"PRIMARY KEY (Id)," +
		  		"FOREIGN KEY (staff_id) " +
		  		"REFERENCES  `HelpManager`.`staff_details` (`staff_id`) " +
		  		"ON DELETE NO ACTION ON UPDATE CASCADE " +
		  		")";
		  try {	
				  		//create hours table
			  			db.ExecuteStatement(sql);
				  		//create launch break table
				  		createLuanchBreakTable(WeekNumber);
				  		// create break table
				  		createBreakTable(WeekNumber);
				  		
		  	  } catch (SQLException e) {
		  		    e.printStackTrace();
		  	  }
		
	  }
	
	 public boolean isTableCreated(String weekNumber)
	  {
		 
		  String sql0 = "SELECT isTableCreated FROM Schedule_Week_UpdateChecker WHERE week_Number = '"+weekNumber+"'";
		 
		  
		  boolean isCreated = true;
		try {
				ResultSet result = db.ExecuteSql(sql0);
				
				result.next();
				//check if the table is created already, if its not create it
				if(result.getBoolean(1) == false)
				{
			  		
					//set that the table is created..
					String sql2 = "UPDATE Schedule_Week_UpdateChecker SET isTableCreated = '1' WHERE week_Number = '"+weekNumber+"'";
					String sql3 = "UPDATE Schedule_Week_UpdateChecker SET isLBTableCreated = '1' WHERE week_Number = '"+weekNumber+"'";
					String sql4 = "UPDATE Schedule_Week_UpdateChecker SET isBreakTableCreated = '1' WHERE week_Number = '"+weekNumber+"'";
					db.ExecuteStatement(sql2);
					db.ExecuteStatement(sql3);
					db.ExecuteStatement(sql4);
					isCreated = false;
				}
				else
				{
					isCreated = true;
				}
				
				result.close();
				
			} catch (SQLException e) {
			e.printStackTrace();
			}
			
		return isCreated;
	  }
	  
	 public void setWeekAsScheuled(String WeekNumber)
	  {
		 
		  String sql = "UPDATE Schedule_Week_UpdateChecker SET isScheduled = '1' WHERE week_number = '"+WeekNumber+"'";
		  
		  try {
			  	db.ExecuteStatement(sql);
		  	  } catch (SQLException e) {
		  		  e.printStackTrace();
		  	  }
	  }
	 
	
	 protected boolean isInsertOrUpdate(String weekNumber, int year, int staff_id)
		{
			//NOTE: true for to update, false for  to insert
			
			
			String sql = "SELECT * FROM scheduleHours_week"+weekNumber+"_"+year+" WHERE staff_id = '"+staff_id+"'";
			
			try {
					ResultSet result = db.ExecuteSql(sql);
					
					if(result.next())
					{
						return true;
					}
				result.close();	
				} catch (SQLException e) {
					e.printStackTrace();
				}
		
			return false;
		}
	 
	 

	 public void InsertOrUpdate(String weekNumber, int year, int staff_id, String day, String UpdateHours, String [] InsertHours, double total_hours, int index, JPanel card, String Name)
		{
		  Calendar date = Calendar.getInstance();
		  int week = date.get(Calendar.WEEK_OF_YEAR);
		  
			if(isInsertOrUpdate(weekNumber, year,  staff_id))
			{
				//update
				
						String sql = "UPDATE scheduleHours_week"+weekNumber+"_"+year+" SET "+day+"_hours ='"+UpdateHours+"', total_hours = '"+total_hours+"' WHERE staff_id = '"+staff_id+"'";
						try {
								db.ExecuteStatement(sql);
								
								JOptionPane.showMessageDialog(card, "Hours for "+day+" has been added for\n\n NAME: "+Name, "GOOD", JOptionPane.INFORMATION_MESSAGE);
							} catch (SQLException e) {
								e.printStackTrace();
							}
			
			}
			else
			{
					//insert
			
				
					
					String sql = "INSERT INTO scheduleHours_week"+weekNumber+"_"+year+" (staff_id, Monday_hours, Tuesday_hours, Wednesday_hours, Thursday_hours, Friday_hours, Saturday_hours, Sunday_hours, total_hours)" +
						 	 "VALUES ('"+staff_id+"', '"+InsertHours[0]+" ', '"+InsertHours[1]+" '," +
						 	 "'"+InsertHours[2]+" ', '"+InsertHours[3]+" ', '"+InsertHours[4]+" ', '"+InsertHours[5]+" ', '"+InsertHours[6]+" ', '"+total_hours+"')";
				 try {
					 	db.ExecuteStatement(sql);
					 	JOptionPane.showMessageDialog(card, "Hours for "+day+" has been added for\n\n NAME: "+ Name, "GOOD", JOptionPane.INFORMATION_MESSAGE);
				 	 } catch (SQLException e) {
				 		 e.printStackTrace();
				 	 }
				}
			
			
		}
		
	 public boolean isPastDay(String days, int weekNumber)
		{
			//Note: true if days has pasted, false if it has not pasted
			Calendar date = Calendar.getInstance();
			
			int currentWeek = date.get(Calendar.WEEK_OF_YEAR);
		
			
			if(weekNumber == currentWeek)
			{
				int currentday = date.get(Calendar.DAY_OF_WEEK);
				
				if(currentday > dayNumber(days))
					return true;
			}
			
			return false;
		}
		
	 private int dayNumber(String day)
		{
		
			if(day.equals("Monday"))
			{
				return 1;
			}
			else if(day.equals("Tuesday"))
			{
				return 2;
			}
			else if(day.equals("Wednesday"))
			{
				return 3;
			}
			else if(day.equals("Thursday"))
			{
				return 4;
			}
			else if(day.equals("Friday"))
			{
				return 5;
			}
			else if(day.equals("Saturday"))
			{
				return 6;
			}
			else if(day.equals("Sunday"))
			{
				return 7;
			}
			return -1;
		}

}
