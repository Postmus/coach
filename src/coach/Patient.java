package coach;

import java.lang.Math;

public class Patient {

	public static int mi, stroke, diabetes, afib, peripheral, sbp, dbp, egfr;
	public static int sex, previousAdm,probnp, na, lvef, hemoglobin;
	public static double age;

	public static int[] simulationResults(int peripheral, double age, int sex, int diabetes, int afib, int mi, int stroke, int probnp, int egfr, int na, int previousAdm, int sbp, int dbp, int lvef, int hemoglobin) {

		int doodInd, endpointD, daysHospital, agePI, randNum1, numHosp;
		double probDead, ageP, randNum2;
		int[] output;
		double maxTimeSim;
		
		maxTimeSim = 1.5*365;
		ageP = age;
		agePI = (int) (age);
		doodInd = 0;
		endpointD = 0;
		daysHospital = 0;
		numHosp = 0;
		output = new int[3];
		while (doodInd==0) {

			randNum1 = (int) (Patient.equation1(afib, diabetes, stroke, mi, peripheral, na, egfr, probnp, previousAdm, sex, hemoglobin, sbp, dbp) + 1);
			ageP = ageP + randNum1/365;
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
				}
				daysHospital = daysHospital + randNum1;
				endpointD = endpointD + randNum1;
				agePI = (int) (ageP);

				// Determine next state (i.e. dead or discharged)
				
				probDead = Patient.equation4(diabetes, hemoglobin, probnp);
				randNum2 = Math.random();
				if (randNum2<=probDead) {		// Next state is dead
					doodInd = 1;
				}
			}
		}

		output[0]=endpointD;
		output[1]=daysHospital;
		output[2]=numHosp;

		return output;

	}

	private static double equation1(int afib, int diabetes, int stroke, int mi, int peripheral, int na, int egfr, int probnp, int previousAdm, int sex, int hemoglobin, int sbp, int dbp) {

		double rho, lambda, uniform;
		rho = 0.712;
		lambda = 1.046 - 0.308*sex + 0.303*mi + 0.160*afib + 0.263*stroke + 0.198*peripheral - 0.039*na + 0.197*diabetes + 0.668*previousAdm - 0.012*egfr + 0.068*Math.log(probnp) - 0.006*hemoglobin - 0.005*(sbp-dbp);
		lambda = Math.exp(lambda);
		uniform = Math.random();
		return Math.pow((-1/lambda * (java.lang.Math.log(1 - uniform))),(1/rho));

	}

	private static double equation2(int age , int stroke, int peripheral, int lvef, int egfr) {

		double rho, lambda, alpha, beta, uniform;
		rho = 1.934;
		lambda = Math.exp(-6.356 + 0.014*age + 0.483*stroke -0.376*peripheral + 0.012*lvef + 0.015*egfr);
		beta = rho;
		alpha = Math.pow((1/lambda),(1/rho)); // Median of the log-logistic distribution
		uniform = Math.random();
		return alpha*(Math.pow((uniform/(1-uniform)),(1/beta)));

	}

	private static double equation3(int age, int sex, int sbp, int dbp, int previous, int probnp) {

		double expRisk;
		expRisk = Math.exp(-4.199 + 0.048*age - 0.540*sex - 0.018*(sbp-dbp) - 0.359*previous + 0.181*Math.log(probnp));
		return expRisk/(1 + expRisk);

	}

	private static double equation4(int diabetes, int hemoglobin, int probnp) {

		double expRisk;
		expRisk = Math.exp(-2.030 + 0.531*diabetes - 0.017*hemoglobin + 0.288*Math.log(probnp));
		return expRisk/(1 + expRisk);

	}

}