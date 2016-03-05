package userInterface;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import dataManagement.Receipt;

public class ReceiptListModel extends AbstractListModel<String> {
	
	private ArrayList<Receipt> myArrayList = new ArrayList<Receipt>();
	
	ReceiptListModel(ArrayList<Receipt> receiptList) {
		myArrayList = receiptList; 
	}

	@Override
	public int getSize() {
		return myArrayList.size();
	}

	@Override
	public String getElementAt(int index) {
		
		Receipt currentReceipt = myArrayList.get(index);
		
		String basicData = "Id: "+currentReceipt.getReceiptId().toString() +
				"\n"+ currentReceipt.getAmount().toString() + " $\n" + currentReceipt.getCompany().getName();

		return basicData;
	}
	
    public void addElement(Receipt obj) {
        myArrayList.add(obj);
        fireIntervalAdded(this, myArrayList.size()-1, myArrayList.size()-1);
    }
    
    public void updateElement(int position, String firstName, String lastName, Integer identifyingNumber, Double income) {
    	// Not implemented.
    }
    
    public void addAll(ArrayList<Receipt> receiptList) {
        
    	for(int i=0; i<receiptList.size(); i++) {
    		myArrayList.add(receiptList.get(i));
    	}
        fireContentsChanged(this, 0, getSize());
        
    }
}
