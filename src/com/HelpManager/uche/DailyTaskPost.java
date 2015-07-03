package com.HelpManager.uche;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.HelpManager.holders.staffDetailsHolder;


/*
 * this class is used to post Task daily. 
 */
public class DailyTaskPost {
	final static int extraWindowWidth = 100;
	
	 private JPanel card ;
	 private JTextArea textArea;
	 private JButton post;
	 private JLabel Title;
	 private JLabel Message;
	 private JLabel EmployeeNames;
	 
	 private JTextField title;
	 private JTextArea login;
	 private JTextArea logout;
	 GridBagConstraints gbc ;
	 DatabaseHelper db;
	private JList employeeList;
	 
	public DailyTaskPost()
	  {
		 //set layout
		  gbc = new GridBagConstraints(); 
		  
		  employeeList = new JList();
			employeeList.setLayoutOrientation(JList.VERTICAL);
			employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			employeeList.setPreferredSize(new Dimension(50, 500));
			JScrollPane listScroller = new JScrollPane(employeeList);
			listScroller.setPreferredSize(new Dimension(200, 200));
			
		  EmployeeNames = new JLabel("EMPLOYEE NAMES");
		  Title = new JLabel("TITLE ");
		  Message = new JLabel("MESSAGE");
	      textArea = new JTextArea("");
	      textArea.setPreferredSize(new Dimension(250, 250));
	      textArea.setLineWrap(true);
	      post = new JButton("Post");
	      title = new JTextField("", 30);

	      
	      db = new DatabaseHelper();
	        
	    
	         card = new JPanel() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				//Make the panel wider than it really needs, so
	            //the window's wide enough for the tabs to stay
	            //in one row.
	            public Dimension getPreferredSize() {
	                Dimension size = super.getPreferredSize();
	                size.width += extraWindowWidth;
	                return size;
	            }
	        };
	        
	        getEmployeeNames();
	  }
	  
	  /*
	   * using Gridbaglayout to set the position all components added to this Jpanel
	   */
		public void addComponents() 
		{
			gbc.gridx = 0;
		    gbc.gridy = 2;
		    gbc.anchor = GridBagConstraints.NORTHWEST;
		    gbc.insets = new Insets(10, 10, 0, 0);
			card.add(EmployeeNames, gbc);
			
		
			gbc.gridx = 0;
		    gbc.gridy = 2;
		    gbc.anchor = GridBagConstraints.WEST;
		    gbc.insets = new Insets(30, 10, 0, 0);
			card.add(employeeList, gbc);
			
		
			gbc.gridx = 1;
		    gbc.gridy = 0;
		    gbc.anchor = GridBagConstraints.WEST;
		    gbc.insets = new Insets(0, 10, 0, 0);
			card.add(Title, gbc);
			
			gbc.gridx = 1;
		    gbc.gridy = 1;
		    gbc.anchor = GridBagConstraints.CENTER;
		    gbc.insets = new Insets(0, 10, 0, 0);
		    gbc.fill = GridBagConstraints.HORIZONTAL;
			card.add(title, gbc);
			
			gbc.gridx = 1;
		    gbc.gridy = 2;
		    gbc.anchor = GridBagConstraints.NORTHWEST;
		    gbc.insets = new Insets(10, 10, 0, 0);
			card.add(Message, gbc);
			
			gbc.gridx = 1;
		    gbc.gridy = 2;
	
		    gbc.insets = new Insets(30, 10, 0, 0);
		    card.add(textArea, gbc);
		    
		    gbc.gridx = 1;
		    gbc.gridy = 3;
		    gbc.insets = new Insets(0, 10, 0, 0);
		    card.add(post, gbc);
		}
	  /*
	   * setters and getter of variables
	   */
	  public JPanel getCard() {
		return card;
	}

	public void setCard(JPanel card) {
		this.card = card;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public JButton getPost() {
		return post;
	}

	public void setPost(JButton post) {
		this.post = post;
	}

	public JTextField getTitle() {
		return title;
	}

	public void setTitle(JTextField title) {
		this.title = title;
	}

	 /*
	  * tigers the post button to post message on the Notice Board
	  */
	  public void PostButtonActionListener(){
	  
		  post.addActionListener(new ActionListener(){			
			public void actionPerformed(ActionEvent e) {
				
				
				int index = employeeList.getSelectedIndex();
				
				if(index == -1)
				{
					JOptionPane.showMessageDialog(card, "Please Select a name From Employee list to Post message", "Error Message",  JOptionPane.ERROR_MESSAGE);
				}
				else{
					String names = (String) employeeList.getModel().getElementAt(employeeList.getSelectedIndex());
					
					
					String sql1 = "SELECT staff_id FROM staff_details WHERE names = '"+names+"'";
						
					
						
						
						try {
							 ResultSet result = db.ExecuteSql(sql1);
							 
							 result.next();
							String sql= "INSERT INTO notice_Board (TaskTitle, message, staff_idd) VALUES ('"+title.getText()+"','"+textArea.getText()+"','"+result.getInt(1)+"') ";
							db.ExecuteStatement(sql);
							
							
							title.setText("");
							textArea.setText("");
							
							JOptionPane.showMessageDialog(card, "Message has been posted to Notice Board", "Information",  JOptionPane.INFORMATION_MESSAGE);
						} catch (SQLException e1) {
						
							e1.printStackTrace();
						}
			}
		}
			  
		  });
	  }
	  
	  public void getEmployeeNames()
		{
		  Calendar date = Calendar.getInstance();
		  int year = date.get(Calendar.YEAR);
		  int week = date.get(Calendar.WEEK_OF_YEAR);
		  String day = dayNumber(date.get(Calendar.DAY_OF_WEEK));
		 //String DAY = "Saturday";
			
			ArrayList<Integer> staffIds = new ArrayList<Integer>();
			ArrayList<String> names = new ArrayList<String>();
			String sql = "SELECT staff_id FROM scheduleHours_week"+week+"_"+year+" WHERE  "+day+"_hours != '00.00-00.00'";
			
		
			try {
					
					ResultSet result = db.ExecuteSql(sql);
					
					while(result.next())
					{
						staffIds.add(result.getInt(1));
					}
					
				} catch (SQLException e) {
					///e.printStackTrace();
				}
			
			  for(int loop = 0; loop < staffIds.size(); loop++)
			  {
				  names.add(getNames(staffIds.get(loop)));
			  }
			putToList(names);

			
		}
		
		private void putToList(ArrayList<String> holderList)
		{
			
			DefaultListModel anItemAdd = new DefaultListModel();
		
			
			for(int loop =0; loop< holderList.size(); loop++)
			{
				
				
				anItemAdd.addElement(holderList.get(loop));
			}
			
			employeeList = new JList(anItemAdd);
			employeeList.setLayoutOrientation(JList.VERTICAL);
			employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			employeeList.setPreferredSize(new Dimension(150, 250));
			employeeList.setLayout(new GridBagLayout());
			
			JScrollPane listScroller = new JScrollPane(employeeList);
			listScroller.setPreferredSize(new Dimension(200, 200));

		}
		
		private String dayNumber(int day)
		{
		
			if(day == 1)
			{
				return "sunday";
			}
			else if(day == 2)
			{
				return "Monday";
			}
			else if(day == 3)
			{
				return "Tuesday";
			}
			else if(day == 4)
			{
				return "Wednesday";
			}
			else if(day == 5)
			{
				return "Thursday";
			}
			else if(day == 6)
			{
				return "Friday";
			}
			else if(day == 7)
			{
				return "Saturday";
			}
			return null;
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
		
		
}
