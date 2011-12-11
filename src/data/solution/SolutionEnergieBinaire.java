package data.solution;

import java.util.Random;

import data.DataBinaire;

/**
 * Une solution du probl�me de management de la production d'�nergie en binaire
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class SolutionEnergieBinaire extends Solution implements SolutionCentrale {

	/**
	 * La matrice de d�cision pour chaque periode, une centrale choisie un
	 * palier de production
	 */
	private int[][] y;
	/** Le vecteur d'activation des sc�narios */
	private boolean[] z;
	/** La trajectoires hydrauliques choisie */
	private int trajectoire;
	/** Les donn�es du probl�mes */
	private DataBinaire donnees;

	/**
	 * Cr�e une nouvelle solution sp�cifique au probl�me de management de la
	 * production d'�nergie sous sa forme binaire et sa relaxation
	 * @param donnees les donn�es du probl�me
	 */
	public SolutionEnergieBinaire(DataBinaire donnees) {
		this.donnees = donnees;
		y = new int[donnees.nbPeriodes][donnees.nbCentrales];
		z = new boolean[donnees.nbScenarios];
	}

	@Override
	public Solution clone() {
		SolutionEnergieBinaire res = new SolutionEnergieBinaire(donnees);
		
		// On copie les valeurs de la solution courante dans le clone
		for (int i = 0; i < z.length; i++)
			res.z[i] = z[i];
		for (int i = 0; i < y.length; i++) {
			for (int j = 0; j < y[i].length; j++)
				res.setDecisionPeriodeCentrale(i, j, y[i][j]);
		}
		res.setTrajectoire(trajectoire);
		return res;
	}

	@Override
	public double deltaF(Solution solution) {

		return solution.fonctionObjectif() - this.fonctionObjectif();
	}

	@Override
	public double fonctionObjectif() {
		double val = 0;
		// Pour chaque p�riode
		for (int p = 0; p < donnees.nbPeriodes; p++) {
			// Pour chaque centrale thermique
			for (int c = 0; c < donnees.nbCentrales; c++) {
				// On calcule son co�t de production
				val += donnees.getPalier(c, y[p][c]) * donnees.getCoutCentrale(c);
			}

			// On calcule le co�t de production du r�servoire
			val += ((donnees.getTrajectoire(trajectoire) / donnees.getTurbinage()) - donnees.getApportsPeriode(p))
					* donnees.getCoutCentrale(4);
		}
		return val;
	}

	@Override
	public void solutionInitiale() {
		Random rand = new Random();

		// Initialise le vecteur z � false
		for (int i = 0; i < z.length; i++)
			z[i] = false;

		// Active des sc�narios al�atoirement jusqu'� obtenir la probabilit� voulue
		int scenarioActive;
		do {
			scenarioActive = rand.nextInt(z.length);
			if (!z[scenarioActive])
				z[scenarioActive] = true;
		} while (probabiliteScenario() < donnees.getProbabilite());

		int centraleCoutMin = 0;
		
		// Tableau pour enregistrer quelles centrales ont �t� modifi�es
		boolean[] centraleUtilisee = new boolean[donnees.nbCentrales + 1];
		for (int c = 0; c < donnees.nbCentrales + 1; c++) {
			centraleUtilisee[c] = false;
		}

		// Initialise la matrice de production � 0
		for (int p = 0; p < donnees.nbPeriodes; p++) {
			for (int c = 0; c < donnees.nbCentrales; c++) {
				y[p][c] = 0;
			}
		}

		trajectoire = 0;

		// Tant qu'on ne r�pond pas � la demande on modifie la solution
		do {
			// On cherche la centrale dont le cout de production est le plus faible
			double coutMin = Integer.MAX_VALUE;
			for (int i = 0; i < donnees.getCouts().length; i++) {
				if (!centraleUtilisee[i] && donnees.getCoutCentrale(i) <= coutMin) {
					coutMin = donnees.getCoutCentrale(i);
					centraleCoutMin = i;
				}
			}
			centraleUtilisee[centraleCoutMin] = true;

			// Pour chaque p�riode
			for (int p = 0; p < donnees.nbPeriodes; p++) {
				// Si on a une centrale thermique
				if (centraleCoutMin < donnees.nbCentrales) {
					// Cherche le palier permettant d'atteindre la demande si possible
					int palier = 0;
					while (palier < donnees.nbPaliers[centraleCoutMin] - 1 && !respecteContrainteDemandePeriode(p)) {
						palier++;
						y[p][centraleCoutMin] = palier;
					}
				}
				// Pour le r�servoir
				else {
					// Cherche la trajectoire permettant d'atteindre la demande si possible
					int trajectoire2 = 0;
					while (trajectoire < donnees.nbTrajectoires - 1 && !respecteContrainteDemandePeriode(p)) {
						trajectoire2++;
						trajectoire = trajectoire2;
					}
				}
			}
		} while (!respecteContrainteDemande());
	}

	/**
	 * V�rifie que la solution respecte la demande
	 * @return true si la solution respecte la demande, false sinon
	 */
	public boolean respecteContrainteDemande() {

		// Pour chaque sc�narios
		for (int s = 0; s < donnees.nbScenarios; s++) {
			// S'il est actif
			if (isActived(s)) {
				// Pour chaque p�riode on calcule la production
				for (int p = 0; p < donnees.nbPeriodes; p++) {
					double production = 0;
					for (int c = 0; c < donnees.nbCentrales; c++) {
						production += donnees.getScenario(s).getPaliersPeriodeCentrale(c, p)[getDecisionPeriodeCentrale(p, c)];
					}

					production += donnees.getTrajectoire(trajectoire);
					
					// Si la production de la p�riode ne r�pond pas � la demande, on retourne false
					if (production < donnees.getScenario(s).getDemandePeriode(p))
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * V�rifie que la solution respecte la demande pour une p�riode donn�e
	 * @param periode la p�riode
	 * @return true si la solution respecte la demande pour la p�riode donn�e, false sinon
	 */
	public boolean respecteContrainteDemandePeriode(int periode) {
		// Pour chaque sc�nario
		for (int s = 0; s < donnees.nbScenarios; s++) {
			// S'il est actif
			if (isActived(s)) {
				// Calcule la production pour la p�riode
				double production = 0;
				for (int c = 0; c < donnees.nbCentrales; c++) {
					production += donnees.getScenario(s).getPaliersPeriodeCentrale(c, periode)[getDecisionPeriodeCentrale(
							periode, c)];
				}

				production += donnees.getTrajectoire(trajectoire);
				
				// Si la production ne r�pond pas � la demande, retourne false
				if (production < donnees.getScenario(s).getDemandePeriode(periode))
					return false;
			}
		}
		return true;
	}

	/**
	 * Retourne la somme des probabilit�s des sc�narios actifs
	 * @return la somme des probabilit�s des sc�narios actifs
	 */
	public double probabiliteScenario() {
		double sommeProba = 0;
		for (int i = 0; i < z.length; i++) {
			if (z[i])
				sommeProba += donnees.getScenario(i).getProbabilite();
		}
		return sommeProba;
	}

	/**
	 * Retourne le vecteur des sc�narios
	 * @return le vecteur des sc�narios
	 */
	public boolean[] getZ() {
		return z;
	}

	/**
	 * Retourne la matrice de d�cisions
	 * @return la matrice de d�cisions
	 */
	public int[][] getY() {
		return y;
	}

	/**
	 * Teste si le sc�nario est actif
	 * @param s le sc�nario
	 * @return true si le sc�nario est actif, false sinon
	 */
	public boolean isActived(int s) {
		return z[s];
	}

	/**
	 * Retourne la d�cision pour une p�riode et une centrale
	 * @param periode la p�riode
	 * @param centrale la centrale
	 * @return la d�cision pour la p�riode et la centrale
	 */
	public int getDecisionPeriodeCentrale(int periode, int centrale) {
		return y[periode][centrale];
	}

	/**
	 * Retourne la trajectoire
	 * @return la trajectoire
	 */
	public int getTrajectoire() {
		return trajectoire;
	}

	/**
	 * Modifie la trajectoire
	 * @param trajectoire la trajectoire
	 */
	public void setTrajectoire(int trajectoire) {
		this.trajectoire = trajectoire;
	}

	/**
	 * Modifie l'activation du sc�nario
	 * @param indiceScenario le sc�nario
	 * @param activation l'activation
	 */
	public void active(int indiceScenario, boolean activation) {
		z[indiceScenario] = activation;
	}

	/**
	 * Modifie la d�cision pour une p�riode et une centrale
	 * @param periode la p�riode
	 * @param centrale la centrale
	 * @param palier le nouveau palier
	 */
	public void setDecisionPeriodeCentrale(int periode, int centrale, int palier) {
		y[periode][centrale] = palier;
	}

	public String toString() {
		String str = "Fonction Objectif = " + fonctionObjectif() + "\n";

		for (int p = 0; p < donnees.nbPeriodes; p++) {
			str += "Periode : " + p + "\n";
			for (int c = 0; c < donnees.nbCentrales; c++) {
				str += "Centrale " + c + " : " + y[p][c] + "\n";
			}
			str += "Reservoir : " + trajectoire + "\n";
		}
		return str;
	}

	public SolutionEnergie genererSolutionEnergie() {
		SolutionEnergie e = new SolutionEnergie();
		double[][] tabEnergie = new double[donnees.nbCentrales + 1][donnees.nbPeriodes];

		int c;
		for (int p = 0; p < donnees.nbPeriodes; p++) {
			for (c = 0; c < donnees.nbCentrales; c++) {
				tabEnergie[c][p] = donnees.getPalier(c, y[p][c]);
			}

			tabEnergie[c][p] = donnees.getTrajectoire(trajectoire) / donnees.getTurbinage();
		}
		e.setEnergies(tabEnergie);

		double[][] energiesMax = donnees.getProductionsMax();
		e.setEnergiesMax(energiesMax);

		return e;
	}
}
