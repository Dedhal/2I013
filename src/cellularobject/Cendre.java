package cellularobject;

public class Cendre extends Cell {
	protected int cendre;
	public Cendre(int x,int y) {
		super(x,y);
		cendre=20;
		color[0] = 0.f;
		color[1] = 0.f;
		color[2] = 0.f;
	}
	
	public int GetCendre(){
		return cendre;
	}
	
	public void step(){
		cendre--;
		color[0] = 0.5f-((float)cendre/10);
		color[1] = 0.5f-((float)cendre/10);
		color[2] = 0.5f-((float)cendre/10);
	}
	
}
