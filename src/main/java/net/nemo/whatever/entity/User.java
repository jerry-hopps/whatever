package net.nemo.whatever.entity;

public class User {

	private Integer id;
	private String name;
	private String email;
	private String password;
	private User contact_of;
	private Integer status = 0;

	public User(String name, String email) {
		this.email = email;
		this.name = name;
	}
	
	public User(Integer id, String name, String email, String password, Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getContact_of() {
		return contact_of;
	}

	public void setContact_of(User contact_of) {
		this.contact_of = contact_of;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "－－Name: " + this.name + System.getProperty("line.separator") + "－－Email: " + this.email
				+ System.getProperty("line.separator") + "－－Status: " + this.status
				+ System.getProperty("line.separator");
	}
}
