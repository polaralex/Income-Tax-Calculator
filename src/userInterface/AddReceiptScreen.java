package userInterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dataManagement.Company;
import dataManagement.Receipt;

public class AddReceiptScreen extends JFrame implements ActionListener {

	private JButton okButton;
	private JButton cancelButton;
	private JLabel explainAction;
	private JTextField idInput;
	private JTextField amountInput;
	private JTextField companyInput;
	//private JTextField categoryInput;
	private JComboBox<String> categoryInput;
	private String[] categories = { "Basic", "Entertainment", "Travel", "Health", "Other" };
	private ReceiptListModel receiptsListModel;
	
	AddReceiptScreen(ReceiptListModel receiptListModel) {
		
		this.receiptsListModel = receiptListModel;
		
		this.setTitle("Add a new Receipt");
		this.setMinimumSize(new Dimension(500,300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		this.add(panel);
		
		explainAction = new JLabel("Enter the Data for a New Receipt:");
		JLabel idLabel = new JLabel("Id: ");
		// Present the user with an "optional" randomly generated receipt id-number:
		idInput = new JTextField(String.valueOf((int)(Math.random() * ( 1000000 - 0 ))));
		JLabel amountLabel = new JLabel("Amount: ");
		amountInput = new JTextField();
		JLabel companyLabel = new JLabel("Company: ");
		companyInput = new JTextField();
		JLabel categoryLabel = new JLabel("Category: ");
		categoryInput = new JComboBox<String>(categories);
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		addGridItem(panel, explainAction, 1, 1, 2, 1, GridBagConstraints.CENTER);
		
		addGridItem(panel, idLabel, 0, 2, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, idInput, 1, 2, 1, 1, GridBagConstraints.EAST);
		
		addGridItem(panel, amountLabel, 0, 3, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, amountInput, 1, 3, 1, 1, GridBagConstraints.EAST);
		
		addGridItem(panel, companyLabel, 0, 4, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, companyInput, 1, 4, 1, 1, GridBagConstraints.EAST);
		
		addGridItem(panel, categoryLabel, 0, 5, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, categoryInput, 1, 5, 1, 1, GridBagConstraints.EAST);
		
		addGridItem(panel, okButton, 0, 6, 1, 1, GridBagConstraints.CENTER);
		addGridItem(panel, cancelButton, 1, 6, 1, 1, GridBagConstraints.CENTER);
		
		this.pack();
		this.setVisible(true);
	}
		
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == okButton ){
			
			Date date = new Date();
			Company company = new Company(companyInput.getText(), "no address");
			receiptsListModel.addElement(new Receipt(Integer.valueOf(idInput.getText()), date, (String)categoryInput.getSelectedItem(), Double.valueOf(amountInput.getText()), company));
			this.dispose();
			
		} else if ( e.getSource() == cancelButton ) {
			
			this.dispose();
			
		}
	}
	
	private void addGridItem(JPanel panel, JComponent comp,
	        int x, int y, int width, int height, int align) {
		
	    GridBagConstraints gcon = new GridBagConstraints();
	    gcon.gridx = x;
	    gcon.gridy = y;
	    gcon.gridwidth = width;
	    gcon.gridheight = height;
	    gcon.weightx = 0.5;       // a hint on apportioning space
	    gcon.weighty = 0.5;
	    gcon.insets = new Insets(5, 5, 5, 5);   // padding
	    gcon.anchor = align;    // applies if fill is NONE
	    gcon.fill = GridBagConstraints.HORIZONTAL;
	    
	    panel.add(comp, gcon);
	}
	
}
