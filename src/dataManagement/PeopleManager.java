package dataManagement;

import java.util.ArrayList;
import java.util.Date;

import dataManagement.Person;
import dataManagement.Single;

public class PeopleManager {

	//Create basic Objects for Testing:
	ArrayList<Person> personList = new ArrayList<Person>();
	
	public void testCreatePersons() {
			
		// Create Person Objects:
		personList.add(new Single("Alex", "Emexezidis", 1, 1200));
		personList.add(new Single("Maria", "Arnaoutaki", 2, 1300));
		personList.add(new MarriedFilingJointly("Larry", "Ioannidis", 3, 1039));
		personList.add(new MarriedFilingSeperately("Basilis", "Sideropoulos", 4, 3000));
		personList.add(new HeadOfHousehold("Kostas", "Konstantinou", 5, 295000));
		personList.add(new Single("Giannis", "Parios", 6, 35000));
		personList.add(new Single("Maria", "Farantouki", 7, 83000));	
		
		// Create Receipt Objects and add them to people:
		Date date = new Date();
		personList.get(1).addReceipt(new Receipt(1, date, "Super Market", 55d, new Company("Basilopoulos","Ioannina")));
		personList.get(1).addReceipt(new Receipt(2, date, "Petrol", 120d, new Company("Shell","Ioannina")));
		personList.get(1).addReceipt(new Receipt(5, date, "Petrol", 100d, new Company("Shell","Ioannina")));
		personList.get(1).addReceipt(new Receipt(6, date, "Tolls", 10d, new Company("Attiki Odos","Athens")));
		personList.get(1).addReceipt(new Receipt(7, date, "Petrol", 100d, new Company("Shell","Ioannina")));
		personList.get(1).addReceipt(new Receipt(8, date, "Super Market", 100d, new Company("Shell","Ioannina")));
		personList.get(1).addReceipt(new Receipt(9, date, "Super Market", 100d, new Company("Shell","Ioannina")));

		personList.get(2).addReceipt(new Receipt(3, date, "Super Market", 55d, new Company("Basilopoulos","Ioannina")));
		personList.get(3).addReceipt(new Receipt(4, date, "Super Market", 55d, new Company("Basilopoulos","Ioannina")));

		System.out.println("Test Create Persons Done!\n\n");
	}
	
	public ArrayList<Person> getPersonList () {

		return personList;
		
	}
	
	public ArrayList<Person> getPersonListOnlyNames () {
		
		ArrayList tempList = new ArrayList();
		
		for(int i=0; i<personList.size(); i++){
			tempList.add(personList.get(i).getFirstName()+" "+personList.get(i).getLastName());
		}

		return tempList;
	}
	
	public Person createNewPerson (int category, String firstName, String lastName, Integer identifyingNumber, Double income){
	
		if (category == Person.MARRIED_FILING_JOINTLY) {
			 return(new MarriedFilingJointly(firstName, lastName, identifyingNumber, income));
		} else if (category == Person.MARRIED_FILING_SEPERATELY) {
			return(new MarriedFilingSeperately(firstName, lastName, identifyingNumber, income));
		} else if (category == Person.HEAD_OF_HOUSEHOLD) {
			return(new HeadOfHousehold(firstName, lastName, identifyingNumber, income));
		} else if (category == Person.SINGLE) {
			return(new Single(firstName, lastName, identifyingNumber, income));
		}
		
		return null;
		
	}
	
	public Person createNewPerson (String category, String firstName, String lastName, Integer identifyingNumber, Double income){
		
		if (category == "Married Filing Jointly") {
			 return(new MarriedFilingJointly(firstName, lastName, identifyingNumber, income));
		} else if (category == "Married Filing Seperately") {
			return(new MarriedFilingSeperately(firstName, lastName, identifyingNumber, income));
		} else if (category == "Head of Household") {
			return(new HeadOfHousehold(firstName, lastName, identifyingNumber, income));
		} else if (category == "Single") {
			return(new Single(firstName, lastName, identifyingNumber, income));
		}
		
		return null;
		
	}
	
	public Person createNewPerson (int category) {
		
		if (category == Person.MARRIED_FILING_JOINTLY) {
			 return(new MarriedFilingJointly());
		} else if (category == Person.MARRIED_FILING_SEPERATELY) {
			return(new MarriedFilingSeperately());
		} else if (category == Person.HEAD_OF_HOUSEHOLD) {
			return(new HeadOfHousehold());
		} else if (category == Person.SINGLE) {
			return(new Single());
		}
		
		return null;
	}
		
}