package userInterface;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class AProposAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFenetre fenetre;
	
	public AProposAction(MainFenetre fenetre, String texte){
		super(texte);
		
		this.fenetre = fenetre;
	}
	
	public void actionPerformed(ActionEvent e) { 
		JOptionPane.showMessageDialog(fenetre, "Ce logiciel a �t� con�u dans le cadre d'un projet" +
				" de programmation stochastique \n en 5e ann�e de Polytech'Paris-Sud." +
				"\n\n Concepteurs du logiciel : Fabien BINI, Nathana�l MASRI et Nicolas Poirier" +
				"\n Responsables du projet : Abdel LISSER et Riadh ZORGATI");
	} 
}