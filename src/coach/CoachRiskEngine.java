package coach;

import java.awt.BorderLayout;
import javax.swing.*;

import java.awt.event.*;

public class CoachRiskEngine extends JFrame implements ActionListener {
	
	private InputPanel inputs;
	private ResultsPanel results;
	private JMenuItem aboutItem;
	
	public CoachRiskEngine() {
		
		// Create CoachRiskEngine frame
		
		this.setSize(840,520);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("COACH Risk Engine v1.0");
		this.setLocation(100,100);
		inputs = new InputPanel(this);
		this.add(inputs, BorderLayout.NORTH);
		results = new ResultsPanel();
		this.add(results);
		
		// Create and configure MenuBar
		
		JMenuBar menuBar = new JMenuBar(); 
        JMenu helpMen = new JMenu("Help"); 
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);
        helpMen.add(aboutItem);
        menuBar.add(Box.createHorizontalGlue()); 
        menuBar.add(helpMen); 
        this.setJMenuBar(menuBar); 
        
        // Make frame visible
        
        this.setVisible(true);

        // Perform simulation for default settings
		
		this.simulation();
		
	}
	
	public void simulation() {
		
		int iterations = 10000;
		int i = 0;
		int[] output = new int[3];
		int[] endpointD = new int[iterations];
		int[] hospitalization = new int[iterations];
		int[] numHosp = new int[iterations];
		
		while (i<iterations) {
			output = Patient.simulationResults(Patient.peripheral, Patient.age, Patient.sex, Patient.diabetes, Patient.afib, Patient.mi, Patient.stroke, Patient.probnp, Patient.egfr, Patient.na, Patient.previousAdm, Patient.sbp, Patient.dbp, Patient.lvef, Patient.hemoglobin);
			endpointD[i]=output[0];
			hospitalization[i]=output[1];
			numHosp[i]=output[2];
			i = i + 1;
		}
		
		java.util.Arrays.sort(endpointD);
		java.util.Arrays.sort(hospitalization);
		java.util.Arrays.sort(numHosp);
					
		// Determine survival function
		
		double[] survProb = new double[7];
		survProb[0] = 1.0;
		for (int j = 1; j<7; j++) {
			
			boolean notFound = true;
			int index = 0;
			while (notFound) {
				
				if (endpointD[index]>(365/4)*(j+1)) {
					notFound = false;
					double interm = (double) (iterations - index);
					survProb[j] = (int) ((interm/iterations)*100 + 0.5) / 100.0;
				}
				index = index + 1;
			}
			
		}
		
		// Determine histogram
		
		double[] histProb = new double[5];
		histProb[0] = 0.0;
		histProb[1] = 0.0;
		histProb[2] = 0.0;
		histProb[3] = 0.0;
		histProb[4] = 0.0;
		int k = 0;
		while (k<iterations) {
			if (numHosp[k]==0) {
				histProb[0]=histProb[0] + 1;
			}
			else if (numHosp[k]==1) {
				histProb[1]=histProb[1] + 1;
			}
			else if (numHosp[k]==2) {
				histProb[2]=histProb[2] + 1;			
			}
			else if (numHosp[k]==3) {
				histProb[3]=histProb[3] + 1;
			}
			else {
				histProb[4]=histProb[4] + 1;
			}
			k=k+1;
		}
		histProb[0]=histProb[0]/iterations;
		histProb[1]=histProb[1]/iterations;
		histProb[2]=histProb[2]/iterations;
		histProb[3]=histProb[3]/iterations;
		histProb[4]=histProb[4]/iterations;
		
		results.updateJFreeChartSurvival(survProb);
		results.updateJFreeChartHistogram(histProb);
	}
	
	public static double median(int[] m) {
		
	    if (m.length%2 == 1) {
	        // Odd number of elements -- return the middle one.
	        return m[(m.length+1)/2];
	    } else {
	       // Even number -- return average of middle two
	       // Must cast the numbers to double before dividing.
	       return (m[(m.length/2)] + m[(1 + m.length/2)]) / 2.0;
	    }
	    
	}
	
	public static int quartile1(int[] m) {
		
		int index = Math.round((m.length + 1)/4);
		return m[index];
		
	}
	
	public static int quartile3(int[] m) {
		
		int index = Math.round((3*(m.length) + 3)/4);
		return m[index];
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==aboutItem) {
			JOptionPane.showMessageDialog(this,
				"COACH Risk Engine v1.0 \n" +
				"\uu00a9 2010, Douwe Postmus (d.postmus@epi.umcg.nl) \n \n" + 
				"This software incorporates JFreeChart, \n" + 
				"\uu00a9 2000-2009 by Object Refinery Limited and Contributors",
				"About COACH Risk Engine",
				JOptionPane.INFORMATION_MESSAGE);
		}	
	}
	
	public static void main(String[] args) {
		
		new CoachRiskEngine();
		
	}
		
}