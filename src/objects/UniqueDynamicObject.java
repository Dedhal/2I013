// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package objects;

import javax.media.opengl.GL2;
import java.util.*;

import worlds.World;

abstract public class UniqueDynamicObject extends Observable // UniqueObject are object defined with particular, unique, properties (ex.: particular location)
{
	protected int x,y;
	protected World world;
	
	public UniqueDynamicObject(int __x, int __y, World __world) 
	{
		this.x = __x;
		this.y = __y;
		this.world = __world;
	}
	
	abstract public void step();
	abstract public int getVie();
	
	public int[] getCoordinate()
	{
		int coordinate[] = new int[2];
		coordinate[0] = this.x;
		coordinate[1] = this.y;
		return coordinate;
	}
	
	abstract public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight );

}
