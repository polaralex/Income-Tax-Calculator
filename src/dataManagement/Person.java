package dataManagement;

import java.util.ArrayList;

public abstract class Person {
	
	private String firstName;
	private String lastName;
	private Integer identifyingNumber;
	private Double income;
	private ArrayList<Receipt> receipts = new ArrayList<Receipt>();
	
	public Person (String firstName, String lastName, Integer identifyingNumber, Object income) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.identifyingNumber = identifyingNumber;
		
		if( income.getClass() != Double.class ){
			this.income = ((Integer)income).doubleValue();
		} else {
			this.income = (Double)income;
		}
	}
	
	// Abstract Functions:
	public abstract Double calculateTaxBeforeReceipts();
	
	// Functions:
	public Double calculateTax() {
		
		Double receiptAmount = calculateReceiptAmount();
		Double taxBeforeReceipts = calculateTaxBeforeReceipts();
		
		if ( receiptAmount >= 0 && receiptAmount < (taxBeforeReceipts * 0.2) ) {
			return( taxBeforeReceipts + (taxBeforeReceipts * 0.08) );
		} else if ( receiptAmount < (taxBeforeReceipts * 0.4) ) {
			return( taxBeforeReceipts + (taxBeforeReceipts * 0.04) );
		} else if ( receiptAmount < (taxBeforeReceipts * 0.6) ) {
			return( taxBeforeReceipts - (taxBeforeReceipts * 0.15) );
		} else {
			return( taxBeforeReceipts - (taxBeforeReceipts * 0.3) );
		}
		
	}
	
	public Double calculateReceiptAmount(){
		
		Double receiptsAmount = 0d;
		
		for( int i=0; i<receipts.size(); i++ ){
			receiptsAmount = receiptsAmount + receipts.get(i).getAmount();
		}
		
		return (receiptsAmount);
	}
	
	// Getters and Setters:
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
	public void setIdentifyingNumber(Integer identifyingNumber) {
		this.identifyingNumber = identifyingNumber;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	
	public void addReceipt (Receipt receipt) {
		receipts.add(receipt);
	}
	
	public ArrayList<Receipt> getReceiptsList () {
		return receipts;
	}
	
}
