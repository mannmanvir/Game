/* Game Final Project
 * Manvir Mann - CSC 130 
 * Professor Matthew Philipps
 * May 13, 2022
 */

package Main;

import java.awt.Color; 
import java.util.ArrayList;

import java.util.HashMap;
import java.util.StringTokenizer;

import Data.Vector2D;
import Data.BoundingBox;

import FileIO.EZFileRead;
import logic.Control;

import Data.spriteInfo;

public class Main{
	
	// Fields (Static) below...
	public static Vector2D currentVec = new Vector2D(600, 430); 	
	public static Vector2D vec1 = new Vector2D(0, 0); 			
	
	public static int frameCounter = 0; 	
	public static spriteInfo currentSprite = new spriteInfo(currentVec, "Stick"+frameCounter);
	public static spriteInfo newSpriteLoc = new spriteInfo (vec1, currentSprite.getTag()); 
	
	public static EZFileRead ezr = new EZFileRead("poseidon.txt");
	public static String raw;
	public static StringTokenizer st;
	public static HashMap<String, String> map = new HashMap<>();
	
	public static ArrayList<BoundingBox> boundingBoxes = new ArrayList<BoundingBox>(); 	
	public static ArrayList<spriteInfo> sprites = new ArrayList<spriteInfo>(); 	
	
	public static String trigger = ""; 			
	
	public static BoundingBox boxSprite;
	public static BoundingBox Rules = new BoundingBox(450, 600, 200, 250);
	public static BoundingBox Door = new BoundingBox(600, 800, 700, 900); 
	public static BoundingBox Trident = new BoundingBox(960, 1020, 200, 250); 
	
	public static boolean checkCollision(BoundingBox box1, BoundingBox box2){
		int w = box1.getX1();
		int x = box1.getY1();
		int y = box1.getX2();
	    int z = box1.getY2();
		
	    int i = box2.getX1();
		int q = box2.getY1();
		int o = box2.getX2();
		int p = box2.getY2();
		
		if ((y < i) || (w > o) || (z < q) || (x > p)) {
			return false;
		}
		else { 
			return true;
		}
	}

	public static void playerRebound(){
		int x = currentSprite.getCoords().getX();
		int y = currentSprite.getCoords().getY();
		int i = newSpriteLoc.getCoords().getX();
		int q = newSpriteLoc.getCoords().getY();
		
		if (y - q != 0){
			if (y - q > 0)		
				currentSprite.getCoords().adjustY(-8);
			if (y - q < 0)		
				currentSprite.getCoords().adjustY(+8);
		}
		else if (x - i != 0){
			if (x -i > 0)		
				currentSprite.getCoords().adjustX(-8);								
			if (x - i < 0)  	
				currentSprite.getCoords().adjustX(+8);
		}
	}

	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control();				/* Do NOT remove! */
		ctrl.gameLoop();							/* Do NOT remove! */
	}
	
	public static void start() {
		sprites.add(currentSprite); //adding actual player sprite in 
		
		boundingBoxes.add(new BoundingBox(-128, 1280, 0, 100));  	    //Boundary box for top of screen so character can't walk through walls. Purposely did not make it realistic in the terms of the character should technically kind of clip through the walls, in order to be reminiscent of "old school" flash games. 
		boundingBoxes.add(new BoundingBox(-128, 50, -128, 800));	    //Boundary box for left of screen so character can't walk through walls. Purposely did not make it realistic in the terms of the character should technically kind of clip through the walls, in order to be reminiscent of "old school" flash games.
		boundingBoxes.add(new BoundingBox(-128, 1280, 760, 800));	    //Boundary box for bottom of screen so character can't walk through walls. Purposely did not make it realistic in the terms of the character should technically kind of clip through the walls, in order to be reminiscent of "old school" flash games.
		boundingBoxes.add(new BoundingBox(1115, 1200, -128, 800));	    //Boundary box for right of screen so character can't walk through walls. Purposely did not make it realistic in the terms of the character should technically kind of clip through the walls, in order to be reminiscent of "old school" flash games.
		boundingBoxes.add(new BoundingBox(990, 1200, 50, 200));         //Boundary box for wedged trident so character can't walk through the tree, but it still looks like the trident is "within grasp" so the character can attempt to pull it out.  	
		
		if(!EZFileRead.doesFileExist("poseidon.txt")) {
			System.out.println("File not found!");
			return;
		}
		EZFileRead ezr = new EZFileRead("poseidon.txt");
		String str;
		for (int i = 0; i < ezr.getNumLines(); i++) {
			String raw = ezr.getLine(i);
			StringTokenizer st = new StringTokenizer(raw, "*");
			if (st.hasMoreTokens()){
				String Key = st.nextToken();
				String Value = st.nextToken();
				map.put(Key, Value);
				i++;
			}	
		}
	}
		
	
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) { 
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		ctrl.addSpriteToFrontBuffer(0, 0, "GameBackground"); //Rendering in the game background display first that was made in paint.net before adding the player sprite's hitbox after
		boxSprite = new BoundingBox(currentSprite, 30, 100);
		boxSprite.setX1(boxSprite.getX1() + 15);
		
		BoundingBox w = boxSprite;
				
		for (int i = 0; i < boundingBoxes.size(); i++)
			if (checkCollision(w, boundingBoxes.get(i)))
				playerRebound(); 
		
		for (int i = 0; i < sprites.size(); i++)
			ctrl.addSpriteToFrontBuffer(sprites.get(i).getCoords().getX(), sprites.get(i).getCoords().getY(), 
					sprites.get(i).getTag());
			ctrl.drawString(80, 265, trigger, Color.YELLOW);
	}
}