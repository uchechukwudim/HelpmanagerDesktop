package com.HelpManager.uche;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.HelpManager.holders.NoticeBoardHolder;

/*
 * this class is used to View Task daily. 
 */
public class NoticeBoard extends JPanel{
	 
	    /**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		final static int extraWindowWidth = 100;
	    private JPanel card ;
	    //private GridBagConstraints gbc;
	   
	    private JTextArea showNotice;
	    private JScrollPane scroll;
	    private JLabel [] title;
	    private JLabel [] Title;
	    private JLabel [] message;
	    private JTextArea [] Message;
	    private JLabel [] date;
	    private JLabel [] Date;
	    private JLabel [] names;
	    private JLabel [] postedBy;
	    
	    public NoticeBoard()
	    {

	    	populateData();
	        populateLabels();

			
			
	        //Create the "cards".
	        card = new JPanel(){
	       
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
	      card.setBackground(Color.white);
	      card.setOpaque(true);
	      setLabelProperties();
	     
	    }  
	    
	    public void setLabelProperties()
	    {
	    	for(int loop = 0; loop < populateData().size(); loop++)
	    	{
	    		names[loop].setForeground(Color.decode("404040"));
	    	
	    		title[loop].setForeground(Color.decode("404040"));
	    		date[loop].setForeground(Color.LIGHT_GRAY);
	    		message[loop].setForeground(Color.decode("404040"));	
	    	}
	    }
	    
	    public ArrayList<NoticeBoardHolder> populateData()
	    {
	    	ArrayList<NoticeBoardHolder> holder = new ArrayList<NoticeBoardHolder>();
	    	String sql = "select staff_idd, TaskTitle, message, date FROM notice_Board ORDER BY date DESC";
	        DatabaseHelper db = new DatabaseHelper();
	    
	        try{

	        			//update Notice Board every time a table is clicked
	        			ResultSet result = db.ExecuteSql(sql);
	        			
	        			while(result.next())
	        			{
	        				NoticeBoardHolder datas = new NoticeBoardHolder();
	        				
	        				datas.setStaffId(result.getInt(1));
	        				datas.setName(getNames(result.getInt(1)));
	        				datas.setTitle(result.getString(2));
	        				datas.setMessage(result.getString(3));
	        				datas.setDate(result.getDate(4).toString());
	        				
	        				holder.add(datas);
	        			}		  									
	           }catch(SQLException e1)	{}
	    	
	        return holder;
	    }
	    
	    public void populateLabels()
	    {
	    	title = new JLabel[populateData().size()];
	    	names = new JLabel[populateData().size()];
	    	message = new JLabel[populateData().size()];
	    	date = new JLabel[populateData().size()];
	    	
	    	for(int loop = 0;  loop < populateData().size(); loop++)
	    	{
	    		title[loop] = new JLabel("");
	    		title[loop].setText(populateData().get(loop).getTitle());
	    		names[loop] = new JLabel(populateData().get(loop).getName());
	    		message[loop] = new JLabel(stringBraker(populateData().get(loop).getMessage()));
	    		date[loop] = new JLabel(populateData().get(loop).getDate());
	    	}

	    }
	    
	    //adding tab into a container
	    public void addComponents() 
	    {
	      GridBagConstraints gbc = new GridBagConstraints();
	     for(int loop = 0; loop < populateData().size(); loop++){	
	    	//int loop = 0;
	    	gbc.gridx = 0;
	    	gbc.gridy = loop;
	    	gbc.anchor = GridBagConstraints.WEST;
	    	gbc.insets = new Insets(-150, 0, 0, 0);
	    	JLabel P = new JLabel("POSTED BY: ");
	    	P.setForeground(Color.gray);
	    	card.add(P, gbc );
	    	gbc.gridx = 0;
	    	gbc.gridy = loop;
	    	gbc.insets = new Insets(-150, 80, 0, 0);
	    	card.add(names[loop], gbc );
	    	
	    	gbc.gridx = 1;
	    	gbc.gridy = loop;
	    	gbc.insets = new Insets(-150, 0, 0, 0);
	    	card.add(date[loop], gbc );
	    	

	    	gbc.gridx = 0;
	    	gbc.gridy = loop;
	    	gbc.insets = new Insets(-100, 0, 0, 0);
	    	JLabel T =  new JLabel("TITLE: ");
	    	T.setForeground(Color.gray);
	    	card.add(T, gbc );
	    	gbc.gridx = 0;
	    	gbc.gridy = loop;
	    	gbc.insets = new Insets(-100, 80, 0, 0);
	    	card.add(title[loop], gbc );
	    	

	    	gbc.gridx = 0;
	    	gbc.gridy = loop;
	    	gbc.insets = new Insets(-50, 0, 0, 0);
	    	JLabel M =  new JLabel("MESSAGE: ");
	    	M.setForeground(Color.gray);
	    	card.add(M, gbc );
	    	gbc.gridx = 0;
	    	gbc.gridy = loop;
	    	gbc.insets = new Insets(-50, 80, 0, 0);
	    	card.add(message[loop], gbc );  
	    	
	    	gbc.gridx = 0;
	    	gbc.gridy = loop;
	    	gbc.anchor = GridBagConstraints.NORTHWEST;
	    	gbc.insets = new Insets(200, 40, 0, 0);
	    	JLabel line = new JLabel("_______________________________________________");
	    	line.setForeground(Color.LIGHT_GRAY);
	    	card.add(line , gbc );
	     }
	    }
	    
	 
		public void RemoveComponents() 
	    {
	    	
	    	card.removeAll();
	     
	    }
	    
	    public void postMessage()
	    {
	    	
						   
		
		}

	    
	    //getters and setters
	    public JPanel getCard() {
			return card;
		}

		public void setCard(JPanel card) {
			this.card = card;
		}

		public JTextArea getShowNotice() {
			return showNotice;
		}

		public void setShowNotice(JTextArea showNotice) {
			this.showNotice = showNotice;
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
			db.closeConnection();
			return name;
		}
		
	private String stringBraker(String string)
	{
		String brokenString = "";
		int counter = 0;
		for(int loop = 0; loop< string.length(); loop++)
		{
			if(counter==43)
			{
				brokenString = "<br>"+brokenString+"<br>"+string.charAt(loop);
				counter = 0;
			}
			else
			{
				brokenString = brokenString+string.charAt(loop);
			}
			counter++;
		}
		return "<html><body>"+brokenString+"</body></html>";
	}
		
}
