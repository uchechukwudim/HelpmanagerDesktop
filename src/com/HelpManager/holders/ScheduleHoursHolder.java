package com.HelpManager.holders;

public class ScheduleHoursHolder {
	private int staff_id;
	private String names;
	private double totalHours;
	private int year;
	
	private String []daysOfWeekHours;
	
	public ScheduleHoursHolder()
	{
		staff_id = 0;
		names = "";
		totalHours = 0;
		year= 0;
		daysOfWeekHours = new String[7];
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public double getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(double totalHours) {
		this.totalHours = totalHours;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
	public String getADayOfTheWeekHour(int dayNumber)
	{
		if(dayNumber <0 && dayNumber >6 )
		{
			return null;
		}
		else{
			return daysOfWeekHours[dayNumber];
		}
	}
	
	public void setADayOfTheWeekHour(int dayNumber, String workTimes)
	{
		if(dayNumber <0 && dayNumber >6 )
		{
			
		}
		else{
			daysOfWeekHours[dayNumber] = workTimes;
		}
	}
}
