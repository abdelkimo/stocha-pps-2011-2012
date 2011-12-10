package src.data;

/** 
 * Cette classe repr�sente un sc�nario pour le probl�me avec recours
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public class ScenarioRecours {

	/** Pour chaque p�riode et chaque centrale thermique il y a un facteur de disponiblit� */
	private double[][] facteursDisponibilite;
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
	public ScenarioRecours(double[][] facteurDisponibilite, double[] demandes, double probabilite) {
		this.facteursDisponibilite = facteurDisponibilite;
		this.demandes = demandes;
		this.probabilite = probabilite;
	}

	/**
	 * Retourne la matrice des facteurs de disponibilit�
	 * @return la matrice des facteurs de disponibilit�
	 */
	public double[][] getFacteursDisponibilite() {
		return facteursDisponibilite;
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
	 * Retourne le facteur de disponibilit� d'une centrale pour une p�riode
	 * @param periode la periode
	 * @param centrale la centrale
	 * @return le facteur de disponibilit� d'une centrale pour une p�riode
	 */
	public double getPaliersPeriodeCentrale(int periode, int centrale)
	{
		return facteursDisponibilite[periode][centrale];
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
