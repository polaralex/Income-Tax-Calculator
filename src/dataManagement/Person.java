package dataManagement;

import java.io.File;
import java.util.ArrayList;

public class Person {

	// Constants for children-types initialization:
	public static final int MARRIED_FILING_JOINTLY = 1;
	public static final int MARRIED_FILING_SEPERATELY = 2;
	public static final int HEAD_OF_HOUSEHOLD = 3;
	public static final int SINGLE = 4;

	private String firstName;
	private String lastName;
	private Integer identifyingNumber;
	private Double income;
	private String personType;
	private ArrayList<Receipt> receipts = new ArrayList<Receipt>();
	private File file = null;

	public Person(String firstName, String lastName, String personType, Integer identifyingNumber, Object income) {

		this.firstName = firstName;
		this.lastName = lastName;
		setIdentifyingNumber(identifyingNumber);
		this.personType = personType;
		setIncome(income);
	}

	public Person() {
		this.firstName = "";
		this.lastName = "";
		this.identifyingNumber = getRandomId();
		this.income = 0d;
		this.personType = "Single";
	}

	private Integer getRandomId() {
		Double idNumberDouble = (Math.random() * (999999999 - 100000000));
		return idNumberDouble.intValue();
	}

	public Double calculateTaxBeforeReceipts(){

		if (personType.equals("Single")) {
			return (calculateTaxSingle());
		}
		if (personType.equals("Head of Household")) {
			return (calculateTaxHeadOfHousehold());
		}
		if (personType.equals("Married Filing Jointly")) {
			return (calculateTaxMarriedFilingJointly());
		}

		return (calculateTaxMarriedFilingSeperately());
	}

	public Double calculateTaxSingle() {

		if ( income >= 0 && income < 24680) {
			return ( income * 0.0535d );
		}
		if ( income < 81080 ) {
			return ( 1320.38 + 0.0705*(income - 24680) );
		}
		if ( income < 90000 ) {
			return ( 5296.58 + 0.0785*(income - 81080) );
		}
		if ( income < 152540 ) {
			return ( 5996.80 + 0.0785*(income - 90000) );
		}
		
		return ( 10906.19 + 0.0985*(income - 152540) );
	}
	
	public Double calculateTaxHeadOfHousehold() {
		
		if (income >= 0 && income < 30390) {
			return (income * 0.0535d);
		}
		if (income < 90000) {
			return (1624.87 + 0.0705 * (income - 30390));
		} 
		if (income < 122110) {
			return (5828.38 + 0.0705 * (income - 90000));
		}
		if (income < 203390) {
			return (8092.13 + 0.0785 * (income - 122110));
		}
		return (14472.61 + 0.0985 * (income - 203390));
	}
	
	public Double calculateTaxMarriedFilingJointly() {
		
		if ( income >= 0 && income < 36080) {
			return ( income * 0.0535d );
		}
		if ( income < 90000 ) {
			return ( 1930.28 + 0.0705*(income - 36080) );
		}
		if ( income < 143350 ) {
			return ( 5731.64 + 0.0785*(income - 90000) );
		}
		if ( income < 254240 ) {
			return ( 9492.82 + 0.0785*(income - 143350) );
		}
		
		return ( 18197.69 + 0.0985*(income - 254240) );
	}
	
	public Double calculateTaxMarriedFilingSeperately() {
		
		if (income >= 0 && income < 18040) {
			return (income * 0.0535d);
		}
		if (income < 71680) {
			return (965.14 + 0.0705 * (income - 18040));
		}
		if (income < 90000) {
			return (4746.76 + 0.0785 * (income - 71680));
		}
		if (income < 127120) {
			return (6184.88 + 0.0785 * (income - 90000));
		}
		return (9098.80 + 0.0985 * (income - 127120));
	}

	public Double calculateFinalTax() {

		Double receiptAmount = calculateReceiptAmount();
		Double taxBeforeReceipts = calculateTaxBeforeReceipts();

		if (receiptAmount >= 0 && receiptAmount < (taxBeforeReceipts * 0.2)) {
			return (taxBeforeReceipts + (taxBeforeReceipts * 0.08));
		}
		if (receiptAmount < (taxBeforeReceipts * 0.4)) {
			return (taxBeforeReceipts + (taxBeforeReceipts * 0.04));
		}
		if (receiptAmount < (taxBeforeReceipts * 0.6)) {
			return (taxBeforeReceipts - (taxBeforeReceipts * 0.15));
		}
		
		return (taxBeforeReceipts - (taxBeforeReceipts * 0.3));
	}

	// This one gives the total amount:
	public Double calculateReceiptAmount() {

		Double receiptsAmount = 0d;

		for (int i = 0; i < receipts.size(); i++) {
			receiptsAmount = receiptsAmount + receipts.get(i).getAmount();
		}

		return (receiptsAmount);
	}

	// Polymorphic alternative that gives the total of a specific category:
	public Double calculateReceiptAmount(String category) {

		Double receiptsAmount = 0d;

		for (Receipt receipt : receipts) {
			if (receipt.getCategory().equals(category)) {
				receiptsAmount = receiptsAmount + receipt.getAmount();
			}
		}

		return (receiptsAmount);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getIdentifyingNumber() {
		return identifyingNumber;
	}

	public void setIdentifyingNumber(Integer input) {
		if (input == 0) {
			this.identifyingNumber = getRandomId();
		} else {
			this.identifyingNumber = input;
		}
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Object input){
		if (input.getClass() != Double.class) {
			this.income = ((Integer) input).doubleValue();
		} else {
			this.income = (Double) input;
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void addReceipt(Receipt receipt) {
		receipts.add(receipt);
	}
	
	public void setCategory(String category) {
		this.personType = category;
	}
	
	public String getPersonCategory(){
		return this.personType;
	}

	public ArrayList<Receipt> getReceiptsList() {
		return receipts;
	}

	public void addReceiptsList(ArrayList<Receipt> importedReceiptsList) {
		receipts.addAll(importedReceiptsList);
	}
}