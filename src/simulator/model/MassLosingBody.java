package simulator.model;

import simulator.misc.Vector2D;

public class MassLosingBody extends Body {
	private double lossFactor;
	private double lossFrequency;
	private double cont;

	public MassLosingBody(String id, Vector2D velocity, Vector2D position, double mass, double factor, double freq) {
		super(id, velocity, position, mass);
		lossFactor = factor;
		lossFrequency = freq;
		cont = 0.0;
	}

	public void move(double t) {
		super.move(t);
		cont += t;
		if (cont >= lossFrequency) {
			mass = mass * (1 - lossFactor);
			cont = 0.0;
		}
	}
	
	public double getFactor() {
		return lossFactor;
	}
	
	public double getFrequency() {
		return lossFrequency;
	}
}
