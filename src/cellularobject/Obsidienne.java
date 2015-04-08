package cellularobject;

public class Obsidienne extends Cell {
	private int vie;
	public Obsidienne(int x, int y) {
		super(x,y);
		vie = 15;
		color[0] = 0.f;
		color[1] = 0.f;
		color[2] = 0.f;
				
	}
	
	public int GetVie(){
		return vie;
	}
	
	public void Step(){
		vie--;
	}

}