package cellularobject;

import cellularautomata.CellularAutomataDouble;

public class Cell {
	protected int x;
	protected int y;
	protected float[] color=new float[3];
	protected int Statue;
	protected double hauteur;
	protected static double hauteurM ;
	protected static CellularAutomataDouble cellsHeightValuesCA;
	protected static double neige=0;
	protected static boolean desert=false;
	
	public Cell(CellularAutomataDouble Height,double M){
		cellsHeightValuesCA =Height;
		hauteurM=M;
	}
	
	public Cell(int _x,int _y) {
		x=_x;
		y=_y;		
		Statue = 0;
		hauteur=cellsHeightValuesCA.getCellState(x, y);
		SetCellColor();
	}


	public void SetHauteur(float h){
		hauteur=h;
	}
	public void SetHauteurMax(float h){
		hauteurM=h;
	}
	
	public void SetCellColor(){
		if(!desert){
		float f=(float)((hauteur+neige)/(hauteurM+neige));
		color[0] = f;
		color[1] = 0.9f + 0.1f * f;
		color[2] = f;
		}
		else {
			color[0] = 1f;
			color[1] = 0.9f;
			color[2] = 0.7f;
		}
	
	}
	
	public void SetDesert(){
		desert=(!desert);
		
	}
	public int getStatut()
	{
		return Statue;
	}
	
	public double getNeige(){
		return neige;
	}

	public float[] GetColor(){					
		return color;
	}
	
	public double GetHauteur(){
		return hauteur;
	}
	
	public void step(){
		
		
	}
	
	public void setNeige(double d){
		neige=d;
		SetCellColor();
	}
	
}
