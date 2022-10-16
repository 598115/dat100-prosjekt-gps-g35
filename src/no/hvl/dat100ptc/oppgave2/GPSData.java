package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {
		
		this.gpspoints = new GPSPoint[n];
		this.antall = 0;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;
		
		if (this.antall<this.gpspoints.length) {
			
			gpspoints[antall] = gpspoint;
			inserted = true;			
		}
		
		this.antall++;		
	    return inserted;
	    
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

    GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);
        
	boolean inserted = insertGPS(gpspoint);
	return inserted;	
	
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for (int i=0; i<gpspoints.length; i++) {
			
			GPSPoint gpspoint = gpspoints[i];
			System.out.println(gpspoint.toString());
		}
		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
