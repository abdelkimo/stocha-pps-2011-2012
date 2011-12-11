package userInterface;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;

import manager.solveurs.Solveur;
import manager.solveurs.PL.CplexEnergieBinaireRelaxe;
import manager.solveurs.PL.CplexEnergieRecours;
import data.DataBinaire;
import data.DataRecours;
import data.solution.Solution;

public class Optimise extends AbstractAction {
	/**
         * 
         */
	private static final long serialVersionUID = 1L;
	private MainFenetre fenetre;
	private Solveur solveur;
	private String fileName;

	public Optimise(MainFenetre fenetre, String texte) {
		super(texte);
		this.fenetre = fenetre;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void actionPerformed(ActionEvent e) {

		fenetre.setEtat(MainFenetre.State.FICHIER_CHOISI);
		String methodeName = (String) fenetre.getChoixMethode()
				.getSelectedItem();

		if (methodeName.equals("mod�le probabiliste")) {
			
			
			ZDialogParamDeter zd = new ZDialogParamDeter(fenetre,
					"Choix des param�tres", true, fileName);
			zd.setVisible(true);
		} else {
			// on normalise les r�pertoires
			if (fileName.charAt(fileName.length() - 1) != File.separatorChar)
				fileName += File.separator;

			if (methodeName.equals("recuit simul�")) {

				ZDialogParamRS zd = new ZDialogParamRS(fenetre,
						"Choix des param�tres", true, fileName);
				zd.setVisible(true);
				solveur = zd.getSolveur();
			} else if (methodeName.equals("mod�le avec recours")) {
				DataRecours data = new DataRecours(
						fileName + "Donn�es_Recours_parametres_hydraulique.csv",
						fileName + "Donn�es_Recours_capacite_max.csv",
						fileName + "Donn�es_Recours_scenarios_demande.csv",
						fileName
								+ "Donn�es_Recours_scenarios_coeff_dispo_centrale1.csv",
						fileName
								+ "Donn�es_Recours_scenarios_coeff_dispo_centrale2.csv",
						fileName
								+ "Donn�es_Recours_scenarios_coeff_dispo_centrale3.csv",
						fileName
								+ "Donn�es_Recours_scenarios_coeff_dispo_centrale4.csv");
				solveur = new CplexEnergieRecours(data);
			} else if (methodeName.equals("relaxation du binaire")) {

				ZDialogProbaBinaire proba = new ZDialogProbaBinaire (fenetre,
						"Choix des param�tres", true, fileName);
				proba.setVisible(true);
				solveur = proba.getSolveur();
			}
			if(solveur != null){	// si l'utilisateur a annul� l'action
				System.out.println("d�but des calculs");
				fenetre.setDescription("calcul en cours");
				solveur.lancer();
				System.out.println("fin des calculs");
				fenetre.setSolution(solveur.getSolution());
				fenetre.setDescription("calcul termin�");
				fenetre.setEtat(MainFenetre.State.RESULTAT_CALCULE);
			}
		}
		fenetre.updateVisibility();

	}

	public Solveur getSolveur() {
		return solveur;
	}

	public void setSolveur(Solveur solveur) {
		this.solveur = solveur;
	}
}
