// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import java.util.ArrayList;

import javax.media.opengl.GL2;

import cellularobject.Cell;
import cellularobject.EauProfonde;
import objects.*;
import worlds.World;

public class WorldOfTrees extends World implements ProieListener, PredateurListener {

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
    	/*
    	for ( int i = 0 ; i < 11 ; i++ )
    	{
    		if ( i%10 == 0 )
    			uniqueObjects.add(new Monolith(110,110+i,this));
    		else
    			uniqueObjects.add(new BridgeBlock(110,110+i,this));
    	}
    	*/
    	
    	for(int i=0;i<30;i++){
    		int x = (int)(Math.random()*getWidth());
    		int y = (int)(Math.random()*getHeight());
    		
    		if(cellularAutomata.getCellState(x, y) instanceof EauProfonde)
    			i--;
    		
    		else
    		{
    			uniqueDynamicObjects.add(new Proie(x,y ,this));
    			((Proie)uniqueDynamicObjects.get(uniqueDynamicObjects.size() - 1)).InitChampsVision(cellularAutomata.getCell(x, y), cellularAutomata);
    		}
    	}
    	
    	for(int i=0;i<20;i++){
    		int x = (int)(Math.random()*getWidth());
    		int y = (int)(Math.random()*getHeight());
    		
    		if(cellularAutomata.getCellState(x, y) instanceof EauProfonde)
    			i--;
    		else
    		{
    			uniqueDynamicObjects.add(new Predateur(x,y,this));
    			((Predateur)uniqueDynamicObjects.get(uniqueDynamicObjects.size() - 1)).InitChampsVision(cellularAutomata.getCell(x, y), cellularAutomata);
    		}
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
    	if ( iteration%5 == 0 ){
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
		switch ( cellState.getStatut() )
		{
		case 3: // trees: green, fire, burnt
			break;
		case 21:
			Leaf.displayObjectAt(_myWorld,gl,cellState,height, x, y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
			break;
		case 1:
			Tree.displayObjectAt(_myWorld,gl,cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);
			break;
		default:
			// nothing to display at this location.
		}
	}
	
	//Gestion de l'Event Nourrir, initiater Proie
	@Override
	public void SeNourrir(int x, int y, Proie p) {
		p.setFaim(35);
		p.setNourri();
		
		if(p.getFaim() > p.getFaimMax())
			p.setFaim(p.getFaimMax());
		
		cellularAutomata.setCellState(x, y, new Cell(x, y));
		cellularAutomata.refresh(); // Turn without step
		
		System.out.println("Nourri en "+x+", "+y+" pour la proie en "+p.getCoordinate()[0]+", "+p.getCoordinate()[1]+"."); // Debug Line
		
	}
	
	//Gestion du champs de vision des Proies
	@Override
	public void Deplacement(Proie p) {
		p.InitChampsVision(cellularAutomata.getCell(p.getCoordinate()[0], p.getCoordinate()[1]), cellularAutomata);
	}
	
	//Gestion de l'Event Reproduction des Proies
	@Override
	public void SeReproduire(Proie p) {
		
		p.setReprodui();
		uniqueDynamicObjects.add(new Proie(p.getCoordinate()[0], p.getCoordinate()[1], this));
		((Proie)uniqueDynamicObjects.get(uniqueDynamicObjects.size() - 1)).setReprodui();
		System.out.println("Reproduis en "+p.getCoordinate()[0]+", "+p.getCoordinate()[1]+"."); // Debug Line
	}
	
	//Gestion de l'Event Mort des Proies (methode propre)
	@Override
	public void Meurt(Proie p) {
		for(int i = 0; i < uniqueDynamicObjects.size(); i++)
			if(p == uniqueDynamicObjects.get(i))
				uniqueDynamicObjects.remove(i);
	}

	@Override
	public ArrayList<Proie> ProximiteProies(Proie p, ArrayList<Cell> ChampsVision) {
		
		ArrayList<Proie> temp = new ArrayList<Proie>();
		
		for(int i = 0; i < uniqueDynamicObjects.size(); i++)
		{
			for(int j = 0; j < ChampsVision.size() ; j++)
				if(uniqueDynamicObjects.get(i) instanceof Proie && ((Proie)uniqueDynamicObjects.get(i)).getCell() == ChampsVision.get(j))
					temp.add((Proie) uniqueDynamicObjects.get(i));
		}
		
		return temp;
	}

	@Override
	public ArrayList<Predateur> ProximitePredateurs(Proie p, ArrayList<Cell> ChampsVision) {
		ArrayList<Predateur> temp = new ArrayList<Predateur>();
		
		for(int i = 0; i < uniqueDynamicObjects.size(); i++)
		{
			for(int j = 0; j < ChampsVision.size() ; j++)
				if(uniqueDynamicObjects.get(i) instanceof Predateur && ((Predateur)uniqueDynamicObjects.get(i)).getCell() == ChampsVision.get(j))
					temp.add((Predateur) uniqueDynamicObjects.get(i));
		}
		
		return temp;
	}

	@Override
	public void SeNourrir(Proie cible, Predateur attaquant) {
		attaquant.setFaim(150);
		attaquant.setNourri();
		
		if(attaquant.getFaim() > attaquant.getFaimMax())
			attaquant.setFaim(attaquant.getFaimMax());
		
		for(int i = 0; i < uniqueDynamicObjects.size(); i++)
		{
			if(uniqueDynamicObjects.get(i) == cible)
				uniqueDynamicObjects.remove(i);
		}
		
		System.out.println("Nourri pour le predateur en "+attaquant.getCoordinate()[0]+", "+attaquant.getCoordinate()[1]+", Proie Morte."); // Debug Line
		
	}

	@Override
	public void Deplacement(Predateur p) {
		p.InitChampsVision(cellularAutomata.getCell(p.getCoordinate()[0], p.getCoordinate()[1]), cellularAutomata);
		
	}

	@Override
	public ArrayList<Proie> ProximiteProies(Predateur p, ArrayList<Cell> ChampsVision) {

		ArrayList<Proie> temp = new ArrayList<Proie>();
		
		for(int i = 0; i < uniqueDynamicObjects.size(); i++)
		{
			for(int j = 0; j < ChampsVision.size() ; j++)
				if(uniqueDynamicObjects.get(i) instanceof Proie && ((Proie)uniqueDynamicObjects.get(i)).getCell() == ChampsVision.get(j))
					temp.add((Proie) uniqueDynamicObjects.get(i));
		}
		
		return temp;
	}

	@Override
	public ArrayList<Predateur> ProximitePredateurs(Predateur p, ArrayList<Cell> ChampsVision) {

		ArrayList<Predateur> temp = new ArrayList<Predateur>();
		
		for(int i = 0; i < uniqueDynamicObjects.size(); i++)
		{
			for(int j = 0; j < ChampsVision.size() ; j++)
				if(uniqueDynamicObjects.get(i) instanceof Predateur && ((Predateur)uniqueDynamicObjects.get(i)).getCell() == ChampsVision.get(j))
					temp.add((Predateur) uniqueDynamicObjects.get(i));
		}
		
		return temp;
	}

	@Override
	public void SeReproduire(Predateur p) {
		p.setReprodui();
		uniqueDynamicObjects.add(new Predateur(p.getCoordinate()[0], p.getCoordinate()[1], this));
		((Predateur)uniqueDynamicObjects.get(uniqueDynamicObjects.size() - 1)).setReprodui();
		System.out.println("Reproduis en "+p.getCoordinate()[0]+", "+p.getCoordinate()[1]+" - Type Predateur."); // Debug Line
		
	}

	@Override
	public void Meurt(Predateur p) {
		for(int i = 0; i < uniqueDynamicObjects.size(); i++)
			if(p == uniqueDynamicObjects.get(i))
				uniqueDynamicObjects.remove(i);
	}
	
	

	//public void displayObject(World _myWorld, GL2 gl, float offset,float stepX, float stepY, float lenX, float lenY, float heightFactor, double heightBooster) { ... } 
    
   
}
