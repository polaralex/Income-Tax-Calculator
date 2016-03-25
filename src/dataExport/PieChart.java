package dataExport;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import dataManagement.Person;
import dataManagement.Receipt;

public class PieChart extends JFrame {

	public PieChart(String applicationTitle, String chartTitle, Person person) {

		super(applicationTitle);
		PieDataset dataset = createDataset(person);
		JFreeChart chart = createChart(dataset, chartTitle);
		ChartPanel chartPanel = new ChartPanel(chart);
		this.setBounds(200, 200, 750, 400);
		chartPanel.setPreferredSize(new java.awt.Dimension(680, 420));
		setContentPane(chartPanel);
		this.pack();
		this.setVisible(true);
	}

	private PieDataset createDataset(Person person) {

		ArrayList<Receipt> receipts = person.getReceiptsList();

		Double basic = 0d;
		Double entertainment = 0d;
		Double travel = 0d;
		Double health = 0d;
		Double other = 0d;

		for (Receipt current : receipts) {

			switch (current.getCategory()) {

			case "Basic":
				basic = basic + current.getAmount();
				break;

			case "Entertainment":
				entertainment = entertainment + current.getAmount();
				break;

			case "Travel":
				travel = travel + current.getAmount();
				break;

			case "Health":
				health = health + current.getAmount();
				break;

			case "Other":
				other = other + current.getAmount();
				break;
			}
		}

		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Basic: " + basic.toString() + " $", basic); 
		result.setValue("Entertainment: " + entertainment.toString() + " $", entertainment); 
		result.setValue("Travel: " + travel.toString() + " $", travel); 
		result.setValue("Health: " + health.toString() + " $", health); 
		result.setValue("Other: " + other.toString() + " $", other); 

		return result;
	}

	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
