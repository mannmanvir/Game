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
	private static char last = ' ';							// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250); 	// Debouncing Timer
	private static stopWatchX timer = new stopWatchX(15); 	// Timer for movement
	private static int i, j; 								// For incrementing sprite tag numbers
	
	public static EZFileRead ezr = new EZFileRead("poseidon.txt");
	public static String raw;
	public static StringTokenizer st;
	public static HashMap<String, String> map = new HashMap<>();
	
	// Static Method(s)
	public static void processKey(char key){
		switch(key){
		case '%':								// ESC key
			if(key == ' ')				return;
			// Debounce routine below...
			if(key == last)
				if(sw.isTimeUp() == false)			return;
			last = key;
			sw.resetWatch();
			
			System.exit(0);
			break;
		
		case 'w':
			Main.trigger = "";
			if (timer.isTimeUp()){
				Main.newSpriteLoc.setCoords(Main.playerSprite.getCoords().getX(), Main.playerSprite.getCoords().getY());
				Main.playerSprite.getCoords().adjustY(-16);
				Main.playerSprite.setTag("UpStick"+j); 
				j++;
				if (j >= 4){
					j = 0;
				}
				timer.resetWatch();
			}
			
			break;
		
		/* Move West */	
		case 'a':
			Main.trigger = ""; 										// Toggles off dialogue text
			if (timer.isTimeUp()){									
				Main.newSpriteLoc.setCoords(Main.playerSprite.getCoords().getX(), Main.playerSprite.getCoords().getY());
				Main.playerSprite.getCoords().adjustX(-16);
				Main.playerSprite.setTag("ReversedStick"+i);
				i++;
				if (i >= 4){
					i = 0;
				}
				timer.resetWatch();
			}
			break;
			
		/* Move South */
		case 's':
			Main.trigger = "";
			if (timer.isTimeUp()){
				Main.newSpriteLoc.setCoords(Main.playerSprite.getCoords().getX(), Main.playerSprite.getCoords().getY());
				Main.playerSprite.getCoords().adjustY(16);
				Main.playerSprite.setTag("DownStick"+j);
				j++;
				if (j >= 4){
					j = 0;
				}
				timer.resetWatch();
			}
			break;
		
		/* Move East */	
		case 'd':
			Main.trigger = "";
			if (timer.isTimeUp()){
				Main.newSpriteLoc.setCoords(Main.playerSprite.getCoords().getX(), Main.playerSprite.getCoords().getY());
				Main.playerSprite.getCoords().adjustX(16);
				Main.playerSprite.setTag("Stick"+i);
				i++;
				if (i >= 4){
					i = 0;
				}
				timer.resetWatch();
			}
			break;
			/* Spacebar */	
		case '$':
			
			if(key == ' ')				return;
			// Debounce routine below...
			if(key == last)
				if(sw.isTimeUp() == false)			return;
			last = key;
			sw.resetWatch();

			if(Main.checkCollision(Main.playerBox, Main.Rules)){
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
			
			if(Main.checkCollision(Main.playerBox, Main.Door)){
				Main.trigger = ezr.getLine(1);
			} 
			
			if(Main.checkCollision(Main.playerBox, Main.Trident)){
				Main.trigger = ezr.getLine(2);
			}
			
			break;
			/* Mouse Coordinates */ 
		case 'm':
			
			if(key == ' ')				return;
			// Debounce routine below...
			if(key == last)
				if(sw.isTimeUp() == false)			return;
			last = key;
			sw.resetWatch();
			
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
		
		}
	}
}

