package userInterface;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dataManagement.PeopleManager;
import dataManagement.Person;
import dataManagement.Receipt;

import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import dataInput.InputFileParser;
import dataInput.TextFileParser;
import dataInput.XmlEncoder;
import dataInput.XmlParser;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class MainScreen extends GridBagBasedScreen {

	PeopleManager peopleManager;
	private JFrame frame;
	private JLabel mainLabelText;
	private JScrollPane scrollPane;
	private JList list;
	private PersonListModel model;
	private JButton buttonImportPerson;
	private JButton buttonExport;
	private JButton buttonCreatePerson;
	private JButton buttonDeletePerson;
	private JButton buttonClose;
	private String[] personTypes = { "Married Filing Jointly", "Married Filing Seperately", "Head of Household", "Single" };
	XmlParser inputFileParser;
	String filename = "";
	JFileChooser fileChooser;
	Person selectedObject;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainScreen() {
		initialize();
	}

	private void initialize() {
		
		frame = new JFrame("Tax-Income Calculator");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(600,400));
		
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout( new GridBagLayout() );
		
		frame.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				frame.repaint();
				frame.revalidate();
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				
			}
		});
		
		// Top Description Text:
		mainLabelText = new JLabel("List of People and Tax Data:");
		mainLabelText.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		// People Manager Initialization:
		peopleManager = new PeopleManager();

		//List model initialization:
		list = new JList();
		list.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		model = new PersonListModel();
		list.setModel(model);
		model.addAll(peopleManager.getPersonList());

		scrollPane = new JScrollPane(list);
		scrollPane.setMinimumSize(new Dimension(400,400));
		
		// Main Screen Layout Configuration:
		addGridItem(panel, mainLabelText, 0, 0, 5, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, 0.5f);
		addGridItem(panel, scrollPane, 0, 1, 5, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0f);
		
		buttonCreatePerson = new JButton("New");
		buttonDeletePerson = new JButton("Delete");
		buttonImportPerson = new JButton("Import");
		buttonExport = new JButton("Export");
		buttonClose = new JButton("Close");
		
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if ( e.getSource() == buttonCreatePerson ) {
					
					Object category = (String)JOptionPane.showInputDialog(frame, "Choose the type of Tax-Payer:", "Create a new Person", JOptionPane.PLAIN_MESSAGE, null, personTypes, personTypes[0]);					
					
					// Finds the index of the selected category:
					int categoryInteger=0;
					while (personTypes[categoryInteger] != category.toString() && categoryInteger < personTypes.length){
						categoryInteger++;
					}

					Person newPerson = peopleManager.createNewPerson(categoryInteger+1);
					PersonCard createPerson = new PersonCard(newPerson);
					model.addElement(newPerson);
					
				} else if ( e.getSource() == buttonDeletePerson ) {
										
					if (list.getSelectedIndex() >= 0) {
						model.removeItem(list.getSelectedIndex());
					}
					
				} else if ( e.getSource() == buttonImportPerson ) {
					
					fileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("XML and TXT files", "xml", "txt");
					fileChooser.setFileFilter(filter);
					int returnVal = fileChooser.showOpenDialog(frame);
					
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						
						filename = fileChooser.getSelectedFile().getAbsolutePath();
						
						InputFileParser inputFileParser;
						
						if( filename.substring(filename.lastIndexOf(".") + 1).equals("xml") ){
							inputFileParser = new XmlParser(new File(filename));
						} else {
							inputFileParser = new TextFileParser(new File(filename));
						}
												
						String firstname = inputFileParser.getFirstname();
						String lastname = inputFileParser.getLastname();
						String category = inputFileParser.getCategory();
						Integer afm = inputFileParser.getAfm();
						Double income = inputFileParser.getIncome();
						ArrayList<Receipt> receiptList = inputFileParser.getReceiptsList();
						
						Person newPerson = peopleManager.createNewPerson(category, firstname, lastname, afm, income);
						
						if (!receiptList.equals(null)){
							newPerson.addReceiptsList(receiptList);
						}
						
						model.addElement(newPerson);
												
					}
				} else if ( e.getSource() == buttonExport ) {
					
					if (list.getSelectedIndex() >= 0) {
						
						Person personObject = model.getPersonAt(list.getSelectedIndex());
						String personAfm = personObject.getIdentifyingNumber().toString();
						fileChooser = new JFileChooser();
						fileChooser.setSelectedFile(new File(personAfm+"_INFO.txt"));
						int returnVal = fileChooser.showSaveDialog(frame);
						
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							filename = fileChooser.getSelectedFile().getAbsolutePath();
							XmlEncoder xmlEncoder = new XmlEncoder(filename, personObject);
							JOptionPane.showMessageDialog(frame, "The file "+filename+" was saved to disk.");
						}
					}
					
				} else if ( e.getSource() == buttonClose ) {
					frame.dispose();
					System.exit(0);
				}
			}
		};
		
		buttonCreatePerson.addActionListener(buttonListener);
		buttonDeletePerson.addActionListener(buttonListener);
		buttonImportPerson.addActionListener(buttonListener);
		buttonExport.addActionListener(buttonListener);
		buttonClose.addActionListener(buttonListener);

		addGridItem(panel, buttonCreatePerson, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonDeletePerson, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonImportPerson, 2, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonExport, 3, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonClose, 4, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 2) {
		          int index = theList.locationToIndex(mouseEvent.getPoint());
		          if (index >= 0 && index < theList.getModel().getSize()) {
		            selectedObject = ((PersonListModel) theList.getModel()).getPersonAt(index);
		            if (selectedObject != null) {
		            	EventQueue.invokeLater(new Runnable(){
		            		public void run(){
		            		new PersonCard(selectedObject).setVisible(true);
		            		}
		            	});
		            }
		          }
		        }
		      }
		};
		    
		list.addMouseListener(mouseListener);
	}
}