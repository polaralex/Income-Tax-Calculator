package userInterface;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import dataManagement.PeopleManager;
import dataManagement.Person;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JList;
import javax.swing.JMenu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.GroupLayout.Alignment.*;
import javax.swing.JButton;
import javax.swing.JComponent;

public class MainScreen {

	private JFrame frame;
	private JTextArea txtpnUseTheopen;
	private JScrollPane scrollPane;
	private PersonListModel model;
	private JButton buttonImportPerson;
	private JButton buttonCreatePerson;
	private JButton buttonClose;

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
		
		// Top Description Text:
		txtpnUseTheopen = new JTextArea();
		txtpnUseTheopen.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtpnUseTheopen.setEditable(false);
		txtpnUseTheopen.setLineWrap(true);
		txtpnUseTheopen.setText("Use the \"Open File\" to Import a new Database of Tax Data.");
		
		// People Manager Initialization:
		final PeopleManager peopleManager = new PeopleManager();
		peopleManager.testCreatePersons();

		//List model initialization:
		JList list = new JList();
		list.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		model = new PersonListModel();
		list.setModel(model);
		model.addAll(peopleManager.getPersonList());
		//list.setBackground(new Color(159,180,204));

		scrollPane = new JScrollPane(list);
		scrollPane.setMinimumSize(new Dimension(400,400));
		
		// Grid Bag Layout Configuration:
		addGridItem(panel, txtpnUseTheopen, 0, 0, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0.5f);
		
		addGridItem(panel, scrollPane, 0, 1, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0f);
		
		// Buttons Layout Setting:
		buttonCreatePerson = new JButton("New");
		buttonImportPerson = new JButton("Import");
		buttonClose = new JButton("Close");
		
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( e.getSource() == buttonCreatePerson ) {
					
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
		addGridItem(panel, buttonClose, 2, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);

		MouseListener mouseListener = new MouseAdapter() {
		      public void mouseClicked(MouseEvent mouseEvent) {
		        JList theList = (JList) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 2) {
		          int index = theList.locationToIndex(mouseEvent.getPoint());
		          if (index >= 0) {
		            Object selectedObject = theList.getModel().getElementAt(index);
		            System.out.println("Double-clicked on: " + selectedObject.toString());
		            
		            PersonCard personCard = new PersonCard(peopleManager.getPersonList().get(index));
		            personCard.showCard();
		          }
		        }
		      }
		};
		    
		list.addMouseListener(mouseListener);
		
	}
	
	private void addGridItem(JPanel panel, JComponent comp, int x, int y, int width, int height, int align, int fill, float weighty) {
		
	    GridBagConstraints gcon = new GridBagConstraints();
	    gcon.gridx = x;
	    gcon.gridy = y;
	    gcon.gridwidth = width;
	    gcon.gridheight = height;
	    gcon.weightx = 0.5;       // a hint on apportioning space
	    gcon.weighty = weighty;
	    gcon.insets = new Insets(5, 5, 5, 5);   // padding
	    gcon.anchor = align;    // applies if fill is NONE
	    gcon.fill = fill;
	    
	    panel.add(comp, gcon);
	}
}
