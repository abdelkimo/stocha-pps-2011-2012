package data.solution;

import data.Data;

/**
 * Une solution du probl�me de management de la production d'�nergie.
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class SolutionEnergieBinaire extends Solution {

	/** Le vecteur de d�cision */
	boolean[] y;
	/** Le vecteur d'activation des sc�narios */
	boolean[] z;
	
	/**
	 * Cr�e une nouvelle solution sp�cifique au probl�me de management de la production d'�nergie en fonction des donn�es
	 * @param data
	 */
	public SolutionEnergieBinaire(Data data)
	{
		
	}
	
	@Override
	public Solution clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double deltaF(Solution solution) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double fonctionObjectif() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void solutionInitiale() {
		// TODO Auto-generated method stub
		
	}
}
