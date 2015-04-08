package applications.simpleworld;

import javax.media.opengl.GL2;

import cellularobject.Cell;
import cellularobject.Feuilles;
import cellularobject.Herbes;
import worlds.World;

import java.util.*;

public class Predateur extends Agent{
	
	protected int vie = 300;
	protected boolean Nourri = false;
	protected boolean Reprodui = false;
	protected int faim;
	protected int faimMax;
	protected int brule = 0;
	
	protected Cell MyCell;
	protected ArrayList<Cell> ChampsVision = new ArrayList<Cell>();
	
	protected ArrayList<Predateur> ProxPredateurs = new ArrayList<Predateur>();
	protected ArrayList<Proie> ProxProies = new ArrayList<Proie>();
	
	protected PredateurListener World;
	
	public Predateur(int x, int y, World __world)
	{
		super(x, y, __world);
		this.faimMax = 500 + (int)(((Math.random()*2) - 1)*100);
		this.faim = this.faimMax;
		this.World = (WorldOfTrees)__world;
	}
	
	public int getVie()
	{
		return this.vie;
	}
	
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
    {

        // display a monolith
        
        //gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));
        
    	int x2 = (x-(offsetCA_x%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
    	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();
    	
    	float red;
    	float green;
    	float blue;
    	
    	// representation de l'etat brule
    	if(this.brule == 0 || (this.brule > 0 && world.getIteration()%40 < 20))
    	{
    		red = 1.0f;
    		green = 1.0f;
    		blue = 0.f;
    	}
    	else
    	{
    		red = 1.0f;
    		green = 0.f;
    		blue = 0.f;
    	}

    	float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );
    	
        gl.glColor3f(1.f,0.f,0.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);

        gl.glColor3f(1.f,0.f,0.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        
        gl.glColor3f(1.f,0.f,0.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);

        gl.glColor3f(1.f,0.f,0.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);

        gl.glColor3f(red,green,blue);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 5.f);
    }

	public void addVisionCell(Cell cell)
	{
		this.ChampsVision.add(cell);
	}
	
	public void removeVisionCell(int index)
	{
		this.ChampsVision.remove(index);
	}
	
	public void InitChampsVision(Cell cell, Automata cellularAutomata)
	{
		this.MyCell = cell;
		ChampsVision.removeAll(ChampsVision);
		this.MakeMap(cellularAutomata);
	}
	
	public Proie eatable()
	{
		ArrayList<Proie> temp = new ArrayList<Proie>();
		temp.addAll(World.ProximiteProies(this, this.ChampsVision));
		
		for(int i = 0; i < temp.size(); i++)
			if	( this.getCoordinate() == temp.get(i).getCoordinate() ||
				( this.getCoordinate()[0] == temp.get(i).getCoordinate()[0] &&
				( this.getCoordinate()[1] == temp.get(i).getCoordinate()[1] - 1 ||
				  this.getCoordinate()[1] == temp.get(i).getCoordinate()[1] + 1 ))
				||
				( this.getCoordinate()[1] == temp.get(i).getCoordinate()[1] &&
				( this.getCoordinate()[0] == temp.get(i).getCoordinate()[0] - 1 ||
				  this.getCoordinate()[0] == temp.get(i).getCoordinate()[0] + 1 ))
				)
				return temp.get(i);
		
		return null;
	}
	
	public void setReprodui()
	{
		this.Reprodui = true;
	}
	
	public void resetReprodui()
	{
		this.Reprodui = false;
	}
	
	public boolean getReprodui()
	{
		return this.Reprodui;
	}
	public boolean ReproductionPossible()
	{
		ArrayList<Predateur> temp = new ArrayList<Predateur>();
		temp.addAll(World.ProximitePredateurs(this, this.ChampsVision));
		
		ArrayList<Predateur> caseAdjacente = new ArrayList<Predateur>();
		
		for(int i = 0; i < temp.size(); i++)
			if	( this.getCoordinate() == temp.get(i).getCoordinate() ||
				( this.getCoordinate()[0] == temp.get(i).getCoordinate()[0] &&
				( this.getCoordinate()[1] == temp.get(i).getCoordinate()[1] - 1 ||
				  this.getCoordinate()[1] == temp.get(i).getCoordinate()[1] + 1 ))
				||
				( this.getCoordinate()[1] == temp.get(i).getCoordinate()[1] &&
				( this.getCoordinate()[0] == temp.get(i).getCoordinate()[0] - 1 ||
				  this.getCoordinate()[0] == temp.get(i).getCoordinate()[0] + 1 ))
				)
				caseAdjacente.add(temp.get(i));
		
		for(int i = 0; i < caseAdjacente.size(); i++)
			if(!caseAdjacente.get(i).getReprodui())
			{
				return true;
			}
		
		return false;
	}
	
	public int getFaim()
	{
		return this.faim;
	}
	
	public Cell getCell() {
		return this.MyCell;
	}

	public void setNourri()
	{
		this.Nourri = true;
	}
	
	public void resetNourri()
	{
		this.Nourri = false;
	}

	public int getFaimMax()
	{
		return this.faimMax;
	}
	
	public void setFaim(int a)
	{
		this.faim += a;
		if(this.faim > faimMax)
			this.faim = faimMax;
	}

	@Override
	public void seDeplacer()
	{
		double dice = Math.random();
		if ( (dice < 0.25)&&(this.world.getCellHeight( ( this.x + 1 ) % this.world.getWidth(),this.y)>=0) )
		{
			this.x = ( this.x + 1 ) % this.world.getWidth() ;
			World.Deplacement(this);
		}
		else
			if ( (dice < 0.5 )&&(this.world.getCellHeight( ( this.x - 1 + this.world.getWidth()) % this.world.getWidth(),this.y)>=0) )
			{
				this.x = ( this.x - 1 +  this.world.getWidth() ) % this.world.getWidth() ;
				World.Deplacement(this);
			}
			else
				if ( (dice < 0.75 )&&(this.world.getCellHeight(this.x,( this.y + 1 ) % this.world.getHeight() )>=0 ) )
				{
					this.y = ( this.y + 1 ) % this.world.getHeight() ;
					World.Deplacement(this);
				}
				else if((this.world.getCellHeight(this.x,( this.y - 1 + this.world.getWidth() ) % this.world.getHeight() )>=0 )  )
				{
					this.y = ( this.y - 1 +  this.world.getHeight() ) % this.world.getHeight() ;
					World.Deplacement(this);
				}
	}
	
	public void MakeMap(Automata cellularAutomata) {
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				if(cellularAutomata.getCell(i, j)!=cellularAutomata.getCell(x, y))
				{
					this.addVisionCell(cellularAutomata.getCell(x + i - 3, y + j - 3));
				}
			}
		}
		for(int i = 0; i < 3; i++)
		{
				this.addVisionCell(cellularAutomata.getCell(x + i - 1, y + 4));
				this.addVisionCell(cellularAutomata.getCell(x + i - 1, y - 4));
				this.addVisionCell(cellularAutomata.getCell(x + 4, y + i - 1));
				this.addVisionCell(cellularAutomata.getCell(x - 4, y + i - 1));
		}
		for(int i = 0; i < 2; i++)
		{
			this.addVisionCell(cellularAutomata.getCell(x + 5 + (i * -10), y));
			this.addVisionCell(cellularAutomata.getCell(x, y + 5 + (i * - 10)));
		}
		
		MyCell = cellularAutomata.getCell(x, y);
		
		ProxProies.removeAll(ProxProies);
		ProxPredateurs.removeAll(ProxPredateurs);
		this.ProxProies.addAll(World.ProximiteProies(this, this.ChampsVision));
		this.ProxPredateurs.addAll(World.ProximitePredateurs(this, this.ChampsVision));
	}
	
	public void step() 
	{
		
		if ( world.getIteration() % 5 == 0 )
		{
			
			/******************* Regles **********************/
			
			//Faim
			if (this.faim < 0)
				this.faim = 0;
			if (this.faim <= 0)
				this.vie -= 10;
			if(this.faim >= (faimMax*(3./4.)))
				this.vie += 10;
			
			if(!Nourri)
				this.setFaim(-5);
			else
				this.resetNourri();
			
			if(this.Reprodui && world.getIteration() % 1000 == 0)
				this.resetReprodui();
			
			//Feu
			if(MyCell instanceof Herbes)
				if(((Herbes)MyCell).GetFeu())
					this.brule += 7;
			if(MyCell instanceof Feuilles)
				if(((Feuilles)MyCell).GetFeu())
					this.brule += 7;
			
			if(this.brule > 0)
			{
				this.vie -= 10;
				this.brule--;
			}
			
			//Mort
			if(this.vie <= 0)
			{
				this.vie = 0;
				this.World.Meurt(this); // Meurt proprement
			}
			
			/*******************   IA   **********************/
			/* 
			 * Cette partie est cense etre remplace par l'interface avec tinyGP
			 * Ceci est simplement pour le test des fonctions
			 */
			
			if(eatable() != null && this.faim < this.faimMax)
				this.World.SeNourrir(eatable(), this);
			
			if(this.ReproductionPossible() && !this.Reprodui)
				this.World.SeReproduire(this);
			
			this.seDeplacer();
			
			
		}
	}
}