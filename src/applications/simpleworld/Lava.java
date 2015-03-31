package applications.simpleworld;

public class Lava {
	
	public Lava() {
		
	}

}
	for ( int i = 0 ; i != _dx ; i++ ){
		for ( int j = 0 ; j != _dy ; j++ ){
			
			//step lava
			if(this.Lave(i,j)){

				double h1, h2, h3, h4;
				
				h1= _cellsHeightValuesCA.getCellState((i+_dx-1)%(_dx) , j )+ this.GetNlave((i+_dx-1)%(_dx) , j );
    			h2= _cellsHeightValuesCA.getCellState((i+1)%(_dx) , j )+ this.GetNlave( (i+1)%(_dx) , j );
    			h3= _cellsHeightValuesCA.getCellState(i , (j+1)%(_dy) ) + this.GetNlave(i , (j+1)%(_dy) );       				
    			h4= _cellsHeightValuesCA.getCellState( i , (j+_dy-1)%(_dy) ) + this.GetNlave( i , (j+_dy-1)%(_dy) );
    				
				double htmp=Math.min(Math.min(h1,h2), Math.min(h3,h4));
				
				if(htmp==h1)
				
				{
					if(this.GetNlave(i, j)+_cellsHeightValuesCA.getCellState(i,j)<h1){
						
					}
					else{
						if(!this.Lave((i+_dx-1)%_dx,j)){
							this.SetLave((i+_dx-1)%_dx,j,true);
						}
						if(h1+this.GetNlave(i, j)<_cellsHeightValuesCA.getCellState(i, j)) {    							
							Nlavetmp[(i+_dx-1)%_dx][j]=Nlavetmp[(i+_dx-1)%_dx][j]+(this.GetNlave(i,j)/2);
							Nlavetmp[i][j]=Nlavetmp[i][j]-(this.GetNlave(i, j)/2); 
						}
						else{
							
							double d=(h1+this.GetNlave(i, j)-(_cellsHeightValuesCA.getCellState(i, j)))/4;
							Nlavetmp[(i+_dx-1)%_dx][j]=Nlavetmp[(i+_dx-1)%_dx][j]+d;
							Nlavetmp[i][j]=Nlavetmp[i][j]-d;
							
						}
						
					}
					
				
				}
				else if(htmp==h2)
					
				{
					if(this.GetNlave(i, j)+_cellsHeightValuesCA.getCellState(i,j)<h2){
						
					}
					else{
						if(!this.Lave((i+1)%_dx,j)){
							this.SetLave((i+1)%_dx,j,true);
						}
						if(h1+this.GetNlave(i, j)<_cellsHeightValuesCA.getCellState(i, j)) {    							
							Nlavetmp[(i+1)%_dx][j]=Nlavetmp[(i+1)%_dx][j]+(this.GetNlave(i,j)/2);
							Nlavetmp[i][j]=Nlavetmp[i][j]-(this.GetNlave(i, j)/2); 
						}
						else{
							
							double d=(h2+this.GetNlave(i, j)-(_cellsHeightValuesCA.getCellState(i, j)))/4;
							Nlavetmp[(i+1)%_dx][j]=Nlavetmp[(i+1)%_dx][j]+d;
							Nlavetmp[i][j]=Nlavetmp[i][j]-d;
							
						}
						
					}
					
				
				}
				else if(htmp==h3)
				{
					if(this.GetNlave(i, j)+_cellsHeightValuesCA.getCellState(i,j)<h3){
						
					}
					else{
						if(!this.Lave(i,(j+1)%_dy)){
							this.SetLave(i,(j+1)%_dy,true);
						}
						if(h1+this.GetNlave(i, j)<_cellsHeightValuesCA.getCellState(i, j)) {    							
							Nlavetmp[i][(j+1)%_dy]=Nlavetmp[i][(j+1)%_dy]+(this.GetNlave(i,j)/2);
							Nlavetmp[i][j]=Nlavetmp[i][j]-(this.GetNlave(i, j)/2); 
						}
						else{
							
							double d=(h3+this.GetNlave(i, j)-(_cellsHeightValuesCA.getCellState(i, j)))/4;
							Nlavetmp[i][(j+1)%_dy]=Nlavetmp[i][(j+1)%_dy]+d;
							Nlavetmp[i][j]=Nlavetmp[i][j]-d;
							
						}
						
					}
					
				
				}else if(htmp==h4)
				{
					if(this.GetNlave(i, j)+_cellsHeightValuesCA.getCellState(i,j)<h4){
						
					}
					else{
						if(!this.Lave(i,(j+_dy-1)%_dy)){
							this.SetLave(i,(j+_dy-1)%_dy,true);
						}
						if(h1+this.GetNlave(i, j)<_cellsHeightValuesCA.getCellState(i, j)) {    							
							Nlavetmp[i][(j+_dy-1)%_dy]=Nlavetmp[i][(j+_dy-1)%_dy]+(this.GetNlave(i,j)/2);
							Nlavetmp[i][j]=Nlavetmp[i][j]-(this.GetNlave(i, j)/2); 
						}
						else{
							
							double d=(h4+this.GetNlave(i, j)-(_cellsHeightValuesCA.getCellState(i, j)))/4;
							Nlavetmp[i][(j+_dy-1)%_dy]=Nlavetmp[i][(j+_dy-1)%_dy]+d;
							Nlavetmp[i][j]=Nlavetmp[i][j]-d;
							
						}
						
					}
					
				
				}
				
			}