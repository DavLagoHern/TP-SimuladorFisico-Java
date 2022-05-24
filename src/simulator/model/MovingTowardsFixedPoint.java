package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	private Vector2D c;
	private double g;

	public MovingTowardsFixedPoint(Vector2D c, double g) {
		this.c = new Vector2D(c);
		this.g = g;
	};

	public void apply(List<Body> bodyList) {
		for (Body Bi : bodyList) {
			Bi.addForce(c.minus(Bi.getPosition()).direction().scale(g*Bi.getMass()));
		}
		
	}

	public String toString() {
		 return "Moving towards " + c + " with constant acceleration " +g;
	}

}
