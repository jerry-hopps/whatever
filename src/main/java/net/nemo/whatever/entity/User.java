package net.nemo.whatever.entity;

public class User {

    public final static Integer STATUS_NEW = 1;
    public final static Integer STATUS_APPROVED = 2;

	private Integer id;
	private String name;
	private String email;
	private String password;
	private String openId;
	private String avatar;
	private Integer status = 0;

    public User(){}

	public User(String name, String email) {
		this.email = email;
		this.name = name;
	}

    public User(Integer id, String name, String email, Integer status) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
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

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getAvatar() {
		return avatar;
	}

	@Override
	public String toString() {
		return "Name: " + this.name + ", Email: " + this.email + ", Status: " + this.status;
	}
}
