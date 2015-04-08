package applications.simpleworld;


import javax.media.opengl.GL2;

import worlds.World;

public abstract class Agents extends objects.UniqueDynamicObject {

	private boolean vie;
	
	public Agents(int _x, int _y, World _W ) {
		super(_x, _y, _W);
	}


}
