package net.nemo.whatever.entity;

import net.nemo.whatever.util.DateUtil;

public class User {

	private String id;
	private String name;
	private String email;
	private String password;
	private User contact_of;
	private boolean enabled = false;
	
	public User(String name, String email) {
		this.email = email;
		this.name = name;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return "－－Name: " + this.name + System.getProperty("line.separator") +
				"－－Email: " + this.email + System.getProperty("line.separator") +
				"－－Enabled: " + this.enabled + System.getProperty("line.separator");
	}
}
