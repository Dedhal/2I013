package cellularobject;

public class Cell {
	protected int x;
	protected int y;
	protected float[] color=new float[3];
	protected int Statue;
	
	public Cell(int _x,int _y) {
		x=_x;
		y=_y;		
		Statue = 0;
		
	}

	public void SetCellColor(float f){
		color[0] = f;
		color[1] = 0.9f + 0.1f * f;
		color[2] = f;
	
	}
	
	public int getStatut()
	{
		return Statue;
	}
	
	public float[] GetColor(){					
		return color;
	}
	
	public void step(){
		
		
	}
	
	
	
}
