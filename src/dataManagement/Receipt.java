package dataManagement;

import java.util.Date;

public class Receipt {
	
	private Integer receiptId;
	private Date dateOfIssue;
	private String category;
	private Double amount;
	private Company company;
	
	public Receipt (Integer receiptId, Date dateOfIssue, String category, Double amount, Company company) {
		
		this.receiptId = receiptId;
		this.dateOfIssue = dateOfIssue;
		this.category = getCorrectCategory(category);
		this.amount = amount;
		this.company = company;
	}
	
	private String getCorrectCategory (String category) {
		
		if ( !category.equals("Basic") && !category.equals("Entertainment") && !category.equals("Travel") && !category.equals("Health") && !category.equals("Other")) {
			return "Other";
		} else {
			return category;
		}
	}
	
	public Integer getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Integer receiptId) {
		this.receiptId = receiptId;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = getCorrectCategory(category);
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}