package applications.simpleworld;

import cellularautomata.CellularAutomataDouble;


import cellularautomata.CellularAutomataInteger;
import cellularobject.Lave;
import cellularobject.Arbre;
import cellularobject.Cell;
import cellularobject.Cendre;
import cellularobject.Eau;
import cellularobject.Feuilles;
import cellularobject.Herbes;
import cellularobject.EauProfonde;
import cellularobject.Obsidienne;
import worlds.World;




import java.util.*;

//public class Automata extends CellularAutomataInteger implements Observer {

//public class Automata extends CellularAutomataInteger implements ProieListener {

public class Automata extends CellularAutomataInteger{

	protected int saison;
	protected boolean eruption;

	public Automata(World __world, int __dx , int __dy, CellularAutomataDouble cellsHeightValuesCA ) {
		
		super(__dx,__dy,true, __world , cellsHeightValuesCA); 
		saison=0;
		eruption=false;

	}
	

	public void update(Observable obs, Object obj)
	{
		
	}
	
	public int getSaison(){
		return saison;
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
	
	public void winter(){
		this.tauxfire=0.2;
		this.h=0.0001;
		this.tauxpop=0.0001;
		this.getCellState(0, 0).setNeige(0);				
	}
	
	public void spring(){
		this.tauxfire=0.4;
		this.h=0.0005;
		this.tauxpop=0.001;
		this.getCellState(0, 0).setNeige(0);
	}
	
	public void summer(){
		this.tauxfire=0.75;
		this.h=0.002;
		this.tauxpop=0.0006;
		this.getCellState(0, 0).setNeige(0);
	}
	
	public void autumn(){
		this.tauxfire=0.6;
		this.h=0.001;
		this.tauxpop=0.0004;
		this.getCellState(0, 0).setNeige(0);
		
	}
	
	
	public void step(){

		if(this.world.getIteration()%90000==0){
			saison=(saison+1)%4;
			switch(saison){
				case 0:
					winter();
					System.out.println("Hiver\n");
					break;
				case 1:
					spring();
					System.out.println("Printemps\n");
					break;
				case 2:
					summer();
					System.out.println("Ete\n");
					break;
				case 3:
					autumn();
					System.out.println("Automne\n");
					break;			
			}
		}
		this.StepLiquideTmp();
		
		for ( int x = 0 ; x != _dx ; x++ )
    		for ( int y = 0 ; y != _dy ; y++ )
    		{  			
    			float[] color;
    			if(this.getCellState(x, y) instanceof EauProfonde) {
    				this.setCellState(x, y, new EauProfonde(x,y));
    				color=this.getCellState(x, y).GetColor();
    				
    				this.world.cellsColorValues.setCellState(x, y, color);
    			}
    			else if(LaveTmp[x][y]!=0){
    				if(!(this.getCellState(x, y) instanceof Lave)) this.setCellState(x, y, new Lave(x,y,LaveTmp[x][y]));  
    				else{
    					((Lave)this.getCellState(x, y)).SetNiveauLave(LaveTmp[x][y]);
    					this.setCellState(x, y, this.getCellState(x,y));
    				}

    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    				
    			}
    				
    			else if(EauTmp[x][y]!=0){
    				if(!(this.getCellState(x, y) instanceof Eau)) this.setCellState(x, y, new Eau(x,y,EauTmp[x][y]));  
    				else{
    					((Eau)this.getCellState(x, y)).SetNiveauEau(EauTmp[x][y]);
    					this.setCellState(x, y, this.getCellState(x,y));
    				}

    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    				
    			}
    			
     			
    			else if(this.getCellState(x, y) instanceof Lave){
    				if(((Lave)this.getCellState(x,y)).GetVie()==0){
    					this.setCellState(x, y, new Obsidienne(x,y));
    				}else{
    					this.StepLave(x, y);
    					this.setCellState(x,y,this.getCellState(x, y));
    				}
    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    			}
    			
    			else if(this.getCellState(x, y) instanceof Obsidienne){
    				Obsidienne ob=(Obsidienne) this.getCellState(x, y);
    				if(ob.GetVie()==0) this.setCellState(x,y,new Cell(x, y));
    				else {
    					ob.Step();
    					this.setCellState(x,y,this.getCellState(x, y));
    				}
    				color=this.getCellState(x, y).GetColor();
    				this.world.cellsColorValues.setCellState(x, y, color);
    				
    			}
    			
    			else if(this.getCellState(x, y) instanceof Eau){
    				this.StepEau(x, y);
    				this.setCellState(x,y,this.getCellState(x, y));
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
		this.resetLaveTmp();
		if(eruption){
		if((this.world.getIteration()%40)==10)this.setCellState(154, 164, new Lave(154, 164, 0.5));
		}
		this.swapBuffer();

	}
	
	public void toggle_eruption(){
		if(!eruption){
			eruption=true;
			System.out.println("Eruption!!\n");
		}
		else{
			eruption=false;
			System.out.println("Fin eruption\n");
		}
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


