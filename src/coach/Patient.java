package coach;

import java.lang.Math;

public class Patient {

	public static int mi, stroke, diabetes, afib, peripheral, sbp, dbp, egfr;
	public static int sex, previousAdm,probnp, na, lvef;
	public static double age;

	public static int[] simulationResults(int peripheral, double age, int sex, int diabetes, int afib, int mi, int stroke, int probnp, int egfr, int na, int previousAdm, int sbp, int dbp, int lvef) {

		int doodInd, endpointD, daysHospital, agePI, randNum1, numHosp, endpointH, endpointHind;
		double probDead, ageP, randNum2;
		int[] output;
		double maxTimeSim;
		
		maxTimeSim = 1.5*365;
		ageP = age;
		agePI = (int) (age);
		doodInd = 0;
		endpointD = 0;
		endpointH = 0;
		endpointHind = 0;
		daysHospital = 0;
		numHosp = 0;
		output = new int[5];
		while (doodInd==0) {

			randNum1 = (int) (Patient.equation1(afib, diabetes, stroke, mi, peripheral, na, egfr, probnp, previousAdm, sex, sbp, dbp) + 1);
			ageP = ageP + randNum1/365;
			if (numHosp==0) {
				endpointH = endpointH + randNum1;
			}
			endpointD = endpointD + randNum1;
			agePI = (int) (ageP);
			previousAdm = 1;

			// Determine next state (i.e. dead or hospitalization) 

			probDead = Patient.equation3(agePI, sex, sbp, dbp, previousAdm, probnp);
			randNum2 = Math.random();
			if (randNum2<=probDead) {		// Next state is dead
				doodInd = 1;
			}
			else {						
				randNum1 = (int) (Patient.equation2(agePI, stroke, peripheral, lvef, egfr) + 1);
				ageP = ageP + randNum1/365;
				if (endpointD<=maxTimeSim) {
					numHosp = numHosp + 1;
					endpointHind = 1;
					daysHospital = daysHospital + randNum1;
				}
				endpointD = endpointD + randNum1;
				agePI = (int) (ageP);

				// Determine next state (i.e. dead or discharged)
				
				probDead = Patient.equation4(diabetes, dbp, probnp);
				randNum2 = Math.random();
				if (randNum2<=probDead) {		// Next state is dead
					doodInd = 1;
				}
			}
		}

		output[0]=endpointD;
		output[1]=daysHospital;
		output[2]=numHosp;
		output[3]=endpointH;
		output[4]=endpointHind;

		return output;

	}

	private static double equation1(int afib, int diabetes, int stroke, int mi, int peripheral, int na, int egfr, int probnp, int previousAdm, int sex, int sbp, int dbp) {

		double rho, lambda, uniform;
		rho = 0.719;
		lambda = 0.816 - 0.287*sex + 0.296*mi + 0.172*afib + 0.275*stroke + 0.177*peripheral - 0.036*na + 0.222*diabetes + 0.648*previousAdm - 0.013*egfr + 0.073*Math.log(probnp) -0.015*dbp - 0.005*(sbp-dbp);
		lambda = Math.exp(lambda);
		uniform = Math.random();
		return Math.pow((-1/lambda * (java.lang.Math.log(1 - uniform))),(1/rho));

	}

	private static double equation2(int age , int stroke, int peripheral, int lvef, int egfr) {

		double rho, lambda, alpha, beta, uniform;
		rho = 1.931;
		lambda = Math.exp(-6.298 + 0.014*age + 0.483*stroke -0.374*peripheral + 0.014*lvef + 0.012*egfr);
		beta = rho;
		alpha = Math.pow((1/lambda),(1/rho)); // Median of the log-logistic distribution
		uniform = Math.random();
		return alpha*(Math.pow((uniform/(1-uniform)),(1/beta)));

	}

	private static double equation3(int age, int sex, int sbp, int dbp, int previous, int probnp) {

		double expRisk;
		expRisk = Math.exp(-4.206 + 0.047*age - 0.534*sex - 0.018*(sbp-dbp) - 0.362*previous + 0.186*Math.log(probnp));
		return expRisk/(1 + expRisk);

	}

	private static double equation4(int diabetes, int dbp, int probnp) {

		double expRisk;
		expRisk = Math.exp(-3.092 + 0.649*diabetes - 0.025*dbp + 0.348*Math.log(probnp));
		return expRisk/(1 + expRisk);

	}

}