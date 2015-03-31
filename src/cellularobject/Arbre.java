package cellularobject;

public class Arbre extends Cell {

	protected int age;
	protected boolean feu;
	protected int vie;
	protected int vieMax;
	protected int cendre;
	protected int h;
	
	public Arbre(int x,int y) {
		super(x,y);
		age=0;
		vieMax=10;
		vie=7;
		cendre=3;
		Statue=1;
		feu=false;
		color[0] = 0.f;
		color[1] = 0.3f;
		color[2] = 0.f;
	}
	
	public void SetFeu(boolean b){
		feu=b;
		if(b){
			color[0] = 1.f;
			color[1] = 0.f;
			color[2] = 0.f;
		}
		else{
			color[0] = 0.f;
			color[1] = 0.3f;
			color[2] = 0.f;			
		}
	}
	
	public int GetVie(){
		return vie;
	}

	public boolean GetFeu(){
		return feu;
	}
	
	public int GetCendre(){
		return cendre;
	}
	public void step(){
		age++;
		if(GetVie()==0){
			feu=false;
			color[0] = 0.f;
			color[1] = 0.f;
			color[2] = 0.f;
			cendre--;
		}
		else if(GetFeu()){
			if(Math.random()<0.5) vie--;
			if(Math.random()<0.01) SetFeu(false);
		}
		else if(vie<vieMax&&Math.random()<0.1) vie++;

			
		
		
	}

	public int GetVieMax() {
		return vieMax;
	}


}
