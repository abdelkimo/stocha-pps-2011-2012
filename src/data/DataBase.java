package data;

/**
 * Cette classe contient les informations de base concernant une instance du probl�me.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 *
 */
public abstract class DataBase {
	
	/** Le vecteur de co�ts. Pour chaque centrale thermique il y a un cout de production en �/MW associ� et pour la centrale hydrolique un cout d'utilisation de l'eau en �/m^3 */
	protected double[] couts;
	/** La production maximale de chaque centrale pour chaque p�riode */
	protected double[][] productionsMax;
	/** Le volume initial d'eau dans le r�servoir */
	protected double volumeInitial;
	/** Les volumes minimum d'eau dans le r�servoir par p�riodes */
	protected double[] volumeMin;
	/** Les volumes maximum d'eau dans le r�servoir par p�riodes */
	protected double[] volumeMax;
	/** Le turbinage */
	protected double turbinage;
	/** Les apports en eau pour chaque p�riode */
	private double[] apports;
	
	/**
	 * Cr�e les donn�es � partir des fichiers
	 * @param parametresHydrauliquesFile le fichier csv contenant les param�tres hydrauliques
	 * @param capaciteMaxFile le fichier csv contenant la capacit� max
	 */
	public DataBase(String parametresHydrauliquesFile, String capaciteMaxFile)
	{
		
	}
	
	/**
	 * Retourne les co�ts
	 * @return les couts
	 */
	public double[] getCouts() {
		return couts;
	}
	
	/**
	 * Retourne les productions maximales
	 * @return les productions maximales
	 */
	public double[][] getProductionsMax() {
		return productionsMax;
	}

	/**
	 * Retourne le volume initial d'eau dans le r�servoir
	 * @return le volume initial d'eau dans le r�servoir
	 */
	public double getVolumeInitial() {
		return volumeInitial;
	}

	/**
	 * Retourne le volume minimum d'eau dans le r�servoir pour un p�riode
	 * @param periode la p�riode
	 * @return le volume minimum d'eau dans le r�servoir pour la p�riode
	 */
	public double getVolumeMin(int periode) {
		return volumeMin[periode];
	}

	/**
	 * Retourne le volume maximum d'eau dans le r�servoir pour un p�riode
	 * @param periode la p�riode
	 * @return le volume maximum d'eau dans le r�servoir pour la p�riode
	 */
	public double getVolumeMax(int periode) {
		return volumeMax[periode];
	}

	/**
	 * Retourne le turbinage
	 * @return le turbinage
	 */
	public double getTurbinage() {
		return turbinage;
	}
	
	/**
	 * Retourne le cout de production d'une centrale
	 * @param centrale la centrale
	 * @return le cout de production d'une centrale
	 */
	public double getCoutPeriodeCentrale(int centrale)
	{
		return couts[centrale];
	}
	
	/**
	 * Retourne la production maximale d'une centrale
	 * @param centrale la centrale
	 * @param periode la p�riode
	 * @return les production maximale d'une centrale
	 */
	public double getProductionMaxCentrale(int centrale, int periode) 
	{
		return productionsMax[centrale][periode];
	}

	/**
	 * Retourne les apports
	 * @return les apports
	 */
	public double[] getApports() {
		return apports;
	}
	
	/**
	 * Retourne les apports d'une p�riode
	 * @return les apports d'une p�riode
	 */
	public double getApportsPeriode(int periode) {
		return apports[periode];
	}
}
