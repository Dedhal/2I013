package applications.simpleworld;

import java.util.ArrayList;

import cellularobject.Cell;

public interface PredateurListener {
	public void SeNourrir(Proie cible, Predateur attaquant);
	public void Deplacement(Predateur predateur);
	public ArrayList<Proie> ProximiteProies(Predateur p, ArrayList<Cell> ChampsVision);
	public ArrayList<Predateur> ProximitePredateurs(Predateur p, ArrayList<Cell> ChampsVision);
	public void SeReproduire(Predateur p);
	public void Meurt(Predateur p);
}
