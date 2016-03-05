package userInterface;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import dataManagement.Person;

public class PersonListModel extends AbstractListModel<String> {
	
	private final ArrayList<Person> myArrayList = new ArrayList<Person>();

	@Override
	public int getSize() {
		return myArrayList.size();
	}

	@Override
	public String getElementAt(int index) {
		
		Person currentPerson = myArrayList.get(index);
		
		String basicData = currentPerson.getIdentifyingNumber()
					+ ". " + currentPerson.getFirstName()
					+ " " + currentPerson.getLastName();

		return basicData;
	}
	
    public void addElement(Person obj) {
        myArrayList.add(obj);
        fireIntervalAdded(this, myArrayList.size()-1, myArrayList.size()-1);
    }
    
    public void updateElement(int position, String firstName, String lastName, Integer identifyingNumber, Double income) {
    	
    	myArrayList.get(position).setFirstName(firstName);
    	myArrayList.get(position).setLastName(lastName);
    	myArrayList.get(position).setIdentifyingNumber(identifyingNumber);
    	myArrayList.get(position).setIncome(income);
        fireIntervalAdded(this, myArrayList.size()-1, myArrayList.size()-1);

    }
    
    public void addAll(ArrayList<Person> personList) {
        
    	for(int i=0; i<personList.size(); i++) {
    		myArrayList.add(personList.get(i));
    	}
        fireContentsChanged(this, 0, getSize());
        
    }
}
