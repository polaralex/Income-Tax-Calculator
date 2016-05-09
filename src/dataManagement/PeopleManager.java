package dataManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import dataManagement.Person;

public class PeopleManager {
	
	ArrayList<Person> personList = new ArrayList<Person>();
	
	public ArrayList<Person> getPersonList () {
		return personList;
	}
	
	public static Person createNewPerson (int category, String firstName, String lastName, Integer identifyingNumber, Double income){
	
		if (category == Person.MARRIED_FILING_JOINTLY) {
			 return(new Person(firstName, lastName, "Married Filing Jointly", identifyingNumber, income));
		} else if (category == Person.MARRIED_FILING_SEPERATELY) {
			return(new Person(firstName, lastName, "Married Filing Seperately", identifyingNumber, income));
		} else if (category == Person.HEAD_OF_HOUSEHOLD) {
			return(new Person(firstName, lastName, "Head of Household", identifyingNumber, income));
		} else if (category == Person.SINGLE) {
			return(new Person(firstName, lastName, "Single", identifyingNumber, income));
		}
		
		System.out.println("Error: Problem with the Identification of Tax-Payer Category.");
		return(new Person(" ", " ", "Single", 0, 0));
	}
	
	public Person createNewPerson (int category) {
		return(createNewPerson(category, " ", " ", 0, 0.0));
	}
	
	public static Person createNewPerson (String categoryBeforeTrim, String firstName, String lastName, Integer identifyingNumber, Double income){
		
		String category = categoryBeforeTrim.trim();
		
		if(isValidCategory(category)){
			return(new Person(firstName, lastName, category, identifyingNumber, income));
		}
			
		System.out.println("Error: Problem with the Identification of Tax-Payer Category.");
		return(new Person(" ", " ", "Single", 0, 0));
	}
	
	private static Boolean isValidCategory(String category) {
		
		ArrayList<String> categories = new ArrayList<String>();
		categories.addAll(Arrays.asList("Single", "Head of Household", "Married Filing Jointly", "Married Filing Seperately"));
		
		for(String current : categories){
			if(current.equals(category)){
				return (true);
			}
		}
		
		return(false);
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
	
	public void testCreatePersons() {
		
		personList.add(new Person("Alex", "Emexezidis", "Single", 1, 1200));
		personList.add(new Person("Maria", "Arnaoutaki", "Single", 2, 1300));
		personList.add(new Person("Larry", "Ioannidis", "Married Filing Jointly", 3, 1039));
		personList.add(new Person("Basilis", "Sideropoulos", "Married Filing Seperately", 4, 3000));
		personList.add(new Person("Kostas", "Konstantinou", "Head of Household", 5, 295000));
		personList.add(new Person("Giannis", "Parios", "Single", 6, 35000));
		personList.add(new Person("Maria", "Farantouki", "Single", 7, 83000));	
		
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
}