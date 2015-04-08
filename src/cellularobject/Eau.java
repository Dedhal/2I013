package cellularobject;

public class Eau extends Cell {
	double niveau_eau;
	public Eau(int x,int y,double eauTmp) {
		super(x,y);
		niveau_eau=eauTmp;
		hauteur=hauteur+niveau_eau;
		color[0] = 0.3f;
		color[1] = 0.3f;
		color[2] = 0.7f;
	}

	public double GetNiveauEau(){
		
		return niveau_eau;
	}

	
	public void SetNiveauEau(double eauTmp){		
		hauteur=hauteur-niveau_eau;
		niveau_eau=niveau_eau+eauTmp;
		hauteur=hauteur+niveau_eau;
	}
	
	public double GetHSansEau(){
		return hauteur-niveau_eau;
	}
	
	
}