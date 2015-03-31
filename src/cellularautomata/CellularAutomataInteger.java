// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package cellularautomata;

import worlds.World;
import cellularobject.Cell;
import cellularobject.Arbre;
import cellularobject.Cendre;
import cellularobject.Feuilles;
import cellularobject.Herbes;
import cellularobject.EauProfonde;

public class CellularAutomataInteger extends CellularAutomata {

	protected Cell Buffer0[][];
	protected Cell Buffer1[][];
	protected World world;
	protected double h;//Humidite, taux de combustion spontanne

	public CellularAutomataInteger ( int __dx , int __dy, boolean __buffering, World __world )
	{
		super(__dx,__dy,__buffering );
		this.world = __world;
		Buffer0 = new Cell[_dx][_dy];
		Buffer1 = new Cell[_dx][_dy];
		h=0;
	    for ( int x = 0 ; x != _dx ; x++ )
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
	    		float f=(float)this.world.getCellHeight(x, y)/(float)this.world.getMaxEverHeight();
    			Buffer0[x][y]=new Cell(x,y);
    			Buffer0[x][y].SetCellColor(f);
    			Buffer1[x][y]=new Cell(x,y);
    			Buffer1[x][y].SetCellColor(f);
	    	}
	}
	
	public Cell getCellState ( int __x, int __y )
	{
		checkBounds (__x,__y);
		
		Cell value;

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

	
	public void setCellState ( int __x, int __y, Cell __value )
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
	
	public Cell[][] getCurrentBuffer()
	{
		if ( activeIndex == 0 || buffering == false ) 
			return Buffer0;
		else
			return Buffer1;		
	}
	
	public boolean PlaceLibreArbre(int i,int j){
				
		if(!(getCellState(i, j) instanceof Arbre||
			getCellState( (i+_dx-1)%(_dx) , j ) instanceof Arbre ||
			getCellState( (i+_dx+1)%(_dx) , j ) instanceof Arbre ||
			getCellState( i , (j+_dy+1)%(_dy) ) instanceof Arbre ||			
			getCellState( i , (j+_dy-1)%(_dy) ) instanceof Arbre ||
			getCellState( (i+_dx-1)%(_dx) , (j+_dy+1)%(_dy)) instanceof Arbre ||
			getCellState( (i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Arbre ||
			getCellState( (i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Arbre ||
			getCellState( (i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Arbre)    							
			) return true;
		else return false;
	
	}
	
	
	
	public boolean FeuFeuillesArbre(int i,int j, double d){
			
		if(getCellState((i+_dx-1)%(_dx) , j ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx-1)%(_dx) , j )).GetFeu()&&Math.random()<d) return true;
		if(getCellState((i+_dx+1)%(_dx) , j ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx+1)%(_dx) , j )).GetFeu()&&Math.random()<d) return true;
		if(getCellState(i , (j+_dy+1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState(i , (j+_dy+1)%(_dy) )).GetFeu()&&Math.random()<d) return true;
		if(getCellState(i , (j+_dy-1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState(i , (j+_dy-1)%(_dy) )).GetFeu()&&Math.random()<d) return true;
		if(getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) )).GetFeu()&&Math.random()<d) return true;
		if(getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) )).GetFeu()&&Math.random()<d) return true;
		if(getCellState((i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx-1)%(_dx) , (j+_dy-1)%(_dy) )).GetFeu()&&Math.random()<d) return true;
		if(getCellState((i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx+1)%(_dx) , (j+_dy-1)%(_dy) )).GetFeu()&&Math.random()<d) return true;

		return false;	
	}
	
	public Boolean PopArbreFeuilles(int i,int j, double d){
			Feuilles f;
		if(getCellState((i+_dx-1)%(_dx) , j ) instanceof Feuilles){
			f=(Feuilles) getCellState((i+_dx-1)%(_dx) , j );
			if(f.GetFeu()==false && Math.random()<d) return true;
		}
		if(getCellState((i+_dx+1)%(_dx) , j ) instanceof Feuilles){
			f=(Feuilles) getCellState((i+_dx+1)%(_dx) , j );
			if(f.GetFeu()==false && Math.random()<d) return true;
		}
		if(getCellState(i , (j+_dy+1)%(_dy) ) instanceof Feuilles){
			f=(Feuilles) getCellState(i , (j+_dy+1)%(_dy) );
			if(f.GetFeu()==false && Math.random()<d) return true;
		}
		if(getCellState(i , (j+_dy-1)%(_dy) ) instanceof Feuilles){			
			f=(Feuilles) getCellState(i , (j+_dy-1)%(_dy) );
			if(f.GetFeu()==false && Math.random()<d) return true;
		}
		if(getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Feuilles){
			f=(Feuilles) getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) ) ;
			if(f.GetFeu()==false && Math.random()<d) return true;
		}
		if(getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Feuilles){
			f=(Feuilles)getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) ;
			if(f.GetFeu()==false && Math.random()<d) return true;
		}
		if(getCellState((i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Feuilles){
			f=(Feuilles)getCellState((i+_dx-1)%(_dx) , (j+_dy-1)%(_dy) ) ;
			if(f.GetFeu()==false && Math.random()<d) return true;
		}
		if(getCellState((i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Feuilles){
			f=(Feuilles)getCellState((i+_dx+1)%(_dx) , (j+_dy-1)%(_dy) ) ;
			if(f.GetFeu()==false && Math.random()<d) return true;
		}

		return false;
				
	}
	
	public Boolean PopHerbes(int i,int j, double d){
		
		if(getCellState((i+_dx-1)%(_dx) , j ) instanceof Herbes)
			if(Math.random()<d) return true;
		if(getCellState((i+_dx+1)%(_dx) , j ) instanceof Herbes)
			if(Math.random()<d) return true;
		if(getCellState(i , (j+_dy+1)%(_dy) ) instanceof Herbes)
			if(Math.random()<d) return true;
		if(getCellState(i , (j+_dy-1)%(_dy) ) instanceof Herbes)
			if(Math.random()<d) return true;
		if(getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Herbes)
			if(Math.random()<d) return true;
		if(getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Herbes)
			if(Math.random()<d) return true;
		if(getCellState((i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Herbes)
			if(Math.random()<d) return true;
		if(getCellState((i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Herbes)
			if(Math.random()<d) return true;

		return false;
				
	}
	
	public Boolean PopFeuilles(int i,int j){
		Arbre ab;
		
		if(getCellState((i+_dx-1)%(_dx) , j ) instanceof Arbre) {
			ab=(Arbre) getCellState((i+_dx-1)%(_dx) , j );
			if(ab.GetFeu()==false&&ab.GetVie()==ab.GetVieMax())return true;
			
		}
		if(getCellState((i+_dx+1)%(_dx) , j ) instanceof Arbre) {
			ab=(Arbre) getCellState((i+_dx+1)%(_dx) , j );
			if(ab.GetFeu()==false&&ab.GetVie()==ab.GetVieMax())return true;
	
		}
		if(getCellState(i , (j+_dy+1)%(_dy) ) instanceof Arbre) {
			ab=(Arbre) getCellState(i,  (j+_dy+1)%(_dy));
			if(ab.GetFeu()==false&&ab.GetVie()==ab.GetVieMax())return true;
		}
		if(getCellState(i , (j+_dy-1)%(_dy) ) instanceof Arbre) {
			ab=(Arbre) getCellState(i,  (j+_dy-1)%(_dy));
			if(ab.GetFeu()==false&&ab.GetVie()==ab.GetVieMax())return true;
		}
		if(getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Arbre) {
			ab=(Arbre) getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) );
			if(ab.GetFeu()==false&&ab.GetVie()==ab.GetVieMax())return true;
		}
		if(getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Arbre) {
			ab=(Arbre) getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) );		
			if(ab.GetFeu()==false&&ab.GetVie()==ab.GetVieMax())return true;
		}
		if(getCellState((i+_dx-1)%(_dx) , (j+_dy-1)%(_dy) ) instanceof Arbre) {
			ab=(Arbre) getCellState((i+_dx-1)%(_dx) , (j+_dy-1)%(_dy) );
			if(ab.GetFeu()==false&&ab.GetVie()==ab.GetVieMax())return true;
		}
		if(getCellState((i+_dx+1)%(_dx) , (j+_dy-1)%(_dy) ) instanceof Arbre) {
			ab=(Arbre) getCellState((i+_dx+1)%(_dx) , (j+_dy-1)%(_dy) );
			if(ab.GetFeu()==false&&ab.GetVie()==ab.GetVieMax())return true;
		}

		return false;
				
	}
	
	public boolean FeuArbreFeuilles(int i,int j){
		
		if(getCellState((i+_dx-1)%(_dx) , j ) instanceof Arbre)
			if(((Arbre)getCellState((i+_dx-1)%(_dx) , j )).GetFeu()) return true;
		if(getCellState((i+_dx+1)%(_dx) , j ) instanceof Arbre)
			if(((Arbre)getCellState((i+_dx+1)%(_dx) , j )).GetFeu()) return true;
		if(getCellState(i , (j+_dy+1)%(_dy) ) instanceof Arbre)
			if(((Arbre)getCellState(i , (j+_dy+1)%(_dy) )).GetFeu()) return true;
		if(getCellState(i , (j+_dy-1)%(_dy) ) instanceof Arbre)
			if(((Arbre)getCellState(i , (j+_dy-1)%(_dy) )).GetFeu()) return true;
		if(getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Arbre)
			if(((Arbre)getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) )).GetFeu()) return true;
		if(getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Arbre)
			if(((Arbre)getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) )).GetFeu()) return true;
		if(getCellState((i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Arbre)
			if(((Arbre)getCellState((i+_dx-1)%(_dx) , (j+_dy-1)%(_dy) )).GetFeu()) return true;
		if(getCellState((i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Arbre)
			if(((Arbre)getCellState((i+_dx+1)%(_dx) , (j+_dy-1)%(_dy) )).GetFeu()) return true;

		return false;	
	}
	
	
	public boolean FeuFeuillesFeuilles(int i,int j, double d1,double d2){
		
		if(getCellState((i+_dx-1)%(_dx) , j ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx-1)%(_dx) , j )).GetFeu()&&Math.random()<d1) return true;
		if(getCellState((i+_dx+1)%(_dx) , j ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx+1)%(_dx) , j )).GetFeu()&&Math.random()<d1) return true;
		if(getCellState(i , (j+_dy+1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState(i , (j+_dy+1)%(_dy) )).GetFeu()&&Math.random()<d1) return true;
		if(getCellState(i , (j+_dy-1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState(i , (j+_dy-1)%(_dy) )).GetFeu()&&Math.random()<d1) return true;
		if(getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) )).GetFeu()&&Math.random()<d2) return true;
		if(getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) )).GetFeu()&&Math.random()<d2) return true;
		if(getCellState((i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx-1)%(_dx) , (j+_dy-1)%(_dy) )).GetFeu()&&Math.random()<d2) return true;
		if(getCellState((i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Feuilles)
			if(((Feuilles)getCellState((i+_dx+1)%(_dx) , (j+_dy-1)%(_dy) )).GetFeu()&&Math.random()<d2) return true;

		return false;	
	}
	
	public boolean FeuHerbesFeuilles(int i,int j, double d1,double d2){
		
		if(getCellState((i+_dx-1)%(_dx) , j ) instanceof Herbes)
			if(((Herbes)getCellState((i+_dx-1)%(_dx) , j )).GetFeu()&&Math.random()<d1) return true;
		if(getCellState((i+_dx+1)%(_dx) , j ) instanceof Herbes)
			if(((Herbes)getCellState((i+_dx+1)%(_dx) , j )).GetFeu()&&Math.random()<d1) return true;
		if(getCellState(i , (j+_dy+1)%(_dy) ) instanceof Herbes)
			if(((Herbes)getCellState(i , (j+_dy+1)%(_dy) )).GetFeu()&&Math.random()<d1) return true;
		if(getCellState(i , (j+_dy-1)%(_dy) ) instanceof Herbes)
			if(((Herbes)getCellState(i , (j+_dy-1)%(_dy) )).GetFeu()&&Math.random()<d1) return true;
		if(getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Herbes)
			if(((Herbes)getCellState((i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) )).GetFeu()&&Math.random()<d2) return true;
		if(getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) instanceof Herbes)
			if(((Herbes)getCellState((i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) )).GetFeu()&&Math.random()<d2) return true;
		if(getCellState((i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Herbes)
			if(((Herbes)getCellState((i+_dx-1)%(_dx) , (j+_dy-1)%(_dy) )).GetFeu()&&Math.random()<d2) return true;
		if(getCellState((i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) instanceof Herbes)
			if(((Herbes)getCellState((i+_dx+1)%(_dx) , (j+_dy-1)%(_dy) )).GetFeu()&&Math.random()<d2) return true;

		return false;	
	}
	
	
	public void StepArbre(int i,int j){
		Arbre arbre=(Arbre) getCellState(i,j);
		if(arbre.GetFeu()) {
			arbre.step();
			setCellState(i,j,arbre);
		}
		else if(arbre.GetVie()==0){
			if(arbre.GetCendre()==0) {
				setCellState(i,j,new Cendre(i,j));
			}
			else {
				arbre.step();			
				setCellState(i,j,arbre);
			}
		}else{
			if(FeuFeuillesArbre(i,j, 0.5)) {
				arbre.SetFeu(true);	
				setCellState(i,j,arbre);
			}
			else {
				arbre.step();
				if(Math.random()<h) arbre.SetFeu(true);
			}
			setCellState(i,j,arbre);
		}		
		
	}
	
	public void StepCell(int i, int j){
		
		
		if(PopFeuilles(i,j)) setCellState(i,j, new Feuilles(i,j));
			
		else if(PopHerbes(i,j,0.0008)) setCellState(i,j, new Herbes(i,j));
		
		else if(PlaceLibreArbre(i,j)){
			if(PopArbreFeuilles(i,j,0.00008)) setCellState(i,j, new Arbre(i,j));
			else {
				float f=(float)this.world.getCellHeight(i, j)/(float)this.world.getMaxEverHeight();
				setCellState(i,j,getCellState(i,j));
				getCellState(i,j).SetCellColor(f);
			}
		}
			
		else if(Math.random()<0.000001) setCellState(i,j, new Arbre(i,j));
			
		
		else { 
			
		float f=(float)this.world.getCellHeight(i, j)/(float)this.world.getMaxEverHeight();
			setCellState(i,j,getCellState(i,j));
			getCellState(i,j).SetCellColor(f);

		}
		
		
	}
	
	public void StepCendre(int i, int j){
	
		float f=(float)this.world.getCellHeight(i, j)/(float)this.world.getMaxEverHeight();
		if(((Cendre)getCellState(i,j)).GetCendre()==0) {
			setCellState(i,j, new Cell(i,j));
			getCellState(i,j).SetCellColor(f);
		}
		else {
			((Cendre)getCellState(i,j)).step();
		setCellState(i,j,this.getCellState(i, j));
		}
	}
	
	
	
	public void StepFeuilles(int i, int j){
		if(PopFeuilles(i,j)==false) {
			setCellState(i,j, new Cendre(i,j));	
		}
		else if(FeuArbreFeuilles(i,j)) {
			((Feuilles)getCellState(i,j)).SetFeu(true);
		}
		//else if(PopFeuilles(i,j))	((Feuilles)getCellState(i,j)).SetFeu(false);			
		else if(FeuFeuillesFeuilles(i,j,0.4,0.1)) ((Feuilles)getCellState(i,j)).SetFeu(true);
		else if(FeuHerbesFeuilles(i,j,0.1,0.02)) ((Feuilles)getCellState(i,j)).SetFeu(true);
		else {
			setCellState(i,j, new Feuilles(i,j));
		}
	}
	
	public void StepHerbes(int i, int j){
		Herbes herbes=(Herbes)getCellState(i,j);
		if(herbes.GetFeu()){
			if(herbes.GetVie()<=0) setCellState(i,j,new Cendre(i,j));
		}
		else{
			if(FeuHerbesFeuilles(i,j,0.5,0.1)) herbes.SetFeu(true);
			else if(FeuFeuillesFeuilles(i,j,0.2,0.05)) herbes.SetFeu(true);
			else {
				herbes.step();
				if(Math.random()<h) herbes.SetFeu(true);
			}
		}
		setCellState(i,j,this.getCellState(i, j));
		
	}
	
	
}
