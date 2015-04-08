// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import javax.media.opengl.GL2;

import cellularobject.Cell;
import objects.UniqueDynamicObject;
import worlds.World;

abstract public class Agent extends UniqueDynamicObject{
	
	int vie;
	
	public Agent ( int __x , int __y, World __world )
	{
		super(__x,__y,__world);
	}
	
	abstract public int getVie();
	abstract public boolean ReproductionPossible();
	abstract public boolean getReprodui();
	abstract public void setReprodui();
	abstract public void resetReprodui();
	// abstract public boolean eatable();
	abstract public void InitChampsVision(Cell cell, Automata cellularAutomata);
	abstract public void removeVisionCell(int index);
	abstract public void addVisionCell(Cell cell);
	abstract public int getFaim();
	abstract public Cell getCell();
	abstract public void setNourri();
	abstract public void resetNourri();
	abstract public int getFaimMax();
	abstract public void setFaim(int a);
	abstract public void seDeplacer();
	
	
	public void step() 
	{
		if ( world.getIteration() % 20 == 0 )
		{
			double dice = Math.random();
			if ( (dice < 0.25)&&(this.world.getCellHeight( ( this.x + 1 ) % this.world.getWidth(),this.y)>=0) )
				this.x = ( this.x + 1 ) % this.world.getWidth() ;
			else
				if ( (dice < 0.5 )&&(this.world.getCellHeight( ( this.x - 1 + this.world.getWidth()) % this.world.getWidth(),this.y)>=0) )
					this.x = ( this.x - 1 + this.world.getWidth() ) % this.world.getWidth() ;
				else
					if ( (dice < 0.75 )&&(this.world.getCellHeight(this.x,( this.y + 1 ) % this.world.getHeight() )>=0 ) )
						this.y = ( this.y + 1 ) % this.world.getHeight() ;
			
					else if((this.world.getCellHeight(this.x,( this.y - 1 + this.world.getWidth() ) % this.world.getHeight() )>=0 )  )
						this.y = ( this.y - 1 +  this.world.getHeight() ) % this.world.getHeight() ;
		}
	}

    public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
    {

        // display a monolith
        
        //gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));
        
    	int x2 = (x-(offsetCA_x%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
    	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();

    	float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );
    	
        gl.glColor3f(1.f,1.f,1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);

        gl.glColor3f(1.f,1.f,1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        
        gl.glColor3f(1.f,1.f,1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);

        gl.glColor3f(1.f,1.f,1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);

        gl.glColor3f(1.0f,1.f,0.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 5.f);
    }
}
