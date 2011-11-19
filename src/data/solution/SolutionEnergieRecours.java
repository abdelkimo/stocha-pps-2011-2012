package data.solution;

/**
 * Une solution du probl�me de management de la production d'�nergie sous forme de recours avec sc�narios.
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class SolutionEnergieRecours extends Solution {

	/** Le vecteur de productions */
	private double[] x;
	/** La matrice contenant l'�nergie achet�e. Pour chaque p�riode et chaque sc�nario il y a de l'�nergie achet�e */
	private double[][] yAchat;
	/** La matrice contenant l'�nergie vendue. Pour chaque p�riode et chaque sc�nario il y a de l'�nergie vendue */
	private double[][] yVente;
	
	/**
	 * Cr�e une nouvelle solution sp�cifique au probl�me de management de la production d'�nergie avec recours
	 */
	public SolutionEnergieRecours()
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
