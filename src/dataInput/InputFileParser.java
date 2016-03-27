package dataInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dataManagement.Receipt;

public abstract class InputFileParser {
	
	// Person data in String form:
	protected String name = null;
	protected String afm = null;
	protected String status = null;
	protected String income = null;
	
	protected ArrayList<String> parsedWords = new ArrayList<String>();
	protected Integer parsedWordsIterator = 0;
	protected String word;
	protected String readWords;

	// Data to Export for Person:
	protected String firstname = "empty";
	protected String lastname = "empty";
	protected Integer afmFinal = 00000;
	protected Double incomeFinal = 00000d;
	
	// Data to Export for Receipts:
	protected ArrayList<Receipt> receiptsList = new ArrayList<Receipt>();
	
	abstract void parsePersonData();
	abstract void parseReceiptData();
	
	protected void tokenizeInput (File textFile) {
		
		BufferedReader in = null;
		
		try {
			// First, we open the file and read all the lines of the file:
		    in = new BufferedReader(new FileReader(textFile));
		    
		    String readLine = "";
		    
		    while ( (readLine = in.readLine()) != null ) {
		    	
		    	if (readWords == null) {
		    		readWords = readLine;
		    	} else {
		    		readWords = readWords+" "+readLine;
		    	}
		    }
		    		    
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
	
	protected void getNextWord() {
		if (parsedWordsIterator < parsedWords.size()) {
			
			word = parsedWords.get(parsedWordsIterator);
			parsedWordsIterator++;
			
		} else {
			word = "\0";
		}
	}
	
	protected void goToPreviousWord() {
		parsedWordsIterator--;
		word = parsedWords.get(parsedWordsIterator);
	}
	
	protected Boolean isEndOfParsedWords() {
		if(parsedWordsIterator >= parsedWords.size()){
			return(true);
		} else {
			return(false);
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

	public String getCategory() {
		return status;
	}
}