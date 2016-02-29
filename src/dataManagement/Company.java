package dataManagement;

public class Company {
	
	private String name;
	private String address;
	
	Company (String name, String address) {
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

}
