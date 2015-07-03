package com.HelpManager.uche;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import com.HelpManager.holders.ScheduleHoursHolder;
import com.HelpManager.holders.YearWeeksHolder;
import com.HelpManager.uche.ScheduledHoursView.MyTableModel;


public class BreakView {
	 private JPanel card;
	 private JTable Hours;
	 private String [] colunmNames; 
	 private Object [][] data;
	 private JTableHeader header;
	 private GridBagConstraints gbc;
	 private JButton finish;
	 private JButton Print;
	 private JButton ChooseWeek;
	 private String Week_Number;
	 private JLabel Week;
	 private JLabel WeekDates;

	 final static int extraWindowWidth = -500;
	  private boolean DEBUG = false;
	public BreakView(int week)
	{
		card = new JPanel(){
			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
	              Dimension size = super.getPreferredSize();
	              size.width += extraWindowWidth;
	              return size;
			  } 
		  };
		  
		
			this.colunmNames = ColunmNames(week);
		    this.data =  populateData(week+"");
		    
		    Week = new JLabel("Week: "+ week);
		    WeekDates = new JLabel("Date From: "+ ColunmNames(week)[1]+" To: "+ColunmNames(week)[7]);
		    
		    if(!isTabelCreated(week))
		    {
		    	Object[][] object = new Object[1][9];
		    	for(int loop = 0; loop < object.length; loop++)
		    	{
		    		for(int loop2 = 0; loop2 < 9; loop2++)
		    		{
		    		 object[loop][loop2] = "";
		    		}
		    	}
		    	Hours = new JTable( object, colunmNames);
		    	
		    }
		    else{
		    	Hours = new JTable(new MyTableModel(data, colunmNames));
		    }
			
			Hours.setPreferredScrollableViewportSize(new Dimension(500, 70));
			Hours.setFillsViewportHeight(true);
			Hours.enableInputMethods(false);
			Hours.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			
			Hours.getColumnModel().getColumn(0).setPreferredWidth(150);
			Hours.getColumnModel().getColumn(1).setPreferredWidth(100);
			Hours.getColumnModel().getColumn(2).setPreferredWidth(100);
			Hours.getColumnModel().getColumn(3).setPreferredWidth(100);
			Hours.getColumnModel().getColumn(4).setPreferredWidth(100);
			Hours.getColumnModel().getColumn(5).setPreferredWidth(100);
			Hours.getColumnModel().getColumn(6).setPreferredWidth(100);
			Hours.getColumnModel().getColumn(7).setPreferredWidth(100);
			Hours.getColumnModel().getColumn(8).setPreferredWidth(100);
			Hours.setShowGrid(true);
			Hours.setGridColor(Color.black);
			header = Hours.getTableHeader();
			finish = new JButton("Finish");
			Print = new JButton("Print");
			ChooseWeek = new JButton("Choose Week");
		
			gbc = new GridBagConstraints();
			
			
	
	}
	
	public void AddComponents()
	{
		
		gbc.gridx = 2;
	    gbc.gridy = 0;
	    gbc.insets = new Insets(-150, -300, 0, 0);
	 	card.add(WeekDates, gbc);
		
		gbc.gridx = 0;
	     gbc.gridy = 0;
	     gbc.insets = new Insets(-150, 50, 0, 0);
	 	card.add(Week, gbc);
	 	
	 
	 	
		gbc.gridx = 1;
	     gbc.gridy = 0;
	     gbc.insets = new Insets(0, -60, 0, 0);
	 	card.add(header, gbc);
	 	
	  
		gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.insets = new Insets(0, -60, 0, 0);
		card.add(Hours, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 3;
	    gbc.anchor = GridBagConstraints.WEST;
		card.add(ChooseWeek, gbc);

		gbc.gridx = 1;
	    gbc.gridy = 3;
	    gbc.anchor = GridBagConstraints.EAST;
		card.add(Print, gbc);
	}
	
	public void PrintButtonListener()
	{
		Print.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				 Print();
			}
			
		});
	}
	
	public void Print()
	{
		MessageFormat header = new MessageFormat("WEEK "+ Week_Number+" HOURS");
		
		MessageFormat footer = new MessageFormat("Page {0,number,Integer}");
		
		try {
				Hours.print(JTable.PrintMode.NORMAL, header, footer);
			} catch (PrinterException e) {
				e.printStackTrace();
			}
	}

	public String[] ColunmNames(int week)
	{
		SimpleDateFormat [] sdf = new SimpleDateFormat[7];
		String [] colunmNames = new String[9];
		colunmNames[0] = "Names";
		colunmNames[8] = "Total Hours";
		int index = 1;
		for(int loop = 0; loop < sdf.length; loop++)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.WEEK_OF_YEAR, week);        
			cal.set(Calendar.DAY_OF_WEEK, index);
			sdf[loop] = new SimpleDateFormat("EEE, MMM d, yy");
			colunmNames[index] = sdf[loop].format(cal.getTime()).toString(); 
			index++;
		}
		
		
		return colunmNames;
	}

	public JTable getHours() {
		return Hours;
	}

	public void setHours(JTable hours) {
		Hours = hours;
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

	public JPanel getCard() {
		return card;
	}

	public void setColunmNames(String[] colunmNames) {
		this.colunmNames = colunmNames;
	}
	
	public String[] getColunmNames() {
		return colunmNames;
	}
	
	
	
	public JLabel getWeek() {
		return Week;
	}

	public JLabel getWeekDates() {
		return WeekDates;
	}

	public JButton getChooseWeek() {
		return ChooseWeek;
	}

   public void setWeek(String week_Number)
   {
   	Week_Number = week_Number;
   }

   class MyTableModel extends AbstractTableModel {
		  
		private String[] columnNames ;
       private Object[][] data ;
       public MyTableModel(Object [][] data, String [] ColumnNames)
       {
       	this.columnNames = ColumnNames;
       	this.data = data;
       }
       public int getColumnCount() {
           return columnNames.length;
       }

       public int getRowCount() {
           return data.length;
       }

       public String getColumnName(int col) {
           return columnNames[col];
       }

       public Object getValueAt(int row, int col) {
           return data[row][col];
       }

       /*
        * JTable uses this method to determine the default renderer/
        * editor for each cell.  If we didn't implement this method,
        * then the last column would contain text ("true"/"false"),
        * rather than a check box.
        */
    
       /*
        * Don't need to implement this method unless your table's
        * editable.
        */
       public boolean isCellEditable(int row, int col) {
           //Note that the data/cell address is constant,
           //no matter where the cell appears onscreen.
        
               return false;
           
       }

       /*
        * Don't need to implement this method unless your table's
        * data can change.
        */
      
       public void setValueAt(Object value, int row, int col) {
           if (DEBUG) {
               System.out.println("Setting value at " + row + "," + col
                                  + " to " + value
                                  + " (an instance of "
                                  + value.getClass() + ")");
           }

           data[row][col] = value;
           fireTableCellUpdated(row, col);

           if (DEBUG) {
               System.out.println("New value of data:");
               printDebugData();
           }
       }
 
       private void printDebugData() {
           int numRows = getRowCount();
           int numCols = getColumnCount();

           for (int i=0; i < numRows; i++) {
               System.out.print("    row " + i + ":");
               for (int j=0; j < numCols; j++) {
                   System.out.print("  " + data[i][j]);
               }
               System.out.println();
           }
           System.out.println("--------------------------");
       }
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
	private void putIntoWeeksHolder(YearWeeksHolder holder, int Wnum, boolean isSched)
	  {
		  holder.setIndex(Wnum, isSched);
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
	  
	  public Object [][] populateData(String weekNumber)
		{
			Calendar date = Calendar.getInstance();
			  int year = date.get(Calendar.YEAR);
			DatabaseHelper db = new DatabaseHelper();
			ArrayList<ScheduleHoursHolder> data1 = new ArrayList<ScheduleHoursHolder>();
		
			String sql = "SELECT staff_id, Monday_break, Tuesday_break, wednesday_break, thursday_break, friday_break, saturday_break, sunday_break, total_break" +
					" FROM  scheduleLaunchBreak_week"+weekNumber+"_"+year+" " +
					"UNION " +
					"SELECT staff_id, Monday_break, Tuesday_break, wednesday_break, thursday_break, friday_break, saturday_break, sunday_break, total_break" +
					" FROM  scheduleBreak_week"+weekNumber+"_"+year+" " +
					"ORDER BY  `staff_id` ASC ";
			
			Object [][] breaks =null;
		
			try {
					ResultSet result = db.ExecuteSql(sql);
					
					while(result.next())
					{
						
						ScheduleHoursHolder holder1 = new ScheduleHoursHolder();
					    int staff_id =   result.getInt(1);
					    holder1.setStaff_id(staff_id);
					    holder1.setNames(getNames(staff_id));
					    holder1.setADayOfTheWeekHour(0, result.getString(2));
					    holder1.setADayOfTheWeekHour(1, result.getString(3));
					    holder1.setADayOfTheWeekHour(2, result.getString(4));
					    holder1.setADayOfTheWeekHour(3, result.getString(5));
					    holder1.setADayOfTheWeekHour(4, result.getString(6));
					    holder1.setADayOfTheWeekHour(5, result.getString(7));
					    holder1.setADayOfTheWeekHour(6, result.getString(8));
					    holder1.setTotalHours(result.getDouble(9));
					   
					    
					    result.next();
					    ScheduleHoursHolder holder2 = new ScheduleHoursHolder();
					    int staff_id2 =   result.getInt(1);
					    holder2.setStaff_id(staff_id2);
					    holder2.setNames(getNames(staff_id));
					    holder2.setADayOfTheWeekHour(0, result.getString(2));
					    holder2.setADayOfTheWeekHour(1, result.getString(3));
					    holder2.setADayOfTheWeekHour(2, result.getString(4));
					    holder2.setADayOfTheWeekHour(3, result.getString(5));
					    holder2.setADayOfTheWeekHour(4, result.getString(6));
					    holder2.setADayOfTheWeekHour(5, result.getString(7));
					    holder2.setADayOfTheWeekHour(6, result.getString(8));
					    holder2.setTotalHours(result.getDouble(9));
					   
					    //process the data's and then add them accordingly to arraylist of "breaks"
					    processData(holder1,  holder2, data1);
					    
					}
					
				} catch (SQLException e)
				{
					//e.printStackTrace();
				}
	
				
			return populateBreakArray(data1);
		}
	


	private Object[][] populateBreakArray(ArrayList<ScheduleHoursHolder> data1) {
		String [][] breaks = new String [data1.size()][9];
		for(int loopTop = 0; loopTop < data1.size(); loopTop++)
		{
			
				breaks[loopTop][0] = data1.get(loopTop).getNames();;
				breaks[loopTop][1] =  data1.get(loopTop).getADayOfTheWeekHour(6);
				breaks[loopTop][2] = data1.get(loopTop).getADayOfTheWeekHour(0);
				breaks[loopTop][3] = data1.get(loopTop).getADayOfTheWeekHour(1);
				breaks[loopTop][4] = data1.get(loopTop).getADayOfTheWeekHour(2);
				breaks[loopTop][5] = data1.get(loopTop).getADayOfTheWeekHour(3);
				breaks[loopTop][6] = data1.get(loopTop).getADayOfTheWeekHour(4);
				breaks[loopTop][7] = data1.get(loopTop).getADayOfTheWeekHour(5);	   
				breaks[loopTop][8] = data1.get(loopTop).getTotalHours()+""; 
				
				
		}
		return breaks;
	}

	private void processData(ScheduleHoursHolder holder1, ScheduleHoursHolder holder2, 
			ArrayList<ScheduleHoursHolder> data) {
	
		   if(isDataTheSame(holder1, holder2))
		   {
			   ScheduleHoursHolder holder = new ScheduleHoursHolder ();
			   
			   holder.setStaff_id(holder1.getStaff_id());
			   holder.setNames(holder1.getNames());
			   
			   int count = 0;
			 
			   while(count <= 6){
				   double num = Double.parseDouble(holder1.getADayOfTheWeekHour(count).substring(0, 5));
				   double num2 = Double.parseDouble(holder2.getADayOfTheWeekHour(count).substring(0, 5));
				  
				   if(num != 0.0 && num2 == 0.0)
				   {
					
					   holder.setADayOfTheWeekHour(count, holder1.getADayOfTheWeekHour(count));

				   }
				   else if (num == 0.0 & num2 != 0.0)
				   {
					 
					   holder.setADayOfTheWeekHour(count, holder2.getADayOfTheWeekHour(count));
				   }
				   
				   count++;
			   }
			   holder.setTotalHours(holder1.getTotalHours() + holder2.getTotalHours());
			   data.add(holder);
		   }
		   else
		   {
			   data.add(holder1);
			   data.add(holder2);
		   }
	}

	private boolean isDataTheSame(ScheduleHoursHolder holder1,
			ScheduleHoursHolder holder2) {
			
		if(holder1.getStaff_id() == holder2.getStaff_id())
			return true;
			else
				return false;
	
	}

	private String getNames(int staffId) {
		
			DatabaseHelper db = new DatabaseHelper();
			
			String sql = "SELECT names FROM staff_details WHERE staff_id = '"+staffId+"'";
			String name = "";
			try {
					ResultSet result = db.ExecuteSql(sql);
					result.next();
					name = result.getString(1);
					
				result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			db.closeConnection();
			
			return name;
		}
	
	private boolean isTabelCreated(int weekNumber)
	{
		DatabaseHelper db = new DatabaseHelper();
		String sql = "SELECT isTableCreated, isLBTableCreated, isBreakTableCreated FROM Schedule_Week_UpdateChecker WHERE week_Number = '"+weekNumber+"'";
		boolean isCreated = false;
		try {
				
				ResultSet result = db.ExecuteSql(sql);
				
				result.next();
				if(result.getBoolean(1) && result.getBoolean(2) && result.getBoolean(3))
				  isCreated = true;
				else
					isCreated = false;
			} catch (SQLException e) {
			e.printStackTrace();
			}
		db.closeConnection();
		return isCreated;
	}
}
