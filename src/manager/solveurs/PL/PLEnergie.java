package manager.solveurs.PL;

import manager.solveurs.Solveur;

import data.Data;
import data.solution.SolutionEnergie;
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
	/** Le vecteur de solution du probl�me. La production de chaque centrale par p�riode en MW */
	protected double[] solution;
	/** Le vecteur des couts de production de l'�nergie en �/MW. */
	protected double[] couts;
	/** Pour chaque sc�nario il y a un vecteur d'�nergie achet�e par p�riode en MW */
	protected double[][] energieAchetee;
	/** Pour chaque sc�nario il y a un vecteur d'�nergie vendue par p�riode en MW */
	protected double[][] energieVendue;
	/** Le prix du MW achet� */
	protected double prixAchete;
	/** Le prix du MW vendu */
	protected double prixVente;
	/** Pour chaque sc�nario il y a un vecteur des demandes par p�riode */
	protected double[][] demandes;
	/** Pour chaque sc�nario il y a un vecteur, toujours le m�me, pour la partie droite des contraintes sur les productions */
	protected double[] vecteurContraintesMembreDroit;
	/** Pour chaque sc�nario il y a une matrice, toujours la m�me, pour la partie gauche des contraintes sur les productions */
	protected int[][] matriceContraintesMembreGauche;
	/** Pour chaque sc�nario il y a un vecteur de disponibilit� par p�riode (entre 0 et 1) */
	protected double[][] matriceDisponibilite;
	
	/**
	 * Cr�e un nouveau PLEnergie.
	 * @param donnees les donn�es du probl�me
	 */
	public PLEnergie(Data donnees)
	{
		
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
	public SolutionEnergie getSolution() {
		return null;
	}
	
	public abstract void lancer();
}
