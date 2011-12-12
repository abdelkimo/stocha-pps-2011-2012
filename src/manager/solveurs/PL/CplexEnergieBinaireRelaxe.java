package manager.solveurs.PL;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import data.DataBinaire;

/**
 * Cette classe permet de r�soudre une instance du probl�me de management de la
 * production d'�nergie � l'aide de CPLEX
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class CplexEnergieBinaireRelaxe extends PLEnergieBinaireRelaxe {

	/**
	 * Cr�e un nouveau CPlexEnergie
	 * 
	 * @param donnees
	 *            les donn�es du probl�me
	 * @param fileName
	 */
	public CplexEnergieBinaireRelaxe(DataBinaire donnees) {
		super(donnees);
		System.out.println("solveur relaxe");
	}

	/**
	 * Lance la r�solution du programme lin�aire. Cr�e le programme � partir des
	 * tableaux de coefficients.
	 */
	public void lancer() {
		try {
			IloCplex cplex = new IloCplex();
			int nbPaliers = donnees.nbPaliers[0] + donnees.nbPaliers[1] + donnees.nbPaliers[2] + donnees.nbPaliers[3];

			IloNumVar[] y = cplex.numVarArray(donnees.nbPeriodes * nbPaliers + donnees.nbTrajectoires, 0, 1);
			IloNumVar[] z = cplex.numVarArray(donnees.nbScenarios*donnees.nbPeriodes, 0, 1);

			int ythetaSize = donnees.nbPeriodes * nbPaliers;
			int ynSize = donnees.nbTrajectoires;

			IloNumVar[] ytheta = cplex.numVarArray(ythetaSize, 0, 1);
			IloNumVar[] yn = cplex.numVarArray(ynSize, 0, 1);
			for (int i = 0; i < ythetaSize; i++) {
				ytheta[i] = y[i];
			}
			for (int i = 0; i < ynSize; i++) {
				yn[i] = y[i + ythetaSize];
			}

			double M = 200000;

			cplex.addMinimize(cplex.diff(cplex.scalProd(couts, y), gains));

			// Un seul palier accept� par p�riode et par centrale
			for (int p = 0; p < donnees.nbPeriodes; p++) {
				int sommePaliers = 0;
				for (int c = 0; c < donnees.nbCentrales; c++) {
					IloNumVar[] varY = new IloNumVar[donnees.nbPaliers[c]];
					for (int numPalier = 0; numPalier < donnees.nbPaliers[c]; numPalier++) {
						varY[numPalier] = ytheta[p * nbPaliers + sommePaliers];
						sommePaliers++;
					}
					IloRange eq = cplex.eq(cplex.sum(varY), 1);
					cplex.add(eq);
				}
			}

			// Une seule trajectoire est choisie
			IloRange eq = cplex.eq(cplex.sum(yn), 1);
			cplex.add(eq);

			// La somme des probabilit�s des sc�narios doit �tre sup�rieurs � p
			for(int p=0; p<donnees.nbPeriodes; p++)
			{
				IloNumVar[] zPeriode = cplex.numVarArray(donnees.nbScenarios, 0, 1);
				for(int i=0; i<donnees.nbScenarios; i++)
					zPeriode[i] = z[p*donnees.nbScenarios+i];
				IloRange eqg = cplex.ge(cplex.scalProd(probabilites, zPeriode), donnees.getProbabilite(p));
				cplex.add(eqg);
			}

			// Contraintes sur les demandes
			IloLinearNumExpr exprYn = cplex.scalProd(donnees.getTrajectoires(), yn);
			for (int p = 0; p < donnees.nbPeriodes; p++) {
				IloNumVar[] varYt = new IloNumVar[nbPaliers];

				for (int i = 0; i < nbPaliers; i++) {
					varYt[i] = ytheta[p * nbPaliers + i];
				}

				for (int s = 0; s < donnees.nbScenarios; s++) {
					double[] prod = new double[nbPaliers];
					int sommePaliers = 0;
					for (int c = 0; c < donnees.nbCentrales; c++) {
						for (int numPalier = 0; numPalier < donnees.nbPaliers[c]; numPalier++) {
							prod[sommePaliers] = donnees.getScenario(s).getPaliersPeriodeCentrale(c, p)[numPalier];
							sommePaliers++;
						}
					}
					IloLinearNumExpr exprYt = cplex.scalProd(prod, varYt);
					IloRange ge = cplex.ge(
							cplex.sum(exprYt, exprYn,
									cplex.prod(-1 * (donnees.getScenario(s).getDemandePeriode(p) + M), z[p*donnees.nbScenarios + s])), -M);
					cplex.add(ge);
				}
			}

			if (cplex.solve()) {
				System.out.println("Solution status = " + cplex.getStatus());
				System.out.println("Solution value = " + cplex.getObjValue());
				double[] valYt = cplex.getValues(ytheta);
				double[] valYn = cplex.getValues(yn);
				double[] valZ = cplex.getValues(z);

				for (int p = 0; p < donnees.nbPeriodes; p++) {					
					int sommePaliers = 0;
					for (int c = 0; c < donnees.nbCentrales; c++) {
						for (int numPalier = 0; numPalier < donnees.nbPaliers[c]; numPalier++) {
							solution.setDecisionPeriodeCentrale(p, c, numPalier, valYt[p * nbPaliers + sommePaliers]);
							sommePaliers++;
						}
					}
				}

				for (int i = 0; i < valYn.length; i++) {
					solution.setChoixTrajectoire(i, valYn[i]);
				}
				
				for(int p=0; p<donnees.nbPeriodes; p++)
				{
					for (int s = 0; s < donnees.nbScenarios; s++) {
						solution.setZ(p ,s, valZ[p*donnees.nbScenarios + s]);
					}
				}

				System.out.println(solution.toString());
				cplex.end();
			}

		} catch (IloException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DataBinaire data = new DataBinaire("Data/Donn�es_Recuit_demandes.csv",
				"Data/Donn�es_Recuit_paliers1.csv", "Data/Donn�es_Recuit_paliers2.csv",
				"Data/Donn�es_Recuit_paliers3.csv", "Data/Donn�es_Recuit_paliers4.csv",
				"Data/Donn�es_Recuit_trajectoire_hydro.csv", "Data/Donn�es_Recuit_parametres_hydro.csv",
				"Data/Donn�es_Recuit_capacit�.csv");
		CplexEnergieBinaireRelaxe test = new CplexEnergieBinaireRelaxe(data);
		test.lancer();

	}
}
