package manager.solveurs.RS;

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
	
	/**
	 * Construit un recuit simul� sp�cialis� pour le probl�me de la p-m�diane.
	 * @param facteurDecroissance le facteur de d�croissance de la temp�rature du recuit.
	 * @param temperatureFinale la temp�rature � atteindre pour arr�ter le recuit.
	 */
	public RSEnergie(double facteurDecroissance, DataBinaire donnees, double temperatureFinale, int nbIterationsParPalier, double tauxAcceptation)
	{
		super(facteurDecroissance, tauxAcceptation);
		solutionCourante = new SolutionEnergieBinaire(donnees);
		meilleureSolution = new SolutionEnergieBinaire(donnees);
		this.temperatureFinale = temperatureFinale;
		this.donnees = donnees;
		this.nbIterationsParPalier = nbIterationsParPalier;
		iterationCourante = 0;
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
		SolutionEnergieBinaire solution = new SolutionEnergieBinaire(donnees);
		
		
		return solution;
	}
}
