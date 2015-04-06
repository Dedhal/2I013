package cellularobject;

public class Lave extends Cell {

	protected double niveau_lave;
	protected int vie;
	protected int tmp;
	public Lave(int x, int y, double d) {
		super(x,y);
		niveau_lave=d;
		hauteur=hauteur+niveau_lave;
		color[0] = 0.3f;
		color[1] = 0.1f;
		color[2] = 0.1f;
		vie=5;
		tmp=0;
	}
	
	public double GetHSansLave(){
		
		return hauteur-niveau_lave;
	}
	
	public double GetNiveauLave(){
		
		return niveau_lave;
	}
	
	
	public int GetVie(){
		return vie;
	}
	
	public void ResetVie(){
		vie = 5;
	}
	
	public int Gettmp(){
		return tmp;
		
	}
	
	public void SetNiveauLave(double d){
		hauteur=hauteur-niveau_lave;
		niveau_lave=niveau_lave+d;
		hauteur=hauteur+niveau_lave;
		
	}

	
	public void step(){
			vie--;
			tmp=(tmp+1)%3;
											
	}

}
