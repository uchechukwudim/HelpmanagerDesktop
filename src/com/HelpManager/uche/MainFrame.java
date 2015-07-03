package com.HelpManager.uche;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;


import javax.swing.*;






public class MainFrame extends JFrame{
    
	protected static final Object JTabbedPane = null;
	/**
	 * 
	 */

	//all pages for HelpManager here
	 private NoticeBoard nbPage;
     private DailyTaskPost postMessagePage;
     private AdminLogin adminLoginPage;
     private NavigationPage NavPage;
     private addEmployee AddEmployee;
     private Employees employees;
     private UpdateEmployeeDetails updateEmployee;
     private ScheduleHours schedHours;
     private JScrollPane scrollp ;
     private JScrollPane scrollHours ;
     private JScrollPane scrollBreak ;
     private JScrollPane scrollNB ;
     private ScheduledHoursView viewHours;
     private BreakView viewBreak;
     
     //tabs
     JTabbedPane tabs;
   
     
	public MainFrame(String title) {
		   super(title);
		   
		 //frame setting here
		  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		  pack();
		  setVisible(true);
		  setSize(520, 570);
		  
		  //initialize cards here
		  nbPage = new NoticeBoard();
		  postMessagePage = new DailyTaskPost();
		  tabs = new JTabbedPane();
		  adminLoginPage = new AdminLogin();
		  NavPage = new NavigationPage();
		  AddEmployee = new addEmployee();
		  employees = new Employees();
		  updateEmployee = new UpdateEmployeeDetails();
		  schedHours = new ScheduleHours();
		  
		  Calendar cal = Calendar.getInstance();
			 int WEEKNUMBER  = cal.get(Calendar.WEEK_OF_YEAR);
		  viewHours = new ScheduledHoursView(NavPage.populateData(WEEKNUMBER+""), WEEKNUMBER); //here will always be current week
		  viewBreak = new BreakView(WEEKNUMBER);
		  //layout for frame
		  setLayouts();
		  
		  //add tabs on frame here
		  AddCards();
		  
		  //listeners for frame
		  UseOtherListeners();
		
		  
		  //container that holds the cards
		  addToContainer(getContentPane());
		  
	
	
		  	
	  }
//:::::::::::::::::::::::::::::::::::::::methods for "card" that is jpanels:::::::::

	
	//every "card" that is jPanel for the frame add here
	//i do this here because it wont respond in the individual classes
	private void AddCards()
	{
		//add components into frame for all classes
		nbPage.addComponents();
		postMessagePage.addComponents();
		adminLoginPage.addComponents();
		NavPage.AddComponents();
		AddEmployee.addComponents();
		employees.AddComponents();
		updateEmployee.addComponents();
		schedHours.AddComponenets();
		viewHours.AddComponents();
		viewBreak.AddComponents();
		
	}
	
	//every "card" layout for the frame here
	private void setLayouts()
	{
		nbPage.getCard().setLayout(new GridBagLayout());
		postMessagePage.getCard().setLayout(new GridBagLayout());
		 adminLoginPage.getCard().setLayout(new GridBagLayout());
		 NavPage.getCard().setLayout(new GridBagLayout());
		 AddEmployee.getCard().setLayout(new GridBagLayout());
		 employees.getCard().setLayout(new GridBagLayout());
		 updateEmployee.getCard().setLayout(new GridBagLayout());
		 schedHours.getCard().setLayout(new GridBagLayout());
		 viewHours.getCard().setLayout(new GridBagLayout());
		 viewBreak.getCard().setLayout(new GridBagLayout());
		 
		 
	}
	
	
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::		
	  // add cards here for this container or frame
	 // all cards at start up
	private void addToContainer(Container container){
		scrollNB = new JScrollPane(nbPage.getCard());
		scrollNB.setSize(new Dimension(550, 570));
		tabs.add("NOTICE BORAD", scrollNB);
		tabs.add("TASK POSTER",postMessagePage.getCard());
		tabs.add("ADMIN LOGIN", adminLoginPage.getCard());
		
		
		scrollHours = new JScrollPane(viewHours.getCard());
		scrollHours.setSize(new Dimension(1000, 300));
		tabs.add("SCHEDULED HOURS", scrollHours);
        
		scrollBreak = new JScrollPane(viewBreak.getCard());
		tabs.add("SCHEDULED BREAKS", scrollBreak);
		
		container.add(tabs);
	}

	
 //things to do any time mouse is clicked is clicked:::::::::::::::::::::::::::::::
	  private void tabListener()
		{
			tabs.addMouseListener(new MouseListener(){
				
					//method to update notice board anything notice board tab is clicked
					public void mouseClicked(MouseEvent e) {
					try{	
					 if(tabs.getComponent(tabs.getSelectedIndex()) == postMessagePage.getCard())
					 {
						  setSize(600, 600);
					 }
					 else if(tabs.getComponent(tabs.getSelectedIndex()) == scrollHours)
					 {
						 setSize(1050, 500); 
						 
						 Calendar cal = Calendar.getInstance();
						 int WEEK  = cal.get(Calendar.WEEK_OF_YEAR);
						 
						 if(!viewHours.isTabelCreated(WEEK))
					 		{
							 	JOptionPane.showMessageDialog(scrollHours, "The Current Week Hours has not been scheduled");
					 		}
					 }
					 else if(tabs.getComponent(tabs.getSelectedIndex()) == scrollBreak)
					 {
						 setSize(1050, 500); 
					 }
					 else if(tabs.getComponent(tabs.getSelectedIndex()) == schedHours.getCard())
					 {
						 setSize(1000, 500);
					 }
					 else if(tabs.getComponent(tabs.getSelectedIndex()) == scrollNB)
					 {
						 	nbPage.populateData();
					        nbPage.populateLabels();
					        nbPage.setLabelProperties();
					        nbPage.RemoveComponents() ;
					        nbPage.addComponents() ;

						 setSize(520, 570);
					 }
					 else if(tabs.getComponent(tabs.getSelectedIndex()) == employees.getCard())
					 {
						 setSize(450, 500);
					 }
					 else if(tabs.getComponent(tabs.getSelectedIndex()) == updateEmployee.getCard())
					 {
						 setSize(800, 800);
					 }
					 else if(tabs.getComponent(tabs.getSelectedIndex()) == AddEmployee.getCard())
					 {
						 setSize(800, 800);
					 }
					 else if(tabs.getComponent(tabs.getSelectedIndex()) == scrollp)
					 {
							setSize(1000, 500);
					 }
					 else
					 {
						 setSize(500, 500);
						 nbPage.postMessage();
						 
					 }
					
						
						
					    //checks when too enable or disable navigation buttons
						//NavPage.checkToUseComponents();	
					}catch(NullPointerException ee){
						
						 setSize(500, 500);
					}
					}
			
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});	
		}
	  
	
	
 //in class listeners methods here::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	  
 // methods for navigation  buttons to open "page" for any button clicked::::::::::::::::::::::::::::::::::::::::::::::::	  
//:::::::::::::::::::::Navigation listeners:::::::::::::::::::::::::
	
	 private void AddEmployeeButtonListener()
	{
		JButton AddEmp = NavPage.getAddEmployee();
		AddEmp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				tabs.add("ADD STAFF", AddEmployee.getCard());
				tabs.setSelectedComponent(AddEmployee.getCard());
				 setSize(800, 800);
			}
		});
	}
	
	private void EmployeeButtonListener()
	{
		JButton employee = NavPage.getEmployees();
		
		employee.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tabs.remove(employees.getCard());
				employees = new Employees();
				
				employees.getCard().setLayout(new GridBagLayout());
				employees.AddComponents();
				tabs.add("EMPLOYEE LIST", employees.getCard());
				tabs.setSelectedComponent(employees.getCard());
				setSize(450, 500);
				EmployeeUpdateDetailsButtonListener();
			    
			    CloseUpdatePage();
			    
			    AddUpdatedDetailsButtonListener();
			    
			    ViewMoreDetailButtonListener();
			    CloseEmployeePage();
			}});
	}
	
	private void ScheduleButtonListener()
	{
		JButton schedule = NavPage.getScheduleHours();
		
		schedule.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		try{
				String weekNumber = NavPage.scheduledWeeksChecker();
			
				if(NavPage.CheckSelectedWeekForIsSchedule(weekNumber))
				{
					schedHours = new ScheduleHours();
					schedHours.getCard().setLayout(new GridBagLayout());
					schedHours.AddComponenets();
					ScheduleHoursFinishButtonListener();
					schedHours.setWeekNumber(weekNumber);
				
					tabs.remove(scrollp);
					scrollp = new JScrollPane(schedHours.getCard());
					
					tabs.addTab("SCHEDULER", scrollp);
					tabs.setSelectedComponent(scrollp);
					setSize(1000, 500);
					
				
				}
				else
				{
					
					if(schedHours.isTableCreated(weekNumber))
					{
						schedHours = new ScheduleHours();
						schedHours.getCard().setLayout(new GridBagLayout());
						schedHours.AddComponenets();
						ScheduleHoursFinishButtonListener();
						schedHours.setWeekNumber(weekNumber);
					
						tabs.remove(scrollp);
						scrollp = new JScrollPane(schedHours.getCard());
						
						tabs.addTab("SCHEDULER", scrollp);
						tabs.setSelectedComponent(scrollp);
						setSize(1000, 500);
						
					}
					else
					{
						
						schedHours.createSchedulerTable(weekNumber);
						
						schedHours = new ScheduleHours();
						schedHours.getCard().setLayout(new GridBagLayout());
						schedHours.AddComponenets();
						
						
						ScheduleHoursFinishButtonListener();
						//set the weekNumber that is being scheduled for
						schedHours.setWeekNumber(weekNumber);
						
						//if week has not been scheduled open the schedule page
						scrollp = new JScrollPane(schedHours.getCard());
					
						
						tabs.addTab("SCHEDULER", scrollp);
						tabs.setSelectedComponent(scrollp);
						setSize(1000, 500);
					}
				}
			 
			}catch(NullPointerException e){}
			}});
	}
	
	public void HChooseWeekButtonListener()
	{
		JButton scheduledHours = viewHours.getChooseWeek();
		
		scheduledHours.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
				try{
						String weekNumber = viewHours.getWeeksNumber();
						
						if(NavPage.CheckSelectedWeekForIsSchedule(weekNumber))
						{
							tabs.remove(scrollHours);
							
							viewHours = new ScheduledHoursView(viewHours.populateData(weekNumber), Integer.parseInt(weekNumber)); 

							viewHours.getCard().setLayout(new GridBagLayout());
							viewHours.AddComponents();
							
							viewHours.getWeek().setText("Week: "+ weekNumber);
							viewHours.getWeekDates().setText("Date From: "+ viewBreak.ColunmNames(Integer.parseInt(weekNumber))[1]+" To: "+viewBreak.ColunmNames(Integer.parseInt(weekNumber))[7]);
							viewHours.getCard().setOpaque(true);
							scrollHours = new JScrollPane(viewHours.getCard());
							scrollHours.setSize(new Dimension(1000, 300));
							tabs.add("SCHEDULED HOURS", scrollHours);
							tabs.setSelectedComponent(scrollHours);
							
							setSize(1050, 500);
							
						}
						else{
							String message = "Hours For week "+ weekNumber+" has not been Scheduled";
							 JOptionPane.showMessageDialog(scrollHours, message, "Information", JOptionPane.INFORMATION_MESSAGE);
						}
				}catch(NullPointerException e){}
				HChooseWeekButtonListener();
				viewHours.PrintButtonListener();
				
			}
			
		});
	}
	

	public void BChooseWeekButtonListener()
	{
		JButton ChooseBreak = viewBreak.getChooseWeek();
		
		ChooseBreak.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
				try{
						String weekNumber = viewBreak.getWeeksNumber();
						
						if(NavPage.CheckSelectedWeekForIsSchedule(weekNumber))
						{
							tabs.remove(scrollBreak);
							
							viewBreak = new BreakView(Integer.parseInt(weekNumber)); 

							viewBreak.getCard().setLayout(new GridBagLayout());
							viewBreak.AddComponents();
							viewBreak.getCard().setOpaque(true);
							viewBreak.getWeek().setText("Week: "+ weekNumber);
							viewBreak.getWeekDates().setText("Date From: "+ viewBreak.ColunmNames(Integer.parseInt(weekNumber))[1]+" To: "+viewBreak.ColunmNames(Integer.parseInt(weekNumber))[7]);
							scrollBreak = new JScrollPane(viewBreak.getCard());
							scrollHours.setSize(new Dimension(1000, 300));
							tabs.add("SCHEDULED BREAKS", scrollBreak);
							tabs.setSelectedComponent(scrollBreak);
							
							setSize(1050, 500);

							BChooseWeekButtonListener();
						}
						else{
							String message = "Hours For week "+ weekNumber+" has not been Scheduled";
							 JOptionPane.showMessageDialog(scrollBreak, message, "Information", JOptionPane.INFORMATION_MESSAGE);
						}
				}catch(NullPointerException e){}
				
				//BChooseWeekButtonListener();
				viewBreak.PrintButtonListener();
				
			}
			
		});
	}
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
 //:::::::::::::::adminLoginPage listeners:::::::::::::::::::::::::::::::::
	private void onLogoutButtonLIstener()
	{
		
		adminLoginPage.getLogout().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//remove every tabe associated with admin access when admin logout 
				tabs.remove(AddEmployee.getCard());
				tabs.remove(NavPage.getCard());
				tabs.remove(employees.getCard());
				tabs.remove(updateEmployee.getCard());
				tabs.remove(schedHours.getCard());
				tabs.remove(scrollp);
				setSize(500, 500);
				
				
			}});
		
	}
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

//:::::::::::::::::employees Listeners:::::::::::::::::::::::::::::::::::::::::
	
	//triggers the update "page" to open with selected employee details, as selected on employees
	private void EmployeeUpdateDetailsButtonListener()
	{
		employees.getUpdate().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//get the employee id from the selected list in employees class
			
				if(employees.getEmployeeList().isSelectionEmpty())
				{
					JOptionPane.showMessageDialog(employees.getCard(), "Please Select an Item From The List", "Error Message", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					String staffid = employees.getEmployeeList().getModel().getElementAt(employees.getEmployeeList().getSelectedIndex()).toString().substring(0, 4);
					
					updateEmployee.GrabDetailsForUpdate(staffid);
					
					tabs.add("UPDATE DETAILS", updateEmployee.getCard());
					tabs.setSelectedComponent(updateEmployee.getCard());
					setSize(800, 800);
				}
			}});
	}
	
	private void ViewMoreDetailButtonListener()
	{
		JButton viewDetails = employees.getViewDetails();
		
		viewDetails.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				employees.viewMoreDeatils();
			}});
	}
	
	private void CloseEmployeePage()
	 {
		 JButton close = employees.getClose();
		 
		 close.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				  
				tabs.remove(employees.getCard());
				
				  if(tabs.getComponent(tabs.getSelectedIndex()) == scrollBreak)
					 {
						 setSize(1050, 500);
					 }
				  else if(tabs.getComponent(tabs.getSelectedIndex()) == scrollHours)
					 {
						 setSize(1050, 500); 
					 }
				  else
				  {
					  setSize(500, 500);
				  }
			}
			 
		 });
	 }
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
//:::::::::::::::::::::updateEmployeeDetails Listeners:::::::::::::::::::::::::::::::::
	private void CloseUpdatePage()
	{
		JButton finish = updateEmployee.getClear();
		
		finish.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tabs.remove(updateEmployee.getCard());
			
				tabs.remove(employees.getCard());
				
				employees = new Employees();
				
				employees.getCard().setLayout(new GridBagLayout());
				employees.AddComponents();
				tabs.add("EMPLOYEE LIST", employees.getCard());
				tabs.setSelectedComponent(employees.getCard());
				setSize(450, 500);
				EmployeeUpdateDetailsButtonListener();
			    
			    CloseUpdatePage();
			    
			    AddUpdatedDetailsButtonListener();
			    
			    ViewMoreDetailButtonListener();
			    CloseEmployeePage();
			}});
	}
	
	private void AddUpdatedDetailsButtonListener()
	{
		JButton update = updateEmployee.getAdd();
		
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					updateEmployee.addUpdatedDetails();
			}});
	}
	
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
//:::::::::::::::AddEmployee Listener:::::::::::::::::::::::::::::::
	
	 private void CloseAddEmpPage()
	 {
		 JButton close = AddEmployee.getClose();
		 
		 close.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				  tabs.remove(AddEmployee.getCard());
				  if(tabs.getComponent(tabs.getSelectedIndex()) == scrollBreak)
					 {
						 setSize(1050, 500);
					 }
				  else if(tabs.getComponent(tabs.getSelectedIndex()) == scrollHours)
					 {
						 setSize(1050, 500); 
					 }
				  else
				  {
					  setSize(500, 500);
				  }
			}
			 
		 });
	 }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	//::::::::::::::::::::::::ScheduleHours LIsteners:::::::::::::::::::
		private void ScheduleHoursFinishButtonListener()
		{
			JButton finish = schedHours.getFinish();
			finish.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					tabs.remove(scrollp);
					 if(tabs.getComponent(tabs.getSelectedIndex()) == scrollBreak)
					 {
						 setSize(1050, 500);
					 }
				  else if(tabs.getComponent(tabs.getSelectedIndex()) == scrollHours)
					 {
						 setSize(1050, 500); 
					 }
				  else
				  {
					  setSize(500, 500);
				  }
				}});
		}

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	//Calling all Listeners in and out of class
	//every listener for the frame goes in here	  
	private void UseOtherListeners()
	{
		tabListener();
		postMessagePage.PostButtonActionListener();
		AddEmployeeButtonListener();
		
		adminLoginPage.LoginActionListener(tabs, NavPage.getCard());
		adminLoginPage.LoginKeyListener(tabs, NavPage.getCard());
		onLogoutButtonLIstener();
	    adminLoginPage.LogoutListener();
	    
	    //this triggers the employee "page" to open, adding it into the tabe
	    EmployeeButtonListener();
	    
	    //triggers the updateEmployee "page" to open
	    EmployeeUpdateDetailsButtonListener();
	    
	    CloseUpdatePage();
	    
	    AddUpdatedDetailsButtonListener();
	    
	    ViewMoreDetailButtonListener();
	    
	  
	    
	    ScheduleButtonListener();
	    
	    ScheduleHoursFinishButtonListener();
	    HChooseWeekButtonListener();
	    BChooseWeekButtonListener();
	    CloseAddEmpPage();
	    CloseEmployeePage();
	    
	    viewHours.PrintButtonListener();
	    viewBreak.PrintButtonListener();
	}
 //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}
