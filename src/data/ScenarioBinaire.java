package data;

/** 
 * Cette classe repr�sente un sc�nario pour le probl�me binaire
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class ScenarioBinaire {

	/** La matrice de productions. Pour chaque centrale, pour chaque p�riode, il y a un vecteur de paliers de production r�els affect�s par l'al�a */
	private double[][][] productions;
	/** Le vecteur de demandes. Pour chaque p�riode il y a une demande  */
	private double[] demandes;
	/** La probabilit� que le sc�nario se d�roule */
	private double probabilite;
	
	/**
	 * Cr�e un sc�nario pour le probl�me binaire � partir des productions et des demandes
	 * @param productions les paliers de productions par p�riode par centrale
	 * @param demandes les demandes par p�riode
	 * @param probabilite la probabilit� que le sc�nario se d�roule
	 */
	public ScenarioBinaire(double[][][] productions, double[] demandes, double probabilite) {
		this.productions = productions;
		this.demandes = demandes;
		this.probabilite = probabilite;
	}

	/**
	 * Retourne la matrice de productions
	 * @return la matrice de productions
	 */
	public double[][][] getProductions() {
		return productions;
	}

	/**
	 * Retourne le vecteur de demandes
	 * @return le vecteur de demandes
	 */
	public double[] getDemandes() {
		return demandes;
	}
	
	public double getProbabilite()
	{
		return probabilite;
	}
	
	/**
	 * Retourne les paliers de production d'une centrale pour une p�riode
	 * @param periode la periode
	 * @param centrale la centrale
	 * @return les paliers de production d'une centrale pour une p�riode
	 */
	public double[] getPaliersPeriodeCentrale(int periode, int centrale)
	{
		return productions[periode][centrale];
	}
	
	/**
	 * Returne la demande de la p�riode souhait�e
	 * @param periode la p�riode
	 * @return la demande de la p�riode souhait�e
	 */
	public double getDemandePeriode(int periode)
	{
		return demandes[periode];
	}
}
