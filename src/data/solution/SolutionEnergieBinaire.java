package data.solution;

import java.util.Random;

import data.DataBinaire;

/**
 * Une solution du probl�me de management de la production d'�nergie en binaire
 * mais aussi pour sa relaxation.
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
	 * 
	 * @param nbScenarios
	 *            le nombre de sc�narios
	 * @param nbCentralesThermiques
	 *            le nombre de centrales
	 * @param nbPeriodes
	 *            le nombre de p�riodes
	 * @param nbTrajectoires
	 *            le nombre de trajectoires
	 */
	public SolutionEnergieBinaire(DataBinaire donnees) {
		this.donnees = donnees;
		y = new int[donnees.nbPeriodes][donnees.nbCentrales];
		z = new boolean[donnees.nbScenarios];
	}

	@Override
	public Solution clone() {
		SolutionEnergieBinaire res = new SolutionEnergieBinaire(donnees);

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
		for (int p = 0; p < donnees.nbPeriodes; p++) {
			for (int c = 0; c < donnees.nbCentrales; c++) {

				val += donnees.getPalier(c, y[p][c]) * donnees.getCoutCentrale(c);
			}

			val += ((donnees.getTrajectoire(trajectoire) / donnees.getTurbinage()) - donnees.getApportsPeriode(p))
					* donnees.getCoutCentrale(4);
		}
		return val;
	}

	@Override
	public void solutionInitiale() {
		Random rand = new Random();

		for (int i = 0; i < z.length; i++)
			z[i] = false;

		int scenarioActive;
		do {
			scenarioActive = rand.nextInt(z.length);
			if (!z[scenarioActive])
				z[scenarioActive] = true;
		} while (probabiliteScenario() < donnees.getProbabilite());

		int centraleCoutMin = 0;
		boolean[] centraleUtilisee = new boolean[donnees.nbCentrales + 1];
		for (int c = 0; c < donnees.nbCentrales + 1; c++) {
			centraleUtilisee[c] = false;
		}

		for (int p = 0; p < donnees.nbPeriodes; p++) {
			for (int c = 0; c < donnees.nbCentrales; c++) {
				y[p][c] = 0;
			}
		}

		trajectoire = 0;

		do {
			double coutMin = Integer.MAX_VALUE;
			for (int i = 0; i < donnees.getCouts().length; i++) {
				if (!centraleUtilisee[i] && donnees.getCoutCentrale(i) <= coutMin) {
					coutMin = donnees.getCoutCentrale(i);
					centraleCoutMin = i;
				}
			}
			centraleUtilisee[centraleCoutMin] = true;

			for (int p = 0; p < donnees.nbPeriodes; p++) {
				if (centraleCoutMin < donnees.nbCentrales) {
					int palier = 0;
					while (palier < donnees.nbPaliers[centraleCoutMin] - 1 && !respecteContrainteDemandePeriode(p)) {
						palier++;
						y[p][centraleCoutMin] = palier;
					}
				} else {
					int trajectoire2 = 0;
					while (trajectoire < donnees.nbTrajectoires - 1 && !respecteContrainteDemandePeriode(p)) {
						trajectoire2++;
						trajectoire = trajectoire2;
					}
				}
			}
		} while (!respecteContrainteDemande());

		System.out.println(fonctionObjectif());
	}

	public boolean respecteContrainteDemande() {

		for (int s = 0; s < donnees.nbScenarios; s++) {
			if (isActived(s)) {
				for (int p = 0; p < donnees.nbPeriodes; p++) {
					double production = 0;
					for (int c = 0; c < donnees.nbCentrales; c++) {
						production += donnees.getScenario(s).getPaliersPeriodeCentrale(c, p)[getDecisionPeriodeCentrale(
								p, c)];
					}

					production += donnees.getTrajectoire(trajectoire);
					if (production < donnees.getScenario(s).getDemandePeriode(p))
						return false;
				}
			}
		}
		return true;
	}

	public boolean respecteContrainteDemandePeriode(int periode) {
		for (int s = 0; s < donnees.nbScenarios; s++) {
			if (isActived(s)) {
				double production = 0;
				for (int c = 0; c < donnees.nbCentrales; c++) {
					production += donnees.getScenario(s).getPaliersPeriodeCentrale(c, periode)[getDecisionPeriodeCentrale(
							periode, c)];
				}

				production += donnees.getTrajectoire(trajectoire);
				if (production < donnees.getScenario(s).getDemandePeriode(periode))
					return false;
			}
		}
		return true;
	}

	public double probabiliteScenario() {
		double sommeProba = 0;
		for (int i = 0; i < z.length; i++) {
			if (z[i])
				sommeProba += donnees.getScenario(i).getProbabilite();
		}
		return sommeProba;
	}

	public boolean[] getZ() {
		return z;
	}

	public int[][] getY() {
		return y;
	}

	public boolean isActived(int s) {
		return z[s];
	}

	public int getDecisionPeriodeCentrale(int periode, int centrale) {
		return y[periode][centrale];
	}

	public int getTrajectoire() {
		return trajectoire;
	}

	public void setTrajectoire(int trajectoire) {
		this.trajectoire = trajectoire;
	}

	public void active(int indiceScenario, boolean activation) {
		z[indiceScenario] = activation;
	}

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

			tabEnergie[c][p] = ((donnees.getTrajectoire(trajectoire) / donnees.getTurbinage()) - donnees
					.getApportsPeriode(p));
		}
		e.setEnergies(tabEnergie);

		double[][] energiesMax = donnees.getProductionsMax();
		e.setEnergiesMax(energiesMax);

		return e;
	}
}
