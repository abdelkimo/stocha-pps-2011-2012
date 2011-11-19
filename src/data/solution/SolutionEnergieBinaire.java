package data.solution;

/**
 * Une solution du probl�me de management de la production d'�nergie en binaire mais aussi pour sa relaxation.
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class SolutionEnergieBinaire extends Solution {

	/** Le vecteur de d�cision */
	double[] y;
	/** Le vecteur d'activation des sc�narios */
	double[] z;
	
	/**
	 * Cr�e une nouvelle solution sp�cifique au probl�me de management de la production d'�nergie sous sa forme binaire et sa relaxation
	 */
	public SolutionEnergieBinaire()
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
