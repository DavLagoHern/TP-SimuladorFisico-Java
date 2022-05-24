package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected String id;
	protected Vector2D velocity;
	protected Vector2D position;
	protected double mass;
	protected Vector2D force;

	public Body(String id, Vector2D velocity, Vector2D position, double mass) {
		this.id = id;
		this.velocity = new Vector2D(velocity);
		this.position = new Vector2D(position);
		this.mass = mass;
		this.force = new Vector2D();

	};

	public String getId() {
		return id;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public Vector2D getPosition() {
		return position;
	}

	public double getMass() {
		return mass;
	}

	public Vector2D getForce() {
		return force;
	}

	// ---------------------------------

	public void addForce(Vector2D a) {
		force = force.plus(a);
	}

	public void resetForce() {
		this.force = new Vector2D();
	}

	public void move(double t) {
		Vector2D aceleration;
		if (mass == 0)
			aceleration = new Vector2D();
		else
			aceleration = new Vector2D(force.scale(1 / mass));
		position = position.plus((velocity.scale(t)).plus(aceleration.scale(t*t/2)));
		velocity = velocity.plus(aceleration.scale(t));
	}

	// ----------------------------------

	public boolean equals(Body b) {
		return this.getId().equals(b.getId());
	}

	public JSONObject getState() {
		JSONObject info = new JSONObject();
		info.put("id", getId());
		info.put("m", getMass());
		info.put("p", getPosition().asJSONArray());
		info.put("v", getVelocity().asJSONArray());
		info.put("f", getForce().asJSONArray());
		return info;
	}

	public String toString() {
		return getState().toString();

	}
}
