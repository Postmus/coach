package coach;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

public class InputPanel extends JPanel implements ItemListener, ActionListener, ChangeListener {

	private JCheckBox mi, stroke, diabetes, afib, peripheral;
	private JComboBox sex, previousAdm;
	private JSpinner ageSpinner, probnpSpinner, sbpSpinner, dbpSpinner, egfrSpinner, naSpinner;
	private JSpinner lvefSpinner, hemoglobinSpinner;
	private CoachRiskEngine riskEngine;
	
	public InputPanel(CoachRiskEngine re) {

		this.riskEngine = re;
		
		setLayout(new GridBagLayout());

		// Create checkBoxes
		stroke = new JCheckBox("Stroke");
		stroke.addItemListener(this);
		Patient.stroke=0;
		diabetes = new JCheckBox("Diabetes");
		diabetes.addItemListener(this);
		mi = new JCheckBox("Myocardial infarction");
		mi.addItemListener(this);
		Patient.mi=0;
		Patient.diabetes=0;
		afib = new JCheckBox("Atrial fibrillation");
		afib.addItemListener(this);
		Patient.afib=0;
		peripheral = new JCheckBox("Pheripheral arterial disease");
		peripheral.addItemListener(this);
		Patient.peripheral=0;

		// Put checkBoxes on one panel
		JPanel checkBoxPanel = new JPanel(new GridLayout(0,1));
		checkBoxPanel.add(stroke);
		checkBoxPanel.add(diabetes);
		checkBoxPanel.add(afib);
		checkBoxPanel.add(mi);
		checkBoxPanel.add(peripheral);
		checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Comorbidities"));

		// Create comboBoxes
		String[] sexString = {"Male", "Female"};
		String[] previousAdmString = {"Yes", "No"};
		sex = new JComboBox(sexString);
		sex.setSelectedItem("Male");
		sex.addActionListener(this);
		Patient.sex = 0;
		previousAdm = new JComboBox(previousAdmString);
		previousAdm.setSelectedItem("No");
		previousAdm.addActionListener(this);
		Patient.previousAdm = 0;
		
		// Create Spinners
		
		SpinnerModel model = new SpinnerNumberModel(70, 60, 90, 1);
		Patient.age=70;
		ageSpinner = new JSpinner(model);		
        ageSpinner.addChangeListener(this);
		//Tweak the spinner's formatted text field.
		JFormattedTextField ftf = getTextField(ageSpinner);
		if (ftf != null ) {
		    ftf.setColumns(5); //specify more width than we need
		    ftf.setHorizontalAlignment(JTextField.LEFT);
		}
		
		SpinnerModel model2 = new SpinnerNumberModel(2500, 100, 10000, 100); // CHECK range
		Patient.probnp=2500; 
		probnpSpinner = new JSpinner(model2);		
        probnpSpinner.addChangeListener(this);
		//Tweak the spinner's formatted text field.
		JFormattedTextField ftf2 = getTextField(probnpSpinner);
		if (ftf2 != null ) {
		    ftf2.setHorizontalAlignment(JTextField.LEFT);
		}
		
		SpinnerModel model3 = new SpinnerNumberModel(118, 100, 160, 1);
		Patient.sbp=118;
		sbpSpinner = new JSpinner(model3);		
        sbpSpinner.addChangeListener(this);
		//Tweak the spinner's formatted text field.
		JFormattedTextField ftf3 = getTextField(sbpSpinner);
		if (ftf3 != null ) {
		    ftf3.setHorizontalAlignment(JTextField.LEFT);
		}
		
		SpinnerModel model4 = new SpinnerNumberModel(68, 50, 90, 1);
		Patient.dbp=68;
		dbpSpinner = new JSpinner(model4);		
        dbpSpinner.addChangeListener(this);
		//Tweak the spinner's formatted text field.
		JFormattedTextField ftf4 = getTextField(dbpSpinner);
		if (ftf4 != null ) {
		    ftf4.setHorizontalAlignment(JTextField.LEFT);
		}
		
		SpinnerModel model5 = new SpinnerNumberModel(55, 10, 120, 5);
		Patient.egfr=55;
		egfrSpinner = new JSpinner(model5);		
        egfrSpinner.addChangeListener(this);
		//Tweak the spinner's formatted text field.
		JFormattedTextField ftf5 = getTextField(egfrSpinner);
		if (ftf5 != null ) {
		    ftf5.setHorizontalAlignment(JTextField.LEFT);
		}
		
		SpinnerModel model6 = new SpinnerNumberModel(139, 130, 150, 1);
		Patient.na=139;
		naSpinner = new JSpinner(model6);		
        naSpinner.addChangeListener(this);
		//Tweak the spinner's formatted text field.
		JFormattedTextField ftf6 = getTextField(naSpinner);
		if (ftf6 != null ) {
			ftf6.setColumns(5); //specify more width than we need
		    ftf6.setHorizontalAlignment(JTextField.LEFT);
		}
		
		SpinnerModel model7 = new SpinnerNumberModel(132, 92, 172, 1);
		Patient.hemoglobin=132;
		hemoglobinSpinner = new JSpinner(model7);		
        hemoglobinSpinner.addChangeListener(this);
		//Tweak the spinner's formatted text field.
		JFormattedTextField ftf7 = getTextField(hemoglobinSpinner);
		if (ftf7 != null ) {
			ftf7.setColumns(5); //specify more width than we need
		    ftf7.setHorizontalAlignment(JTextField.LEFT);
		}
		
		SpinnerModel model8 = new SpinnerNumberModel(34, 10, 70, 1);
		Patient.lvef = 34;
		lvefSpinner = new JSpinner(model8);		
        lvefSpinner.addChangeListener(this);
		//Tweak the spinner's formatted text field.
		JFormattedTextField ftf8 = getTextField(lvefSpinner);
		if (ftf8 != null ) {
			ftf8.setColumns(5); //specify more width than we need
		    ftf8.setHorizontalAlignment(JTextField.LEFT);
		}
		
				
		// Create labels for spinners and combo-boxes 
		
		JLabel ageLabel = new JLabel("Age (years):");
		JLabel probnpLabel = new JLabel("NT-proBNP (pg/mL):");
		JLabel naLabel = new JLabel("Serum sodium (mEq/L):");
		JLabel egfrLabel = new JLabel("eGFR (mL/min/1.73m^2):");
		JLabel previousAdmLabel = new JLabel("Previous HF admission:");
		JLabel genderLabel = new JLabel("Sex:");
		JLabel sbpLabel = new JLabel("Systolic blood pressure (mm Hg):");
		JLabel dbpLabel = new JLabel("Diastolic blood pressure (mm Hg):");
		JLabel hemoglobinLabel = new JLabel("Hemoglobin (g/L):");
		JLabel lvefLabel = new JLabel("Left ventricular ejection fraction (%):");
		
		
		
		// Add all elements to the input panel
		
		GridBagConstraints c = new GridBagConstraints();
		
		// First column 
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,5);
		c.anchor = GridBagConstraints.WEST;
		add(ageLabel,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,5);
		c.anchor = GridBagConstraints.WEST;
		add(genderLabel,c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,5);
		c.anchor = GridBagConstraints.WEST;
		add(sbpLabel,c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,5);
		c.anchor = GridBagConstraints.WEST;
		add(dbpLabel,c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,5);
		c.anchor = GridBagConstraints.WEST;
		add(probnpLabel,c);
		
		// Second Column
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(ageSpinner,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(sex,c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(sbpSpinner,c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(dbpSpinner,c);
		
		c.gridx = 1;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(probnpSpinner,c);
		
		// Third column
		
		c.gridx = 2;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,10,5);
		c.anchor = GridBagConstraints.WEST;
		add(naLabel,c);
		
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,10,5);
		c.anchor = GridBagConstraints.WEST;
		add(egfrLabel,c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,10,5);
		c.anchor = GridBagConstraints.WEST;
		add(hemoglobinLabel,c);
		
		c.gridx = 2;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,10,5);
		c.anchor = GridBagConstraints.WEST;
		add(previousAdmLabel,c);	
		
		c.gridx = 2;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,10,5);
		c.anchor = GridBagConstraints.WEST;
		add(lvefLabel,c);
		
		// Fourth column
		
		c.gridx = 3;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(naSpinner,c);
		
		c.gridx = 3;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(egfrSpinner,c);
		
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(hemoglobinSpinner,c);
		
		c.gridx = 3;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(previousAdm,c);
		
		c.gridx = 3;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5,5,5,10);
		c.fill = GridBagConstraints.BOTH;
		add(lvefSpinner,c);
		
		// Final column (Check boxes)
		
		c.gridx = 4;
		c.gridy = 0;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0,10,10,10);
		add(checkBoxPanel,c);
		
		setBorder(BorderFactory.createTitledBorder("Patient characteristics at hospital discharge"));	
		
	}
		
	public JFormattedTextField getTextField(JSpinner spinner) {
	    JComponent editor = spinner.getEditor();
	    if (editor instanceof JSpinner.DefaultEditor) {
	        return ((JSpinner.DefaultEditor)editor).getTextField();
	    } else {
	        System.err.println("Unexpected editor type: "
	                           + spinner.getEditor().getClass()
	                           + " isn't a descendant of DefaultEditor");
	        return null;
	    }
	}
	
	public void stateChanged(ChangeEvent e) {
		JSpinner mySpinner = (JSpinner)(e.getSource());
		SpinnerModel model = mySpinner.getModel();
        if (model instanceof SpinnerNumberModel) {
        	if (e.getSource()==ageSpinner) {
        		Patient.age = ((SpinnerNumberModel)(model)).getNumber().intValue();
        	}
        	else if (e.getSource()==probnpSpinner) {
        		Patient.probnp = ((SpinnerNumberModel)(model)).getNumber().intValue();
        	}
        	else if (e.getSource()==sbpSpinner) {
        		Patient.sbp = ((SpinnerNumberModel)(model)).getNumber().intValue();
        	}
        	else if (e.getSource()==dbpSpinner) {
        		Patient.dbp = ((SpinnerNumberModel)(model)).getNumber().intValue();
        	}
        	else if (e.getSource()==egfrSpinner) {
        		Patient.egfr = ((SpinnerNumberModel)(model)).getNumber().intValue();
        	}
        	else if (e.getSource()==hemoglobinSpinner) {
        		Patient.hemoglobin = ((SpinnerNumberModel)(model)).getNumber().intValue();
        	}
        	else if (e.getSource()==lvefSpinner) {
        		Patient.lvef = ((SpinnerNumberModel)(model)).getNumber().intValue();
        	}
        	else {
        		Patient.na = ((SpinnerNumberModel)(model)).getNumber().intValue();
        	}
        }
        riskEngine.simulation();
    }

	public void itemStateChanged(ItemEvent e) {

		if (e.getSource()==mi) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Patient.mi=1;
			}
			else {
				Patient.mi=0;
			}
		}
		else if (e.getSource()==stroke) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Patient.stroke=1;
			}
			else {
				Patient.stroke=0;
			}
		}
		else if (e.getSource()==diabetes) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Patient.diabetes=1;
			}
			else {
				Patient.diabetes=0;
			}
		}
		else if (e.getSource()==afib) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Patient.afib=1;
			}
			else {
				Patient.afib=0;
			}
		}
		else {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Patient.peripheral=1;
			}
			else {
				Patient.peripheral=0;
			}
		}
		riskEngine.simulation();
	}

	public void actionPerformed(ActionEvent e) {

		JComboBox cb = (JComboBox) e.getSource();
		if (e.getSource()==sex) { 
			String sexSelected = (String) cb.getSelectedItem();
			if (sexSelected=="Male") {
				Patient.sex=0;
			}
			else {
				Patient.sex=1;
			}
		}
		else {
			String previousAdmSelected = (String) cb.getSelectedItem();
			if (previousAdmSelected=="Yes") {
				Patient.previousAdm=1;
			}
			else {
				Patient.previousAdm=0;				
			}
		}
		riskEngine.simulation();
	}



}
