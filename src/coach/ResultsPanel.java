package coach;

import java.awt.*;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;

public class ResultsPanel extends JPanel {
	
	private JLabel medianHosp;
	private XYPlot JFreeChartSurvivalCurve;
	private CategoryPlot JFreeChartHistogram;
			
	public ResultsPanel() {
		
		// Create a JFreeChart XYLinePlot for the survival curve
				
		double[][] series1 = {{0.0,3.0,6.0,9.0,12.0,15.0,18.0},{1.0,0.89,0.81,0.75,0.70,0.65,0.60}};
		DefaultXYDataset dataset3 = new DefaultXYDataset();
		dataset3.addSeries("All-cause mortality",series1);
		dataset3.addSeries("HF-related hospitalization",series1);
		JFreeChart chart3 = ChartFactory.createXYLineChart(null, "Time (months)", "Cumulative incidence", dataset3, PlotOrientation.VERTICAL, true, false, false);
		chart3.setBackgroundPaint(null);
		JFreeChartSurvivalCurve = (XYPlot) chart3.getPlot();
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) JFreeChartSurvivalCurve.getRenderer();
		renderer.setBaseShapesVisible(true);
		renderer.setBaseShapesFilled(true);
		NumberAxis domainAxis = (NumberAxis) JFreeChartSurvivalCurve.getDomainAxis();
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		domainAxis.setLabelFont(new Font("TimesNewRoman", Font.PLAIN, 12));
		ChartPanel panel3 = new ChartPanel(chart3);
		ValueAxis rangeAxis = JFreeChartSurvivalCurve.getRangeAxis();
		rangeAxis.setLabelFont(new Font("TimesNewRoman", Font.PLAIN, 12));
		Range defaultRange = new Range(0,0.5); 
		rangeAxis.setDefaultAutoRange(defaultRange);
		panel3.setBorder(BorderFactory.createTitledBorder("Cumulative incidence curves"));
		
		// Create a JFreeChart BarChart for the hospitalization frequency function
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(0.60, "Row 1", "0");
		dataset.addValue(0.20, "Row 1", "1");
		dataset.addValue(0.10, "Row 1", "2");
		dataset.addValue(0.075, "Row 1", "3");
		dataset.addValue(0.025, "Row 1", ">3");
		
		JFreeChart chart = ChartFactory.createBarChart(
				null, // chart title
				"Number of HF-related hospitalizations", // x-axis label
				"Probability", // y-axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				false, // tooltips?
				false // URLs?
				);
		chart.setBackgroundPaint(null);
		JFreeChartHistogram = (CategoryPlot) chart.getPlot();
				
		ChartPanel panel = new ChartPanel(chart);
		panel.setBorder(BorderFactory.createTitledBorder("Recurrent hospitalization at 18 months after index discharge"));
		JFreeChartHistogram = (CategoryPlot) chart.getPlot();
		CategoryAxis categoryAxis = JFreeChartHistogram.getDomainAxis();
		categoryAxis.setLabelFont(new Font("TimesNewRoman", Font.PLAIN, 12));
		ValueAxis rangeAxis2 = JFreeChartHistogram.getRangeAxis();
		rangeAxis2.setLabelFont(new Font("TimesNewRoman", Font.PLAIN, 12));

		// Survival curve Panel
		JPanel survival = new JPanel();
		survival.setLayout(new BorderLayout());
		survival.add(panel3, BorderLayout.CENTER);
		
		// Hospitalization Panel
		JPanel hospitalization = new JPanel();
		hospitalization.setLayout(new BorderLayout());
		hospitalization.add(panel, BorderLayout.CENTER);
		
		medianHosp = new JLabel("Expected number of days lost due to HF hospitalization: ");
		hospitalization.add(medianHosp, BorderLayout.SOUTH);
									
		// Add both panels together
		
		this.setLayout(new GridLayout(0,2,5,5));
		this.add(survival);
		this.add(hospitalization);
		this.setBorder(BorderFactory.createTitledBorder("Results of the patient-level simulation"));
		
	}
	
	public void updateJFreeChartSurvival(double[] survProb, double[] hospProb) {
		double[][] newSeries1 = {{0.0,3.0,6.0,9.0,12.0,15.0,18.0},survProb};
		double[][] newSeries2 = {{0.0,3.0,6.0,9.0,12.0,15.0,18.0},hospProb};
		DefaultXYDataset newDataset = new DefaultXYDataset();
		newDataset.addSeries("All-cause mortality",newSeries1);
		newDataset.addSeries("HF-related hospitalization",newSeries2);
		this.JFreeChartSurvivalCurve.setDataset(newDataset);
	}
	
	public void updateJFreeChartHistogram(double[] histProb) {
		DefaultCategoryDataset newDataset = new DefaultCategoryDataset();
		newDataset.addValue(histProb[0], "Row 1", "0");
		newDataset.addValue(histProb[1], "Row 1", "1");
		newDataset.addValue(histProb[2], "Row 1", "2");
		newDataset.addValue(histProb[3], "Row 1", "3");
		newDataset.addValue(histProb[4], "Row 1", ">3");
		this.JFreeChartHistogram.setDataset(newDataset);
	}
	
	public void updateDaysLost(long l) {
		this.medianHosp.setText("Expected number of days lost due to HF-related hospitalization: " + l + " days");
	}
	
	
}
