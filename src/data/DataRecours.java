package data;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


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
	public DataRecours(String paramHydroFile, String capaciteMaxFile, String demandesFile, String coeffCentrale1File, String coeffCentrale2File, String coeffCentrale3File, String coeffCentrale4File) {
		super(paramHydroFile, capaciteMaxFile);
		
		int nbScenarios = 100;
		int nbPeriodes = 7;
		int nbCentralesThermiques = 4;
		
		scenarios = new ScenarioRecours[nbScenarios];
		prixAchat = new double[nbPeriodes];
		prixVente = new double[nbPeriodes];
		
		for(int i = 0; i < nbPeriodes; i++)
		{
			prixAchat[i] = 200;
			prixVente[i] = 100;
		}
		
		double[][] demandesTmp = new double[nbScenarios][nbPeriodes];
		double[][][] facteursTmp = new double[nbScenarios][nbCentralesThermiques][nbPeriodes];
		
		try{
			BufferedReader buffDemandes = new BufferedReader(new FileReader(demandesFile));
			BufferedReader buffCoeffCentrale1 = new BufferedReader(new FileReader(coeffCentrale1File));
			BufferedReader buffCoeffCentrale2 = new BufferedReader(new FileReader(coeffCentrale2File));
			BufferedReader buffCoeffCentrale3 = new BufferedReader(new FileReader(coeffCentrale3File));
			BufferedReader buffCoeffCentrale4 = new BufferedReader(new FileReader(coeffCentrale4File));
			
			try {
				String line;
				int i = 0;
				// Lecture du fichier des demandes ligne par ligne. Cette boucle se termine
				// quand la m�thode retourne la valeur null.
				while ((line = buffDemandes.readLine()) != null && i < 100) {
					StringTokenizer tokenizer = new StringTokenizer(line, ";");
					int j = 0;
					// Tant qu'il y a des tokens on les parcourt
					while(tokenizer.hasMoreTokens())
					{
						String token = tokenizer.nextToken();
						demandesTmp[i][j] = Double.parseDouble(token);
						j++;
					}
					i++;
				}
				
				i = 0;
				// Lecture du fichier des coefficients de la centrale 1 ligne par ligne. Cette boucle se termine
				// quand la m�thode retourne la valeur null.
				while ((line = buffCoeffCentrale1.readLine()) != null) {
					// La premi�re ligne n'a pas de donn�es
					if(i > 0)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						if(tokenizer.hasMoreTokens())
						{
							tokenizer.nextToken();
							int j = 0;
							// Tant qu'il y a des tokens on les parcourt
							while(tokenizer.hasMoreTokens())
							{
								String token = tokenizer.nextToken();
								facteursTmp[i-1][0][j] = Double.parseDouble(token);
								j++;
							}
						}
					}
					i++;
				}
				
				i = 0;
				// Lecture du fichier des coefficients de la centrale 2 ligne par ligne. Cette boucle se termine
				// quand la m�thode retourne la valeur null.
				while ((line = buffCoeffCentrale2.readLine()) != null) {
					// La premi�re ligne n'a pas de donn�es
					if(i > 0)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						if(tokenizer.hasMoreTokens())
						{
							tokenizer.nextToken();
							int j = 0;
							// Tant qu'il y a des tokens on les parcourt
							while(tokenizer.hasMoreTokens())
							{
								String token = tokenizer.nextToken();
								facteursTmp[i-1][1][j] = Double.parseDouble(token);
								j++;
							}
						}
					}
					i++;
				}
				
				i = 0;
				// Lecture du fichier des coefficients de la centrale 3 ligne par ligne. Cette boucle se termine
				// quand la m�thode retourne la valeur null.
				while ((line = buffCoeffCentrale3.readLine()) != null) {
					// La premi�re ligne n'a pas de donn�es
					if(i > 0)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						if(tokenizer.hasMoreTokens())
						{
							tokenizer.nextToken();
							int j = 0;
							// Tant qu'il y a des tokens on les parcourt
							while(tokenizer.hasMoreTokens())
							{
								String token = tokenizer.nextToken();
								facteursTmp[i-1][2][j] = Double.parseDouble(token);
								j++;
							}
						}
					}
					i++;
				}
				
				i = 0;
				// Lecture du fichier des coefficients de la centrale 4 ligne par ligne. Cette boucle se termine
				// quand la m�thode retourne la valeur null.
				while ((line = buffCoeffCentrale4.readLine()) != null) {
					// La premi�re ligne n'a pas de donn�es
					if(i > 0)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						if(tokenizer.hasMoreTokens())
						{
							tokenizer.nextToken();
							int j = 0;
							// Tant qu'il y a des tokens on les parcourt
							while(tokenizer.hasMoreTokens())
							{
								String token = tokenizer.nextToken();
								facteursTmp[i-1][3][j] = Double.parseDouble(token);
								j++;
							}
						}
					}
					i++;
				}

				
				
				// Cr�ation des sc�narios
				for(int scenario=0; scenario<nbScenarios; scenario++)
				{
					scenarios[scenario] = new ScenarioRecours(facteursTmp[scenario], demandesTmp[scenario], 1/(double)nbScenarios);
				}
				
				
			} finally {
				// dans tous les cas, on ferme nos flux
				buffDemandes.close();
				buffCoeffCentrale1.close();
				buffCoeffCentrale2.close();
				buffCoeffCentrale3.close();
				buffCoeffCentrale4.close();
			}
		} catch (IOException ioe) {
			// erreur de fermeture des flux
			System.out.println("Erreur --" + ioe.toString());
		}
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
	
	
	public static void main(String[] args)
	{
		DataRecours data = new DataRecours("Data/Donn�es_Recours_parametres_hydraulique.csv", "Data/Donn�es_Recours_capacite_max.csv", "Data/Donn�es_Recours_scenarios_demande.csv", "Data/Donn�es_Recours_scenarios_coeff_dispo_centrale1.csv", "Data/Donn�es_Recours_scenarios_coeff_dispo_centrale2.csv", "Data/Donn�es_Recours_scenarios_coeff_dispo_centrale3.csv", "Data/Donn�es_Recours_scenarios_coeff_dispo_centrale4.csv");
		for(ScenarioRecours scenario : data.getScenarios())
		{
			double test = scenario.getPaliersPeriodeCentrale(0, 0);
			System.out.println(test);
		}
	}
}
