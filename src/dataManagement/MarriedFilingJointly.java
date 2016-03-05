package dataManagement;

public class MarriedFilingJointly extends Person {

	MarriedFilingJointly(String firstName, String lastName, Integer identifyingNumber, Object income) {
		super(firstName, lastName, identifyingNumber, income);
	}
	
public Double calculateTaxBeforeReceipts() {
		
		Double income = getIncome();
		
		if( income >= 0 && income < 36080) {
			
			return ( income * 0.0535d );
			
		} else if ( income < 90000 ) {
			
			return ( 1930.28 + 0.0705*(income - 36080) );
		
		} else if ( income < 143350 ) {
			
			return ( 5731.64 + 0.0705*(income - 90000) );
		
		} else if ( income < 254240 ) {
			
			return ( 9492.82 + 0.0785*(income - 143350) );
		
		} else {
			
			return ( 18197.69 + 0.0985*(income - 254240) );
		
		}
	}
}
