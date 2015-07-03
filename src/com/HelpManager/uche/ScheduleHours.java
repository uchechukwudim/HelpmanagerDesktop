package com.HelpManager.uche;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

import com.HelpManager.holders.staffDetailsHolder;

public class ScheduleHours extends HoursDatabaseManager{
	 private ArrayList<staffDetailsHolder> holder;
	 private JPanel card;
	 private JLabel WeekNumber;
	 private JLabel Week;
	 private JLabel [] name;
	 private int[] id;
	 private JComboBox [] daysOfWeek;
	 private JComboBox [] HoursStart;
	 private JComboBox [] HoursFinish;
	 private JComboBox [] MinutesStart;
	 private JComboBox [] MinutesFinish;
	 private JButton []add;
	 private JButton Finish;
	 private JLabel []To;
	 int count = 0;
	
	 GridBagConstraints gbc;
	 
	 private final static String [] DaysOfWeek = {"Days","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	 private final static String [] Hours = {"Hours","01", "02", "03", "04", "05", "06", "07", "08", "09", "10","11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
	 private final static String [] Minutes = {"Minutes","00", "15", "30", "45"};
	 
	 static int extraWindowWidth = 100;
	 
	 public ScheduleHours()
	 {
	

		 holder = new ArrayList<staffDetailsHolder>();
		 getStaffsDetails(holder);
		 
		 Week = new JLabel("Scheduling for Week: ");
		 WeekNumber = new JLabel();
		 To = new JLabel[holder.size()];
		
		 daysOfWeek = new JComboBox[holder.size()];
		 HoursStart = new JComboBox[holder.size()];
		 HoursFinish = new JComboBox[holder.size()];
		 MinutesStart = new JComboBox[holder.size()];
		 MinutesFinish = new JComboBox[holder.size()];
		 add = new JButton[holder.size()];
		 name = new JLabel[holder.size()];
		 id = new int[holder.size()];
		 Finish = new JButton("Finish");
		 Finish.setPreferredSize(new Dimension(100, 20));
		 
		 //populate hours, minutes, days of week add button
		 populate(daysOfWeek, DaysOfWeek);
		 populate(HoursStart, Hours);
		 populate(HoursFinish, Hours);
		 populate(MinutesStart, Minutes);
		 populate(MinutesFinish, Minutes);
		 populateTo(To);
		 populateAddBtn(add);
		 populateId(id, holder);
		 
		populateNames(name, holder);
		 
		 card = new JPanel(){
				private static final long serialVersionUID = 1L;

				public Dimension getPreferredSize() {
	                Dimension size = super.getPreferredSize();
	                size.width += extraWindowWidth;
	                return size;
	            }
	        };
	        
	    gbc = new GridBagConstraints(); 
	    
	    FinishButtoneListener();
	 }
	 

	public void AddComponenets()
	 {
		gbc.insets = new Insets(0,0,10,10);
		
		gbc.gridx = 0;
		 gbc.gridy = 0;
		 gbc.weighty = 1;
		 gbc.anchor = GridBagConstraints.WEST;
		 card.add(Week, gbc);
		 
		 gbc.gridx = 1;
		 gbc.gridy = 0;
		 gbc.weighty = 1;
		 gbc.anchor = GridBagConstraints.WEST;
		 card.add(WeekNumber, gbc);
		 int count = 1;
		 //this part will go into a for loop
		for(int loop = 0; loop< holder.size(); loop++)
		{
		 gbc.gridx = 0;
		 gbc.gridy = count;
		 card.add(name[loop], gbc );
		 
		 gbc.gridx = 1;
		 gbc.gridy = count;
		 card.add(daysOfWeek[loop], gbc );
		 
		 
		 gbc.gridx = 2;
		 gbc.gridy = count;
		 card.add(HoursStart[loop], gbc);
		 
		 gbc.gridx = 3;
		 gbc.gridy = count;
		 gbc.anchor = GridBagConstraints.WEST;
		 card.add(MinutesStart[loop], gbc);
		 
		 gbc.gridx = 4;
		 gbc.gridy = count;
		 card.add(To[loop], gbc);

		 
		 gbc.gridx = 5;
		 gbc.gridy = count;
		 card.add(HoursFinish[loop], gbc);
		 
		 gbc.gridx = 6;
		 gbc.gridy = count;
		 card.add(MinutesFinish[loop], gbc);
		 
		 gbc.gridx = 7;
		 gbc.gridy = count;
		 gbc.anchor = GridBagConstraints.EAST;
		 card.add(add[loop], gbc);
		 
		 count++;

		}
		
		 
		 gbc.gridx = 3;
		 gbc.gridy = count++;
		 card.add(Finish, gbc);
	 }
	 
	private void populateId(int[] id2, ArrayList<staffDetailsHolder> holder2) {
		 for(int loop =0; loop < id2.length; loop++)
		 {
			 id2[loop] = holder.get(loop).getStaff_Id();
		 }
		
	}
	 private void populateNames(JLabel[] name2,
				ArrayList<staffDetailsHolder> holder2) {
			 
			 for(int loop =0; loop < name2.length; loop++)
			 {
				 name2[loop] = new JLabel(holder.get(loop).getNames());
			 }
	}
	 
	 private void populateAddBtn(JButton[] addBtn) {
		 for(int loop =0; loop < addBtn.length; loop++)
		 {
			 addBtn[loop] = new JButton("Add Hours");
			 add[loop].setActionCommand(""+loop);
			 addBtn[loop].addActionListener(new BListener());
			
		 }
	}
	 
	 private void populateTo(JLabel[] toLabel) {
		 for(int loop =0; loop < toLabel.length; loop++)
		 {
			 toLabel[loop] = new JLabel("To");
		 }
	}


	private void populate(JComboBox[] ToPopulate, String[] PopulateWith) {
	
		 for(int loop =0; loop < ToPopulate.length; loop++)
		 {
			 ToPopulate[loop] = new JComboBox(PopulateWith);
		 }
	}

	private void getStaffsDetails(ArrayList<staffDetailsHolder> holder)
	 {
		 DatabaseHelper db = new DatabaseHelper();
		 String sql = "SELECT staff_id, Names FROM staff_details";
		 
		 try {
			 	ResultSet result = db.ExecuteSql(sql);
			 	
			 	while(result.next())
			 	{
			 		staffDetailsHolder hold = new staffDetailsHolder();
			 		
			 		hold.setStaff_Id(result.getInt(1));
			 		hold.setNames(result.getString(2));
			 		holder.add(hold);
			 	}
			 	
			 	result.close();
		 	 } catch (SQLException e)
		 	 {
		 		 e.printStackTrace();
		 	 }
		 
		 db.closeConnection();
	 }
	

	private void processSchedulerEntry(int index) {
	     
		String Day = (String) daysOfWeek[index].getSelectedItem();
		String startHours = (String) HoursStart[index].getSelectedItem();
		String finishHours = (String) HoursFinish[index].getSelectedItem();
		String startMinutes = (String) MinutesStart[index].getSelectedItem();
		String finishMinutes = (String) MinutesFinish[index].getSelectedItem();
		
		if(Day.equals("Days") || startHours.equals("Hours") || finishHours.equals("Hours") || startMinutes.equals("Minutes") || finishMinutes.equals("Minutes"))
		{
		
			
			JOptionPane.showMessageDialog(card, "Work time was not selected for\n\n NAME: "+ name[index].getText(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			Calendar date = Calendar.getInstance();
			int year = date.get(Calendar.YEAR);
			int week = date.get(Calendar.WEEK_OF_YEAR);
			int staff_id = id[index];
			
			//convert start and finish hours to double
			double startTime = Double.parseDouble(startHours+"."+startMinutes);
			double FinishTime = Double.parseDouble(finishHours+"."+finishMinutes);
			
			//making sure the user uses 24 hours clock
			if(startTime >= FinishTime)
			{
				JOptionPane.showMessageDialog(card, "Work time is 1:00-24:00 not 1:00-12:00 hours clock ", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else if(isPastDay(Day, Integer.parseInt(WeekNumber.getText())) && Integer.parseInt(WeekNumber.getText()) == week )
			{
				//display message that day has past
				JOptionPane.showMessageDialog(card, "You cannot schedule for past days", "ERROR", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				//convert start and finish hours to string version
				String stTime = startHours+":"+startMinutes;
				String finTime = finishHours+":"+finishMinutes;
				
				//get calculated given start and finish hours for total
				double total_hours = TimeCalculation(startTime, FinishTime, id[index], stTime, Day);
				
				//convert to update database
				String UpdatedayWorkHours = stTime+"-"+finTime;
				String [] InsertDayWorkHours = setDayHourPosition(Day, UpdatedayWorkHours);
			
				// then put into database by inserting or updating depending if a staff member is being updated for the first time in a week
				InsertOrUpdate(WeekNumber.getText(), year, staff_id,  Day, UpdatedayWorkHours, InsertDayWorkHours, total_hours, index, card, name[index].getText());
			}
			
		}
		
		
	}

	
	
	
	
  private double TimeCalculation(double startTime, double finishTime, int staff_id, String StartTime, String day) {
		
	  Calendar date = Calendar.getInstance();
	  int year = date.get(Calendar.YEAR);
	  double currentTotal = 0.0;
	  
	//get total from database and add with current total
	  DatabaseHelper db = new DatabaseHelper();
	  String sql = "SELECT Total_hours, "+day+"_hours FROM scheduleHours_week"+getWeekNumber().getText()+"_"+year+" WHERE staff_id = '"+staff_id+"'";
	  
	  try {
		  	ResultSet result = db.ExecuteSql(sql);
		  	if(result.next()){
			  	currentTotal = result.getDouble(1);
			  	
			  	double currentHours = Double.parseDouble(result.getString(2).subSequence(6, 8)+"."+result.getString(2).subSequence(9, 11)) - Double.parseDouble(result.getString(2).subSequence(0, 2)+"."+result.getString(2).subSequence(3, 5));
			  	
			  	double CurrentTotal = currentTotal - currentHours;
			  	double total = finishTime-startTime;
			  	
			  	CalculateBreakes(staff_id, total, StartTime, getWeekNumber().getText(), day, year);
		        currentTotal = total+CurrentTotal;
		        
			  	currentTotal = Math.round(currentTotal * 100.0 ) / 100.0;// round up to 2 decimal point
		  	}
		  	else{
		  		
		  		currentTotal = finishTime - startTime;
		  		
		  		CalculateBreakes(staff_id, currentTotal, StartTime, getWeekNumber().getText(), day, year);
		  	}
		  	
		  
		  	result.close();
		  	db.closeConnection();
	  	  } catch (SQLException e) {
	  		  e.printStackTrace();
	  	  }
	  db.closeConnection();
	  return currentTotal;
	}

 private void FinishButtoneListener()
 {
	 Finish.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			setWeekAsScheuled(WeekNumber.getText());
		}});
 }

	public JButton getFinish() {
		return Finish;
	}


	public JPanel getCard() {
		return card;
	}
	
	public JLabel getWeekNumber() {
		return WeekNumber;
	}



	public void setWeekNumber(String weekNumber) {
		WeekNumber.setText(weekNumber);
	}


	//Listener class for add hours buttons
	private class BListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			
			String c = event.getActionCommand();
			processSchedulerEntry(Integer.parseInt(c));
			
		}
		
	}
	
}
