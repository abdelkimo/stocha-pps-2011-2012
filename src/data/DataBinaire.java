package data;


/**
 * Cette classe contient les informations concernant une instance du probl�me sous la forme binaire et sa relaxation.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 *
 */
public class DataBinaire {
	
	/** Le vecteur de co�ts. Pour chaque p�riode et chaque centrale thermique il y a un cout de production en �/MW associ� et pour la centrale hydrolique un cout d'utilisation de l'eau */
	private double[][] couts;
	/** Tableau contenant les diff�rents sc�narios */
	private ScenarioBinaire[] scenarios;
	
	/**
	 * Construit une donn�e en fonction d'un fichier
	 * @param fileName le chemin vers le fichier de donn�es
	 */
	public DataBinaire(String fileName) {
		
	}

	/**
	 * Retourne les co�ts
	 * @return les couts
	 */
	public double[][] getCouts() {
		return couts;
	}

	/**
	 * Retourne les sc�narios
	 * @return les scenarios
	 */
	public ScenarioBinaire[] getScenarios() {
		return scenarios;
	}
	
	/**
	 * Retourne le sc�nario voulu
	 * @param scenario l'indice du sc�nario
	 * @return le sc�nario voulu
	 */
	public ScenarioBinaire getSc�nario(int scenario)
	{
		return scenarios[scenario];		
	}
	
	/**
	 * Retourne le cout de production d'une centrale pour une p�riode
	 * @param periode la p�riode
	 * @param centrale la centrale
	 * @return le cout de production d'une centrale pour une p�riode
	 */
	public double getCoutPeriodeCentrale(int periode, int centrale)
	{
		return couts[periode][centrale];
	}
}
