// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package cellularautomata;

public class CellularAutomataInteger extends CellularAutomata {

	protected int Buffer0[][];
	protected int Buffer1[][];
	protected boolean eau[][];
	protected double niveau_eau[][];
	protected double eau_max;
	protected boolean lave[][];
	protected double niveau_lave[][];
	public CellularAutomataInteger ( int __dx , int __dy, boolean __buffering )
	{
		super(__dx,__dy,__buffering );

		Buffer0 = new int[_dx][_dy];
		Buffer1 = new int[_dx][_dy];
		eau = new boolean[_dx][_dy];
		niveau_eau = new double[_dx][_dy];
		lave = new boolean[_dx][_dy];
		niveau_lave = new double[_dx][_dy];
		eau_max = 0;
	    for ( int x = 0 ; x != _dx ; x++ )
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
    			Buffer0[x][y]=0;
    			Buffer1[x][y]=0;
    			eau[x][y]=false;
    			niveau_eau[x][y]=0;
    			lave[x][y]=false;
    			niveau_lave[x][y]=0;
	    	}
	}
	
	public int getCellState ( int __x, int __y )
	{
		checkBounds (__x,__y);
		
		int value;

		if ( buffering == false )
		{
			value = Buffer0[__x][__y];
		}
		else
		{
			if ( activeIndex == 1 ) // read old buffer
			{
				value = Buffer0[__x][__y];
			}
			else
			{
				value = Buffer1[__x][__y];
			}
		}
		
		return value;
	}
	
	public boolean Eau(int __x, int __y){
		checkBounds(__x,__y);
		
		return eau[__x][__y];
		
	}
	
	public double GetNeau(int __x, int __y){
		checkBounds(__x,__y);
		
		return niveau_eau[__x][__y];
				
	}
	
	public boolean Lave(int __x, int __y){
		checkBounds(__x,__y);
		return lave[__x][__y];
	
	}

	public double GetNlave(int __x, int __y){
		checkBounds(__x,__y);
		return niveau_lave[__x][__y];
	
	}
	
	public void SetEau(int __x, int __y, boolean b){
		checkBounds(__x,__y);
		
		eau[__x][__y]=b;
				
	}
	
	public void SetNeau(int __x, int __y, double d){
		checkBounds(__x,__y);
		if (eau_max<d) eau_max=d;
		niveau_eau[__x][__y]=d;
	}
	
	public void SetLave(int __x, int __y, boolean b){
		checkBounds(__x,__y);
		
		lave[__x][__y]=b;
				
	}
	
	public void SetNlave(int __x, int __y, double d){
		checkBounds(__x,__y);
		
		niveau_lave[__x][__y]=d;
	}
	
	public void setCellState ( int __x, int __y, int __value )
	{
		checkBounds (__x,__y);
		
		if ( buffering == false )
		{
			Buffer0[__x][__y] = __value;
		}
		else
		{
			if ( activeIndex == 0 ) // write new buffer
			{
				Buffer0[__x][__y] = __value;
			}
			else
			{
				Buffer1[__x][__y] = __value;
			}
		}
	}
	
	public int[][] getCurrentBuffer()
	{
		if ( activeIndex == 0 || buffering == false ) 
			return Buffer0;
		else
			return Buffer1;		
	}
	
}
