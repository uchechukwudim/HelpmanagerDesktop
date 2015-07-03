package com.HelpManager.uche;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import com.HelpManager.holders.staffDetailsHolder;

public class Employees {
	
	JPanel card;
	private JList employeeList;
	private DatabaseHelper db;
	private GridBagConstraints gbc;
	private JButton Update;
	private JButton ViewDetails;
	private JLabel staffID;
	private JLabel Names;
	private  final static int extraWindowWidth = 100;
	JScrollPane listScroller;
	private JButton close;
	
	public Employees()
	{
		
		staffID = new JLabel("STAFF ID");
		Names = new JLabel("STAFF NAMES");
		
		employeeList = new JList();
		employeeList.setLayoutOrientation(JList.VERTICAL);
		employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//employeeList.setPreferredSize(new Dimension(500, 500));
		JScrollPane listScroller = new JScrollPane(employeeList);
		listScroller.setPreferredSize(new Dimension(300, 300));
		
		Update = new JButton("Update Detail");
		ViewDetails = new JButton("More Details");
		
	
		db = new DatabaseHelper();
		
		close = new JButton("Close X");
		 close.setOpaque(true);
		 close.setForeground(Color.red);
		 
		gbc = new GridBagConstraints();
		
		card = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 7978236871774767144L;

			public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };  
        
        getEmployeeDetails();
       
	}
	
	public void AddComponents()
	{
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.insets = new Insets(-350, 0, 0, 0);
		card.add(close, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
	 	gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(-20, 0, 0, 0);
		card.add(staffID, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(-20, 100, 0, 0);
		card.add(Names, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		card.add(listScroller, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
	
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		card.add(Update, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		card.add(ViewDetails, gbc);
		
	}
	
	public void getEmployeeDetails()
	{
		ArrayList<staffDetailsHolder> holderList = new ArrayList<staffDetailsHolder>();
		String sql = "SELECT staff_id, names, position FROM staff_details";
		
	
		try {
				
			ResultSet result = db.ExecuteSql(sql);
				
				while(result.next())
				{
					staffDetailsHolder holder = new staffDetailsHolder();
					
					holder.setStaff_Id(result.getInt(1));
					holder.setNames(result.getString(2));
					holder.setPosition(result.getString(3));
					holderList.add(holder);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		putToList(holderList);

		
	}
	
	private void putToList(ArrayList<staffDetailsHolder> holderList)
	{
		
		DefaultListModel anItemAdd = new DefaultListModel();
		
		for(int loop =0; loop< holderList.size(); loop++)
		{
			staffDetailsHolder holder = new staffDetailsHolder();
			
			holder = holderList.get(loop);
			
			anItemAdd.addElement(holder.getStaff_Id()+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+ holder.getNames());
		}
		
		employeeList = new JList(anItemAdd);
		employeeList.setLayoutOrientation(JList.VERTICAL);
		employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//employeeList.setPreferredSize(new Dimension(500, 500));
		employeeList.setLayout(new GridBagLayout());
		
		listScroller = new JScrollPane(employeeList);
		listScroller.setPreferredSize(new Dimension(300, 300));

	}
	
	//Update button  method to do
	

	// view more details method to do
	public void viewMoreDeatils()
	{
		if(employeeList.isSelectionEmpty())
		{
			JOptionPane.showMessageDialog(card, "Please Select an Item From The List", "Error Message",  JOptionPane.ERROR_MESSAGE);
		}
		else
		{
				String id = employeeList.getModel().getElementAt(employeeList.getSelectedIndex()).toString().substring(0, 4);
				
				DatabaseHelper db  = new DatabaseHelper();
				String sql = "SELECT * FROM staff_details WHERE staff_id = '"+id+"'";
				String rank = "";
				ResultSet result;
				try {
						result = db.ExecuteSql(sql);
						result.next();
						
						if(result.getString(8).equals("1"))
						{
							rank = "1-Manager";
						}
						else if(result.getString(8).equals("2"))
						{
							rank = "2-Assistant Manager";
						}
						else
						{
							rank = "3-Other";
						}
						
						String message = "Staff ID:  "+result.getString(1)+"\n" +
										 "Names:  "+result.getString(2)+"\n" +
										 "Address:  "+result.getString(3)+"\n" +
										 "Phone:  "+result.getString(4)+"\n" +
										 "Email:  "+result.getString(5)+"\n" +
										 "Position:  "+result.getString(6)+"\n"+
										 "Contract:  "+result.getString(7)+"\n" +
										 "Rank:  "+rank+"";
						String title = "Emplyee Details";
						
				
						JOptionPane.showMessageDialog(card, message, title, JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e) {
					e.printStackTrace();
					}
		}
		
		
	}
	
	public JPanel getCard() {
		return card;
	}

	public JButton getUpdate() {
		return Update;
	}

	public JButton getViewDetails() {
		return ViewDetails;
	}

	public JList getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(JList employeeList) {
		this.employeeList = employeeList;
	}

	public JButton getClose() {
		return close;
	}
	
	

}
