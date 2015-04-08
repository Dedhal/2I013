package objects;

import javax.media.opengl.GL2;

import worlds.World;
import cellularobject.Cell;
import cellularobject.Feuilles;

public class Leaf extends CommonObject{
	
	public static void displayObjectAt(World myWorld, GL2 gl,Cell cellState, double height, int x, int y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
	{	
    	if(((Feuilles)cellState).GetFeu())
    		gl.glColor3f(0.9f, 0.f, 0.f);
    	else
    		gl.glColor3f(1.f,1.f,0.f);
		
		gl.glVertex3f( offset+x*stepX-lenX, offset+y*stepY-lenY, (float)height*normalizeHeight + 5.f);
		gl.glVertex3f( offset+x*stepX-lenX, offset+y*stepY+lenY, (float)height*normalizeHeight + 5.f);
		gl.glVertex3f( offset+x*stepX+lenX, offset+y*stepY+lenY, (float)height*normalizeHeight + 5.f);
		gl.glVertex3f( offset+x*stepX+lenX, offset+y*stepY-lenY, (float)height*normalizeHeight + 5.f);
	}
}