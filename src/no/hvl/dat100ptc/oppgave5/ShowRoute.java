package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double ystep;
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		ystep = MAPYSIZE / (Math.abs(maxlat - minlat)); 

		return ystep;
		
	}

	public void showRouteMap(int ybase) {

    setColor(0,255,0);
    
    final int RADIUS = 5;
    
    int i = 0, x = 0, y = 0;
    double la = 0, lo = 0, la2 = 0, lo2 = 0;
    
    double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
    double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
       
    while(i<gpspoints.length) {
    	
    la = maxlat - gpspoints[i].getLatitude();  
    lo = maxlon - gpspoints[i].getLongitude();
    		   
    la2 = la * ystep();
    lo2 = lo * xstep();
    
    y = (int)la2 + ybase - MAPYSIZE;
    x = MAPXSIZE - (int)lo2; 
    
    System.out.println("x: " + (int)lo2);   
    System.out.println((int)la2);	  	
     
    setColor(0,255,0);
    fillCircle(x, y, RADIUS);

    i++;
    
    }
   	
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		 String time = GPSUtils.formatTime(gpscomputer.totalTime());
		   double km = gpscomputer.totalDistance()/1000.0;
		   
		   String strKm = String.format("%.2f", km);
		   String strEle = String.format("%.2f", gpscomputer.totalElevation());
		   String strMspeed = String.format("%.2f", gpscomputer.maxSpeed());
		   String strAvspeed = String.format("%.2f", gpscomputer.averageSpeed());
		   String strKcal = String.format("%.2f", gpscomputer.totalKcal(80));
		   		 
		   drawString("Total time           :    "+ time, 10,TEXTDISTANCE);
		   drawString("Total distance    :     "+ strKm + " km", 10,TEXTDISTANCE*2);
		   drawString("Total elevation   :     "+ strEle + " m", 10,TEXTDISTANCE*3);
		   drawString("Max speed         :     "+ strMspeed+ " km/t",10,TEXTDISTANCE*4);
		   drawString("Average speed  :     "+ strAvspeed+ " km/t",10,TEXTDISTANCE*5);
		   drawString("Energy               :     "+ strKcal+ " kcal",10,TEXTDISTANCE*6);
				              
			
	}

}
