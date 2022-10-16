package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	public double totalDistance() {

		double distance = 0;
		int i = 1;
		GPSPoint point1, point2;
		
  if(gpspoints.length > 1) {
	  
	while (i < gpspoints.length) {
		
		point1 = gpspoints[i-1];
		point2 = gpspoints[i];
		
		distance += GPSUtils.distance(point1, point2);		
		i++;
	 }
  }	else distance = 0;	
  
	return distance;	
	
 }

	
	public double totalElevation() {

		double elevation = 0;
		int i = 1;

	GPSPoint point1, point2;	
	
	if (gpspoints.length > 1) {
	
		while(i<gpspoints.length) {
			
	    point1 = gpspoints[i-1];
	    point2 = gpspoints[i];
	
	    double ele1 = point1.getElevation();
	    double ele2 = point2.getElevation();
	    
	    if (ele1<ele2) { elevation += ele2-ele1; }
	    i++;    
		}
	}
	
	return elevation;
	
  }

	
	public int totalTime() {
	
	int i = 1;
		
	int totalTid = 0;
	
	if(gpspoints.length > 1) {
		
		while(i<gpspoints.length) {
		
		totalTid += gpspoints[i].getTime() - gpspoints[i-1].getTime();
		
		i++;
		}	
	}
         return totalTid;
 }
		

	public double[] speeds() {
		
    double[] speeds = new double[gpspoints.length-1];
    
    int i = 1;
    
    if (gpspoints.length > 1) {
    	
       while (i<gpspoints.length) {	
    	   
       speeds[i-1] = GPSUtils.speed(gpspoints[i-1], gpspoints[i]);   
       
       i++;	   
       }
    	
    }
		
	return speeds;	

  }
	
	public double maxSpeed() {
		
		
		double maxspeed = 0;
		int i = 1;
		GPSPoint point1, point2;
		
		if (gpspoints.length > 1) {	
			
		   while (i<gpspoints.length) {	
			
			point1 = gpspoints[i-1];
			point2 = gpspoints[i];
			
		      if (maxspeed < GPSUtils.speed(point1, point2)) {	
		    	  maxspeed = GPSUtils.speed(point1, point2);		    	  
		      }
			i++;
		   }
					
		 }
		return maxspeed;		
	}

	public double averageSpeed() {
		
		double average = 0;
		double distance = totalDistance();
		double time = totalTime();
		
		average = (distance / time) * 3.6;
        
		return average;
}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;
		double timer = secs / 3600.0;

	    if (speedmph < 10) {met = 4.0;}
	    else if (speedmph <= 12 && speedmph >= 10) {met = 6.0;}
	    else if (speedmph <= 14 && speedmph > 12) {met = 8.0;}
	    else if (speedmph <= 16 && speedmph > 14) {met = 10.0;}
	    else if (speedmph <= 20 && speedmph > 16) {met = 12.0;}
	    else if (speedmph > 20) {met = 16.0;}
	    	    
	    kcal = met * weight * timer;
	    
	    return kcal;
	    
	}

	public double totalKcal(double weight) {

		double total = 0;
	    
	    double vekt = weight;
		 
		double averageSpeed = averageSpeed();
		int time = totalTime();
		
		total = kcal(vekt, time, averageSpeed);
		
			   	
        return total;
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {
		
	   String time = GPSUtils.formatTime(totalTime());
	   double km = totalDistance()/1000.0;
	   
	   String strKm = String.format("%.2f", km);
	   String strEle = String.format("%.2f", totalElevation());
	   String strMspeed = String.format("%.2f", maxSpeed());
	   String strAvspeed = String.format("%.2f", averageSpeed());
	   String strKcal = String.format("%.2f", totalKcal(WEIGHT));
	   

	   System.out.println("==============================================\n"
			              +"Total time     :     "+ time + "\n"
			              +"Total distance :     "+ strKm + " km\n"
			              +"Total elevation:     "+ strEle + " m\n"
			              +"Max speed      :     "+ strMspeed + " km/t\n"
			              +"Average speed  :     "+ strAvspeed + "km/t\n"
			              +"Energy         :     "+ strKcal + " kcal\n"
			              +"==============================================\n");
			   			            	  
	}

}
