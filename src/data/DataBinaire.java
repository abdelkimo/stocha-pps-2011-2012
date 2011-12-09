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
	 * Construit une donn�e binaire en fonction des fichiers. Les sc�narios sont �quiprobables
	 * @param demandesFile
	 * @param coeffCentrale1File
	 * @param coeffCentrale2File
	 * @param coeffCentrale3File
	 * @param coeffCentrale4File
	 * @param trajectoiresFile
	 * @param parametresHydrauliquesFile le fichier csv contenant les param�tres hydrauliques
	 * @param capaciteMaxFile le fichier csv contenant la capacit� max
	 */
	public DataBinaire(double probabilite, String demandesFile, String coeffCentrale1File, String coeffCentrale2File, String coeffCentrale3File, String coeffCentrale4File, String trajectoiresFile, String parametresHydrauliquesFile, String capaciteMaxFile) {
		super(parametresHydrauliquesFile, capaciteMaxFile);
		
		
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
