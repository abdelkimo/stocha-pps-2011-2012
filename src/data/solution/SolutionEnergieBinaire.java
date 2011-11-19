package data.solution;

import data.DataBinaire;

/**
 * Une solution du probl�me de management de la production d'�nergie en binaire mais aussi pour sa relaxation.
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class SolutionEnergieBinaire extends Solution {

	/** Le vecteur de d�cision */
	private double[] y;
	/** Le vecteur d'activation des sc�narios */
	private double[] z;
	/** Les donn�es du probl�me */
	private DataBinaire donn�es;
	
	/**
	 * Cr�e une nouvelle solution sp�cifique au probl�me de management de la production d'�nergie sous sa forme binaire et sa relaxation
	 * @param donnees les donnees du probl�me
	 */
	public SolutionEnergieBinaire(DataBinaire donnees)
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
