package userInterface;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dataManagement.PeopleManager;
import dataManagement.Person;

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

import javax.swing.JScrollPane;
import javax.swing.JButton;

public class MainScreen extends GridBagBasedScreen {

	private JFrame frame;
	private JLabel mainLabelText;
	private JScrollPane scrollPane;
	private PersonListModel model;
	private JButton buttonImportPerson;
	private JButton buttonExport;
	private JButton buttonCreatePerson;
	private JButton buttonClose;
	private String[] personTypes = { "Married Filing Jointly", "Married Filing Seperately", "Head of Household", "Single" };

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
		final PeopleManager peopleManager = new PeopleManager();
		peopleManager.testCreatePersons();

		//List model initialization:
		JList list = new JList();
		list.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		model = new PersonListModel();
		list.setModel(model);
		model.addAll(peopleManager.getPersonList());

		scrollPane = new JScrollPane(list);
		scrollPane.setMinimumSize(new Dimension(400,400));
		
		// Main Screen Layout Configuration:
		addGridItem(panel, mainLabelText, 0, 0, 4, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, 0.5f);
		addGridItem(panel, scrollPane, 0, 1, 4, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0f);
		
		buttonCreatePerson = new JButton("New");
		buttonImportPerson = new JButton("Import");
		buttonExport = new JButton("Export");
		buttonClose = new JButton("Close");
		
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if ( e.getSource() == buttonCreatePerson ) {
					
					Object category = (String)JOptionPane.showInputDialog(frame, "Choose the type of Tax-Payer:", "Create a new Person", JOptionPane.PLAIN_MESSAGE, null, personTypes, personTypes[0]);					
					System.out.println(category);
					
					// Finds the index of the selected category:
					int i=0;
					while (personTypes[i] != category.toString() && i < personTypes.length){
						i++;
					}

					Person newPerson = peopleManager.createNewPerson(i+1);
					PersonCard createPerson = new PersonCard(newPerson);
					model.addElement(newPerson);
					createPerson.showCard();
					
				} else if ( e.getSource() == buttonImportPerson ) {
					
				} else if ( e.getSource() == buttonClose ) {
					frame.dispose();
					System.exit(0);
				}
			}
		};
		
		buttonCreatePerson.addActionListener(buttonListener);
		buttonImportPerson.addActionListener(buttonListener);
		buttonClose.addActionListener(buttonListener);

		addGridItem(panel, buttonCreatePerson, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonImportPerson, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonExport, 2, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonClose, 3, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 2) {
		          int index = theList.locationToIndex(mouseEvent.getPoint());
		          if (index >= 0 && index < theList.getModel().getSize()) {
		            Person selectedObject = ((PersonListModel) theList.getModel()).getPersonAt(index);
		            if (selectedObject != null) {
		            	PersonCard personCard = new PersonCard(selectedObject);
		            	personCard.showCard();
		            }
		          }
		        }
		      }
		};
		    
		list.addMouseListener(mouseListener);
	}
}