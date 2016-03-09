package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import dataManagement.Person;
import dataManagement.Receipt;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

public class PersonCard extends GridBagBasedScreen implements ActionListener {
	
	private Person person;
	
	private JButton buttonSaveChanges;
	private JButton buttonClose;
	private JButton buttonAddReceipt;
	private JButton buttonDeleteReceipt;
	private JTextArea textId;
	private JTextArea textAreaName;
	private JTextArea textAreaSurname;
	private JTextArea textIncome;
	private JLabel personTypeLabel;
	
	private ReceiptsPanel receiptsPanel;
	
	public PersonCard(Person person) {
		this.person = person;
		showCard();
	}
	
	public void showCard() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(150, 150, 750, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(750,400));
		this.setTitle("Tax-Income Calculator");
		
		this.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				// Do nothing (yet).
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				repaint();
				revalidate();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		// Get text-field Strings from the Person Object:
		textId = new JTextArea(person.getIdentifyingNumber().toString());
		textAreaName = new JTextArea(person.getFirstName());
		textAreaSurname = new JTextArea(person.getLastName());
		textIncome = new JTextArea(person.getIncome().toString());
		personTypeLabel = new JLabel("Category: "+ person.getPersonType());
		personTypeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		// Buttons Initialization:
		buttonSaveChanges = new JButton("Save");
		buttonSaveChanges.addActionListener(this);
		buttonClose = new JButton("Close");
		buttonClose.addActionListener(this);
		buttonAddReceipt = new JButton("Add");
		buttonAddReceipt.addActionListener(this);
		buttonDeleteReceipt = new JButton("Delete");
		buttonDeleteReceipt.addActionListener(this);

		// Person placeholder image initialization:
		BufferedImage myPicture = null;
		
		try {
			myPicture = ImageIO.read(getClass().getResourceAsStream("/res/icon-user-small.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setMaximumSize(new Dimension(200,200));
		
		// Set up the Grid Bag Layout:
		new GridBagConstraints();
		
		// We put a "title" wrapper around the JTextAreas:
		JPanel wrapperName = new JPanel(new BorderLayout());
		wrapperName.setBorder(new TitledBorder("First Name:"));
		wrapperName.add(textAreaName);
		
		JPanel wrapperSurname = new JPanel(new BorderLayout());
		wrapperSurname.setBorder(new TitledBorder("Last Name:"));
		wrapperSurname.add(textAreaSurname);
		
		JPanel wrapperIncome = new JPanel(new BorderLayout());
		wrapperIncome.setBorder(new TitledBorder("Income:"));
		wrapperIncome.add(textIncome);
		
		JPanel wrapperId = new JPanel(new BorderLayout());
		wrapperId.setBorder(new TitledBorder("Id:"));
		wrapperId.add(textId);
		
		// Receipts Panel initialization:
		receiptsPanel = new ReceiptsPanel(person.getReceiptsList());
		
		// Add the receipts FlowLayout inside the Titled border Panel:
		JScrollPane scrollReceiptsPanel = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollReceiptsPanel.setBorder(new TitledBorder("Receipts:"));
		scrollReceiptsPanel.setMinimumSize(new Dimension(100, 120));
		scrollReceiptsPanel.setViewportView(receiptsPanel);
		
		addGridItem(panel, personTypeLabel, 0, 0, 2, 1, GridBagConstraints.CENTER);
		addGridItem(panel, picLabel, 0, 1, 2, 2, GridBagConstraints.CENTER);
		addGridItem(panel, wrapperName, 2, 0, 2, 1, GridBagConstraints.EAST);
		addGridItem(panel, wrapperSurname, 2, 1, 2, 1, GridBagConstraints.EAST);
		addGridItem(panel, wrapperIncome, 2, 2, 2, 1, GridBagConstraints.EAST);
		addGridItem(panel, wrapperId, 2, 3, 2, 1, GridBagConstraints.EAST);
		
		addGridItem(panel, scrollReceiptsPanel, 4, 0, 2, 3, GridBagConstraints.SOUTH);
		addGridItem(panel, buttonAddReceipt, 4, 3, 1, 2, GridBagConstraints.NORTHWEST);
		addGridItem(panel, buttonDeleteReceipt, 5, 3, 1, 2, GridBagConstraints.NORTHEAST);
		
		addGridItem(panel, buttonSaveChanges, 0, 4, 1, 1, GridBagConstraints.CENTER);
		addGridItem(panel, buttonClose, 1, 4, 1, 2, GridBagConstraints.CENTER);
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
		
		textId.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textAreaName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textAreaSurname.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textIncome.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == buttonSaveChanges ) {
			
			updatePersonData();
			this.dispose();
			
		} else if ( e.getSource() == buttonClose ) {
			
			this.dispose();
			
		} else if ( e.getSource() == buttonAddReceipt ) {
			
			AddReceiptScreen addReceiptScreen = new AddReceiptScreen(receiptsPanel.getModel());
			
		} else if ( e.getSource() == buttonDeleteReceipt ) {
			
			if ( receiptsPanel.isAnyListCellSelected() == true ){
				receiptsPanel.deleteSelectedCell();
			}
		}
	}
	
	private void updatePersonData() {
		person.setFirstName(textAreaName.getText());
		person.setLastName(textAreaSurname.getText());
		person.setIncome( Double.valueOf(textIncome.getText()) );
		person.setIdentifyingNumber( Integer.valueOf(textId.getText()) );
	}

	public void hideCard() {
		this.setVisible(false);
	}
}