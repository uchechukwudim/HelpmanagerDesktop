package com.HelpManager.uche;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdateEmployeeDetails {
	private JPanel card;
    
	static int extraWindowWidth = 100;
	Employees employee;
	 
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
	
	private GridBagConstraints gbc;

	

	
	public UpdateEmployeeDetails()
	{
		 employee = new Employees();
		 
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
		 
		 add = new JButton("Update");
		 clear = new JButton("Finish");
		 
		gbc = new GridBagConstraints();
		
		
		card = new JPanel(){
			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
               Dimension size = super.getPreferredSize();
               size.width += extraWindowWidth;
               return size;
           }
       };
       
   
	}
	
	public void addComponents()
	{
		//Staff Identification positioning
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.WEST;
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
	
	//grab the details of whatever staff id passed to it, the set the fields to what it grabbed on the database
	public void GrabDetailsForUpdate(String staff_id)
	{
		
		DatabaseHelper db = new DatabaseHelper();
		
		String sql = "SELECT * FROM staff_details WHERE staff_id = '"+staff_id+"'";
		
		try {
				ResultSet result = db.ExecuteSql(sql);
				
				result.next();
				
				Staff_ID.setText(result.getString(1));
				Staff_ID.setEditable(false);
				Staff_Names.setText(result.getString(2));
				Staff_Address.setText(result.getString(3));
				Staff_Phone.setText(result.getString(4));
				Staff_Email.setText(result.getString(5));
				Staff_Position.setText(result.getString(7));
				
				//setting text for contract list
				if(result.getString(6).equals("Full Time                  "))
				{
					Staff_Contract.setSelectedIndex(1);
				}
				else if(result.getString(6).equals("Part Time"))
				{
					Staff_Contract.setSelectedIndex(2);
				}
				
				//setting text for rank list
				if(result.getString(8).equals("1"))
				{
					Staff_Rank.setSelectedIndex(1);
				}
				else if(result.getString(8).equals("2"))
				{
					Staff_Rank.setSelectedIndex(2);
				}
				else{
					 Staff_Rank.setSelectedIndex(3);
				}
			db.closeConnection();
			result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}
	
	public void addUpdatedDetails()
	{
		DatabaseHelper db = new DatabaseHelper();
		String staffid = Staff_ID.getText();
		String names = Staff_Names.getText();
		String address = Staff_Address.getText();
		String email = Staff_Email.getText();
		String phone = Staff_Phone.getText();
		String position = Staff_Position.getText();
		String contract = (String) Staff_Contract.getSelectedItem();
		String rank = (String) Staff_Rank.getSelectedItem();
		//check when required field is empty
				if(names.isEmpty() || staffid.isEmpty() || contract.equals("Choose Option") || rank.contentEquals("Choose Option") || position.isEmpty())
				{
					JOptionPane.showMessageDialog(card, "Field with * have to be filled");
				}
				else if(!email.isEmpty() && !EmailValidator(email)) //check when email format is not valid
				{
					JOptionPane.showMessageDialog(card, "Email is not in the right format");
				}
				else
				{ 
					int rankInt = Integer.parseInt(rank.substring(0, 1));
					String sql = "UPDATE staff_details SET staff_id = '"+staffid+"', names = '"+names+"', address = '"+address+"', phone = '"+phone+"'," +
							"email = '"+email+"', contract = '"+contract+"', position = '"+position+"', positionRank = '"+rankInt+"' WHERE staff_id = '"+staffid+"'";
					try {
							db.ExecuteStatement(sql);
							GrabDetailsForUpdate(staffid);
							JOptionPane.showMessageDialog(card, names+" details has been updated");
						} catch (SQLException e) {
							e.printStackTrace();
						}
				}
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

	public JButton getClear() {
		return clear;
	}
	
	
}
