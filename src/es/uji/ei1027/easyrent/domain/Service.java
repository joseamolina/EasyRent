package es.uji.ei1027.easyrent.domain;

public class Service {
	private String name;
	private String id_prop;
	
	public Service() {
		super();
	}
	
	public Service(String name, String id_prop) {
		super();
		this.name = name;
		this.id_prop = id_prop;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId_prop() {
		return id_prop;
	}
	
	public void setId_prop(String id_prop) {
		this.id_prop = id_prop;
	}
	
	@Override
	public String toString() {
		return "Service [name=" + name + ", id_prop=" + id_prop + "]";
	}

}
