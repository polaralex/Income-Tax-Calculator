package dataManagement;

public class Person {
	
	private String firstName;
	private String lastName;
	private Integer identifyingNumber;
	private Double income;
	
	Person (String firstName, String lastName, Integer identifyingNumber, Double income) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.identifyingNumber = identifyingNumber;
		this.income = income;
	}
	
	// Getters and Setters:
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getIdentifyingNumber() {
		return identifyingNumber;
	}
	public void setIdentifyingNumber(Integer identifyingNumber) {
		this.identifyingNumber = identifyingNumber;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	
}
