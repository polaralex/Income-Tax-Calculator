package userInterface;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dataManagement.Receipt;

public class ReceiptsPanel extends JPanel implements ActionListener {
	
	private ArrayList<Receipt> receiptsList;
	private ReceiptListModel model;
	private JList<String> list;

	ReceiptsPanel(ArrayList<Receipt> receiptsList) {
		
		this.receiptsList = receiptsList;
		
		list = new JList<String>();
		model = new ReceiptListModel(receiptsList);
		list.setModel(model);

		this.add(list);
	}
	
	private Color chooseReceiptColor (Receipt receipt) {
		
		if(receipt.getCategory()=="Super Market") {
			return(new Color(121,189,143));
		} else if (receipt.getCategory()=="Petrol") {
			return(new Color(255,97,96));
		} else {
			return(new Color(255,255,157));
		}
	}
	
	public ReceiptListModel getModel () {
		return model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
