package dataInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
			parseInputLines(in, textFile);
			splitInputLinesToWords();
		    closeInputFile(in);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	private void closeInputFile(BufferedReader in){
		try {
	        in.close();
	    } catch (Exception e) {
	    }
	}
	
	private void parseInputLines(BufferedReader in, File textFile) throws IOException{
	    in = new BufferedReader(new FileReader(textFile));
	    String readLine = "";
	    
	    while ( (readLine = in.readLine()) != null ) {
	    	concatenateInputWordsToBuffer(readLine);
	    }
	    
	    in.close();
	}
	
	private void splitInputLinesToWords(){
	    if ( readWords != null) {
	    	String[] splited;
	        splited = readWords.split("\\s+");
	        for (String word : splited) {
	            if (word != null) { 
	            	parsedWords.add(word);
	            }
	        }
	    }
	}
	
	private void concatenateInputWordsToBuffer(String readLine) {
    	if (readWords == null) {
    		readWords = readLine;
    	} else {
    		readWords = readWords+" "+readLine;
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