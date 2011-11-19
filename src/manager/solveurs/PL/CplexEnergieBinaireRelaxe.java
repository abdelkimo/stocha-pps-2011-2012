package manager.solveurs.PL;

import data.DataBinaire;

/**
 * Cette classe permet de r�soudre une instance du probl�me de management de la production d'�nergie � l'aide de CPLEX
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class CplexEnergieBinaireRelaxe extends PLEnergieBinaireRelaxe
{
	/**
	 * Cr�e un nouveau CPlexEnergie
	 * 
	 * @param donnees les donn�es du probl�me
	 */
	public CplexEnergieBinaireRelaxe(DataBinaire donnees)
	{
		super(donnees);
	}

	/**
	 * Lance la r�solution du programme lin�aire.
	 * Cr�e le programme � partir des tableaux de coefficients.
	 */
	public void lancer()
	{
		
	}
}
