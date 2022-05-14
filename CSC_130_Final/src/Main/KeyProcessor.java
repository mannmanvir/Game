/* This will handle the "Hot Key" system. */

package Main;

import logic.Control; 
import timer.stopWatchX;

import java.util.StringTokenizer;
import FileIO.EZFileRead;
import FileIO.EZFileWrite;
import java.util.HashMap;


public class KeyProcessor{
	// Static Fields
	private static char last = ' ';				// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250); 	
	private static stopWatchX timer = new stopWatchX(40); 	
	private static int i;
	private static int q; 							
	
	public static EZFileRead ezr = new EZFileRead("poseidon.txt");
	public static String raw;
	public static StringTokenizer st;
	public static HashMap<String, String> map = new HashMap<>();
	
	// Static Method(s)
	public static void processKey(char key){
		switch(key){
		
		case '%':								// ESC key							
			System.exit(0);
			break;
		
		case 'w':
			Main.trigger = "";
			if (timer.isTimeUp()){
				int x = Main.currentSprite.getCoords().getX();
				int y = Main.currentSprite.getCoords().getY();
				Main.newSpriteLoc.setCoords(x, y);
				Main.currentSprite.getCoords().adjustY(-8);
				Main.currentSprite.setTag("UpStick"+q); 
				q++;
				if (q>=4){
					q=0;
				}
				timer.resetWatch();
			}
			
			break;
		
		case 'a':
			Main.trigger = "";
			if (timer.isTimeUp()){
				int x = Main.currentSprite.getCoords().getX();
				int y = Main.currentSprite.getCoords().getY();
				Main.newSpriteLoc.setCoords(x, y);
				Main.currentSprite.getCoords().adjustX(-8);
				Main.currentSprite.setTag("ReversedStick"+i);
				i++;
				if (i>=4){
					i=0;
				}
				timer.resetWatch();
			}
			break;
			
		case 's':
			Main.trigger = "";
			if (timer.isTimeUp()){
				int x = Main.currentSprite.getCoords().getX();
				int y = Main.currentSprite.getCoords().getY();
				Main.newSpriteLoc.setCoords(x, y);
				Main.currentSprite.getCoords().adjustY(8);
				Main.currentSprite.setTag("DownStick"+q);
				q++;
				if (q>=4){
					q=0;
				}
				timer.resetWatch();
			}
			break;
		
		case 'd':
			Main.trigger = "";
			if (timer.isTimeUp()){
				int x = Main.currentSprite.getCoords().getX();
				int y = Main.currentSprite.getCoords().getY();
				Main.newSpriteLoc.setCoords(x, y);
				Main.currentSprite.getCoords().adjustX(8);
				Main.currentSprite.setTag("Stick"+i);
				i++;
				if (i>=4){
					i=0;
				}
				timer.resetWatch();
			}
			break;

		case '$':
			
			if(key == ' ')				return;
			// Debounce routine below...
			if(key == last)
				if(sw.isTimeUp()==false)			return;
			last = key;
			sw.resetWatch();

			if(Main.checkCollision(Main.boxSprite, Main.Rules)){
				EZFileWrite ezw = new EZFileWrite("poseidon.txt");
				if(!EZFileRead.doesFileExist("poseidon.txt")) {
					System.out.println("File not found!");
					return;
				}
				ezw.writeLine("line0*Poseidon: 'Where, where am I? There's a trident...and a door I should check them out.'");
				ezw.writeLine("line1*Poseidon: 'Hmmm, this is a door that appears to be locked.'");
				ezw.writeLine("line2*Poseidon: 'This golden trident is carved with runes and deeply wedged in the tree, I can't pull it out.'");
				ezw.saveFile();
				Main.trigger = ezr.getLine(0);
			} 
			
			if(Main.checkCollision(Main.boxSprite, Main.Door)){
				Main.trigger = ezr.getLine(1);
			} 
			
			if(Main.checkCollision(Main.boxSprite, Main.Trident)){
				Main.trigger = ezr.getLine(2);
			}
			
			break;
			/* Mouse Coordinates */ 
		case 'm':
			
			if(key == ' ')				return;
			// Debounce routine below...
			if(key == last)
				if(sw.isTimeUp()==false)			return;
			last = key;
			sw.resetWatch();
			
			Control.isMouseCoordsDisplayed=!Control.isMouseCoordsDisplayed;
			break;
		
		}
	}
}

