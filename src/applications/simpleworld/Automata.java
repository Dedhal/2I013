package applications.simpleworld;

import cellularautomata.CellularAutomataDouble;

import cellularautomata.CellularAutomataInteger;
import cellularobject.Arbre;
import cellularobject.Cell;
import cellularobject.Cendre;
import cellularobject.Eau;
import cellularobject.Feuilles;
import cellularobject.Herbes;
import cellularobject.EauProfonde;
import worlds.World;



import java.util.*;

//public class Automata extends CellularAutomataInteger implements Observer {

//public class Automata extends CellularAutomataInteger implements ProieListener {

public class Automata extends CellularAutomataInteger{


	public Automata(World __world, int __dx , int __dy, CellularAutomataDouble cellsHeightValuesCA ) {
		
		super(__dx,__dy,true, __world , cellsHeightValuesCA); 
		

	}
	

	public void update(Observable obs, Object obj)
	{
		
	}
	
	

	public void init()
	{
		for ( int x = 0 ; x != _dx ; x++ )
    		for ( int y = 0 ; y != _dy ; y++ )
    		{
    			if ( _cellsHeightValuesCA.getCellState(x,y) >= 0 )
    			{
    				if ( PlaceLibreArbre(x,y) &&( Math.random() < 0.001 )) // was: 0.71
    					this.setCellState(x, y, new Arbre(x,y)); // tree
    				
    				else if( Math.random() < 0.003)
    					this.setCellState(x, y, new Herbes(x,y));
    				
    				else
    					this.setCellState(x, y, new Cell(x,y)); // empty
    			}
    			else
    			{
    				this.setCellState(x, y, new EauProfonde(x,y)); // water (ignore)
    			}    			
    		}
		this.swapBuffer();
		
		for (int i=0;i<15;i++) step();
		
		this.h=0.001;
    	this.swapBuffer();
    	
	}

	public void step(){
		
		this.StepLiquideTmp();
		
		for ( int x = 0 ; x != _dx ; x++ )
    		for ( int y = 0 ; y != _dy ; y++ )
    		{
    			float[] color;
    			if(EauTmp[x][y]!=0){
    				if(!(this.getCellState(x, y) instanceof Eau)) this.setCellState(x, y, new Eau(x,y,EauTmp[x][y]));  
    				else{
    					((Eau)this.getCellState(x, y)).SetNiveauEau(EauTmp[x][y]);
    					this.setCellState(x, y, this.getCellState(x,y));
    				}

    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    				
    			}
    			
    			else if(this.getCellState(x, y) instanceof Eau){
    				this.StepEau(x, y);
    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    			}
    			
    			else if(this.getCellState(x, y) instanceof Arbre){
    				this.StepArbre(x, y);
    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    			}
    			   			
    			else if(this.getCellState(x, y) instanceof Cendre) {
    				this.StepCendre(x, y);
    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    			}
    			
    			else if(this.getCellState(x, y) instanceof Feuilles) {
    				this.StepFeuilles(x, y);
    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    			}
    			else if(this.getCellState(x, y) instanceof Herbes) {

    				if(((Herbes)this.getCellState(x, y)).GetVie() <= 0)
    					this.setCellState(x, y, new Cendre(x, y));
    				else
    				{
    					this.StepHerbes(x, y);
    					color=this.getCellState(x, y).GetColor();
    					this.world.cellsColorValues.setCellState(x, y, color);
    				}

    			}
    			else if(this.getCellState(x, y) instanceof EauProfonde) {
    				this.setCellState(x, y, new EauProfonde(x,y));
    				color=this.getCellState(x, y).GetColor();
    				
    				this.world.cellsColorValues.setCellState(x, y, color);
    			}
    			   			
    			else if(this.getCellState(x, y) instanceof Cell) {
    				this.StepCell(x, y);     				
    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    				    				
    			}
    			
    			else {
    				this.setCellState(x, y, this.getCellState(x, y));
    				
    			}
    		}
		this.resetEauTmp();
		this.setCellState(154, 164, new Eau(154, 164, 0.01));
		this.swapBuffer();
	}

	/*
	@Override
	public void SeNourrir(Proie p) {
		int[] coordinates = p.getCoordinate();
		
		p.setFaim(10);
		
		if(p.getFaim() > p.getFaimMax())
			p.setFaim(p.getFaimMax());
		
		this.setCellState(coordinates[0], coordinates[1], new Cell(coordinates[0], coordinates[1]));
	}

	@Override
	public void Deplacement(Proie p) {
	
		
	}
	*/
}	


