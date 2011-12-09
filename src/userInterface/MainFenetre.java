package userInterface;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.solution.Solution;


public class MainFenetre extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel methodeText;
	private JComboBox choixMethode;
	private JMenuItem ouvrir;
	private JPanel panel;
	private MainFenetre me;
	private JLabel fileName;

	private ActionListener charger;

	private Parametres params;

	private JButton enregistrer;

	private JButton resultat;
	
	public MainFenetre() {
		super();
		params = new Parametres();
		me = this;
		build();// On initialise notre fen�tre
	}

	private void build() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menu1 = new JMenu("Menu");

		JMenuItem suivant = new JMenuItem(new Optimise(this, "Suivant", params));
		menu1.add(suivant);

		//Cr�ation du bouton de chargement de fichier
		ouvrir = new JMenuItem("Charger fichier");
		charger = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//L'affichage pr�sent sur la fen�tre du programme est supprim�
		//		me.getContentPane().removeAll();
			//	me.repaint();
				me.validate();
				
				ZDialogCharger zd = new ZDialogCharger(me, "Fichier de donn�es", true);
				zd.setVisible(true);
			}
		};
		ouvrir.addActionListener(charger);
		//Le bouton de chargement de fichier est plac� dans la barre de menu dans l'onglet "fichier"
		menu1.add(ouvrir);
		menu1.addSeparator();

		JMenuItem quitter = new JMenuItem(new QuitterAction("Quitter"));
		menu1.add(quitter);

		menuBar.add(menu1);

		JMenu menu2 = new JMenu("?");

		JMenuItem aPropos = new JMenuItem(new AProposAction(this, "A propos"));
		menu2.add(aPropos);
		JMenuItem aide = new JMenuItem("aide");
		menu2.add(aide);
		aide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// L'aide est ouverte dans le navigateur par d�faut de
					// l'utilisateur
					Desktop.getDesktop().browse(new URI("index.html"));
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Le fichier d'aide est introuvable", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} catch (URISyntaxException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"L'aide n'est pas disponible pour votre OS",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		menuBar.add(menu2);

		setJMenuBar(menuBar);

		setTitle("Programmation stochastique"); // On donne un titre � l'application
		setSize(600, 300); // On donne une taille � notre fen�tre
		setLocationRelativeTo(null); // On centre la fen�tre sur l'�cran
		setResizable(false); // On interdit la redimensionnement de la fen�tre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit �
														// l'application de se
														// fermer lors du clic
														// sur la croix
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane() {
		panel = new JPanel();
		panel.setBackground(Color.white);

		
		GridLayout layout = new GridLayout(2, 3);
		layout.setVgap(8);
		panel.setLayout(layout);
		
		methodeText = new JLabel("methode de r�solution : ");
		Object[] listeMethode = new Object[]{"mod�le probabiliste", "mod�le avec recours", 
				"recuit simul�", "relaxation du binaire"};
		choixMethode = new JComboBox(listeMethode);
		JPanel configurePanel = new JPanel();
		GridLayout configureLayout = new GridLayout(2,1);
		configurePanel.setLayout(configureLayout);
		configurePanel.setBackground(Color.white);
		panel.add(configurePanel);
		JPanel methodePanel = new JPanel();
		methodePanel.setBackground(Color.white);
		methodePanel.add(methodeText);
		methodePanel.add(choixMethode);
		configurePanel.add(methodePanel);
		JPanel fichierPanel = new JPanel();
		fichierPanel.setBackground(Color.white);
		panel.add(fichierPanel);
		JButton fileButton = new JButton("charger fichier");
		fileButton.addActionListener(charger);
		fichierPanel.add(fileButton);
		fileName = new JLabel();
		fichierPanel.add(fileName);
		
		JPanel suite = new JPanel();
		
		enregistrer = new JButton("enregister le r�sultat");
		enregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ZDialogSave(me,"enregistrer le r�sultat", params).setVisible(true);
			}
		});
	//	enregistrer.setVisible(false);
		
		suite = new JPanel();
		suite.setBackground(Color.white);
		suite.add(enregistrer);
		panel.add(suite);

		suite = new JPanel();
		suite.setBackground(Color.white);
		FlowLayout l = new FlowLayout();
		l.setHgap(30);
		suite.setLayout(l);
		panel.add(suite);
		Solution s = null;
		resultat = new JButton(new AfficheResultat(this,
				"voir le r�sultat", s));
	//	resultat.setVisible(false);
		suite.add(resultat);

		suite = new JPanel();
		suite.setBackground(Color.white);
		JButton suivant = new JButton(new Optimise(this,
				"suivant", params));
		suite.setBackground(Color.white);
		suite.add(suivant);
		panel.add(suite);
		

		suite = new JPanel();
		suite.setBackground(Color.white);
		JLabel cout = new JLabel("co�t optimal : 89 083");
		suite.add(cout);
		panel.add(suite);
		
		return panel;
	}

	public JComboBox getChoixMethode() {
		return choixMethode;
	}

	// lire les donn�es depuis un fichier
	public void chargerDonnees(String file) throws FileNotFoundException{
		fileName.setText(file);
	}


	
}