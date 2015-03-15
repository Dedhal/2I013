// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import cellularautomata.CellularAutomataDouble;
import cellularautomata.CellularAutomataInteger;
import worlds.World;

public class ForestCA extends CellularAutomataInteger {

	CellularAutomataDouble _cellsHeightValuesCA;
	CellularAutomataDouble _cellsHeightWithWater;
	double Neautmp[][];

	World world;
	public ForestCA ( World __world, int __dx , int __dy, CellularAutomataDouble cellsHeightValuesCA )
	{
		super(__dx,__dy,true ); // buffering must be true.
		Neautmp=new double[__dx][__dy];
		_cellsHeightValuesCA = cellsHeightValuesCA;
		
		this.world = __world;
	}
	
	public void init()
	{
		for ( int x = 0 ; x != _dx ; x++ )
    		for ( int y = 0 ; y != _dy ; y++ )
    		{
    			if ( _cellsHeightValuesCA.getCellState(x,y) >= 0 )
    			{
    				if ( Math.random() < 0.001 ) // was: 0.71
    					this.setCellState(x, y, 1); // tree
    				
    				else if( Math.random() < 0.003)
    					this.setCellState(x, y, 4);
    				
    				else
    					this.setCellState(x, y, 0); // empty
    			}
    			else
    			{
    				this.setCellState(x, y, -1); // water (ignore)
    			}
    		}
		
		this.SetEau(154, 164, true);
		this.SetNeau(154, 164, 10);
		
    	this.swapBuffer();

	}

	
	public void step()
	{
		this.SetEau(154, 164, true);
		this.SetNeau(154, 164, 30);
    	for ( int i = 0 ; i != _dx ; i++ )
    		for ( int j = 0 ; j != _dy ; j++ ){
    			if(this.Eau(i,j)){
    				
    				double h1,h2,h3,h4;
    				h1= _cellsHeightValuesCA.getCellState((i+_dx-1)%(_dx) , j )+ this.GetNeau((i+_dx-1)%(_dx) , j );
    				h2= _cellsHeightValuesCA.getCellState((i+_dx+1)%(_dx) , j )+ this.GetNeau( (i+_dx+1)%(_dx) , j );
    				h3= _cellsHeightValuesCA.getCellState(i , (j+_dy+1)%(_dy) ) + this.GetNeau(i , (j+_dy+1)%(_dy) );
    				h4= _cellsHeightValuesCA.getCellState( i , (j+_dy-1)%(_dy) ) + this.GetNeau( i , (j+_dy-1)%(_dy) );
    				
    				double htmp=Math.min(Math.min(h1,h2), Math.min(h3, h4));
    				
    				if(htmp==h1)
    				{
    					if(this.GetNeau(i, j)+_cellsHeightValuesCA.getCellState(i, j)<h1){
    						System.out.println("Bassin\n");
    					}
    					else{
    						if (!this.Eau((i+_dx-1)%(_dx) , j)){
    							this.SetEau((i+_dx-1)%(_dx) , j, true);
    						}
    						
    						if(h1+this.GetNeau(i, j)<_cellsHeightValuesCA.getCellState(i, j)) {    							
    							Neautmp[(i+_dx-1)%_dx][j]=this.GetNeau(i,j);
    							Neautmp[i][j]=0;
    							 							
    						}
    						
    						else{
    							double d=(h1+this.GetNeau(i, j)-(_cellsHeightValuesCA.getCellState(i, j)))/2;
    							Neautmp[(i+_dx-1)%_dx][j]=this.GetNeau(i, j)-d;
    							Neautmp[i][j]=d;
    							
    						}
    												
    						
    					}
    				}
    				else if(htmp==h2)
    				{
    					
    					if(this.GetNeau(i, j)+_cellsHeightValuesCA.getCellState(i, j)<=h2){
    						System.out.println("Bassin\n");
    					}
    					else {
    						if (!this.Eau((i+_dx+1)%(_dx) , j)){
    							this.SetEau((i+_dx+1)%(_dx) , j, true);
    						}
    						
    						if(h2+this.GetNeau(i, j)<_cellsHeightValuesCA.getCellState(i, j)) {    							
    							Neautmp[(i+_dx+1)%_dx][j]=this.GetNeau(i,j);
    							Neautmp[i][j]=0;
    							 							
    						}
    						
    						else{
    							double d=(h2+this.GetNeau(i, j)-(_cellsHeightValuesCA.getCellState(i, j)))/2;
    							Neautmp[(i+_dx+1)%_dx][j]=this.GetNeau(i, j)-d;
    							Neautmp[i][j]=d;
    							
    						}
    												
    						
    					}
    					
    				}
    				else if(htmp==h3) 
    				{
    					if(this.GetNeau(i, j)+_cellsHeightValuesCA.getCellState(i, j)<=h3){
    						System.out.println("Bassin\n");
    					}
    					else{
    						if (!this.Eau(i , (j+_dy+1)%(_dy))){
    							this.SetEau(i , (j+_dy+1)%_dy, true);
    						}
    						
    						if(h3+this.GetNeau(i, j)<_cellsHeightValuesCA.getCellState(i, j)) {    							
    							Neautmp[i][(j+_dy+1)%_dy]=this.GetNeau(i,j);
    							Neautmp[i][j]=0;
    							 							
    						}
    						
    						else{
    							double d=(h3+this.GetNeau(i, j)-(_cellsHeightValuesCA.getCellState(i, j)))/2;
    							Neautmp[i][(j+_dy+1)%_dy]=this.GetNeau(i, j)-d;
    							Neautmp[i][j]=d;
    							
    						}
    												
    						
    					}
    				
    				}
    				else if(htmp==h4)
    				{
    					if(this.GetNeau(i, j)+_cellsHeightValuesCA.getCellState(i, j)<=h4){
    						System.out.println("Bassin\n");
    					}
    					else{
    						if (!this.Eau(i , (j+_dy-1)%(_dy))){
    							this.SetEau(i , (j+_dy-1)%(_dy), true);
    						}
    						
    						if(h4+this.GetNeau(i, j)<_cellsHeightValuesCA.getCellState(i, j)) {    							
    							Neautmp[i][(j+_dy-1)%_dy]=this.GetNeau(i,j);
    							Neautmp[i][j]=0;
    							System.out.println("Total\n");		
    						}
    						
    						else{
    							double d=(h4+this.GetNeau(i, j)-(_cellsHeightValuesCA.getCellState(i, j)))/2;
    							Neautmp[i][(j+_dy-1)%_dy]=this.GetNeau(i, j)-d;
    							Neautmp[i][j]=d;
    							System.out.println("partiel\n");
    						}
    												
    						
    					}
    				
    					
    				}
    				else{
    					
    					System.out.printf("error water height not found \n");
    					
    				}
    			
    			}
    		}
		
    	for ( int i = 0 ; i != _dx ; i++ )
    		for ( int j = 0 ; j != _dy ; j++ )
    		{
    			
    				if(this.Eau(i,j)) {
    					this.SetNeau(i, j, Neautmp[i][j]);
    					if(Neautmp[i][j]==0){
    						this.setCellState(i, j, 0);
    						this.SetEau(i, j, false);
    						System.out.println("test\n");
    					}
    					else{
    					this.setCellState(i,j,-1);
    					}
    				}
    			
    				else if ( this.getCellState(i,j) == 1 ) // tree?
	    			{
	    				// check if neighbors are burning
	    				if ( 
	    						(this.getCellState( (i+_dx-1)%(_dx) , j ) == 2 ||
	    						this.getCellState( (i+_dx+1)%(_dx) , j ) == 2 ||
	    						this.getCellState( i , (j+_dy+1)%(_dy) ) == 2 ||
	    						this.getCellState( i , (j+_dy-1)%(_dy) ) == 2)&&
	    						(Math.random() < 0.10)
	    					)
	    				{
	    					this.setCellState(i,j,2);
	
	    				}
	    				else if(
	    							(this.getCellState( (i+_dx-1)%(_dx) , (j+_dy+1)%(_dy)) == 2 ||
	    							this.getCellState( (i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) == 2 ||
	    							this.getCellState( (i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) == 2 ||
	    							this.getCellState( (i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) == 2)&&
	    							(Math.random() < 0.05)
	    						)
	    				{
	    					this.setCellState(i,j,2);
	    				}
	    						
	    				
	    				
	    				else if ( Math.random() < 0.00001 ) // spontaneously take fire ?
	    				{
	    					this.setCellState(i,j,2);
	    				}
	    				else
	    				{
	   						this.setCellState(i,j,1); // copied unchanged
	   					}
	    			}
	    			else if ( (this.getCellState( i , j )%10 == 2 )&&(Math.random()<0.10)) // burning?
	       				{
        					this.setCellState(i,j,3); 
	        						        					
	       				}
	    			

	    			else if (this.getCellState(i , j)==3)
	    				{
	        				this.setCellState(i,j,13);
	        			}
	    			
	    			else if (this.getCellState(i , j)==13)
						{
    						this.setCellState(i,j,23);
						}
			
	    			
	    			else if (this.getCellState(i , j)==23)
						{
    						this.setCellState(i,j,33);
						}
			
	    			
	    			else if (this.getCellState(i,j)==33)
	   					{
	       					this.setCellState(i, j, 0);
	   					}
	    			
	    			else if (this.getCellState(i,j)==4)
	    				{
	    				
	    				if ( 
	    						(this.getCellState( (i+_dx-1)%(_dx) , j ) == 12 ||
	    						this.getCellState( (i+_dx+1)%(_dx) , j ) == 12 ||
	    						this.getCellState( i , (j+_dy+1)%(_dy) ) == 12 ||
	    						this.getCellState( i , (j+_dy-1)%(_dy) ) == 12)&&
	    						(Math.random() < 0.10)
	    					)
	    				{
	    					this.setCellState(i,j,12);
	
	    				}
	    				else if(
	    							(this.getCellState( (i+_dx-1)%(_dx) , (j+_dy+1)%(_dy)) == 12 ||
	    							this.getCellState( (i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) == 12 ||
	    							this.getCellState( (i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) == 12 ||
	    							this.getCellState( (i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) == 12)&&
	    							(Math.random() < 0.05)
	    						)
	    				{
	    					this.setCellState(i,j,12);
	    				}
	    						
	    				else if(
    							(this.getCellState( (i+_dx-1)%(_dx) , (j+_dy+1)%(_dy)) == 2 ||
    							this.getCellState( (i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) == 2 ||
    							this.getCellState( (i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) == 2 ||
    							this.getCellState( (i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) == 2)&&
    							(Math.random() < 0.01)
    						)
	    				{				
	    					this.setCellState(i,j,12);
	    				}
    					
	    				
	    				
	    				else if(
    							(this.getCellState( (i+_dx-1)%(_dx) , (j+_dy+1)%(_dy)) == 2 ||
    							this.getCellState( (i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) == 2 ||
    							this.getCellState( (i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) == 2 ||
    							this.getCellState( (i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) == 2)&&
    							(Math.random() < 0.005)
    						)
	    				{
	    					this.setCellState(i,j,12);
	    				}
    					
	    				
	    				
	    				
	    				
	    				
	    				else if ( Math.random() < 0.000001 ) // spontaneously take fire ?
	    				{
	    					this.setCellState(i,j,12);
	    				}
	    				else
	    				{
	    				
	    					this.setCellState(i,j, this.getCellState(i,j) ); 
	    				}
	    				
	    				}
	    			
	    			
	    			else if(this.getCellState(i,j)==0)
	    				{	
	    				if ( 
	    						(this.getCellState( (i+_dx-1)%(_dx) , j ) == 1 ||
	    						this.getCellState( (i+_dx+1)%(_dx) , j ) == 1 ||
	    						this.getCellState( i , (j+_dy+1)%(_dy) ) == 1 ||
	    						this.getCellState( i , (j+_dy-1)%(_dy) ) == 1)&&
	    						(Math.random() < 0.0005)
	    					)
	    					
	    				{
	    					
	    				this.setCellState(i,j,1);	
	    					
	    				}
	    					    				
	    				
	    				else if ( 
	    						(this.getCellState( (i+_dx-1)%(_dx) , (j+_dy+1)%(_dy)) == 1 ||
    							this.getCellState( (i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) == 1 ||
    							this.getCellState( (i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) == 1 ||
    							this.getCellState( (i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) == 1)&&
    							(Math.random() < 0.0001)
	    					)
	    					
	    				{
	    					
	    				this.setCellState(i,j,1);	
	    					
	    				}
	    				
	    					
	    				else if ( 
	    						(this.getCellState( (i+_dx-1)%(_dx) , j ) == 4 ||
	    						this.getCellState( (i+_dx+1)%(_dx) , j ) == 4 ||
	    						this.getCellState( i , (j+_dy+1)%(_dy) ) == 4 ||
	    						this.getCellState( i , (j+_dy-1)%(_dy) ) == 4)&&
	    						(Math.random() < 0.001)
	    					)
	    					
	    				{
	    					
	    				this.setCellState(i,j,4);	
	    					
	    				}
	    					    				
	    				
	    				else if ( 
	    						(this.getCellState( (i+_dx-1)%(_dx) , (j+_dy+1)%(_dy)) == 4 ||
    							this.getCellState( (i+_dx+1)%(_dx) , (j+_dy+1)%(_dy) ) == 4 ||
    							this.getCellState( (i+_dx-1)%(_dx)  , (j+_dy-1)%(_dy) ) == 4 ||
    							this.getCellState( (i+_dx+1)%(_dx)  , (j+_dy-1)%(_dy) ) == 4)&&
    							(Math.random() < 0.0005)
	    					)
	    					
	    				{
	    					
	    				this.setCellState(i,j,4);	
	    					
	    				}
	    				else if(Math.random()<0.0000001){
	    					this.setCellState(i, j, 4);
	    				}
	    				
	    				
	    				else if(Math.random()<0.0000001){
	    					this.setCellState(i, j, 1);
	    				}
	    				
	    				else{
	    					this.setCellState(i,j, this.getCellState(i,j) ); 
	    				}
	    
	    				
	    				}
    					
	     			else		
	        									
	        				{
	        					this.setCellState(i,j, this.getCellState(i,j) ); // copied unchanged
	        			
	   						}
	        				
    				
	    			
	    			float color[] = new float[3];
	    			switch ( this.getCellState(i, j) )
	    			{
	    				case -1:
	    					color[0] = 0.f;
	    					color[1] = 0.f;
	    					color[2] = 0.3f;	 
	    					break; 					
	       				case 0:
	       					float height=(float)this.world.getCellHeight(i, j);
	       					color[0] = height / ( (float)this.world.getMaxEverHeight() );
	    					color[1] = 0.9f + 0.1f * height / ( (float)this.world.getMaxEverHeight() );
	    					color[2] = height / ( (float)this.world.getMaxEverHeight() );
	    					break;
	    				case 1:
	    					color[0] = 0.f;
	    					color[1] = 0.3f;
	    					color[2] = 0.f;
	    					break;
	    				case 2: // burning tree
	    					color[0] = 1.f;
	    					color[1] = 0.f;
	    					color[2] = 0.f;
	    					break;
	    					
	    				case 12:
	    					color[0] = 0.f;
	    					color[1] = 0.f;
	    					color[2] = 0.9f;
	    					break;
	    					
	    				case 3: // burnt tree
	    					color[0] = 0.f;
	    					color[1] = 0.f;
	    					color[2] = 0.f;	    					
	    					break;
	    					
	    				case 13: // burnt tree
	    					color[0] = 0.f;
	    					color[1] = 0.f;
	    					color[2] = 0.f;	    					
	    					break;
	    			
	    				case 23: // burnt tree
	    					color[0] = 0.f;
	    					color[1] = 0.f;
	    					color[2] = 0.f;	    					
	    					break;
	    					
	    				case 33: // burnt tree
	    					color[0] = 0.f;
	    					color[1] = 0.f;
	    					color[2] = 0.f;	    					
	    					break;
	    				case 4:
	    					color[0] = 0.5f;
	    					color[1] = 0.5f;
	    					color[2] = 0.f;	   
	    					break;
	    					
	    				default:
	    					color[0] = 0.5f;
	    					color[1] = 0.5f;
	    					color[2] = 0.5f;
	    					System.out.print("cannot interpret CA state: " + this.getCellState(i, j));
	    					System.out.println(" (at: " + i + "," + j + " -- height: " + this.world.getCellHeight(i,j) + " )");
	    		
	    			}	   
	    			this.world.cellsColorValues.setCellState(i, j, color);
    			
    		}
    	this.swapBuffer();
	}

	
}
