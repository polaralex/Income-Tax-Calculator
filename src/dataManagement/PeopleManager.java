package dataManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import dataManagement.Person;

public class PeopleManager {

	private ArrayList<Person> personList = new ArrayList<Person>();

	public ArrayList<Person> getPersonList () {
		return personList;
	}

	public static Person createNewPerson (int category, String firstName, String lastName, Integer identifyingNumber, Double income){

		ArrayList<String> categories = new ArrayList<String>();
		categories.addAll(Arrays.asList("Married Filing Jointly", "Married Filing Seperately", "Head of Household", "Single"));

		if ( (category-1) < categories.size()) {
			return(new Person(firstName, lastName, categories.get(category-1), identifyingNumber, income));
		}

		System.out.println("Error: Problem with the Identification of Tax-Payer Category.");
		return (new Person("", "", "Single", 0, 0));
	}

	public Person createNewPerson (int category) {
		return (createNewPerson(category, "", "", 0, 0.0));
	}

	public static Person createNewPerson (String categoryBeforeTrim, String firstName, String lastName, Integer identifyingNumber, Double income){

		String category = categoryBeforeTrim.trim();

		if (isValidCategory(category)){
			return (new Person(firstName, lastName, category, identifyingNumber, income));
		}

		System.out.println("Error: Problem with the Identification of Tax-Payer Category.");
		return (new Person(" ", " ", "Single", 0, 0));
	}

	private static Boolean isValidCategory(String category) {

		ArrayList<String> categories = new ArrayList<String>();
		categories.addAll(Arrays.asList("Single", "Head of Household", "Married Filing Jointly", "Married Filing Seperately"));

		for (String current : categories) {
			if (current.equals(category)) {
				return (true);
			}
		}

		return (false);
	}

	public Company companyCreator(String name, String address) {
		
		if (Company.allCompaniesList != null) {
			ArrayList<Company> companyList = Company.allCompaniesList;

			for (int i=0; i<companyList.size(); i++) {
				if ((companyList.get(i).getName() == name) && (companyList.get(i).getAddress() == address)) {
					return companyList.get(i);
				}
			}
		}
		
		return (new Company(name, address));
	}
}
