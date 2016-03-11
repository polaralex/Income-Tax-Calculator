package dataInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ScannerXmlParser {
	
	private String name = null;
	private String afm = null;
	private String status = null;
	private String income = null;
	
	private ArrayList<String> parsedWords = new ArrayList<String>();
	private Integer parsedWordsIterator = 0;
	
	private String word;
	private String peekWord;
	private String readWords;
	
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
		getNextWord();
		
		name = checkInsideTag("Name");
		afm = checkInsideTag("AFM");
		status = checkInsideTag("Status");
		income = checkInsideTag("Income");
		
		// Consume the <Person> closing tag:
		getNextWord();
		
		printCurrentData();
	}
	
	private void parseReceiptData() {
		
		// Consume the <Receipts> opening tag (and go to the next one):
		getNextWord();
		
		while (peekWord != "</Receipts>" || word != "</Receipts>") {
						
			String receiptId = checkInsideTag("ReceiptID");
			String date = checkInsideTag("Date");
			String kind = checkInsideTag("Kind");
			String amount = checkInsideTag("Amount");
			String company = checkInsideTag("Company");
			String country = checkInsideTag("Country");
			String city = checkInsideTag("City");
			String street = checkInsideTag("Street");
			String number = checkInsideTag("Number");
			
			System.out.println(receiptId+" "+date+" "+kind+" "+amount+" "+company+" "+country+" "+city+" "+street+" "+number);
		}
	}
	
	private void getNextWord() {
		if (parsedWordsIterator < parsedWords.size()-2) {
			parsedWordsIterator = parsedWordsIterator + 1;
			word = parsedWords.get(parsedWordsIterator);
			peekWord = parsedWords.get(parsedWordsIterator+1);
			System.out.println("Get Next Word: " + word);
		} else if (parsedWordsIterator == parsedWords.size()-1) {
			word = parsedWords.get(parsedWordsIterator);
			peekWord = null;
			parsedWordsIterator++;
		} else {
			word = null;
			peekWord = null;
			parsedWordsIterator++;
		}
	}
	
	private String checkInsideTag(String tagElement) {
		
		System.out.println("Element: "+ tagElement+ ", Word: "+ word);
		String currentWord = "";
		
		if(word.equals("<"+tagElement+">")){
						
			getNextWord();
			
			while( !(word.equalsIgnoreCase("</"+tagElement+">")) ){
				currentWord = currentWord + " " + word;
				getNextWord();
			}
			
			getNextWord();			
			return currentWord;
			
		} else {
			System.out.println("Problem in XML Parsing of tag: "+tagElement);
			return null;
		}
	}
	
	public String getName() {
		return name;
	}

	public String getAfm() {
		return afm;
	}

	public String getStatus() {
		return status;
	}

	public String getIncome() {
		return income;
	}
	
	private void printCurrentData() {
		System.out.println("Imported Person Data: "+name+" "+afm+" "+status+" "+income);
	}
}