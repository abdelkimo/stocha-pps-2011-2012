package userInterface;

import manager.solveurs.PL.CplexEnergieBinaire;
import manager.solveurs.PL.CplexEnergieBinaireRelaxe;
import data.DataBinaire;

/**
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 * 
 *         Fen�tre de dialogue demandant � l'utilisateur d'entrer les param�tres
 *         � utiliser pour le traitement par RS
 */
public class ZDialogProbaBinaire {

	private DataBinaire data;

	/**
	 * Constructeur
	 * 
	 * @param parent
	 * @param title
	 * @param modal
	 */
	public ZDialogProbaBinaire(MainFenetre parent, String fileName) {

		data = new DataBinaire(fileName + "Donn�es_Recuit_demandes.csv",
				fileName + "Donn�es_Recuit_paliers1.csv", fileName
						+ "Donn�es_Recuit_paliers2.csv", fileName
						+ "Donn�es_Recuit_paliers3.csv", fileName
						+ "Donn�es_Recuit_paliers4.csv", fileName
						+ "Donn�es_Recuit_trajectoire_hydro.csv", fileName
						+ "Donn�es_Recours_parametres_hydraulique.csv",
				fileName + "Donn�es_Recours_capacite_max.csv");
		parent.setEtat(MainFenetre.State.FICHIER_CHOISI);
		parent.updateVisibility();
	}

	public CplexEnergieBinaireRelaxe getSolveurRelaxe() {
		return new CplexEnergieBinaireRelaxe(data);
	}

	public CplexEnergieBinaire getSolveurBinaire() {
		return new CplexEnergieBinaire(data);
	}

}
