package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {
			
	private static final int MARGIN = 50;
	private static final int BARHEIGHT = 200; // assume no speed above 200 km/t

	private GPSComputer gpscomputer;
	private GPSPoint[] gpspoints;
	
	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}
	
	// read in the files and draw into using EasyGraphics
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length-1; // number of data points
		
		makeWindow("Speed profile", 2*MARGIN + 2 * N, 2 * MARGIN + BARHEIGHT);
		
		showSpeedProfile(MARGIN + BARHEIGHT,N);
	}
	
	public void showSpeedProfile(int ybase, int N) {

		// get segments speeds from the GPS computer object		
		double[] speeds = gpscomputer.speeds();

		int x = MARGIN,y;
		
		int x2 = x, y2 = 0;;
				
		int i = 0, speedsum = 0;
		
		while(i<speeds.length) {
			
		     y = ybase - (int)speeds[i];
			
		     if(y < ybase && y > 0) {
			
			    setColor(0,0,255);
			
			    drawLine(x,ybase,x,y);
			
			    speedsum += y;								
		     }
		     x += 2;
		     i++;
		}
		
		y2 = speedsum/(i+1);
		
	    if(y2 < ybase && y2 > 0) {
			
		setColor(0,255,0);
		
		drawLine(x2,y2,x,y2);	
			
		}
	}
}
