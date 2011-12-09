package data;


/**
 * Cette classe contient les informations concernant une instance du probl�me sous la forme binaire et sa relaxation.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 *
 */
public class DataBinaire extends DataBase {
	/** Tableau contenant les diff�rents sc�narios */
	private ScenarioBinaire[] scenarios;
	/** Les trajectoires hydrauliques */
	private double[] trajectoires;
	/** La probabilit� voulue que les sc�narios se d�roulent */
	double probabilite;
	
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
	public double[] getCouts() {
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
	 * Retourne les trajectoires hydrauliques
	 * @return les trajectoires hydrauliques
	 */
	public double[] getTrajectoires()
	{
		return trajectoires;
	}
	
	/**
	 * Retourne la trajectoire hydraulique voulue
	 * @param trajectoire la trajectoire voulue
	 * @return la trajectoire hydraulique voulue
	 */
	public double getTrajectoire(int trajectoire)
	{
		return trajectoires[trajectoire];
	}
}
