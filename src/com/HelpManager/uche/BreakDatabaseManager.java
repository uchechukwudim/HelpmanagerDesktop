package com.HelpManager.uche;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class BreakDatabaseManager {

	private DatabaseHelper db;
	final static double Minutes_15_break = 4.30;
	final static double Minutes_30_break = 6.00;
	final static double hour_01_break = 7.00;

	final static double break_15 = 00.15;
	final static double break_30 = 00.30;
	final static double break_60 = 01.00;
	public BreakDatabaseManager()
	{
		db = new DatabaseHelper();
	}
	
	public void createLuanchBreakTable(String WeekNumber)
	{
		  Calendar date = Calendar.getInstance();
		  int year = date.get(Calendar.YEAR);
		  String sql = "CREATE TABLE scheduleLaunchBreak_week"+WeekNumber+"_"+year+"(" +
		  		"Id int NOT NULL  AUTO_INCREMENT," +
		  		"staff_id int NOT NULL," +
		  		"Monday_break varchar(255)," +
		  		"Tuesday_break varchar(255)," +
		  		"Wednesday_break varchar(255)," +
		  		"Thursday_break varchar(255)," +
		  		"Friday_break varchar(255)," +
		  		"Saturday_break varchar(255)," +
		  		"Sunday_break varchar(255)," +
		  		"Total_break DOUBLE," +
		  		"PRIMARY KEY (Id)," +
		  		"FOREIGN KEY (staff_id) " +
		  		"REFERENCES  `HelpManager`.`staff_details` (`staff_id`) " +
		  		"ON DELETE NO ACTION ON UPDATE CASCADE " +
		  		")";
		  try {	
				  		db.ExecuteStatement(sql);
		  	  } catch (SQLException e) {
		  		    e.printStackTrace();
		  	  }
		 
	}
	
	public void createBreakTable(String WeekNumber)
	{
		  Calendar date = Calendar.getInstance();
		  int year = date.get(Calendar.YEAR);
		
		  String sql = "CREATE TABLE scheduleBreak_week"+WeekNumber+"_"+year+"(" +
		  		"Id int NOT NULL  AUTO_INCREMENT," +
		  		"staff_id int NOT NULL," +
		  		"Monday_break varchar(255)," +
		  		"Tuesday_break varchar(255)," +
		  		"Wednesday_break varchar(255)," +
		  		"Thursday_break varchar(255)," +
		  		"Friday_break varchar(255)," +
		  		"Saturday_break varchar(255)," +
		  		"Sunday_break varchar(255)," +
		  		"Total_break DOUBLE," +
		  		"PRIMARY KEY (Id)," +
		  		"FOREIGN KEY (staff_id) " +
		  		"REFERENCES  `HelpManager`.`staff_details` (`staff_id`) " +
		  		"ON DELETE NO ACTION ON UPDATE CASCADE " +
		  		")";
		  try {	
				  		db.ExecuteStatement(sql);
		  	  } catch (SQLException e) {
		  		    e.printStackTrace();
		  	  }
	}

	public void CalculateBreakes(int staff_id, double totalWorkHours, String startTime, String weekNumber, String Day, int year)
	{
		double breakStartTime = 00.00;
		double breakFinishTime = 00.00;
		
		
		if(totalWorkHours >= Minutes_15_break && totalWorkHours < Minutes_30_break)
		{
			//15Minutes break here
			//get the start and finish time, go to break after 2hours (add to database)
			breakStartTime = ConvertStartTime(startTime) + 2.00;
			breakFinishTime = breakStartTime+break_15;
			
			updateCalculatedBreakIntoDatabase(staff_id, breakStartTime, breakFinishTime, weekNumber, Day, year);
			
		}
		else if(totalWorkHours >=Minutes_30_break && totalWorkHours < hour_01_break )
		{
			//30 minutes break
			breakStartTime = ConvertStartTime(startTime) + 3.00;
			breakFinishTime = breakStartTime+break_30;
			updateCalculatedBreakIntoDatabase(staff_id, breakStartTime, breakFinishTime, weekNumber, Day, year);

		}
		else if(totalWorkHours >=hour_01_break)
		{
			//1 hour break
			breakStartTime = ConvertStartTime(startTime) + 3.00;
			breakFinishTime = breakStartTime+break_60;
			updateCalculatedBreakIntoDatabase(staff_id, breakStartTime, breakFinishTime, weekNumber, Day, year);
		}
	}
	
	private double ConvertStartTime(String startTime)
	{
		String st = startTime.substring(0, 2)+"."+startTime.substring(4, 5);
		
		return Double.parseDouble(st);
	}
	
	private void updateCalculatedBreakIntoDatabase(int staff_id, double breakStartTime, double breakFinishTime, String weekNumber, String day, int year)
	{
		DatabaseHelper db = new DatabaseHelper();
		
		String time = convertToTime(breakStartTime+"", breakFinishTime+"");
				
		String sql = "SELECT Total_break, "+day+"_break FROM scheduleBreak_week"+weekNumber+"_"+year+" WHERE staff_id = '"+staff_id+"'";
		String sql1 = "SELECT Total_break, "+day+"_break FROM scheduleLaunchBreak_week"+weekNumber+"_"+year+" WHERE staff_id = '"+staff_id+"'";
		
		String [] InsertHours =  setDayHourPosition(day, time);
		double total = breakFinishTime - breakStartTime;
		try {
		
				if(total >0.14 && total <= 0.31)
				{
					
					ResultSet result = db.ExecuteSql(sql);
					
					if(result.next())
					{
					
						//get total from database
						double currentTotal = result.getDouble(1);
						
						//this has to happen because you are about to change or update a break so i have to get the break of the day you to update and minus it from current total
						double currentBreak = Double.parseDouble(result.getString(2).subSequence(6, 11)+"") - Double.parseDouble(result.getString(2).subSequence(0, 5)+"");
							
						//if total is 15 or 30 minutes break insert into break table if it is greater insert into Launch table.
						double CurrentTotal =   currentTotal - currentBreak;
						double Total = total+CurrentTotal;
						
						Total = Math.round( Total * 100.0 ) / 100.0;
						
						//update database
						String Sql = "UPDATE scheduleBreak_week"+weekNumber+"_"+year+" SET "+day+"_break = '"+time+"', total_break = '"+Total+"' WHERE staff_id = '"+staff_id+"'";
					
						//reset  to 0 time and subtract accordingly in the launch break table if the hours for a day are rescheduled 
						//to hours that is no longer 15 or 30 minutes break
						ResultSet result2 = db.ExecuteSql(sql1);
						result2.next();
						if(result2.getDouble(1) > 0.0)
						{
							String Sql1 = "UPDATE scheduleLaunchBreak_week"+weekNumber+"_"+year+" SET "+day+"_break = '00.00-00.00', total_break = total_break - 1 WHERE staff_id = '"+staff_id+"'";
							db.ExecuteStatement(Sql1);
							db.ExecuteStatement(Sql);
							
						
						}else
						{
							String Sql1 = "UPDATE scheduleLaunchBreak_week"+weekNumber+"_"+year+" SET "+day+"_break = '00.00-00.00', total_break = total_break - 0 WHERE staff_id = '"+staff_id+"'";
							db.ExecuteStatement(Sql1);
							db.ExecuteStatement(Sql);
						
						}
						
					}else
					{
						
						total = Math.round( total * 100.00 ) / 100.00;
						//insert into database
						String SQL = "INSERT INTO scheduleBreak_week"+weekNumber+"_"+year+" (staff_id, Monday_break, Tuesday_break, Wednesday_break, Thursday_break, Friday_break, Saturday_break, Sunday_break, total_break)" +
					 	 "VALUES ('"+staff_id+"', '"+InsertHours[0]+" ', '"+InsertHours[1]+" '," +
					 	 "'"+InsertHours[2]+" ', '"+InsertHours[3]+" ', '"+InsertHours[4]+" ', '"+InsertHours[5]+" ', '"+InsertHours[6]+" ', '"+total+"')";
						db.ExecuteStatement(SQL);
						
						String SQL1 = "INSERT INTO scheduleLaunchBreak_week"+weekNumber+"_"+year+" (staff_id, Monday_break, Tuesday_break, Wednesday_break, Thursday_break, Friday_break, Saturday_break, Sunday_break, total_break)" +
							 	 "VALUES ('"+staff_id+"', '00.00-00.00', '00.00-00.00'," +
							 	 "'00.00-00.00', '00.00-00.00', '00.00-00.00', '00.00-00.00', '00.00-00.00', '0')";
								db.ExecuteStatement(SQL1);

					}
					
				}
				else if(total > 0.31)
				{
					ResultSet result1 = db.ExecuteSql(sql1);
					if(result1.next())
					{
						//get total from database
						double currentTotal = result1.getDouble(1);
						//this has to happen because you are about to change or update a break so i have to get the break of the day you to update and minus it from current total
						double currentBreak = Double.parseDouble(result1.getString(2).subSequence(6, 11)+"") - Double.parseDouble(result1.getString(2).subSequence(0, 5)+"");
						
						double CurrentTotal =   currentTotal - currentBreak;
						double Total = total+CurrentTotal;
						
						Total = Math.round( Total * 100.0 ) / 100.0;
						
						//update database
						String Sql = "UPDATE scheduleLaunchBreak_week"+weekNumber+"_"+year+" SET "+day+"_break = '"+time+"', total_break = '"+Total+"' WHERE staff_id = '"+staff_id+"'";
						db.ExecuteStatement(Sql);
				
						//reset  to 0 time and subtract accordingly in the break table if the hours for a day are rescheduled 
						//to hours that is no longer an hour launch break
						String sql2 = "SELECT Total_break, "+day+"_break FROM scheduleBreak_week"+weekNumber+"_"+year+" WHERE staff_id = '"+staff_id+"'";
						double breakcurrentTotal = 0.0;
						
						ResultSet result2 = db.ExecuteSql(sql2);
						if(result2.next()){
							breakcurrentTotal = result2.getDouble(1);
							//this has to happen because you are about to change or update a break so i have to get the break of the day you to update and minus it from current total
							double BcurrentBreak = Double.parseDouble(result2.getString(2).subSequence(6, 11)+"") - Double.parseDouble(result2.getString(2).subSequence(0, 5)+"");
								
							//if total is 15 or 30 minutes break insert into break table if it is greater insert into Launch table.
							double BCurrentTotal =   breakcurrentTotal - BcurrentBreak;
							
							
							double bTotal = Math.round( BCurrentTotal * 100.0 ) / 100.0;
							
							
							//update database
							
							String Sql1 = "UPDATE scheduleBreak_week"+weekNumber+"_"+year+" SET "+day+"_break = '00.00-00.00', total_break = '"+bTotal+"' WHERE staff_id = '"+staff_id+"'";	
							db.ExecuteStatement(Sql1);
						}
						
					}
					else
					{
						double ntotal = Math.round( total * 100.0 ) / 100.0;
						//insert into database
						String SQL = "INSERT INTO scheduleLaunchBreak_week"+weekNumber+"_"+year+" (staff_id, Monday_break, Tuesday_break, Wednesday_break, Thursday_break, Friday_break, Saturday_break, Sunday_break, total_break)" +
					 	 "VALUES ('"+staff_id+"', '"+InsertHours[0]+" ', '"+InsertHours[1]+" '," +
					 	 "'"+InsertHours[2]+" ', '"+InsertHours[3]+" ', '"+InsertHours[4]+" ', '"+InsertHours[5]+" ', '"+InsertHours[6]+"', '1')";
						db.ExecuteStatement(SQL);
						
						String SQL1 = "INSERT INTO scheduleBreak_week"+weekNumber+"_"+year+" (staff_id, Monday_break, Tuesday_break, Wednesday_break, Thursday_break, Friday_break, Saturday_break, Sunday_break, total_break)" +
							 	 "VALUES ('"+staff_id+"', '00.00-00.00', '00.00-00.00'," +
							 	 "'00.00-00.00', '00.00-00.00', '00.00-00.00', '00.00-00.00', '00.00-00.00', '0')";
								db.ExecuteStatement(SQL1);
								
							
					}
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
     private String convertToTime(String st, String ft)
     {

 		String ST = ""; 
 		String FT = ""; 
 		
    	 if(st.length()==4)
 		 {
 			ST = st+"0";
 		 }
 		 else
 		 {
 			ST = st;
 		 }
 		
 		if(ft.length()==4)
 		{
 			FT = ft+"0";
 		}
 		else
 		{
 			FT = ft;
 		}
 		
 		return ST+"-"+FT;
     }
     
     
    public String[] setDayHourPosition(String day, String hours)
 	{
 		String [] Hours = new String[7];
 		if(day.equals("Monday"))
 		{
 			Hours[0] = hours;
 		}
 		else{
 			Hours[0] = "00.00-00.00";
 		}
 		
 		if(day.equals("Tuesday"))
 		{
 			Hours[1] = hours;
 		}
 		else{
 			Hours[1] = "00.00-00.00";
 		}
 		
 		if(day.equals("Wednesday"))
 		{
 			Hours[2] = hours;
 		}
 		else{
 			Hours[2] = "00.00-00.00";
 		}
 		
 		if(day.equals("Thursday"))
 		{
 			Hours[3] = hours;
 		}
 		else{
 			Hours[3] = "00.00-00.00";
 		}
 		
 		if(day.equals("Friday"))
 		{
 			Hours[4] = hours;
 		}
 		else{
 			Hours[4] = "00.00-00.00";
 		}
 		
 		if(day.equals("Saturday"))
 		{
 			Hours[5] = hours;
 		}
 		else{
 			Hours[5] = "00.00-00.00";
 		}
 		
 		if(day.equals("Sunday"))
 		{
 			Hours[6] = hours;
 		}
 		else{
 			Hours[6] = "00.00-00.00";
 		}
 		return Hours;
 	}

	
}
