package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


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
	private double probabilite;
	/** Les paliers de chaque centrales thermiques */
	private double[][] paliers;
	
	public final int nbScenarios = 100;
	public final int nbTrajectoires = 8;
	public final int[] nbPaliers = {3, 4, 4, 5};
	public final int nbPeriodes = 7;
	public final int nbCentrales = 4;
	
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
		
		scenarios = new ScenarioBinaire[nbScenarios];
		trajectoires = new double[nbTrajectoires];
		this.probabilite = probabilite;
		paliers = new double[nbCentrales][];
		
		for(int i=0; i<nbCentrales; i++)
			paliers[i] = new double[nbPaliers[i]];
			
		double[][] demandesTmp = new double[nbScenarios][nbPeriodes];
		double[][][][] productionsTmp = new double[nbScenarios][nbCentrales][nbPeriodes][];
		
		for(int i=0; i<nbScenarios; i++)
		{
			for(int j=0; j<nbPeriodes; j++)
			{
				productionsTmp[i][0][j] = new double[nbPaliers[0]];
				productionsTmp[i][1][j] = new double[nbPaliers[1]];
				productionsTmp[i][2][j] = new double[nbPaliers[2]];
				productionsTmp[i][3][j] = new double[nbPaliers[3]];
			}
		}

		try{
			BufferedReader buffDemandes = new BufferedReader(new FileReader(demandesFile));
			BufferedReader buffCoeffCentrale1 = new BufferedReader(new FileReader(coeffCentrale1File));
			BufferedReader buffCoeffCentrale2 = new BufferedReader(new FileReader(coeffCentrale2File));
			BufferedReader buffCoeffCentrale3 = new BufferedReader(new FileReader(coeffCentrale3File));
			BufferedReader buffCoeffCentrale4 = new BufferedReader(new FileReader(coeffCentrale4File));
			BufferedReader buffTrajectoires = new BufferedReader(new FileReader(trajectoiresFile));

			try {
				String line;
				int i = 0;
				// Lecture du fichier des demandes ligne par ligne. Cette boucle se termine
				// quand la m�thode retourne la valeur null.
				while ((line = buffDemandes.readLine()) != null && i < nbScenarios) {
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
					if(i == 1)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						tokenizer.nextToken();
						int j = 0;
						// On parcourt les n paliers
						while(tokenizer.hasMoreTokens() && j < nbPaliers[0])
						{
							String token = tokenizer.nextToken();
							paliers[0][j] = Double.parseDouble(token);
							j++;
						}
					}
					else if(i > 2)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						tokenizer.nextToken();
						int j = 0;
						// Tant qu'il y a des tokens on les parcourt
						while(tokenizer.hasMoreTokens())
						{
							String token = tokenizer.nextToken();
							productionsTmp[i-3][0][j/nbPaliers[0]][j%nbPaliers[0]] = Double.parseDouble(token);
							j++;
						}
					}
					i++;
				}
				
				i = 0;
				// Lecture du fichier des coefficients de la centrale 2 ligne par ligne. Cette boucle se termine
				// quand la m�thode retourne la valeur null.
				while ((line = buffCoeffCentrale2.readLine()) != null) {
					if(i == 1)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						tokenizer.nextToken();
						int j = 0;
						// On parcourt les n paliers
						while(tokenizer.hasMoreTokens() && j < nbPaliers[1])
						{
							String token = tokenizer.nextToken();
							paliers[1][j] = Double.parseDouble(token);
							j++;
						}
					}
					else if(i > 2)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						tokenizer.nextToken();
						int j = 0;
						// Tant qu'il y a des tokens on les parcourt
						while(tokenizer.hasMoreTokens())
						{
							String token = tokenizer.nextToken();
							productionsTmp[i-3][1][j/nbPaliers[1]][j%nbPaliers[1]] = Double.parseDouble(token);
							j++;
						}
					}
					i++;
				}
				
				i = 0;
				// Lecture du fichier des coefficients de la centrale 3 ligne par ligne. Cette boucle se termine
				// quand la m�thode retourne la valeur null.
				while ((line = buffCoeffCentrale3.readLine()) != null) {
					if(i == 1)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						tokenizer.nextToken();
						int j = 0;
						// On parcourt les n paliers
						while(tokenizer.hasMoreTokens() && j < nbPaliers[2])
						{
							String token = tokenizer.nextToken();
							paliers[2][j] = Double.parseDouble(token);
							j++;
						}
					}
					else if(i > 2)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						tokenizer.nextToken();
						int j = 0;
						// Tant qu'il y a des tokens on les parcourt
						while(tokenizer.hasMoreTokens())
						{
							String token = tokenizer.nextToken();
							productionsTmp[i-3][2][j/nbPaliers[2]][j%nbPaliers[2]] = Double.parseDouble(token);
							j++;
						}
					}
					i++;
				}
				
				i = 0;
				// Lecture du fichier des coefficients de la centrale 4 ligne par ligne. Cette boucle se termine
				// quand la m�thode retourne la valeur null.
				while ((line = buffCoeffCentrale4.readLine()) != null) {
					if(i == 1)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						tokenizer.nextToken();
						int j = 0;
						// On parcourt les n paliers
						while(tokenizer.hasMoreTokens() && j < nbPaliers[3])
						{
							String token = tokenizer.nextToken();
							paliers[3][j] = Double.parseDouble(token);
							j++;
						}
					}
					else if(i > 2)
					{
						StringTokenizer tokenizer = new StringTokenizer(line, ";");
						// Le premier token n'est pas int�ressant
						tokenizer.nextToken();
						int j = 0;
						// Tant qu'il y a des tokens on les parcourt
						while(tokenizer.hasMoreTokens())
						{
							String token = tokenizer.nextToken();
							productionsTmp[i-3][3][j/nbPaliers[3]][j%nbPaliers[3]] = Double.parseDouble(token);
							j++;
						}
					}
					i++;
				}

				// Lecture du fichier des trajectoires hydrauliques.
				// La premi�re ligne n'est pas int�ressante
				buffTrajectoires.readLine();
				line = buffTrajectoires.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, ";");
				// Le premier token n'est pas int�ressant
				tokenizer.nextToken();
				int j = 0;
				// Tant qu'il y a des tokens on les parcourt
				while(tokenizer.hasMoreTokens())
				{
					String token = tokenizer.nextToken();
					trajectoires[j] = Double.parseDouble(token);
					j++;
				}
				
				// Cr�ation des sc�narios
				for(int scenario=0; scenario<nbScenarios; scenario++)
				{
					scenarios[scenario] = new ScenarioBinaire(productionsTmp[scenario], demandesTmp[scenario], 1/(double)nbScenarios);
				}			
			} finally {
				// dans tous les cas, on ferme nos flux
				buffDemandes.close();
				buffCoeffCentrale1.close();
				buffCoeffCentrale2.close();
				buffCoeffCentrale3.close();
				buffCoeffCentrale4.close();
				buffTrajectoires.close();
			}
		} catch (IOException ioe) {
			// erreur de fermeture des flux
			System.out.println("Erreur --" + ioe.toString());
		}
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
	public ScenarioBinaire getScenario(int scenario)
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
	
	/**
	 * Retourne la probabilit� voulue
	 * @return la probabilit� voulue
	 */
	public double getProbabilite()
	{
		return probabilite;
	}
	
	public static void main(String[] args)
	{
		DataBinaire data = new DataBinaire(98, "Data/Donn�es_Recuit_demandes.csv", "Data/Donn�es_Recuit_paliers1.csv", "Data/Donn�es_Recuit_paliers2.csv", "Data/Donn�es_Recuit_paliers3.csv", "Data/Donn�es_Recuit_paliers4.csv", "Data/Donn�es_Recuit_trajectoire_hydro.csv", "Data/Donn�es_Recuit_parametres_hydro.csv", "Data/Donn�es_Recuit_capacit�.csv");
		for(ScenarioBinaire scenario : data.getScenarios())
		{
			double[] test = scenario.getPaliersPeriodeCentrale(0, 0);
			System.out.println(test.length);
			for(double palier : test)
			{
				System.out.println(palier);
			}
		}
	}

	public double getPalier(int centrale, int palier) {
		return paliers[centrale][palier];
	}
}
