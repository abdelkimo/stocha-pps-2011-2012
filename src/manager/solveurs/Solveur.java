package manager.solveurs;

import data.solution.Solution;


/**
 * Interface correspondant � un algorithme de r�solution.
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 * 
 */
public interface  Solveur {

	/**
	 * Lance la r�solution du probl�me
	 */
	public void lancer();

	/**
	 * Retourne la solution
	 * @return la solution trouv�e
	 */
	public Solution getSolution();
}
