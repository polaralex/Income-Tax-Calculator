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
		personList.get(1).addReceipt(new Receipt(1, date, "Basic", 55d, companyCreator("Basilopoulos","Ioannina")));
		personList.get(1).addReceipt(new Receipt(2, date, "Travel", 120d, companyCreator("Shell","Ioannina")));
		personList.get(1).addReceipt(new Receipt(5, date, "Travel", 100d, companyCreator("Shell","Ioannina")));
		personList.get(1).addReceipt(new Receipt(6, date, "Travel", 10d, companyCreator("Attiki Odos","Athens")));
		personList.get(1).addReceipt(new Receipt(7, date, "Travel", 100d, companyCreator("Shell","Ioannina")));
		personList.get(1).addReceipt(new Receipt(8, date, "Basic", 100d, companyCreator("Shell","Ioannina")));
		personList.get(1).addReceipt(new Receipt(9, date, "Health", 100d, companyCreator("Shell","Ioannina")));
		personList.get(2).addReceipt(new Receipt(3, date, "Basic", 55d, companyCreator("Basilopoulos","Ioannina")));
		personList.get(3).addReceipt(new Receipt(4, date, "Other", 55d, companyCreator("Basilopoulos","Ioannina")));
	}
	
	public ArrayList<Person> getPersonList () {
		return personList;
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
	
	public Company companyCreator(String name, String address) {
		
		if (Company.allCompaniesList != null ) {
			ArrayList<Company> companyList = Company.allCompaniesList;
			
			for (int i=0; i<companyList.size(); i++) {
				if ( (companyList.get(i).getName() == name) && (companyList.get(i).getAddress() == address) ) {
					return companyList.get(i);
				}
			}
		}
		
		return ( new Company(name, address) );
	}
	
	public Person createNewPerson (int category) {
		
		if (category == Person.MARRIED_FILING_JOINTLY) {
			System.out.println("Did this");
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