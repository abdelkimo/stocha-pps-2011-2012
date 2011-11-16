package manager.solveurs.PL;

import manager.solveurs.Solveur;

import data.Data;
import data.solution.SolutionEnergieBinaire;

/**
 * Cette classe permet d'�crire un programme lin�aire � partir de donn�es pour le probl�me de management de la production d'�nergie.
 * Ce PL est utilis� par un solveur de programmes lin�aires.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 * 
 */
public abstract class PLEnergie implements Solveur {

	/** Variable contenant les donn�es du probl�me */
	protected Data donnees;

	/** La solution du probl�me. */
	protected SolutionEnergieBinaire solution;
	
	/** Le tableau contenant les coefficients de la fonction objectif. */
	protected double[] coefFonctionObjectif;
	
	/**
	 * Cr�e un nouveau PLEnergie.
	 * @param donnees les donn�es du probl�me
	 */
	public PLEnergie(Data donnees)
	{
		this.donnees = donnees;
		solution = new SolutionEnergieBinaire(donnees);
		genererPL();
	}

	/**
	 * G�n�re le programme lin�aire de la p-mediane � partir des donn�es.
	 * Remplit les tableaux du probl�me.
	 */
	private void genererPL()
	{
		// Remplit le tableau coefFonctionObjectif
		// A chaque paire i j on associe la distance les s�parant
		coefFonctionObjectif = new double[donnees.getNbEntites()*donnees.getNbEntites()];
		for(int i=0; i<donnees.getNbEntites(); i++)
		{
			for(int j=0; j<donnees.getNbEntites(); j++)
			{
				coefFonctionObjectif[i*donnees.getNbEntites()+j] = donnees.getDistance(i, j);
			}
		}
		
		// Remplit le tableau coefNbCentres
		// A chaque paire j j on met un poid de 1 pour les activer dans la contrainte
		coefNbCentres = new int[donnees.getNbEntites()*donnees.getNbEntites()];
		for(int i=0; i<donnees.getNbEntites(); i++)
		{
			for(int j=0; j<donnees.getNbEntites(); j++)
			{
				if(i == j)
				{
					coefNbCentres[i*donnees.getNbEntites()+j] = 1;
				}
				else
				{
					coefNbCentres[i*donnees.getNbEntites()+j] = 0;
				}
			}
		}
		
		// Remplit le tableau coefEntiteRelieeUneEntite
		// Pour chaque contrainte on a la plage de variables � prendre en compte (pour �viter le d�bordement de m�moire)
		coefEntiteRelieeUneEntite = new int[donnees.getNbEntites()];
		for(int l=0; l<donnees.getNbEntites(); l++)
		{
			coefEntiteRelieeUneEntite[l] = (l + 1) * donnees.getNbEntites() - 1;
		}
		
		// Remplit le tableau coefGaucheEntiteRelieeUnCentre et coefDroiteEntiteRelieeUnCentre
		coefDroiteEntiteRelieeUnCentre = new int[donnees.getNbEntites()*donnees.getNbEntites()];
		coefGaucheEntiteRelieeUnCentre = new int[donnees.getNbEntites()*donnees.getNbEntites()];
		for(int i=0; i<donnees.getNbEntites(); i++)
		{
			for(int j=0; j<donnees.getNbEntites(); j++)
			{
				coefDroiteEntiteRelieeUnCentre[i*donnees.getNbEntites()+j] = j*donnees.getNbEntites()+j;
				coefGaucheEntiteRelieeUnCentre[i*donnees.getNbEntites()+j] = i*donnees.getNbEntites()+j;
			}
		}
	}
	
	/**
	 * Renvoie la solution calcul�e.
	 * @return la solution calcul�e
	 */
	public SolutionEnergieBinaire getSolution() {
		return solution;
	}
	
	public abstract void lancer();
}
