package manager.solveurs.RS;

import data.solution.Solution;
import manager.solveurs.Solveur;

/**
 * Impl�mentation g�n�rale du recuit simul�.
 * @author Fabien BINI & Nathana�l MASRI & Nicolas POIRIER
 */
public abstract class RecuitSimule implements Solveur
{
	/** La solution courante du recuit simul�. */
	protected Solution solutionCourante;
	/** La meilleure solution trouv�e pour le moment. */
	protected Solution meilleureSolution;
	/** Le facteur de d�croissance de la temp�rature du recuit. */
	protected double facteurDecroissance;
	/** La temp�rature du recuit. */
	protected double temperature;
	/** La valeur de la meilleure solution trouv�e. */
	protected double meilleureF;
	/** Le taux d'acceptation de solutions co�teuses accept�es par le recuit � la temp�rature initiale. */
	protected double tauxAcceptation;
	
	/**
	 * Construit un recuit simul�.
	 * @param facteurDecroissance le facteur de d�croissance de la temp�rature.
	 */
	protected RecuitSimule(double facteurDecroissance, double tauxAcceptation)
	{
		this.facteurDecroissance = facteurDecroissance;
		this.tauxAcceptation = tauxAcceptation;
	}
	
	/**
	 * D�marre le recuit simul�.
	 * Impl�mente le coeur de l'algorithme du recuit simul� commun � tous les probl�mes.
	 */
	public void lancer()
	{
		Solution solutionVoisine;
		double delta;
		
		// Initilaisation du recuit
		solutionCourante.solutionInitiale();
		initialiserTemperature();
		meilleureF = solutionCourante.fonctionObjectif();
		meilleureSolution = solutionCourante.clone();
		
		// Les paliers de temp�rature
		while(testerCondition1())
		{
			System.out.println(temperature);
			// Les it�rations par palier
			while(testerCondition2())
			{
				// On cherche une solution dans le voisinage
				solutionVoisine = voisin();
				delta = solutionCourante.deltaF(solutionVoisine);
				
				// Si la solution du voisinage est meilleure que la solution courante
				if(delta <= 0)
				{
					// La solution voisine devient la solution courante
					solutionCourante = solutionVoisine.clone();
					
					// Si la solution voisine est meilleure que la meilleure trouv�e pour le moment
					// Elle devient la meilleure solution
					if(solutionCourante.fonctionObjectif() < meilleureF)
					{
						meilleureF = solutionCourante.fonctionObjectif();
						meilleureSolution = solutionCourante.clone();
					}
				}
				// Si la solution voisine n'am�liore pas la solution courante
				// Elle peut �tre accept�
				else
				{
					double p = Math.random();
					if(p <= Math.exp(-delta/temperature))
					{
						solutionCourante = solutionVoisine.clone();
					}
				}
			}
			decroitreTemperature();
		}
	}
	
	/**
	 * Initialise la temp�rature du recuit.
	 * La temp�rature initiale doit accepter un certain nombre de solutions co�teuses.
	 * Ce taux est fix� par l'utilisateur.
	 */
	private void initialiserTemperature()
	{
		Solution solutionVoisine;
		double taux = 0;
		temperature = 1000;
		
		// Tant que le taux d'acceptation n'est pas celui voulu
		// On multiplie la temp�rature par 2
		do
		{
			int nbCouteuses = 0;
			int nbCouteusesAcceptees = 0;
			
			// On fait 40 tirages de solutions voisines
			for(int i=0; i<40; i++)
			{
				solutionVoisine = voisin();
				
				// Si on a une solution co�teuse, on regarde si elle est accept�e
				if(solutionCourante.deltaF(solutionVoisine) > 0)
				{
					double p = Math.random();
					nbCouteuses++;
					
					if(p <= Math.exp(-solutionCourante.deltaF(solutionVoisine)/temperature))
					{
						nbCouteusesAcceptees++;
					}
				}
			}
			
			taux = (double) nbCouteusesAcceptees / (double) nbCouteuses;
			if(taux < tauxAcceptation)
			{
				temperature *= 2;
			}
		} while(taux < tauxAcceptation);
	}
	
	/**
	 * D�croit la temp�rature du recuit.
	 * La fonction utilis�e est : f(t) = alpha x t avec alpha fix�e par l'utilisateur.
	 */
	private void decroitreTemperature()
	{
		temperature *= facteurDecroissance;
	}
	
	/**
	 * S�lectionne une solution dans le voisinage de la solution courante.
	 * @return une solution voisine de la solution courante.
	 */
	protected abstract Solution voisin();
	
	/**
	 * Teste si le recuit est arriv� � sa temp�rature minimale.
	 * @return true si le recuit peut passer au palier de temp�rature suivant, false sinon.
	 */
	protected abstract boolean testerCondition1();
	
	/**
	 * Teste si le recuit doit continuer � un palier de temp�rature.
	 * @return true si le recuit doit continuer � ce palier de temp�rature, false sinon.
	 */
	protected abstract boolean testerCondition2();
	
	public Solution getSolution()
	{
		return meilleureSolution;
	}
}
