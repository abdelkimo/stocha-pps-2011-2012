package data.solution;

/**
 * Classe contenant la solution d'un probl�me
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 * 
 */
public abstract class Solution {

	/**
	 * Initialise la solution.
	 */
	public abstract void solutionInitiale();
	
	/**
	 * Retourne la diff�rence entre deux solutions.
	 * Une valeur n�gative si le param�tre est meilleur que la solution courante.
	 * 
	 * @param solution
	 *            la solution � comparer � la solution courante.
	 * @return la diff�rence entre la solution courante et la solution en
	 *         argument : solution - solution courante
	 */
	public abstract double deltaF(Solution solution);

	/**
	 * Retourne la valeur de la fonction objectif du probl�me.
	 * 
	 * @return retourne la valeur de la solution avec la fonction objectif du
	 *         probl�me.
	 */
	public abstract double fonctionObjectif();
	
	/**
	 * Cr�e une copie de la solution courante.
	 * @return une copie de la solution courante
	 */
	public abstract Solution clone();
}
