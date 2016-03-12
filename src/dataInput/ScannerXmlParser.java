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
import dataManagement.Receipt;

import java.util.ArrayList;
import java.util.Locale;

public class ScannerXmlParser {
	
	private String name = null;
	private String afm = null;
	private String status = null;
	private String income = null;
	
	private ArrayList<String> parsedWords = new ArrayList<String>();
	private Integer parsedWordsIterator = 0;
	private String word;
	private String readWords;

	//Data to Export:
	private String firstname;
	private String lastname;
	private Integer afmFinal;
	private Double incomeFinal;
	private ArrayList<Receipt> receiptsList = new ArrayList<Receipt>();
		
	public ScannerXmlParser(File xmlFile) {
		
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
		   		    
		    // Then, do the parsing on the cleaned up data:
		    parsePersonData();
		    parseReceiptData();
		    
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
		name = checkInsideTag("Name");
		afm = checkInsideTag("AFM");
		status = checkInsideTag("Status");
		income = checkInsideTag("Income");

		//String[] splittedName = name.split("\\s+");
		
		// Encoded to Person Object compatible types:
		firstname = name;
		lastname = " ";
		afmFinal = Integer.getInteger(afm);
		incomeFinal = Double.parseDouble(income);
		
		// Consume the <Person> closing tag:
		getNextWord();

	}
	
	private String checkInsideTag(String tagElement) {
		
		getNextWord();
		System.out.println("Element: "+ tagElement+ ", Word: "+ word +", Iterator: "+parsedWordsIterator);
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
	
	private void parseReceiptData() {
		
		consumeReceipt();
		
		while ( isNextWordReceiptId() ) {
						
			String receiptId = checkInsideTag("ReceiptID");
			String date = checkInsideTag("Date");
			String kind = checkInsideTag("Kind");
			String amount = checkInsideTag("Amount");
			String company = checkInsideTag("Company");
			String country = checkInsideTag("Country");
			String city = checkInsideTag("City");
			String street = checkInsideTag("Street");
			String addressNumber = checkInsideTag("Number");
			
			// Encode to types compatible with the Receipt Object:
			Integer receiptIdFinal = Integer.getInteger(receiptId);
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
	
	public String getName() {
		return name;
	}

	public String getCategory() {
		return status;
	}
}