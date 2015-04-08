package applications.simpleworld;

import java.util.ArrayList;

import cellularobject.Cell;

public interface ProieListener {
	public void SeNourrir(int x, int y, Proie p);
	public void Deplacement(Proie p);
	public ArrayList<Proie> ProximiteProies(Proie p, ArrayList<Cell> ChampsVision);
	public ArrayList<Predateur> ProximitePredateurs(Proie p, ArrayList<Cell> ChampsVision);
	public void SeReproduire(Proie p);
	public void Meurt(Proie p);
}
