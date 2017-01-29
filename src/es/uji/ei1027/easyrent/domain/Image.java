package es.uji.ei1027.easyrent.domain;

public class Image {
	private String href;
	private String caption;
	private String id_prop;
	
	public Image() {
		super();
	}
	
	public Image(String href, String caption, String id_prop) {
		super();
		this.href = href;
		this.caption = caption;
		this.id_prop = id_prop;
	}
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getId_prop() {
		return id_prop;
	}
	
	public void setId_prop(String id_prop) {
		this.id_prop = id_prop;
	}
	
	@Override
	public String toString() {
		return "Image [href=" + href + ", caption=" + caption
				+ ", id_prop=" + id_prop + "]";
	}

}
