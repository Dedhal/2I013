// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package landscapegenerator;

public class PerlinNoiseLandscapeGenerator {
	
	static final float
	PERSISTANCE = 0f;
	static final int
	NUMBER_OCTAVES = 7;
    public static double[][] generatePerlinNoiseLandscape ( int dxView, int dyView, double scaling, double landscapeAltitudeRatio, int perlinLayerCount )
    {
    	double landscape[][] = new double[dxView][dyView];

    	// A ECRIRE ! 
    	// ...
    	for(int x = 0; x < dxView; x++)
    	{
    		for(int y = 0; y < dyView; y++)
    		{
    			landscape[x][y] = PerlinNoise(Math.random()*2-1,Math.random()*2-1)*10;
    		}
    	}
    	// ...
    	// cf. http://freespace.virgin.net/hugo.elias/models/m_perlin.htm pour une explication


    	// scaling and polishing
    	landscape = LandscapeToolbox.scaleAndCenter(landscape, scaling, landscapeAltitudeRatio);
    	landscape = LandscapeToolbox.smoothLandscape(landscape);
    	
		return landscape;
    }
    
    public static double IntNoise(int x, int y)
    {
    	int n = x + y * 57;
    	double res;
    	n = (int)Math.pow((x<<13), x);
    	//y = ( 1.0 - ( (x * (x * x * 15731 + 789221) + 1376312589) & 7fffffff) / 1073741824.0);
    	n = n * ( n * n * 20143 + 102181) + 1303235369;
    	n = n & 0x7fffffff;
    	res = (double)1.0 - ( n / 1096174777.0);
    	return res;
    }
    
    public static double IntNoise1(int x, int y)
    {
    	int n = x + y * 57;
    	double res;
    	n = (int)Math.pow((x<<13), x);
    	//y = ( 1.0 - ( (x * (x * x * 15731 + 789221) + 1376312589) & 7fffffff) / 1073741824.0);
    	n = n * ( n * n * 15731 + 789221) + 1376312589;
    	n = n & 0x7fffffff;
    	res = (double)1.0 - ( n / 1073741824.0);
    	return res;
    }
    
    public static double IntNoise2(int x, int y)
    {
    	int n = x + y * 57;
    	double res;
    	n = (int)Math.pow((x<<13), x);
    	//y = ( 1.0 - ( (x * (x * x * 15731 + 789221) + 1376312589) & 7fffffff) / 1073741824.0);
    	n = n * ( n * n * 35803 + 600631) + 1303236413;
    	n = n & 0x7fffffff;
    	res = (double)1.0 - ( n / 1061741833.0);
    	return res;
    }
    
    public static double IntNoise3(int x, int y)
    {
    	int n = x + y * 57;
    	double res;
    	n = (int)Math.pow((x<<13), x);
    	//y = ( 1.0 - ( (x * (x * x * 15731 + 789221) + 1376312589) & 7fffffff) / 1073741824.0);
    	n = n * ( n * n * 18637 + 971851) + 1320267899;
    	n = n & 0x7fffffff;
    	res = (double)1.0 - ( n / 1058906617.0);
    	return res;
    }
    
    public static double SmoothNoise(int x, int y)
    {
    	double corners = ( IntNoise(x-1, y-1) + IntNoise(x+1, y-1) + IntNoise(x-1,y+1) + IntNoise(x+1, y+1)) / 16;
    	double sides = ( IntNoise(x+1, y) + IntNoise(x-1, y) + IntNoise(x, y+1) + IntNoise(x, y-1)) / 8;
    	double center = IntNoise(x, y) / 4;
    	
    	return corners + sides + center;
    }
    
    public static double SmoothNoise1(int x, int y)
    {
    	double corners = ( IntNoise1(x-1, y-1) + IntNoise1(x+1, y-1) + IntNoise1(x-1,y+1) + IntNoise1(x+1, y+1)) / 16;
    	double sides = ( IntNoise1(x+1, y) + IntNoise1(x-1, y) + IntNoise1(x, y+1) + IntNoise1(x, y-1)) / 8;
    	double center = IntNoise1(x, y) / 4;
    	
    	return corners + sides + center;
    }
    
    public static double SmoothNoise2(int x, int y)
    {
    	double corners = ( IntNoise2(x-1, y-1) + IntNoise2(x+1, y-1) + IntNoise2(x-1,y+1) + IntNoise2(x+1, y+1)) / 16;
    	double sides = ( IntNoise2(x+1, y) + IntNoise2(x-1, y) + IntNoise2(x, y+1) + IntNoise2(x, y-1)) / 8;
    	double center = IntNoise2(x, y) / 4;
    	
    	return corners + sides + center;
    }
    
    public static double SmoothNoise3(int x, int y)
    {
    	double corners = ( IntNoise3(x-1, y-1) + IntNoise3(x+1, y-1) + IntNoise3(x-1,y+1) + IntNoise3(x+1, y+1)) / 16;
    	double sides = ( IntNoise3(x+1, y) + IntNoise3(x-1, y) + IntNoise3(x, y+1) + IntNoise3(x, y-1)) / 8;
    	double center = IntNoise3(x, y) / 4;
    	
    	return corners + sides + center;
    }
    
    public static double LinearInterpolate(double a, double b, double x)
    {
    	
    	double ft = x * Math.PI;
    	double f = (1 - Math.cos(ft)) * 0.5;
    	return a * (1 - f) + b * f;
    }
    
    public static double Interpolate(double x, double y)
    {
    	int integer_x = (int)x;
    	double fractional_x = x - integer_x;
    	
    	int integer_y = (int)y;
    	double fractional_y = y - integer_y;
    	
    	double v1=SmoothNoise(integer_x, integer_y);
    	double v2=SmoothNoise(integer_x + 1, integer_y);
    	double v3=SmoothNoise(integer_x, integer_y + 1);
    	double v4=SmoothNoise(integer_x + 1, integer_y + 1);
    	
    	double i1 = LinearInterpolate(v1, v2, fractional_x);
    	double i2 = LinearInterpolate(v3, v4, fractional_x);
    	
    	return LinearInterpolate(i1, i2, fractional_y);
    }
    
    public static double Interpolate1(double x, double y)
    {
    	int integer_x = (int)x;
    	double fractional_x = x - integer_x;
    	
    	int integer_y = (int)y;
    	double fractional_y = y - integer_y;
    	
    	double v1=SmoothNoise1(integer_x, integer_y);
    	double v2=SmoothNoise1(integer_x + 1, integer_y);
    	double v3=SmoothNoise1(integer_x, integer_y + 1);
    	double v4=SmoothNoise1(integer_x + 1, integer_y + 1);
    	
    	double i1 = LinearInterpolate(v1, v2, fractional_x);
    	double i2 = LinearInterpolate(v3, v4, fractional_x);
    	
    	return LinearInterpolate(i1, i2, fractional_y);
    }
    
    public static double Interpolate2(double x, double y)
    {
    	int integer_x = (int)x;
    	double fractional_x = x - integer_x;
    	
    	int integer_y = (int)y;
    	double fractional_y = y - integer_y;
    	
    	double v1=SmoothNoise2(integer_x, integer_y);
    	double v2=SmoothNoise2(integer_x + 1, integer_y);
    	double v3=SmoothNoise2(integer_x, integer_y + 1);
    	double v4=SmoothNoise2(integer_x + 1, integer_y + 1);
    	
    	double i1 = LinearInterpolate(v1, v2, fractional_x);
    	double i2 = LinearInterpolate(v3, v4, fractional_x);
    	
    	return LinearInterpolate(i1, i2, fractional_y);
    }
    
    public static double Interpolate3(double x, double y)
    {
    	int integer_x = (int)x;
    	double fractional_x = x - integer_x;
    	
    	int integer_y = (int)y;
    	double fractional_y = y - integer_y;
    	
    	double v1=SmoothNoise3(integer_x, integer_y);
    	double v2=SmoothNoise3(integer_x + 1, integer_y);
    	double v3=SmoothNoise3(integer_x, integer_y + 1);
    	double v4=SmoothNoise3(integer_x + 1, integer_y + 1);
    	
    	double i1 = LinearInterpolate(v1, v2, fractional_x);
    	double i2 = LinearInterpolate(v3, v4, fractional_x);
    	
    	return LinearInterpolate(i1, i2, fractional_y);
    }
    
    public static double PerlinNoise(double x, double y)
    {
    	double total = 0;
    	float p = PERSISTANCE;
    	int n = NUMBER_OCTAVES - 1;
    	double frequency;
    	double amplitude;
    	
    	for(int i = 0; i < n ; i++)
    	{
    		frequency = Math.pow(2, i);
    		amplitude = Math.pow(p, i);
    		switch(i)
    		{
    			case 0:
    				total = total + Interpolate(x * frequency, y * frequency) * amplitude;
    				break;
    			case 1:
    				total = total + Interpolate2(x * frequency, y * frequency) * amplitude;
    				break;
    			case 2:
    				total = total + Interpolate1(x * frequency, y * frequency) * amplitude;
    				break;
    			case 3:
    				total = total + Interpolate3(x * frequency, y * frequency) * amplitude;
    				break;
    			case 4:
    				total = total + Interpolate(x * frequency, y * frequency) * amplitude;
    				break;
    			case 5:
    				total = total + Interpolate2(x * frequency, y * frequency) * amplitude;
    				break;
    			case 6:
    				total = total + Interpolate1(x * frequency, y * frequency) * amplitude;
    				break;
    			case 7:
    				total = total + Interpolate3(x * frequency, y * frequency) * amplitude;
    				break; 
    		}
    	}
    	
    	return total;
    	
    }
    
}
