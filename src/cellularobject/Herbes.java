package cellularobject;

public class Herbes extends Cell {
	public boolean feu;
	public int vie;
	public int vieMax;
	
	public Herbes(int x,int y) {
		super(x,y);
		Statue=4;
		vie=3;
		vieMax=3;
		feu=false;
		color[0] = 0.5f;
		color[1] = 0.5f;
		color[2] = 0.f;	
		
	}
	
	public void SetFeu(boolean b){
		feu=b;
		if(b){
			color[0] = 0.5f;
			color[1] = 0.1f;
			color[2] = 0.1f;
		}
		else{
			color[0] = 0.5f;
			color[1] = 0.5f;
			color[2] = 0.f;
		}
	}

	public boolean GetFeu(){
		return feu;
	}
	
	
	public int GetVie(){
		return vie;
	}


	public void step(){
		if(GetFeu()){
			if(Math.random()<0.7) vie--;
			if(Math.random()<0.1) SetFeu(false);
		}
		else{
			if((vie<vieMax)&&(Math.random()<0.1)) vie++;
		}
	}

}

