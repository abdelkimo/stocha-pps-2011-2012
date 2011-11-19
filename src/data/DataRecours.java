package data;


/**
 * Cette classe contient les informations concernant une instance du probl�me sous la forme d'un recours avec sc�narios.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 *
 */
public class DataRecours extends DataBase {
	
	/** Le prix d'achat de l'�nergie par p�riode */
	private double[] prixAchat;
	/** Le prix de vente de l'�nergie par p�riode */
	private double[] prixVente;
	/** La liste des sc�narios */
	private ScenarioRecours[] scenarios;
	
	/**
	 * Construit une donn�e en fonction d'un fichier
	 * @param fileName le chemin vers le fichier de donn�es
	 */
	public DataRecours(String fileName) {

	}

	/**
	 * Retourne les sc�narios
	 * @return les sc�narios
	 */
	public ScenarioRecours[] getScenarios() {
		return scenarios;
	}
	
	/**
	 * Retourne les prix d'achat par p�riode
	 * @return les prix d'achat par p�riode
	 */
	public double[] getPrixAchat() {
		return prixAchat;
	}

	/**
	 * Retourne les prix de vente par p�riode
	 * @return les prix de vente par p�riode
	 */
	public double[] getPrixVente() {
		return prixVente;
	}
	
	/**
	 * Retourne le prix d'achat de l'�nergie pour une p�riode
	 * @param periode la p�riode
	 * @return le prix d'achat de l'�nergie pour une p�riode
	 */
	public double getPrixAchatPeriode(int periode)
	{
		return prixAchat[periode];
	}
	
	/**
	 * Retourne le prix d'achat de l'�nergie pour une p�riode
	 * @param periode la p�riode
	 * @return le prix d'achat de l'�nergie pour une p�riode
	 */
	public double getPrixVentePeriode(int periode)
	{
		return prixVente[periode];
	}

	/**
	 * Retourne le sc�nario voulu
	 * @param scenario l'indice du sc�nario
	 * @return le sc�nario voulu
	 */
	public ScenarioRecours getScenario(int scenario)
	{
		return scenarios[scenario];		
	}
}
