package com.HelpManager.uche;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.*;

public class addEmployee {
	private JPanel card;

	 static int extraWindowWidth = 100;
	
	 final static String[] Contract =  {"Choose Option","Full Time                  ", "Part Time"};
	 final static String [] Rank = {"Choose Option","1-Manager", "2-Assistant Manager", "3-Other"};
	 
	private JLabel StaffID;
	private JTextField Staff_ID;
	private JLabel StaffNames;
	private JTextField Staff_Names;
	private JLabel StaffAddress;
	private JTextField Staff_Address;
	private JLabel StaffPhone;
	private JTextField Staff_Phone;
	private JLabel StaffEmail;
	private JTextField Staff_Email;
	
	private JLabel StaffContract;
	private JComboBox Staff_Contract;
	
	private JLabel StaffPosition;
	private JTextField Staff_Position;
	
	private JLabel StaffRank;
	private JComboBox Staff_Rank;
	private JButton add;
	private JButton clear;
	 private JButton close;
	 
	GridBagConstraints gbc;
	DatabaseHelper db;
	
	public addEmployee()
	{
		
		 StaffID = new JLabel("Staff ID*:      ");
		 Staff_ID = new JTextField("", 40);
		 StaffNames = new JLabel("Staff Names*:   ");
		 Staff_Names = new JTextField("", 40);
		 StaffAddress = new JLabel("Staff Address: ");
		 Staff_Address = new JTextField("", 40);
		 StaffPhone = new JLabel("Staff Phone:    ");
	     Staff_Phone = new JTextField("", 40);
	     StaffEmail  = new JLabel("Staff Email:    ");
		 Staff_Email = new JTextField("", 40);
		 
		 StaffContract = new JLabel("Staff Contract*: ");
		 Staff_Contract = new JComboBox(Contract);
		 
		 StaffPosition = new JLabel("Staff Position*:  ");
		 Staff_Position  = new JTextField("", 40);
		 
		 StaffRank = new JLabel("RANK*: ");;
		 Staff_Rank = new JComboBox(Rank);
		 
		 add = new JButton("ADD");
		 clear = new JButton("CLEAR");
		 
		 close = new JButton("Close X");
		 close.setOpaque(true);
		 close.setForeground(Color.red);
		  
		gbc = new GridBagConstraints();
		db = new DatabaseHelper();
		
		card = new JPanel(){
			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };
        
        
       
        //ghost worker
		GHOST();

        
	}
	
	public void addComponents()
	{
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.insets = new Insets(-40, 30, 0, 0);
		card.add(close, gbc);
		//Staff Identification positioning
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(40, 0, 0, 0);
	    card.add(StaffID, gbc);
	    gbc.gridx = 1;
		gbc.gridy = 0;
	    card.add(Staff_ID, gbc);
	    
	    //staff names positioning
	    gbc.gridx = 0;
		gbc.gridy = 1;
	    card.add(StaffNames, gbc);
	    gbc.gridx = 1;
		gbc.gridy = 1;
	    card.add(Staff_Names, gbc);
	    
	    //staff address positioning
	    gbc.gridx = 0;
		gbc.gridy = 2;
	    card.add(StaffAddress, gbc);
	    gbc.gridx = 1;
		gbc.gridy = 2;
	    card.add(Staff_Address, gbc);
	    
	    //staff phone positioning
	    gbc.gridx = 0;
		gbc.gridy = 3;
	    card.add(StaffPhone, gbc);
	    gbc.gridx = 1;
		gbc.gridy = 3;
	    card.add(Staff_Phone, gbc);
	    
	    //staff email positioning
	    gbc.gridx = 0;
		gbc.gridy = 4;
	    card.add(StaffEmail, gbc);
	    gbc.gridx = 1;
		gbc.gridy = 4;
	    card.add(Staff_Email, gbc);
	    
	    //staff position positioning
	    gbc.gridx = 0;
		gbc.gridy = 5;
	    card.add(StaffPosition, gbc);
	    gbc.gridx = 1;
		gbc.gridy = 5;
	    card.add(Staff_Position, gbc);
	    
	    //staff contract positioning
	    gbc.gridx = 0;
		gbc.gridy = 6;
	    card.add(StaffContract, gbc);
	    gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.WEST;
	    card.add(Staff_Contract, gbc);
	    
	    //staff ranking positioning
	    gbc.gridx = 0;
		gbc.gridy = 7;
	    card.add(StaffRank, gbc);
	    gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.WEST;
	    card.add(Staff_Rank, gbc);
	    
	    gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.anchor = GridBagConstraints.WEST;
	    card.add(add, gbc);
	    
	    gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.anchor = GridBagConstraints.EAST;
	    card.add(clear, gbc);
	}
	
	/*
	 * when add button is clicked it uses the AddButtonWorker to do what it is supposed to do
	 */
	private void AddButtonListener()
	{
		add.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				AddEmployeeValidator();

			}
			
		});
	}
	
	/*
	 * this method uses SwingWorker to check for validation for adding an employee
	 * and then adds the employee to the database when every requirement is met
	 */
	private void GHOST()
	{
		SwingWorker<Void, Void> BackGroundWorker = new SwingWorker<Void, Void>(){
			protected Void doInBackground() throws Exception {
				
				//things not to do when trying to add employee
				AddButtonListener();
				
				clearFields();
				return null;
			}
		};
		BackGroundWorker.execute();
	}
	
	//add functions methods here
	
	private void AddEmployeeValidator()
	{

		String names = Staff_Names.getText();
		String Id = Staff_ID.getText();
		String contract = (String) Staff_Contract.getSelectedItem();
		String rank = (String) Staff_Rank.getSelectedItem();
		String position = Staff_Position.getText();
		
		String address = Staff_Address.getText();
		String phone = Staff_Phone.getText();
		String email = Staff_Email.getText();
		
		//check when required field is empty
		if(names.isEmpty() || Id.isEmpty() || contract.equals("Choose Option") || rank.contentEquals("Choose Option") || position.isEmpty())
		{
			JOptionPane.showMessageDialog(card, "Field with * have to be filled");
		}
		else if(!isInteger(Id)) //check for when  staff id is not a number
		{
			JOptionPane.showMessageDialog(card, "Field  ID is not a Number");
		}
		else if(!email.isEmpty() && !EmailValidator(email)) //check when email format is not valid
		{
			JOptionPane.showMessageDialog(card, "Email is not in the right format");
		}
		else if(Id.length() >6 || Id.length()< 3)// c
		{
			JOptionPane.showMessageDialog(card, "Staff ID must be up to 3 to 6 numbers");
		}
		else if(IDValidator(Id))
		{
			JOptionPane.showMessageDialog(card, "Staff ID already exist please choose a different ID");

		}
		else
		{ 
			     if(Integer.parseInt(rank.substring(0, 1)) == 1 && email.isEmpty() || Integer.parseInt(rank.substring(0, 1)) ==2 && email.isEmpty())
			     {
			    	 JOptionPane.showMessageDialog(card, "Email has to be filled for the Rank Manager");
			     }
			     else
			     {
			    	 addEmployeeToDataBase(Id, names, address, phone, email, position, contract, rank);
			     }
	
				
		}
	}

	
	private void addEmployeeToDataBase(String id, String names, String address,
			String phone, String email, String position, String contract2,
			String rank2) 
	{
		int rankInt = Integer.parseInt(rank2.substring(0, 1));
		String sql_into_sd = "INSERT INTO staff_details (staff_id, names, address, phone, email, contract, position, positionRank) VALUES " +
				"('"+id+"','"+names+"','"+address+"','"+phone+"','"+email+"','"+contract2+"','"+position+"','"+rankInt+"')";
		
		
		//SQL to insert into staff_login
		String  sql_into_sl = "INSERT INTO staff_login(staff_id, isActive) VALUES ('"+id+"','0')";
		
		//SQL to insert into admin_login
		String sql_into_al = "INSERT INTO admin_login (staff_id, password, isLogedin) VALUES ('"+id+"','admin','0')";
		
			try {
					db.ExecuteStatement(sql_into_sd);
					db.ExecuteStatement(sql_into_sl);
					
					
					if(rank2.charAt(0)=='1' || rank2.charAt(0)=='2')
					{
						db.ExecuteStatement(sql_into_al);
					}
					
					JOptionPane.showMessageDialog(card, ""+names+" has been add as an Employee");
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			
			
			
	}
	
	public void clearFields()
	{
		
		clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Staff_ID.setText("");
				 Staff_Names.setText("");
				 Staff_Address.setText("");
			     Staff_Phone.setText("");
				 Staff_Email.setText("");
				 Staff_Position.setText("");
				 Staff_Contract.setSelectedIndex(0);
				 Staff_Rank.setSelectedIndex(0);
				
			}
			
		});
		 
		 	}
	
	private boolean IDValidator(String Staff_Id)
	{
		String sql_toget_StaffId = "SELECT staff_id FROM staff_details";
		ArrayList<Integer> ExistingStaffId = new ArrayList<Integer>();
		try {
		
					ResultSet result = db.ExecuteSql(sql_toget_StaffId);
					
					while(result.next())
					{
						ExistingStaffId.add(result.getInt(1));
					}
					
					if(ExistingStaffId.contains(Integer.parseInt(Staff_Id)))
					{
						return true;
					}
			} 
			catch (SQLException e) {}
		//db.close();
		return false;
		
		
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

	
	//checking if email format is correct
	private boolean EmailValidator(String email)
	{
		 String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
		 Pattern pt = Pattern.compile(regEx);
		 
		 Matcher match = pt.matcher(email);
		 
		 return match.find();
	}

	public JPanel getCard() {
		return card;
	}

	public JButton getAdd() {
		return add;
	}

	public JButton getClose() {
		return close;
	}
	
	
	
}
