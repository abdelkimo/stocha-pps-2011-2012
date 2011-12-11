package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import manager.solveurs.PL.CplexEnergieBinaireRelaxe;
import manager.solveurs.RS.RSEnergie;
import data.DataBinaire;

/**
 * 
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 * 
 *         Fen�tre de dialogue demandant � l'utilisateur d'entrer les param�tres
 *         � utiliser pour le traitement par RS
 */
public class ZDialogProbaBinaire extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labIter, labTempF, labTaux, labDec;
	private JTextField iter, tempF, taux, dec;
	private CplexEnergieBinaireRelaxe solveur;
	private String fileName;
	private double probabilite;
	private JTextField proba;
	private JLabel labProba;

	/**
	 * Constructeur
	 * 
	 * @param parent
	 * @param title
	 * @param modal
	 */
	public ZDialogProbaBinaire(MainFenetre parent, String title, boolean modal,
			String fileName) {
		super(parent, title, modal);
		this.setSize(350, 110);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.initComponent();
		parent.setEtat(MainFenetre.State.FICHIER_CHOISI);
		parent.updateVisibility();
		solveur = null;
		this.fileName = fileName;
	}

	/**
	 * Initialise le contenu de la bo�te
	 */
	private void initComponent() {

		JPanel panProba = new JPanel();
		panProba.setBackground(Color.white);
		panProba.setPreferredSize(new Dimension(300, 40));
		proba = new JTextField();
		proba.setPreferredSize(new Dimension(50, 25));
		proba.setText("0.98");
		labProba = new JLabel("probabilit� des sc�narios :");
		panProba.add(labProba);
		panProba.add(proba);

		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panProba);

		JPanel control = new JPanel();
		control.setBackground(Color.white);
		JButton okBouton = new JButton("OK");
		JButton cancelBouton = new JButton("Annuler");

		// Cr�ation du bouton "OK" dont l'ex�cution v�rifie que les param�tres
		// entr�s sont corrects
		// S'ils ne le sont pas un message d'information est transmis �
		// l'utilisateur
		okBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String rep = new String();
				boolean valide = true;

				try {
					probabilite = Double.parseDouble(proba.getText());
					if (probabilite > 1 || probabilite < 0) {
						valide = false;
						rep += "le champ \"probabilit� des sc�narios\" n'est pas valide.\n";
					}

				} catch (java.lang.NumberFormatException e) {
					valide = false;
					rep += "le champ \"probabilit� des sc�narios\" n'est pas valide.\n";
				}

				if (valide) {
					System.out.println("d�but lecture des donn�es");

					DataBinaire data = new DataBinaire(probabilite, fileName
							+ "Donn�es_Recuit_demandes.csv", fileName
							+ "Donn�es_Recuit_paliers1.csv", fileName
							+ "Donn�es_Recuit_paliers2.csv", fileName
							+ "Donn�es_Recuit_paliers3.csv", fileName
							+ "Donn�es_Recuit_paliers4.csv", fileName
							+ "Donn�es_Recuit_trajectoire_hydro.csv", fileName
							+ "Donn�es_Recours_parametres_hydraulique.csv",
							fileName + "Donn�es_Recours_capacite_max.csv");
					System.out.println("fin lecture des donn�es");
					setVisible(false);
					solveur = new CplexEnergieBinaireRelaxe(data);
					System.out.println("solveur construit");
				} else {
					JOptionPane.showMessageDialog(null, rep, "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		cancelBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		control.add(okBouton);
		control.add(cancelBouton);

		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}

	public CplexEnergieBinaireRelaxe getSolveur() {
		return solveur;
	}

}
