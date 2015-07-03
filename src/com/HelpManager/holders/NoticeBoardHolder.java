package com.HelpManager.holders;

public class NoticeBoardHolder {
    
	private String Title;
	private String message;
	private String name;
	private String date;
	private int staffId;
	
	public NoticeBoardHolder()
    {
    	Title = new String ();
    	message = new String ();
    	name = new String ();
    	date = new String ();
    	staffId = 0;
    }
	
	public NoticeBoardHolder(String Title, String message, String name, String date, int staffId)
	{
		this.Title = Title;
		this .message = message;
		this.name = name;
		this.date = date;
		this.staffId = staffId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	
	
}
