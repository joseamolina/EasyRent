package es.uji.ei1027.easyrent.domain;

public class Property {
	private String id;
	private String title;
	private String description;
	private String property_type;
	private Integer capacity;
	private Integer num_rooms;
	private Integer num_baths;
	private Integer num_beds;
	private Integer square_meters;
	private String street;
	private Integer number_home;
	private Integer floor_home;
	private String city;
	private Double daily_price;
	private Boolean is_active;
	private String owner;
	private Double total_rate;
	private Integer num_rates;
	
	public Property() {
		super();
	}
	
	public Property(String id, String title, String description, String property_type, Integer capacity, Integer num_rooms,
			Integer num_baths, Integer num_beds, Integer square_meters, String street, Integer number_home, Integer floor_home, String city,
			Double daily_price, Boolean is_active, String owner, Double total_rate, Integer num_rates) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.property_type = property_type;
		this.capacity = capacity;
		this.num_rooms = num_rooms;
		this.num_baths = num_baths;
		this.num_beds = num_beds;
		this.square_meters = square_meters;
		this.street = street;
		this.number_home = number_home;
		this.floor_home = floor_home;
		this.city = city;
		this.daily_price = daily_price;
		this.is_active = is_active;
		this.owner = owner;
		this.total_rate = total_rate;
		this.num_rooms = num_rates;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String tittle) {
		this.title = tittle;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getProperty_type() {
		return property_type;
	}
	
	public void setProperty_type(String property_type) {
		this.property_type = property_type;
	}
	
	public Integer getCapacity() {
		return capacity;
	}
	
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	public Integer getNum_rooms() {
		return num_rooms;
	}
	
	public void setNum_rooms(Integer num_rooms) {
		this.num_rooms = num_rooms;
	}
	
	public Integer getNum_baths() {
		return num_baths;
	}
	
	public void setNum_baths(Integer num_baths) {
		this.num_baths = num_baths;
	}
	
	public Integer getNum_beds() {
		return num_beds;
	}
	
	public void setNum_beds(Integer num_beds) {
		this.num_beds = num_beds;
	}
	
	public Integer getSquare_meters() {
		return square_meters;
	}
	
	public void setSquare_meters(Integer square_meters) {
		this.square_meters = square_meters;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public Integer getNumber_home() {
		return number_home;
	}
	
	public void setNumber_home(Integer number_home) {
		this.number_home = number_home;
	}
	
	public Integer getFloor_home() {
		return floor_home;
	}
	
	public void setFloor_home(Integer floor_home) {
		this.floor_home = floor_home;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public Double getDaily_price() {
		return daily_price;
	}
	
	public void setDaily_price(Double daily_price) {
		this.daily_price = daily_price;
	}
	
	public Boolean is_active() {
		return is_active;
	}
	
	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public Double getTotal_rate() {
		return total_rate;
	}
	
	public void setTotal_rate(Double total_rate) {
		this.total_rate = total_rate;
	}
	
	public Integer getNum_rates() {
		return num_rates;
	}
	
	public void setNum_rates(Integer num_rates) {
		this.num_rates = num_rates;
	}
	
	public Double getRateOverFive() {
		if(total_rate == null || num_rates == 0)
			return null;
		
		// Format with 2 decimal places
		Double rate = (total_rate/num_rates)*0.5;
		String rateString = String.format("%.2f", rate);
		rateString = rateString.replace(',', '.');
		return Double.parseDouble(rateString);
	}
	
	public String getAddress() {
		return street+", number: "+number_home+", floor: "+floor_home+", "+city;
	}
	
	@Override
	public String toString() {
		return "Property [id=" + id + ", title=" + title + ", description=" + description 
				+ ", property_type=" + property_type + ", capacity=" + capacity + ", num_roomse=" + num_rooms 
				+ ", num_baths=" + num_baths + ", num_beds=" + num_beds + ", square_meters=" + square_meters 
				+ ", street=" + street + ", number_home=" + number_home + ", floor_home=" + floor_home
				+ ", city=" + city + ", daily_price=" + daily_price + ", is_active=" + is_active
				+ ", owner=" + owner + ", total_rate=" + total_rate + ", num_rates=" + num_rates + "]";
	}
	
}
