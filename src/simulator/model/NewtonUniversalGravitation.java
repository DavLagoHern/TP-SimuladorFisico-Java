package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	private static double _G;

	public NewtonUniversalGravitation(double grav) {
		_G = grav;
	}

	public void apply(List<Body> listBodys) {
		Vector2D Ft;
		double moduleF;
		double denominator;
		Vector2D d;
		for (Body Bi : listBodys) {
			Ft = new Vector2D();
			for (Body Bj : listBodys) {
				if (Bi != Bj) {
					moduleF = 0;
					if (Bi.getMass() == 0.0)
						Bi.velocity = new Vector2D();
					// En el body inicializas la aceleracion a 0
					else {
						//numerator = Bi.getMass() * Bj.getMass(); // mi+mj
						denominator = Bi.getPosition().distanceTo(Bj.getPosition()); // |pj-bi|
						if (denominator != 0.0)
							moduleF = (_G * Bi.getMass() * Bj.getMass()) / Math.pow(denominator, 2); // f = G+ [(mi+mj)/|pj-bi|^2]
						else
							moduleF = 0.0;
						Ft =  Ft.plus(Bj.getPosition().minus(Bi.getPosition()).direction().scale(moduleF)); // F = d*f
					}
				}
				
			}
			Bi.addForce(Ft);
			
		}
	}

	public String toString() {
		return "Newton’s Universal Gravitation with G="+_G;
	}
}
