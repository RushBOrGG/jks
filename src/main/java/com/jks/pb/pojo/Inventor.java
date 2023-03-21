package com.jks.pb.pojo;


import java.util.Date;

public class Inventor {

    public Inventor(String id, String name, String nationality, Date birthdate) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.birthdate = birthdate;
    }
    
    

    public Inventor() {
		super();
	}



	private String id;
    private String name;
    private String nationality;
    private String[] inventions;
    private Date birthdate;
    // 省略其它方法

    public String getContinuationNum() {
        return "28";
    }

    public String getTotalNum() {
        return "500";
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String[] getInventions() {
		return inventions;
	}

	public void setInventions(String[] inventions) {
		this.inventions = inventions;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
    
    
}
