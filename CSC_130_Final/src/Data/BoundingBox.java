package Data;

public class BoundingBox {
	private spriteInfo playerSprite;
	private int x1;
	private int x2;
	private int y1;
	private int y2;	
	private int latitude;
	private int longitude;
	
	public BoundingBox(int x1, int x2, int y1, int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;		
	}
	
	public BoundingBox(spriteInfo playerSprite, int latitude, int longitude) {
		this.playerSprite = playerSprite;
		this.latitude = latitude;
		this.longitude = longitude;
		this.x1 = playerSprite.getCoords().getX();
		this.y1 = playerSprite.getCoords().getY();
		this.x2 = playerSprite.getCoords().getX() + latitude;
		this.y2 = playerSprite.getCoords().getY() + longitude;
	}
	
	public String toString(){
		return "" + x1 + " " + x2 + " " + y1 + " " + y2;
	}
	
	/* Getters */
	public int getX1(){
		return x1;
	}
	
	public int getX2(){
		return x2;
	}
	
	public int getY1(){
		return y1;
	}
	
	public int getY2(){
		return y2;
	}
	
	/* Setters */
	public void setX1(int newValue){
		this.x1 = newValue;
	}
	
	public void setX2(int newValue){
		this.x2 = newValue;
	}
	
	public void setY1(int newValue){
		this.y1 = newValue;
	}
	
	public void setY2(int newValue){
		this.y2 = newValue;
	}
	
	public void adjustX1(int adjustment){
		// TODO: Change the previous value of x by adding the adjustment to the previous value (Relative assignment)
		// Backward adjustments can be made by passing a negative number as an adjustment
		this.x1 += adjustment;
	}
	
	public void adjustX2(int adjustment){
		// TODO: Change the previous value of x by adding the adjustment to the previous value (Relative assignment)
		// Backward adjustments can be made by passing a negative number as an adjustment
		this.x2 += adjustment;
	}
	
	public void adjustY1(int adjustment){
		// TODO: Change the previous value of y by adding the adjustment to the previous value (Relative assignment)
		// Backward adjustments can be made by passing a negative number as an adjustment
		this.y1 += adjustment;
	}
	
	public void adjustY2(int adjustment){
		// TODO: Change the previous value of y by adding the adjustment to the previous value (Relative assignment)
		// Backward adjustments can be made by passing a negative number as an adjustment
		this.y2 += adjustment;
	}

} 