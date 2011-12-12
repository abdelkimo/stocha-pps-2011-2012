package manager.solveurs.PL;

import manager.solveurs.Solveur;
import data.DataBinaire;
import data.solution.SolutionEnergieBinaireRelaxe;

public abstract class PLEnergieBinaireRelaxe implements Solveur {
	/** Variable contenant les donn�es du probl�me */
	protected DataBinaire donnees;
	/** Le vecteur de solution du probl�me */
	protected SolutionEnergieBinaireRelaxe solution;
	/** Le vecteur de co�ts */
	protected double[] couts;
	/** Le gain avec les apports en eau */
	protected double gains;
	/** Les probabilit�s des sc�narios */
	protected double[] probabilites;

	/**
	 * Cr�e un nouveau PLEnergie.
	 * 
	 * @param donnees
	 *            les donn�es du probl�me
	 */
	public PLEnergieBinaireRelaxe(DataBinaire donnees) {
		this.donnees = donnees;
		solution = new SolutionEnergieBinaireRelaxe(donnees);
		genererPL();
	}

	/**
	 * G�n�re le programme lin�aire � partir des donn�es. Remplit les tableaux
	 * du probl�me.
	 */
	private void genererPL() {
		int nbPaliers = donnees.nbPaliers[0] + donnees.nbPaliers[1] + donnees.nbPaliers[2] + donnees.nbPaliers[3];
		couts = new double[donnees.nbPeriodes * nbPaliers + donnees.nbTrajectoires];
		gains = 0;
		for (int p = 0; p < donnees.nbPeriodes; p++) {
			int sommePaliers = 0;
			for (int c = 0; c < donnees.nbCentrales; c++) {
				for (int numPalier = 0; numPalier < donnees.nbPaliers[c]; numPalier++) {
					couts[p * nbPaliers + sommePaliers] = donnees.getCoutCentrale(c) * donnees.getPalier(c, numPalier);
					sommePaliers++;
				}
			}
			gains += donnees.getApportsPeriode(p) *  donnees.getCoutCentrale(4);
		}
		for (int i = 0; i < donnees.nbTrajectoires; i++) {
			couts[donnees.nbPeriodes * nbPaliers + i] = donnees.nbPeriodes * donnees.getCoutCentrale(4)
					* donnees.getTrajectoire(i) / donnees.getTurbinage();
		}
	}

	/**
	 * Renvoie la solution calcul�e.
	 * 
	 * @return la solution calcul�e
	 */
	public SolutionEnergieBinaireRelaxe getSolution() {
		return solution;
	}

	public abstract void lancer();
}
