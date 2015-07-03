package com.HelpManager.holders;

public class staffDetailsHolder {
       private int staff_Id;
       private String names;
       private String address;
       private String phone;
       private String email;
       private String contract;
       private String position;
       private int positionRank;
       
       public staffDetailsHolder()
       {
    	   staff_Id = 0;
    	   names = "";
    	   address = "";
    	   phone = "";
    	   email = "";
    	   contract = "";
    	   position ="";
    	   positionRank = 0;
       }
       
       public staffDetailsHolder(int staff_Id, String names, String address, String phone, String email, String contract, String position, int positionRank )
       {
    	   this.staff_Id = staff_Id;
    	   this.names = names;
    	   this.address = address;
    	   this.phone = phone;
    	   this.email = email;
    	   this.contract = contract;
    	  this.position =position;
    	   this.positionRank = positionRank;
       }

	public int getStaff_Id() {
		return staff_Id;
	}

	public void setStaff_Id(int staff_Id) {
		this.staff_Id = staff_Id;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getPositionRank() {
		return positionRank;
	}

	public void setPositionRank(int positionRank) {
		this.positionRank = positionRank;
	}
       
       
       
}
