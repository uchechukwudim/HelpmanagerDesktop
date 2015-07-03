package com.HelpManager.holders;

public class AdminLoginHolder {
	private int staff_Id;
	private String password;
	private boolean isLogedin;
	
	public AdminLoginHolder()
	{
		staff_Id = 0;
		password = "";
		isLogedin = false;
	}
	

	public AdminLoginHolder(int staff_Id, String password, boolean isLogedin)
	{
		this.staff_Id = staff_Id;
		this.password = password;
		this.isLogedin = isLogedin;
	}


	public int getStaff_Id() {
		return staff_Id;
	}


	public void setStaff_Id(int staff_Id) {
		this.staff_Id = staff_Id;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isLogedin() {
		return isLogedin;
	}


	public void setLogedin(boolean isLogedin) {
		this.isLogedin = isLogedin;
	}
	
	
}
