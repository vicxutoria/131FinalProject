package project;

public class Wind {

	private static int nrWindStates = 6;
	private double currentWind;
	
	public Wind(){
	}
	
	public double setRandomWind(){
		currentWind = (int)(Math.random()*((nrWindStates - 1)*2)) - nrWindStates + 1;
		currentWind *= 0.1;
		return currentWind;
	}
	
	public double getWind(){
		return currentWind;
	}
}
