package dataInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dataManagement.Company;
import dataManagement.HeadOfHousehold;
import dataManagement.MarriedFilingJointly;
import dataManagement.MarriedFilingSeperately;
import dataManagement.PeopleManager;
import dataManagement.Person;
import dataManagement.Receipt;
import dataManagement.Single;

import java.util.ArrayList;
import java.util.Locale;

public class ScannerXmlParser {
	
	// Person data in String form:
	private String name = null;
	private String afm = null;
	private String status = null;
	private String income = null;
	
	private ArrayList<String> parsedWords = new ArrayList<String>();
	private Integer parsedWordsIterator = 0;
	private String word;
	private String readWords;

	// Data to Export for Person:
	private String firstname = "empty";
	private String lastname = "empty";
	private Integer afmFinal = 00000;
	private Double incomeFinal = 00000d;
	
	// Data to Export for Receipts:
	private ArrayList<Receipt> receiptsList = new ArrayList<Receipt>();
			
	public ScannerXmlParser(File xmlFile) {
		
			tokenizeInput(xmlFile);
		    parsePersonData();
		    parseReceiptData();
	}
	
	private void tokenizeInput (File xmlFile) {
		
		BufferedReader in = null;
		
		try {
			// First, we open the file and read all the lines of the file:
		    in = new BufferedReader(new FileReader(xmlFile));
		    String readLine = "";
		    while ( (readLine = in.readLine()) != null ) {
		    	if (readWords == null) {
		    		readWords = readLine;
		    	} else {
		    		readWords = readWords+" "+readLine;
		    	}
		    }
		    
		    System.out.println(readWords);
		    
		    // Now we want to split the lines string in words:
		    if ( readWords != null) {
		    	String[] splited;
		        splited = readWords.split("\\s+");
		        for (String word : splited) {
		            if (word != null) { 
		            	parsedWords.add(word);
		            }
		        }
		    }
		    
		    // For Debugging:
		    for (int i=0; i<parsedWords.size(); i++){
		    	System.out.println(i+": "+parsedWords.get(i));
		    }
		    
		} catch (IOException e) {
		    System.out.println("There was a problem: " + e);
		    e.printStackTrace();
		} finally {
		    try {
		        in.close();
		    } catch (Exception e) {
		    }
		}
	}
	
	private void parsePersonData() {
			
		// Consume the <Person> opening tag:
		consumePerson();
		name = checkInsideTag("Name").trim();
		afm = checkInsideTag("AFM").trim();
		status = checkInsideTag("Status").trim();
		income = checkInsideTag("Income").trim();

		// Preparing name for processing:
		String[] splittedName = name.split("\\s+");
		
		// Encoding to Person Object compatible types:
		firstname = splittedName[0];
		lastname = splittedName[1];
		afmFinal = Integer.parseInt(afm);
		incomeFinal = Double.parseDouble(income);
				
		// Consume the <Person> closing tag:
		getNextWord();

	}
	
	private void parseReceiptData() {
		
		consumeReceipt();
		
		while ( isNextWordReceiptId() ) {
						
			String receiptId = checkInsideTag("ReceiptID").trim();
			String date = checkInsideTag("Date").trim();
			String kind = checkInsideTag("Kind").trim();
			String amount = checkInsideTag("Amount").trim();
			String company = checkInsideTag("Company").trim();
			String country = checkInsideTag("Country").trim();
			String city = checkInsideTag("City").trim();
			String street = checkInsideTag("Street").trim();
			String addressNumber = checkInsideTag("Number").trim();
			
			// Encode to types compatible with the Receipt Object:
			Integer receiptIdFinal = Integer.parseInt(receiptId);
			Date dateFinal = new Date();
			DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
			try {
				dateFinal = format.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String categoryFinal = kind;
			Double amountFinal = Double.valueOf(amount);
			Company companyFinal = new Company(company, street + " " + addressNumber + ", " + city + ", " + country);
			
			receiptsList.add(new Receipt(receiptIdFinal, dateFinal, categoryFinal, amountFinal, companyFinal));
			
			// Debugging:
			System.out.println(receiptId+" "+date+" "+kind+" "+amount+" "+company+" "+country+" "+city+" "+street+" "+addressNumber);
			System.out.println("Receipts List Size: "+receiptsList.size());
		}
	}
	
	private String checkInsideTag(String tagElement) {
		
		getNextWord();
		System.out.println("Element: "+ tagElement+ ", Word: "+ word +", Iterator: " + parsedWordsIterator);
		String currentWord = "";
		
		if(word.equals("<"+tagElement+">")){
			
			System.out.println("The word is a starting tag.");
			getNextWord();
			
			while( !(word.equalsIgnoreCase("</"+tagElement+">")) ){
				currentWord = currentWord + " " + word;
				getNextWord();
			}
			
			System.out.println("The word is a closing tag.");		
			
			return currentWord;
			
		} else {
			System.out.println("Problem in XML Parsing of tag: "+tagElement);
			return null;
		}
	}

	private void getNextWord() {
		if (parsedWordsIterator < parsedWords.size()) {
			parsedWordsIterator++;
			word = parsedWords.get(parsedWordsIterator);
			System.out.println("Got Next Word: "+word);
		} else {
			word = "\0";
		}
	}
	
	private Boolean isNextWordReceiptId() {
		getNextWord();
		if (word.equals("<ReceiptID>")){
			parsedWordsIterator = parsedWordsIterator - 2;
			getNextWord();
			return (true);
		} else {
			return (false);
		}
	}
	
	private void consumePerson(){
		word = parsedWords.get(0);

		if( word.equals("<Person>")){
			System.out.println("Person correctly consumed");
		}
	}
	
	private void consumeReceipt(){

		getNextWord();
		
		if( word.equals("<Receipt>")){
			System.out.println("Receipt correctly consumed");
		}
	}
	
	// Person Getters:
	public ArrayList<Receipt> getReceiptsList() {
		return receiptsList;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Integer getAfm() {
		return afmFinal;
	}

	public Double getIncome() {
		return incomeFinal;
	}

	public String getCategory() {
		return status;
	}
}