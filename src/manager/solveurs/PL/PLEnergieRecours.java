package manager.solveurs.PL;

import manager.solveurs.Solveur;

import data.DataRecours;
import data.solution.SolutionEnergieRecours;

/**
 * Cette classe permet d'�crire un programme lin�aire � partir de donn�es pour le probl�me de management de la production d'�nergie.
 * Ce PL est utilis� par un solveur de programmes lin�aires.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 * 
 */
public abstract class PLEnergieRecours implements Solveur {

	/** Variable contenant les donn�es du probl�me */
	protected DataRecours donnees;
	/** La solution */
	protected SolutionEnergieRecours solution;
	/** Le vecteur contenant les prix par p�riode multipli� par la probabilit� de chaque sc�narios */
	protected double[] probabilitesPrix;
	/** Le vecteur de co�ts par p�riode et par centrale */
	protected double[] couts;
	/** La matrice qui pour chaque sc�nario et chaque p�riode associe la liste des facteurs de disponibilit� */
	protected double[][][] facteursDisponibilite;
	
	/**
	 * Cr�e un nouveau PLEnergie.
	 * @param donnees les donn�es du probl�me
	 */
	public PLEnergieRecours(DataRecours donnees)
	{
		genererPL();
	}

	/**
	 * G�n�re le programme lin�aire de la p-mediane � partir des donn�es.
	 * Remplit les tableaux du probl�me.
	 */
	private void genererPL()
	{
		
	}
	
	/**
	 * Renvoie la solution calcul�e.
	 * @return la solution calcul�e
	 */
	public SolutionEnergieRecours getSolution() {
		return null;
	}
	
	public abstract void lancer();
}
