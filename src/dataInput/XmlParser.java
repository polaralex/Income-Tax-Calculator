package dataInput;

import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dataManagement.Company;
import dataManagement.Receipt;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class XmlParser extends InputFileParser {

	public XmlParser(File filename) {
		super(filename);
	}
	
	protected void parseReceiptData() {

		consumeReceipt();

		while (isNextWordReceiptId()) {
			
			receiptId = checkInsideTag("ReceiptID").trim();
			parseReceiptDataCommonCode();
		}
	}

	protected String checkInsideTag(String tagElement) {

		getNextWord();

		String currentWord = "";

		if (word.equals("<" + tagElement + ">")) {

			getNextWord();

			while (!(word.equalsIgnoreCase("</" + tagElement + ">"))) {
				currentWord = currentWord + " " + word;
				getNextWord();
			}

			return currentWord;

		} else {
			return null;
		}
	}

	private Boolean isNextWordReceiptId() {
		getNextWord();
		if (word.equals("<ReceiptID>")) {
			parsedWordsIterator = parsedWordsIterator - 2;
			getNextWord();
			return (true);
		} else {
			return (false);
		}
	}

}