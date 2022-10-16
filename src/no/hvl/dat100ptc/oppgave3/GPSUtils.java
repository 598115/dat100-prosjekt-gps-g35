package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;
		
		min = da[0];
	
	for (double d : da) {
		if (d < min) {
			min = d;
		}
	}
	
        return min;
}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] latitudes = new double[gpspoints.length];
		
		for (int i=0; i<gpspoints.length; i++) {
			
		GPSPoint point = gpspoints[i];
		double lati = point.getLatitude();
		latitudes[i] = lati;
			
		}
		
		return latitudes;

	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

        double[] longitudes = new double[gpspoints.length];
		
		for (int i=0; i<gpspoints.length; i++) {
			
		GPSPoint point = gpspoints[i];
		double longi = point.getLongitude();
		longitudes[i] = longi;
			
		}

        return longitudes;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double a, c, d;
		       
		double latitude1 = Math.toRadians(gpspoint1.getLatitude()), longitude1 = Math.toRadians(gpspoint1.getLongitude());
		double latitude2 = Math.toRadians(gpspoint2.getLatitude()), longitude2 = Math.toRadians(gpspoint2.getLongitude());
		
		double latitudeDiff = latitude2-latitude1;
		double longitudeDiff = longitude2-longitude1; 
		
		a = (Math.sin(latitudeDiff / 2) * (Math.sin(latitudeDiff / 2)) +				
		(Math.cos(latitude1) * Math.cos(latitude2)) *				
		(Math.sin(longitudeDiff)/2) * Math.sin(longitudeDiff/2));
			
		c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		d = R * c;
		
		return d;			

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		secs = (gpspoint2.getTime()) - (gpspoint1.getTime());

		double MeterPerSekund = (distance(gpspoint1, gpspoint2) / secs);
         
		speed = MeterPerSekund * 3.6;
		
		return speed;		

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		
		int timer, minutter, sekunder;

		timer = secs / 3600;
		    
		minutter = (secs % 3600) / 60;
		    
		sekunder = secs - minutter*60 - timer * 3600;
		
		timestr = String.format("  %02d%s%02d%s%02d", timer, TIMESEP, minutter, TIMESEP, sekunder);
		
		return timestr;

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;

	      int i = (int) ((d+0.005) * 100);   
		  double dAv = (double)i / 100;	
		
		str = String.format("%" + TEXTWIDTH + ".2f", dAv);
		
	return str;
		
	}
}
