package manager.solveurs.RS;

import java.util.Random;

import data.DataBinaire;
import data.solution.Solution;
import data.solution.SolutionEnergieBinaire;

/**
 * Impl�mentation sp�cialis�e du recuit simul� pour le probl�me de management de la production d'�nergie.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class RSEnergie extends RecuitSimule
{
	/** Les donn�es du probl�me */
	private DataBinaire donnees;
	/** Le nombre d'it�rations par palier de temp�rature */
	private int nbIterationsParPalier;
	/** Le nombre d'it�rations courant pour le palier de temp�rature courant.*/
	private int iterationCourante;
	/** La temp�rature � atteindre pour arr�ter le recuit. */
	private double temperatureFinale;
	/** Le nombre de modifications pour lesquelles on garde le m�me sc�narios */
	private int nbTransformationsParScenarios;
	/** Le nombre de transformations imposibles avant de changer de sc�narios */
	private int nbTestsTransformations;
	/** Le nombre courant de modifications pour lesquelles on garde le m�me sc�narios */
	private int nbTransformationsParScenariosCourant;
	
	/**
	 * Construit un recuit simul� sp�cialis� pour le probl�me de la p-m�diane.
	 * @param facteurDecroissance le facteur de d�croissance de la temp�rature du recuit.
	 * @param temperatureFinale la temp�rature � atteindre pour arr�ter le recuit.
	 */
	public RSEnergie( DataBinaire donnees, double facteurDecroissance, double temperatureFinale, int nbIterationsParPalier, double tauxAcceptation, int nbTransformationsParScenarios, int nbTestsTransformations)
	{
		super(facteurDecroissance, tauxAcceptation);
		solutionCourante = new SolutionEnergieBinaire(donnees);
		meilleureSolution = new SolutionEnergieBinaire(donnees);
		this.temperatureFinale = temperatureFinale;
		this.donnees = donnees;
		this.nbIterationsParPalier = nbIterationsParPalier;
		this.nbTestsTransformations = nbTestsTransformations;
		this.nbTransformationsParScenarios = nbTransformationsParScenarios;
		iterationCourante = 0;
		nbTransformationsParScenariosCourant = 0;
	}

	/**
	 * Teste si le recuit est arriv� la temp�rature finale demand�e � la cr�ation.
	 * @return true si le recuit peut passer au palier de temp�rature suivant, false sinon.
	 */
	protected boolean testerCondition1()
	{
		if(temperature > temperatureFinale)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Teste si le recuit a encore des it�rations � faire pour un palier de temp�rature.
	 * @return true si le recuit doit continuer � ce palier de temp�rature, false sinon.
	 */
	protected boolean testerCondition2()
	{
		iterationCourante++;
		
		if(iterationCourante < nbIterationsParPalier)
		{
			return true;
		}
		else
		{
			iterationCourante = 0;
			return false;
		}
	}

	/**
	 * S�lectionne une solution dans le voisinage de la solution courante en modifiant l'activation d'un sc�nario et une d�cision.
	 * Un sc�nario actif devient inactif et inversement. Il faut que la nouvelle configuration ait une probabilit� sup�rieure � celle demand�e.
	 * Une d�cision passe de 0 � 1 ou de 1 � 0. Il faut que la nouvelle configuration respecte l'offre et la demande et les contraintes d'unicit�es.
	 * @return une solution voisine de la solution courante.
	 */
	protected Solution voisin()
	{
		SolutionEnergieBinaire solution = (SolutionEnergieBinaire) solutionCourante.clone();
		
		Random rand = new Random();
		int nbTests;
		boolean recherche;
		nbTransformationsParScenariosCourant++;
		
		do
		{
			// Active ou desactive un sc�nario si c'est le moment
			if(nbTransformationsParScenariosCourant >= nbTransformationsParScenarios)
			{
				nbTransformationsParScenariosCourant = 0;
				int indiceScenarioChange;
				do
				{
					recherche = false;
					indiceScenarioChange = rand.nextInt(solution.getZ().length);
					solution.active(indiceScenarioChange, !solution.isActived(indiceScenarioChange));
					
					if(solution.probabiliteScenario() < donnees.getProbabilite())
					{
						solution.active(indiceScenarioChange, !solution.isActived(indiceScenarioChange));
						recherche = true;
					}
					
				} while(recherche);
			}
			
			// Modifie les d�cisions
			nbTests = 0;
			do
			{
				recherche = false;
				nbTests++;
				
				// On modifie le palier d'une centrale pour une p�riode
				if(rand.nextInt(1) < 80)
				{
					int periodeChange;
					int centraleChange;
					int palierChange;
					periodeChange = rand.nextInt(donnees.nbPeriodes);
					centraleChange = rand.nextInt(donnees.nbCentrales);
					int ancienPalier = solution.getDecisionPeriodeCentrale(periodeChange, centraleChange);
					do
					{
						palierChange = rand.nextInt(donnees.nbPaliers[centraleChange]);
					} while(palierChange == solution.getDecisionPeriodeCentrale(periodeChange, centraleChange));
					
					solution.setDecisionPeriodeCentrale(periodeChange, centraleChange, palierChange);
					
					if(!solution.respecteContrainteDemande())
					{
						recherche = true;
						solution.setDecisionPeriodeCentrale(periodeChange, centraleChange, ancienPalier);
					}
				}
				// Si la trajectoire hydrolique est modifi�e
				else
				{					
					int trajectoireChange;
					int ancienneTrajectoire = solution.getTrajectoire();
					do
					{
						trajectoireChange = rand.nextInt(donnees.nbTrajectoires);
					} while(trajectoireChange == solution.getTrajectoire());
					solution.setTrajectoire(trajectoireChange);
					
					if(!solution.respecteContrainteDemande())
					{
						recherche = true;
						solution.setTrajectoire(ancienneTrajectoire);
					}
				}
			} while(recherche && nbTests < nbTestsTransformations);
			
		} while(!solution.respecteContrainteDemande() && nbTests == nbTestsTransformations);
		
		return solution;
	}
	
	public static void main(String[] args)
	{
		DataBinaire data = new DataBinaire(0.98, "Data/Donn�es_Recuit_demandes.csv", "Data/Donn�es_Recuit_paliers1.csv", "Data/Donn�es_Recuit_paliers2.csv", "Data/Donn�es_Recuit_paliers3.csv", "Data/Donn�es_Recuit_paliers4.csv", "Data/Donn�es_Recuit_trajectoire_hydro.csv", "Data/Donn�es_Recuit_parametres_hydro.csv", "Data/Donn�es_Recuit_capacit�.csv");
		RSEnergie rs = new RSEnergie(data, 0.8, 20000, 16384, 0.8, 10, 100);
		rs.lancer();
		SolutionEnergieBinaire solution = (SolutionEnergieBinaire) rs.getSolution();
		System.out.println(solution);
	}
}
