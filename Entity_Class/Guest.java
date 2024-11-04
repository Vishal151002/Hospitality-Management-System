package hospatalitymanagement.Entity_Class;
public class Guest {
		private int id;
	    private String name;
	    private String email;
	    private String phoneNumber;

	    public Guest() {}

	    // New constructor that takes only the name
	    public Guest(String name) {
	        this.name = name;
	    }

	    public Guest(int id, String name, String email, String phoneNumber) {
	        this.id = id;
	        this.name = name;
	        this.email = email;
	        this.phoneNumber = phoneNumber;
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

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPhoneNumber() {
	        return phoneNumber;
	    }

	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }
	    
	    @Override
	    public String toString() {
	        return name; // Display the guest's name in the JComboBox
	    }

}
