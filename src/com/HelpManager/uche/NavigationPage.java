package com.HelpManager.uche;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.*;

import com.HelpManager.holders.ScheduleHoursHolder;
import com.HelpManager.holders.YearWeeksHolder;

/*
 * This class only activates when administrator personnel is logged in i.e manager with rank of 1 or 2.
 * contains buttons to navigate to scheduling hours, viewing scheduled hours, adding new employee,
 * update employee details, and view breaks for employees
 */
public class NavigationPage {
  private JPanel card;
  private JButton ScheduleHours;
  private JButton ResetPassword;
  private JButton AddEmployee;
  private JButton Employees;
  private JButton Breaks;
  private GridBagConstraints gbc;
  private JButton close;

  final static int extraWindowWidth = 100;
  
	  public NavigationPage()
	  {
		  ScheduleHours = new JButton("Schedule Hours");
		  
		  ScheduleHours.setPreferredSize(new Dimension(300, 50));

	  
		  AddEmployee = new JButton("Add New Employee");
		  AddEmployee.setPreferredSize(new Dimension(300, 50));
		  
		  Employees = new JButton("View Employees");
		  Employees.setPreferredSize(new Dimension(300, 50));
		  
		  ResetPassword = new JButton("Change Password");
		  ResetPassword.setPreferredSize(new Dimension(300, 50));

		  close = new JButton("Close X");
		  close.setOpaque(true);
		  close.setForeground(Color.red);
		  
		  gbc = new GridBagConstraints();
		  card = new JPanel(){
	
			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
	              Dimension size = super.getPreferredSize();
	              size.width += extraWindowWidth;
	              return size;
			  } 
		  };
		  
		  changePassword();
		  
	  }
	  
	  public void AddComponents()
	  {
		  
		
		  
		  gbc.gridx = 2;
		  gbc.gridy = 0;
		  //gbc.weighty = -1.5;
		  gbc.fill = GridBagConstraints.HORIZONTAL;
		  //gbc.weightx = -50.0;
		  gbc.insets = new Insets(0, 0, 5, 5);
		  card.add(ScheduleHours, gbc);
		  
		  
		  gbc.gridx = 2;
		  gbc.gridy = 2;
		  //gbc.weighty = -1.5;
		  gbc.fill = GridBagConstraints.HORIZONTAL;
		
		  card.add(AddEmployee, gbc);
		  
		  gbc.gridx = 2;
		  gbc.gridy = 3;
		  //gbc.weighty = -1.5;
		  gbc.fill = GridBagConstraints.HORIZONTAL;
		
		  card.add(Employees, gbc);
		  
		  
		  gbc.gridx = 2;
		  gbc.gridy = 4;
		  gbc.fill = GridBagConstraints.HORIZONTAL;
		  card.add(ResetPassword, gbc);
		  
		  
	  }

	  private void GHOST()
	  {
		  SwingWorker<Void, Void> GostWorker = new  SwingWorker<Void, Void>(){

				
					protected Void doInBackground() throws Exception {
						
						return null;
					}
			  
			};
	  }
	  
	  public void checkToUseComponents()
	  {
		DatabaseHelper db = new DatabaseHelper();
		ResultSet result;
		boolean check = false;
		  //get if someone is loggedin into the admin, enable navigation buttons
			//checks for login any time a tab is clicked
			String sql1 = "SELECT isLogedin FROM admin_login WHERE isLogedin='1'";
		
		
			try{
				
				result = db.ExecuteSql(sql1);
				result.next();
				check = result.getBoolean(1);
			if(check)
			{
				//get buttons from navigation page and enable them
				enableComponents();									
			}
			} catch (Exception ee) {}

	        //disable buttons for navigation when logout
		 
			    if(!check)
				{
		        	//get buttons from navigation page and enable them
		        	disableComponents();
		        	
		        	//remove any cards used by admin
		       
				} 


	  }
	 
	  private void enableComponents()
	  {
		    getScheduleHours().setEnabled(true);
			getScheduledHours().setEnabled(true);
			getAddEmployee().setEnabled(true);
			getEmployees().setEnabled(true);
			getBreaks().setEnabled(true);
	  }
	  
	  public void disableComponents()
	  {
		  	getScheduleHours().setEnabled(false);
			getScheduledHours().setEnabled(false);
			getAddEmployee().setEnabled(false);
			getEmployees().setEnabled(false);
			getBreaks().setEnabled(false);
	  }
	  
	  
	  public String scheduledWeeksChecker() 
	  {
		  DatabaseHelper db = new DatabaseHelper();
		  YearWeeksHolder weeksHolder = new YearWeeksHolder();
		  String weekNumber = "";
		  
		  //check what week we are on at the moment
		 Calendar date = Calendar.getInstance();
		 int CurrentWeekNumber = date.get(Calendar.WEEK_OF_YEAR);
		 
		 //select weeks from the current week of the current year
		 String sql = "SELECT week_Number, isScheduled FROM Schedule_Week_UpdateChecker WHERE week_Number >= '"+CurrentWeekNumber+"'";
		 
		 try {
			 	ResultSet result = db.ExecuteSql(sql);
			 	
			 	while(result.next())
			 	{
			 		int weekNum = result.getInt(1);
			 		boolean isWeekScheduled = result.getBoolean(2);
			 		
			 		//put weeks into weeks holder for use
			 		putIntoWeeksHolder(weeksHolder, weekNum, isWeekScheduled);
			 	}
			 	result.close();//close result
			 	//get all weeks that has not passed for display 
		 		String [] weeksForDisplay = getWeeks(weeksHolder, CurrentWeekNumber);
		 		
		 		//show dialog box to collect input from user here
		 		
		 		
		 		weekNumber = (String) JOptionPane.showInputDialog(card, 
		 		        										"What week do you want to Schedule hours for? \n Current Week: Week "+CurrentWeekNumber,
		 		        										"Select Week Number",
		 		        										JOptionPane.QUESTION_MESSAGE, null, weeksForDisplay, weeksForDisplay[0]);
		 		
		 		 db.closeConnection();//close database;
		 		
				 
		 	 } 
		 	catch (SQLException e)
		 	{
		 		e.printStackTrace();
		 	} 
		 return breakStringToNumber(weekNumber);
	  }
	  
	  
	  
	  
	  //break the string to get week number
	  private String breakStringToNumber(String value)
	  {
		  return value.substring(5, 7);
	  }
	  
	  //method to grab weeks for display
	  private String [] getWeeks(YearWeeksHolder holder, int CurrentWeek) {
		int key = CurrentWeek;
		int loop = 0;
		final int numberOfWeeks = holder.size(); 
	    String [] AvailableWeeks = new String[numberOfWeeks];
	    
	    Calendar date = Calendar.getInstance();
		while(loop < numberOfWeeks)
		{
			 AvailableWeeks[loop] = "week "+holder.getKey(key)+" of "+ date.get(Calendar.YEAR);
			 key++;
			 loop++;
		}
		return AvailableWeeks;
	}
	  
	  
	 //method to put data gotten from database into the holder class
	private void putIntoWeeksHolder(YearWeeksHolder holder, int Wnum, boolean isSched)
	  {
		  holder.setIndex(Wnum, isSched);
	  }
	
	
	public boolean CheckSelectedWeekForIsSchedule(String weekNumber)
	{
		DatabaseHelper db = new DatabaseHelper();
		String sql ="SELECT isScheduled FROM Schedule_Week_UpdateChecker WHERE week_Number = '"+weekNumber+"'";
		
		try {
				ResultSet result = db.ExecuteSql(sql);
				
				result.next();
				
				if(result.getBoolean(1))
					return true;
				
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		return false;
	}
	
	
	public String getWeeksNumber() 
	  {
		  DatabaseHelper db = new DatabaseHelper();
		  YearWeeksHolder weeksHolder = new YearWeeksHolder();
		  String weekNumber = "";
		  

		 //select weeks from the current week of the current year
		 String sql = "SELECT week_Number, isScheduled  FROM Schedule_Week_UpdateChecker ";
		 
		 try {
			 	ResultSet result = db.ExecuteSql(sql);
			 	
			 	while(result.next())
			 	{
			 		int weekNum = result.getInt(1);
			 		boolean isWeekScheduled = result.getBoolean(2);
			 		
			 		//put weeks into weeks holder for use
			 		putIntoWeeksHolder(weeksHolder, weekNum, isWeekScheduled);
			 	}
			 	result.close();//close result
			 	//get all weeks that has not passed for display 
		 		String [] weeksForDisplay = getWeeks(weeksHolder, 01);
		 		
		 		//show dialog box to collect input from user here
		 		
		 		
		 		weekNumber = (String) JOptionPane.showInputDialog(card, 
		 		        										"Choose week to view hours",
		 		        										"Select Week Number",
		 		        										JOptionPane.QUESTION_MESSAGE, null, weeksForDisplay, weeksForDisplay[0]);
		 		
		 		 db.closeConnection();//close database;
		 		
				 
		 	 } 
		 	catch (SQLException e)
		 	{
		 		e.printStackTrace();
		 	} 
		 return breakStringToNumber(weekNumber);
	  }
	
	public Object [][] populateData(String weekNumber)
	{
		Calendar date = Calendar.getInstance();
		  int year = date.get(Calendar.YEAR);
		DatabaseHelper db = new DatabaseHelper();
		ArrayList<ScheduleHoursHolder> hours = new ArrayList<ScheduleHoursHolder>();
		String sql = "SELECT * FROM scheduleHours_week"+weekNumber+"_"+year+"";
		Object [][] Data =null;
	
		try {
				ResultSet result = db.ExecuteSql(sql);
				while(result.next())
				{
					ScheduleHoursHolder hold = new ScheduleHoursHolder();
					int StaffId = result.getInt(2);
					
					hold.setStaff_id(StaffId);
					hold.setNames(getNames(StaffId));
					hold.setADayOfTheWeekHour(0, result.getString(3));
					hold.setADayOfTheWeekHour(1, result.getString(4));
					hold.setADayOfTheWeekHour(2, result.getString(5));
					hold.setADayOfTheWeekHour(3, result.getString(6));
					hold.setADayOfTheWeekHour(4, result.getString(7));
					hold.setADayOfTheWeekHour(5, result.getString(8));
					hold.setADayOfTheWeekHour(6, result.getString(9));
					hold.setTotalHours(Double.parseDouble(result.getString(10)));
				
					
					hours.add(hold);
				}
				
				//fill in data for table heres
				Data = new String [hours.size()][9];
				for(int loopTop = 0; loopTop < hours.size(); loopTop++)
				{
					
						Data[loopTop][0] = hours.get(loopTop).getNames();;
						
						if(hours.get(loopTop).getADayOfTheWeekHour(6).contains("00.00-00.00"))
						{
							Data[loopTop][1] = "";
						}
						else{
							Data[loopTop][1] = hours.get(loopTop).getADayOfTheWeekHour(6);
						}
						
						if(hours.get(loopTop).getADayOfTheWeekHour(0).contains("00.00-00.00"))
						{
							Data[loopTop][2] = "";
						}
						else{
							Data[loopTop][2] = hours.get(loopTop).getADayOfTheWeekHour(0);
						}
						
						if(hours.get(loopTop).getADayOfTheWeekHour(1).contains("00.00-00.00"))
						{
							Data[loopTop][3] = "";
						}
						else{
							Data[loopTop][3] = hours.get(loopTop).getADayOfTheWeekHour(1);
						}
						
						if(hours.get(loopTop).getADayOfTheWeekHour(2).contains("00.00-00.00"))
						{
							Data[loopTop][4] = "";
						}
						else{
							Data[loopTop][4] = hours.get(loopTop).getADayOfTheWeekHour(2);
						}
						
						if(hours.get(loopTop).getADayOfTheWeekHour(3).contains("00.00-00.00"))
						{
							Data[loopTop][5] = "";
						}
						else{
							Data[loopTop][5] = hours.get(loopTop).getADayOfTheWeekHour(3);
						}
						
						if(hours.get(loopTop).getADayOfTheWeekHour(4).contains("00.00-00.00"))
						{
							Data[loopTop][6] = "";
						}
						else{
							Data[loopTop][6] = hours.get(loopTop).getADayOfTheWeekHour(4);
						}
						
						if(hours.get(loopTop).getADayOfTheWeekHour(5).contains("00.00-00.00"))
						{
							Data[loopTop][7] = "";
						}
						else{
							Data[loopTop][7] = hours.get(loopTop).getADayOfTheWeekHour(5);
						}
						   
						Data[loopTop][8] = hours.get(loopTop).getTotalHours()+""; 
						
				}
				
				
			} catch (SQLException e)
			{
				//e.printStackTrace();
			}
		
		return Data;
	}
	
	private String getNames(int staffId) {
		// TODO Auto-generated method stub
		
		DatabaseHelper db = new DatabaseHelper();
		
		String sql = "SELECT names FROM staff_details WHERE staff_id = '"+staffId+"'";
		String name = "";
		try {
				ResultSet result = db.ExecuteSql(sql);
				result.next();
				name = result.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return name;
	}
	
	private void changePassword()
	{
		ResetPassword.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				passwordChnger();
				
			}
			
		});
	}
	
	private void passwordChnger()
	{
		DatabaseHelper db = new DatabaseHelper();
		   
		while(true)
		{
		   try{
			String staffId = JOptionPane.showInputDialog("Please type in your Staff Identification");
			if(staffId.length() == 0)
			{
				JOptionPane.showMessageDialog(card, "Field is empty");
			}
			else{
			if(!isInteger(staffId))
			{
				JOptionPane.showMessageDialog(card, "Staff identification has to be a number");
			}
			else
			{
				String sql = "SELECT staff_id, password FROM admin_login WHERE staff_id = '"+staffId+"'";
				try {
						ResultSet result = db.ExecuteSql(sql);
						
						if(result.next())
						{
							boolean isPasswordRight = true;
							while(isPasswordRight)
							{									
							//get current password from user and database
									String currentPassword = result.getString(2);
									String tempCurrentPassword = "";
									JPasswordField PwF = new JPasswordField();
									 int OkOption = JOptionPane.showConfirmDialog(null, PwF, "Enter current Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE );
											if(OkOption == JOptionPane.OK_OPTION) 
											{
													tempCurrentPassword = new String(PwF.getPassword());
											}
											else
											{
												return;
											}
									
									//if the user cancels end while loop and do nothing
									if(tempCurrentPassword.length() == 0)
									{
										JOptionPane.showMessageDialog(card, "Filled is empty");
									}else
									{
										//check if the password is right if its not show message 
										 if(currentPassword.equals(tempCurrentPassword))
										 {
											
											 boolean isCorrectSize = true;
											 while(isCorrectSize)
											 {
												 JPasswordField PF = new JPasswordField();
												 int optionOk = JOptionPane.showConfirmDialog(null, PF, "Enter new Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE );
														if(optionOk == JOptionPane.OK_OPTION) 
														{
															String newPassword = new String(PF.getPassword());
												 			if(newPassword.length() < 6 || newPassword.length() > 10)
															 {
																 JOptionPane.showMessageDialog(card, "Password Has to be greater than six and less than ten characters ");
															 }
															 else
															 {
																 //update database here
																 updatePassword(staffId, newPassword,  db);
			
																 JOptionPane.showMessageDialog(card, "You new password has been updated");
																 isCorrectSize = false;
																 isPasswordRight = false;
																 return;
															 }
														}
														else
														{
															return;
														}
											 }
											 

										 }
										 else
										 {
											 JOptionPane.showMessageDialog(card, "You typed in the wrong password");
										 }
							}
						  }
						}
						else{
							 JOptionPane.showMessageDialog(card, "Staff Identification Number is wrong");
						}
							
					} catch (SQLException e) {	
						e.printStackTrace();}
				  
				}
			
			}
		   } catch(NullPointerException e1)
			   {
				   return;
			   }
		}
	}
	
	private void updatePassword(String staffId, String NewPassword, DatabaseHelper db) throws SQLException
	{
		String sql = "UPDATE admin_login SET password ='"+NewPassword+"' WHERE staff_id = '"+staffId+"'";
		db.ExecuteStatement(sql);
	}
	//checking if a string contains only integer
	private boolean isInteger(String s)
	{
		 boolean isNumber = true;
		try{
				int n =  Integer.parseInt(s);
		   }catch(NumberFormatException n)
		   {
			   isNumber = false;
		   }

		return isNumber;
	}
	  
	public JPanel getCard() {
		return card;
	}

	public void setCard(JPanel card) {
		this.card = card;
	}

	public JButton getScheduleHours() {
		return ScheduleHours;
	}

	public void setScheduleHours(JButton scheduleHours) {
		ScheduleHours = scheduleHours;
	}

	public JButton getScheduledHours() {
		return ResetPassword;
	}

	public void setScheduledHours(JButton resetPassword) {
		ResetPassword = resetPassword;
	}

	public JButton getAddEmployee() {
		return AddEmployee;
	}

	public void setAddEmployee(JButton addEmployee) {
		AddEmployee = addEmployee;
	}

	public JButton getEmployees() {
		return Employees;
	}

	public void setEmployees(JButton employees) {
		Employees = employees;
	}

	public JButton getBreaks() {
		return Breaks;
	}

	public void setBreaks(JButton breaks) {
		Breaks = breaks;
	}

	public JButton getClose() {
		return close;
	}


}
