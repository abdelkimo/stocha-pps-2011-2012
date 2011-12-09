package userInterface;

import java.awt.event.ActionEvent;

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
	private Parametres params;
	private Solveur solveur;
	private Solution solution;
	private String fileName;

	public Optimise(MainFenetre fenetre, String texte, Parametres params) {
		super(texte);
		this.fenetre = fenetre;
		this.params = params;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void actionPerformed(ActionEvent e) {

		fenetre.setEtat(MainFenetre.State.FICHIER_CHOISI);
		String methodeName = (String) fenetre.getChoixMethode()
				.getSelectedItem();
		if (methodeName.equals("recuit simul�")) {

			DataBinaire data = new DataBinaire(fileName);
			ZDialogParamRS zd = new ZDialogParamRS(fenetre,
					"Choix des param�tres", true, params, data);
			zd.setVisible(true);
			solveur = zd.getSolveur();

		} else if (methodeName.equals("mod�le probabiliste")) {
			ZDialogParamDeter zd = new ZDialogParamDeter(fenetre,
					"Choix des param�tres", true, params);
			zd.setVisible(true);
		} else {
			if (methodeName.equals("mod�le avec recours")) {
				DataRecours data = new DataRecours(fileName);
				solveur = new CplexEnergieRecours(data);
			} else if (methodeName.equals("relaxation du binaire")) {
				DataBinaire data = new DataBinaire(fileName);
				solveur = new CplexEnergieBinaireRelaxe(data);
			}
			fenetre.setSolution( solveur.getSolution() );
			fenetre.setEtat(MainFenetre.State.RESULTAT_CALCULE);
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
