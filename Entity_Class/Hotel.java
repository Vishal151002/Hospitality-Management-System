package hospatalitymanagement.Entity_Class;
public class Hotel {
	   	private int id;
	    private String name;
	    private String location;
	    private String amenities;

	    public Hotel() {}

	    // Constructor for creating a Hotel with just a name
	    public Hotel(String name) {
	        this.name = name;
	    }

	    public Hotel(int id, String name, String location, String amenities) {
	        this.id = id;
	        this.name = name;
	        this.location = location;
	        this.amenities = amenities;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getLocation() {
	        return location;
	    }

	    public void setLocation(String location) {
	        this.location = location;
	    }

	    public String getAmenities() {
	        return amenities;
	    }

	    public void setAmenities(String amenities) {
	        this.amenities = amenities;
	    }
	    
	    @Override
	    public String toString() {
	        return name; // Display the hotel name in JComboBox
	    }
}
