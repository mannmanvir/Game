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
	public static Vector2D currentVec = new Vector2D(600, 430); 	/* Holds current position of player */
	public static Vector2D vec1 = new Vector2D(0, 0); 			/* Holds previous position of player */
	public static int frameCounter = 0; 			/* To hold reference to current png */
	
	public static spriteInfo playerSprite = new spriteInfo(currentVec, "Stick"+frameCounter);
	public static spriteInfo newSpriteLoc = new spriteInfo (vec1, playerSprite.getTag()); 
	
	public static EZFileRead ezr = new EZFileRead("poseidon.txt");
	public static String raw;
	public static StringTokenizer st;
	public static HashMap<String, String> map = new HashMap<>();
	
	public static ArrayList<BoundingBox> boundingBoxes = new ArrayList<BoundingBox>(); 	
	public static ArrayList<spriteInfo> sprites = new ArrayList<spriteInfo>(); 	
	
	public static String trigger = ""; 				/* For toggling dialog */
	public static BoundingBox playerBox;
	public static BoundingBox Rules = new BoundingBox(450, 600, 200, 250);
	public static BoundingBox Door = new BoundingBox(600, 800, 700, 900); 
	public static BoundingBox Trident = new BoundingBox(960, 1020, 200, 250); 
	
	public static boolean checkCollision(BoundingBox box1, BoundingBox box2){
		if (((box1.getX1() > box2.getX2()) || (box1.getX2() < box2.getX1()) || (box1.getY1() > box2.getY2()) || (box1.getY2() < box2.getY1()))) {
			return false;
		}
		else { 
			return true;
		}
	}

	public static void bouncePlayer(){
		if ((playerSprite.getCoords().getX() - newSpriteLoc.getCoords().getX()) != 0){
			if ((playerSprite.getCoords().getX() - newSpriteLoc.getCoords().getX()) > 0)		// If moved from West to East
				playerSprite.getCoords().adjustX(-16);								
			if ((playerSprite.getCoords().getX() - newSpriteLoc.getCoords().getX()) < 0)  	// If moved from East to West
				playerSprite.getCoords().adjustX(+16);
		}
		if ((playerSprite.getCoords().getY() - newSpriteLoc.getCoords().getY()) != 0){
			if ((playerSprite.getCoords().getY() - newSpriteLoc.getCoords().getY()) > 0)		// If moved from North to South
				playerSprite.getCoords().adjustY(-16);
			if ((playerSprite.getCoords().getY() - newSpriteLoc.getCoords().getY()) < 0)		// If moved from South to North
				playerSprite.getCoords().adjustY(+16);
		}
	}

	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control();				/* Do NOT remove! */
		ctrl.gameLoop();							/* Do NOT remove! */
	}
	
	public static void start() {
		sprites.add(playerSprite); //adding actual player sprite in 
		
		boundingBoxes.add(new BoundingBox(-128, 1280, 0, 100));  	    //Boundary box for top of screen so character can't walk through walls. Purposely did not make it realistic in the terms of the character should technically kind of clip through the walls, in order to be reminiscent of "old school" flash games. 
		boundingBoxes.add(new BoundingBox(-128, 50, -128, 800));	    //Boundary box for left of screen so character can't walk through walls. Purposely did not make it realistic in the terms of the character should technically kind of clip through the walls, in order to be reminiscent of "old school" flash games.
		boundingBoxes.add(new BoundingBox(-128, 1280, 760, 800));	    //Boundary box for bottom of screen so character can't walk through walls. Purposely did not make it realistic in the terms of the character should technically kind of clip through the walls, in order to be reminiscent of "old school" flash games.
		boundingBoxes.add(new BoundingBox(1120, 1200, -128, 800));	    //Boundary box for right of screen so character can't walk through walls. Purposely did not make it realistic in the terms of the character should technically kind of clip through the walls, in order to be reminiscent of "old school" flash games.
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
		playerBox = new BoundingBox(playerSprite, 30, 100);
		playerBox.setX1(playerBox.getX1() + 15);
		
		for (int i = 0; i < boundingBoxes.size(); i++)
			if (checkCollision(playerBox, boundingBoxes.get(i)))
				bouncePlayer(); 
		
		for (int i = 0; i < sprites.size(); i++)
			ctrl.addSpriteToFrontBuffer(sprites.get(i).getCoords().getX(), sprites.get(i).getCoords().getY(), 
					sprites.get(i).getTag());
			ctrl.drawString(80, 265, trigger, Color.WHITE);
	}
}