package es.uji.ei1027.easyrent.domain;

public class Credentials {
	private String id_actor;
	private String username;
	private String pwd;
	private String role;
	
	public Credentials() {
		super();
	}
	
	public Credentials(String id_actor, String username, String pwd, String role) {
		this.id_actor = id_actor;
		this.username = username;
		this.pwd = pwd;
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String password_user) {
		this.pwd = password_user;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getId_actor() {
		return id_actor;
	}
	public void setId_actor(String id) {
		this.id_actor = id;
	}
	
	@Override
	public String toString() {
		return "Credentials [id_actor=" + id_actor + ", username=" + username
				+ ", pwd=" + pwd + ", role=" + role + "]";
	}
	
}
