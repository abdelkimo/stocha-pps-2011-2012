package data;


/**
 * Cette classe contient les informations concernant une instance du probl�me sous la forme probabiliste.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 *
 */
public class DataProbabiliste extends DataBase {

	/** Pour chaque p�riode il y a une probabilit� minimale souhait�e */
	private double[] probabilites;
	/** L'esp�rance du facteur de disponibilit� par p�riode */
	private double[] EFacteurDisponibilite;
	/** L'esp�rance de la demande par p�riode */
	private double[] EDemande;
	/** La variance de la loi de distribution voulue */
	private double variance;
	
	/**
	 * Construit une donn�e en fonction d'un fichier
	 * @param fileName le chemin vers le fichier de donn�es
	 */
	public DataProbabiliste(String fileName) {
		super(null, null);
	}

	/**
	 * Retourne les probabilit�s
	 * @return les probabilit�s
	 */
	public double[] getProbabilites() {
		return probabilites;
	}

	/**
	 * Retourne les esp�rances des facteur de disponibilit�
	 * @return les esp�rances des facteur de disponibilit�
	 */
	public double[] getEFacteurDisponibilite() {
		return EFacteurDisponibilite;
	}

	/**
	 * Retourne les esp�rances des demandes
	 * @return les esp�rances des demandes
	 */
	public double[] getEDemande() {
		return EDemande;
	}

	/**
	 * @return la variance
	 */
	public double getVariance() {
		return variance;
	}
	
	/**
	 * @return the eFacteurDisponibilite
	 */
	public double getEFacteurDisponibilitePeriode(int periode) {
		return EFacteurDisponibilite[periode];
	}

	/**
	 * @return the eDemande
	 */
	public double getEDemandePeriode(int periode) {
		return EDemande[periode];
	}
}
