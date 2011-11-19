package manager.solveurs.PL;

import data.DataRecours;

/**
 * Cette classe permet de r�soudre une instance du probl�me de management de la production d'�nergie � l'aide de CPLEX
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class CplexEnergieRecours extends PLEnergieRecours 
{
	/**
	 * Cr�e un nouveau CPlexEnergie
	 * 
	 * @param donnees les donn�es du probl�me
	 */
	public CplexEnergieRecours(DataRecours donnees)
	{
		super(donnees);
	}

	/**
	 * Lance la r�solution du programme lin�aire.
	 * Cr�e le programme � partir des tableaux de coefficients.
	 */
	public void lancer()
	{
		// min couts * x + probabilitesPrix * y
		// une contrainte par sc�nario et par p�riode pour l'offre et la demande
	}
}
