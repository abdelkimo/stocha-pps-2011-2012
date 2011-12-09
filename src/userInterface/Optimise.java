package userInterface;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;


public class Optimise extends AbstractAction {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private MainFenetre fenetre;
		private Parametres params;
        
        public Optimise(MainFenetre fenetre, String texte, Parametres params){
                super(texte);
                this.fenetre = fenetre;
                this.params = params;
        }
        
        public void actionPerformed(ActionEvent e) { 
        	

            fenetre.setEtat(MainFenetre.State.FICHIER_CHOISI);
        	String methodeName = (String) fenetre.getChoixMethode().getSelectedItem();
        	if(methodeName.equals("recuit simul�")){
        		ZDialogParamRS zd = new ZDialogParamRS(fenetre, "Choix des param�tres", true, params);
				zd.setVisible(true);
        		
        	}
        	else if(methodeName.equals("mod�le probabiliste")){
        		ZDialogParamDeter zd = new ZDialogParamDeter(fenetre, "Choix des param�tres", true, params);
				zd.setVisible(true);
        	}
        	else{
        		fenetre.setEtat(MainFenetre.State.RESULTAT_CALCULE);
        	}

            fenetre.updateVisibility();
        	
        } 
}
