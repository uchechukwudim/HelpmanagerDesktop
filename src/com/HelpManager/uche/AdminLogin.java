package com.HelpManager.uche;

import java.awt.Color;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.HelpManager.holders.AdminLoginHolder;

//NOTE: explain how this page and every page works tomorrow before continuing

public class AdminLogin extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel UName;
    private JLabel Pword;
    private JLabel LoginName;
    private JLabel LoginLabel;
    private JButton resetPassword;
    
    private JTextField UserName;
    private JPasswordField Password;
    private JPanel card;
    final static int extraWindowWidth = 100;
    private GridBagConstraints gbc;
    private JButton LogIn;
    private JButton Logout;
    
   
    private DatabaseHelper db;
      
    /*
     * This class handles login process 
     */
    @SuppressWarnings("serial")
	public AdminLogin()
    {
    	LoginLabel = new JLabel("Logedin As: ");
    	LoginName = new JLabel("");
    	
    	LoginLabel.setForeground(Color.red);
    	UName = new JLabel("Staff ID:	");
    	Pword = new JLabel("Password:	");
    	
    	resetPassword = new JButton();
    	
    	resetPassword.setText("<HTML>Forgot Password? <FONT color=\"#000099\"><U>Reset Password</U></FONT></HTML>");
    	resetPassword.setHorizontalAlignment(SwingConstants.LEFT);
    	resetPassword.setBorderPainted(false);
    	resetPassword.setFont(new Font("Dialog", Font.PLAIN, 10));
    
    	
    	UserName = new JTextField("", 20);
    	Password = new JPasswordField("", 20);
    	LogIn = new JButton("Login");
    	Logout = new JButton("Logout");
    	Logout.setEnabled(false);
    	gbc = new GridBagConstraints();
    	
    	db = new DatabaseHelper();
    	
    
    	
    	
    	//Make the panel wider than it really needs, so
        //the window's wide enough for the tabs to stay
        //in one row.
    	card = new JPanel() {
	            public Dimension getPreferredSize() {
	                Dimension size = super.getPreferredSize();
	                size.width += extraWindowWidth;
	                return size;
	            }
	        };  
	     
	        resetPasswordCliclListner();
    }
    
    
    public void addComponents()
    {
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.anchor = GridBagConstraints.SOUTHWEST;
    	gbc.insets = new Insets(0, 0, 5, 5);
    	card.add(LoginLabel, gbc);
    	
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	card.add(LoginName, gbc);
    	
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	gbc.insets = new Insets(0,65, 0, 0);
    	card.add(resetPassword, gbc);
    	
    	
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	gbc.insets = new Insets(0, 0, 0, 0);
    	card.add(UName, gbc);
    	
    	gbc.gridx = 1;
    	gbc.gridy = 1;
    	card.add(UserName, gbc);
    	
    	gbc.gridx = 0;
    	gbc.gridy = 2;
    	card.add(Pword, gbc);
    	
    	gbc.gridx = 1;
    	gbc.gridy = 2;
    	card.add(Password, gbc);
    	
    	gbc.gridx = 1;
    	gbc.gridy = 4;
    	gbc.anchor = GridBagConstraints.EAST;
    	gbc.insets = new Insets(0, 0, 5, 5);
    	card.add(Logout, gbc);
    	
    	gbc.gridx = 1;
    	gbc.gridy = 4;
    	gbc.anchor = GridBagConstraints.WEST;
    	gbc.insets = new Insets(0, 0, 5, 5);
    	card.add(LogIn, gbc);
    	
    	
    }

	public JLabel getPword() {
		return Pword;
	}

	public void setPword(JLabel pword) {
		Pword = pword;
	}

	public JPasswordField getPassword() {
		return Password;
	}

	public void setPassword(JPasswordField password) {
		Password = password;
	}

	public JPanel getCard() {
		return card;
	}

	public void setCard(JPanel card) {
		this.card = card;
	}
	

    
    public void LoginActionListener(final JTabbedPane tab, final JPanel c)
    {
    	LogIn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String username = UserName.getText();
				String password = new String(Password.getPassword());
				
				if(username.isEmpty() || password.isEmpty())
				{
					JOptionPane.showMessageDialog(card, "Staff ID and Password has to be filled ");
					UserName.setText("");
					Password.setText("");
				}
				else if(!isInteger(username))
				{
					JOptionPane.showMessageDialog(card, "Staff ID is not a number ");
					UserName.setText("");
					 Password.setText("");
				}
				else 
				{
					  //check if the ID exist
					   if(!processLogin(username, password))
					   {
						   JOptionPane.showMessageDialog(card, "Wrong Staff ID or Password ");
						   Password.setText("");
					   }
					   else
					   {
						   //sql to update database for when someone is logged in
						   String sql = "UPDATE admin_login SET isLogedin = '1' WHERE staff_id = '"+username+"'";
						   
						   try {
									//execute statement
							   		db.ExecuteStatement(sql);
								   
								//disable logIn button when someone is logged in
								   LogIn.setEnabled(false);
								   Logout.setEnabled(true);
								   
								   //show a message that login was successful
								   JOptionPane.showMessageDialog(card, "Welcome!! ");
								   
								   LoginName.setText(username);
								   LoginLabel.setForeground(Color.GREEN);
								   
								 //Reset staff id and password here
								   UserName.setText("");
								   Password.setText("");
								   
								   UserName.setEnabled(false);
								   Password.setEnabled(false);
								   
								   //Open Navigation tab
								   tab.add("NAVIGATION PAGE", c);
								   tab.setSelectedComponent(c);
								   
								   resetPassword.setEnabled(false);
								   
						   	   } catch (SQLException e1) {
							
						   		   	e1.printStackTrace();
						   	   }
						   
						   
					   }
				}
								
			}
    		
    	});
    }
    
    /*
     * listens to when logout button is clicked
     * updates the database admin_login for logout and disable logout button
     * and show logout message.
     */
    public void LogoutListener()
    {
    	Logout.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String sql = "UPDATE admin_login SET isLogedin = '0' WHERE isLogedin = '1'";
			
				JOptionPane.showMessageDialog(card, "You have logged Out from admin ");

				try {
							db.ExecuteStatement(sql);
								
							LoginName.setText("");
							LoginLabel.setForeground(Color.red);//disable logout button on login
							Logout.setEnabled(false);
							
							//enable login button on logout
							LogIn.setEnabled(true);
							
							//Reset staff id and password here
							   UserName.setText("");
							   Password.setText("");
							resetPassword.setEnabled(true);
							
							  UserName.setEnabled(true);
							   Password.setEnabled(true);
					} catch (SQLException e1) {}
				
			}
    		
    	});
    }
    
    private void resetPasswordCliclListner()
    {
    	resetPassword.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DatabaseHelper db = new DatabaseHelper();
				
				String rand = UUID.randomUUID().toString().substring(0, 8);
				
				while(true)
				{
					String staff_Id = JOptionPane.showInputDialog("Enter Staff Identification Number ");
				
					try{
						
						if(staff_Id.isEmpty())
						{
							JOptionPane.showMessageDialog(card, "Field is Empty");
						}
						else if(!isInteger(staff_Id))
						{
							JOptionPane.showMessageDialog(card, "Staff identification has to be a number");
							return;
						}
						else
						{
							 String sql = "SELECT email, names FROM staff_details WHERE staff_id = '"+staff_Id+"'";
							 
							 try {
								 	ResultSet result = db.ExecuteSql(sql);
								 
								 	if(result.next())
								 	{
								 		resetAndEmailPassword(Integer.parseInt(staff_Id), rand, result.getString("email"), result.getString("names"));
								 		return;
								 	}
								 	else
								 	{
								 		JOptionPane.showMessageDialog(card, "Wrong Staff Identification Number");
								 	}
							 	 } catch (SQLException e) {
								    e.printStackTrace();
							 	 }
						}
				
						
						
					}
					catch(NullPointerException e){
						return ;
					}
				}
			
			}});
    }
    
    private void resetAndEmailPassword(int staff_id, String password, String emailTo, String names)
    {
    	//upate database
    	updateResetedPassword(staff_id, password);
    	//send email to user with password
    	SendEmail(password, emailTo, names);
    }
    
    public void updateResetedPassword(int staff_id, String password) 
    {
    	DatabaseHelper db = new DatabaseHelper();
    	
    	String sql = "UPDATE admin_login SET password = '"+password+"' WHERE staff_id = '"+staff_id+"'";
    	try {
			db.ExecuteStatement(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
    }
    
    private void SendEmail(String password, String emailTo, String staffNames)
    {
    	// Recipient's email ID needs to be mentioned.
        String to = emailTo;

        // Sender's email ID needs to be mentioned
        String from = "noreply@HelpManager.com";

        // Assuming you are sending email from localhost
        String host = "smtp.upcmail.ie";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
   
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try{
           // Create a default MimeMessage object.
           MimeMessage message = new MimeMessage(session);

           // Set From: header field of the header.
           message.setFrom(new InternetAddress(from));

           // Set To: header field of the header.
           message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(to));

           // Set Subject: header field
           message.setSubject("Password Change From Help Manager");

           // Now set the actual message
           message.setText("Hello "+staffNames +", \n\n Your new Password is: "+ password +".\n\n Regards \n Help Manager Team");

           // Send message
           Transport.send(message);
           System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
           mex.printStackTrace();
        }
    }
    public void LoginKeyListener(final JTabbedPane tab, final JPanel c)
    {
    	Password.addKeyListener(new KeyListener(){

			

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyCode() == 10)
				{
					String username = UserName.getText();
					String password = new String(Password.getPassword());
					
					if(username.isEmpty() || password.isEmpty())
					{
						JOptionPane.showMessageDialog(card, "Staff ID and Password has to be filled ");
						UserName.setText("");
						Password.setText("");
					}
					else if(!isInteger(username))
					{
						JOptionPane.showMessageDialog(card, "Staff ID is not a number ");
						UserName.setText("");
						 Password.setText("");
					}
					else 
					{
						  //check if the ID exist
						   if(!processLogin(username, password))
						   {
							   JOptionPane.showMessageDialog(card, "Wrong Staff ID or Password ");
							   Password.setText("");
						   }
						   else
						   {
							   //sql to update database for when someone is logged in
							   String sql = "UPDATE admin_login SET isLogedin = '1' WHERE staff_id = '"+username+"'";
							   
							   try {
										//execute statement
								   		db.ExecuteStatement(sql);
									   
									//disable logIn button when someone is logged in
									   LogIn.setEnabled(false);
									   Logout.setEnabled(true);
									   
									   //show a message that login was successful
									   JOptionPane.showMessageDialog(card, "Welcome!! ");
									   
									   LoginName.setText(username);
									   LoginLabel.setForeground(Color.GREEN);
									   
									 //Reset staff id and password here
									   UserName.setText("");
									   Password.setText("");
									   
									   //Open Navigation tab
									   tab.add("NAVIGATION PAGE", c);
									   tab.setSelectedComponent(c);
									   
									   resetPassword.setEnabled(false);
									   
							   	   } catch (SQLException e1) {
								
							   		   	e1.printStackTrace();
							   	   }
							   
							   
						   }
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    }
   
    
   
    
	/*
	 * Gets login details from database,store or "holds" them in an ArrayList,
	 * Checks if the inputed userName is a number, if it is, it now checks if the
	 * inputed userName and password is in the database, using the ArrayList holding 
	 * the Login details. If it is, it logs user in if its not, informs user accordingly.
	 */
private boolean processLogin(String StaffId, String pw)
{
		
		final ArrayList<AdminLoginHolder> loginList = new ArrayList<AdminLoginHolder>();
		int staffId = Integer.parseInt(StaffId);
		String password = new String(pw);
		
		 //sql statement to retrieve userName and password
		String sql = "select staff_id, password, isLogedin from admin_login";
		try {
			ResultSet result = db.ExecuteSql(sql);
			
			while(result.next()){
				AdminLoginHolder holder = new AdminLoginHolder();
				holder.setStaff_Id(Integer.parseInt(result.getString(1)));
				holder.setPassword(result.getString(2));
				holder.setLogedin(result.getBoolean(3));
				
				loginList.add(holder);	
			}
			
			AdminLoginHolder hold = new AdminLoginHolder();
			for(int loop = 0; loop< loginList.size(); loop++)
			{
				
				
				hold = loginList.get(loop);
				
				if(hold.getStaff_Id() == staffId && hold.getPassword().equals(password))
				{
					return true;
				}
					
				
			}
			 //check if userName is a number here
		} catch (SQLException e) {}
		return false;
		
	}


	
	public JButton getLogIn() {
		return LogIn;
	}
	
	
	public void setLogIn(JButton logIn) {
		LogIn = logIn;
	}
	
	
	public JButton getLogout() {
		return Logout;
	}
	
	
	public void setLogout(JButton logout) {
		Logout = logout;
	}


/*
 * checks if a string contains integers. returns true if it does false otherwise.
 */
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


@SuppressWarnings("unused")
private void changePanel(JPanel panel) {
   panel = new JPanel();
	card.remove(card);
    //getRootPane()
    //card.getRootPane().doLayout();
    //update(getGraphics());
}

}
