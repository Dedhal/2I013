// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import javax.media.opengl.GL2;

import cellularobject.Cell;
import cellularobject.EauProfonde;
import objects.*;
import worlds.World;

public class WorldOfTrees extends World {

    protected Automata cellularAutomata;

    public void init ( int __dxCA, int __dyCA, double[][] landscape )
    {
    	super.init(__dxCA, __dyCA, landscape);
    	
    	// add colors
    	
    	for ( int x = 0 ; x < __dxCA ; x++ )
    		for ( int y = 0 ; y < __dyCA ; y++ )
    		{
	        	float color[] = new float[3];

	        	float height = (float) this.getCellHeight(x, y);
		    	
		        if ( height >= 0 )
		        {
		        	// snowy mountains
		        	/*
		        	color[0] = height / (float)this.getMaxEverHeight();
					color[1] = height / (float)this.getMaxEverHeight();
					color[2] = height / (float)this.getMaxEverHeight();
					/**/
		        	
					// green mountains
		        	/**/
		        	color[0] = height / ( (float)this.getMaxEverHeight() );
					color[1] = 0.9f + 0.1f * height / ( (float)this.getMaxEverHeight() );
					color[2] = height / ( (float)this.getMaxEverHeight() );
					/**/
		        }
		        else
		        {
		        	// water
					color[0] = -height;
					color[1] = -height;
					color[2] = 1.f;
		        }
		        this.cellsColorValues.setCellState(x, y, color);
    		}
    	
    	// add some objects
    	for ( int i = 0 ; i < 11 ; i++ )
    	{
    		if ( i%10 == 0 )
    			uniqueObjects.add(new Monolith(110,110+i,this));
    		else
    			uniqueObjects.add(new BridgeBlock(110,110+i,this));
    	}
    	
    	
    	for(int i=0;i<15;i++){
    		int x = (int)(Math.random()*getWidth());
    		int y = (int)(Math.random()*getHeight());
    		
    		if(cellularAutomata.getCellState(x, y) instanceof EauProfonde)
    			i--;
    		
    		else
    		{
    			uniqueDynamicObjects.add(new Proie(x,y ,this));
    		}
    	}
    	
    	for(int i=0;i<15;i++){
    		int x = (int)(Math.random()*getWidth());
    		int y = (int)(Math.random()*getHeight());
    		
    		if(cellularAutomata.getCellState(x, y) instanceof EauProfonde)
    			i--;
    		else
    			uniqueDynamicObjects.add(new Predateur(x,y,this));
        }
    }
    
    protected void initCellularAutomata(int __dxCA, int __dyCA, double[][] landscape)
    {
    	cellularAutomata = new Automata(this,__dxCA,__dyCA,cellsHeightValuesCA);
    	cellularAutomata.init();
    	for(int i = 0; i < 1; i ++)
    	{
    		cellularAutomata.step();
    	}
    }
    
    protected void stepCellularAutomata()
    {
    	if ( iteration%10 == 0 ){
    		cellularAutomata.step();
    	}
    }
    
    protected void stepAgents()
    {
    	// nothing to do.
    	for ( int i = 0 ; i < this.uniqueDynamicObjects.size() ; i++ )
    	{
    		if(this.uniqueDynamicObjects.get(i).getVie() <= 0)
    			uniqueDynamicObjects.remove(i);
    		else
    			this.uniqueDynamicObjects.get(i).step();
    		
    	}
    }

    public Cell getCellValue(int x, int y) // used by the visualization code to call specific object display.
    {
    	return cellularAutomata.getCellState(x%dxCA,y%dyCA);
    }

    public void setCellValue(int x, int y, Cell state)
    {
    	cellularAutomata.setCellState( x%dxCA, y%dyCA, state);
    }
    
	public void displayObjectAt(World _myWorld, GL2 gl, Cell cellState, int x,
			int y, double height, float offset,
			float stepX, float stepY, float lenX, float lenY,
			float normalizeHeight) 
	{
		switch ( 0 )
		{
		case 1: // trees: green, fire, burnt
		case 2:
		case 3:
			//Tree.displayObjectAt(_myWorld,gl,cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);
		default:
			// nothing to display at this location.
		}
	}

	//public void displayObject(World _myWorld, GL2 gl, float offset,float stepX, float stepY, float lenX, float lenY, float heightFactor, double heightBooster) { ... } 
    
   
}
