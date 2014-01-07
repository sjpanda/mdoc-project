package domain;

public interface IAddress {
	public long getId();
	
	public void setId(long id);
	
	public String getStreet();
	
	public void setStreet(String street);
	
	public String getCity();
	
	public void setCity(String city);
	
	public String getZip();
	
	public void setZip(String zip);
	
	public String getCountry();
	
	public void setCountry(String country);
}
