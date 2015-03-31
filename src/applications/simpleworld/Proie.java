package applications.simpleworld;

import javax.media.opengl.GL2;
import javax.swing.event.*;
import java.util.*;

import worlds.World;

public class Proie extends Agent {
	
	int vie = 100;
	int faim;
	int faimMax;
	int endurance;
	static int enduranceMax = 5;
	int vitesse;
	int ligneMir;
	float tauxReprod;
	boolean Apeure;
	boolean Famine;
	int brule = 0;
	
	ProieListener listener;
	
	public Proie(int x, int y, World __world)
	{
		super(x, y, __world);
		this.faimMax = 300 + (int)(((Math.random()*2) - 1)*100);
		this.faim = this.faimMax;
	}
	
	public int[] SeNourrir(Proie p)
	{
		p.setFaim(10);
		
		if(p.getFaim() > p.getFaimMax())
			p.setFaim(p.getFaimMax());
		
		return p.getCoordinate();
	}
	
	public int getFaim()
	{
		return this.faim;
	}
	
	public int getFaimMax()
	{
		return this.faimMax;
	}
	
	public void setFaim(int a)
	{
		this.faim += a;
	}
	
	public void seDeplacer()
	{
		double dice = Math.random();
		if ( (dice < 0.25)&&(this.world.getCellHeight( ( this.x + 1 ) % this.world.getWidth(),this.y)>=0) )
			this.x = ( this.x + 1 ) % this.world.getWidth() ;
		else
			if ( (dice < 0.5 )&&(this.world.getCellHeight( ( this.x - 1 + this.world.getWidth()) % this.world.getWidth(),this.y)>=0) )
				this.x = ( this.x - 1 +  this.world.getWidth() ) % this.world.getWidth() ;
			else
				if ( (dice < 0.75 )&&(this.world.getCellHeight(this.x,( this.y + 1 ) % this.world.getHeight() )>=0 ) )
					this.y = ( this.y + 1 ) % this.world.getHeight() ;
		
				else if((this.world.getCellHeight(this.x,( this.y - 1 + this.world.getWidth() ) % this.world.getHeight() )>=0 )  )
					this.y = ( this.y - 1 +  this.world.getHeight() ) % this.world.getHeight() ;
		
	}
	
	@Override public void finalize()
	{
		System.out.println("Proie morte en ("+this.x+", "+this.y+")");
	}
	
	public int getVie()
	{
		return this.vie;
	}
	
	
	public void step() 
	{
		
		if ( world.getIteration() % 20 == 0 )
		{
			if (this.faim <= 0)
			{
				this.vie -= 10;
				if(this.vie <= 0)
				{
					this.vie = 0;
					this.finalize();
				}
			}
			if(this.brule > 0)
			{
				this.vie -= 10;
				this.brule--;
			}
			this.setFaim(-5);
			
			this.seDeplacer();
			
		}
	}
	
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
    {

        // display a monolith
        
        //gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));
        if(this.vie <= 0)
        {
        	
        }
        
        else
        {
        	int x2 = (x-(offsetCA_x%myWorld.getWidth()));
        	if ( x2 < 0) x2+=myWorld.getWidth();
        	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
        	if ( y2 < 0) y2+=myWorld.getHeight();
        	float red;
        	float green;
        	float blue;
        	
        	if(this.brule == 0 || (this.brule > 0 && world.getIteration()%40 == 0))
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
    	
        	gl.glColor3f(0.f,0.f,1.f);
        	gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        	gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        	gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        	gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);

        	gl.glColor3f(0.f,0.f,1.f);
        	gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        	gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        	gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        	gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        
        	gl.glColor3f(0.f,0.f,1.f);
        	gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        	gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        	gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        	gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);

        	gl.glColor3f(0.f,0.f,1.f);
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
    }
}