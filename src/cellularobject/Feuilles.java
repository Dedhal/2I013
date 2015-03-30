package cellularobject;

public class Feuilles extends Cell {
	boolean feu;
	
	public Feuilles(int x, int y) {
		super(x,y);
		Statue=21;
		feu=false;
		color[0]=0.9f;
		color[1]=0.9f;
		color[2]=0.f ;
	}
	
	public void SetFeu(boolean b){
		feu=b;
		if(b){
			color[0] = 0.9f;
			color[1] = 0.f;
			color[2] = 0.f;
		}
		else{
			color[0] = 0.9f;
			color[1] = 0.9f;
			color[2] = 0.f;
		}
	}

	public boolean GetFeu(){
		return feu;
	}


}
