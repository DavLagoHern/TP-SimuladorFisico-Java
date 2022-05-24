package simulator.control;

import org.json.JSONObject;

public class NotEqualStatesException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public NotEqualStatesException() {super();}
	
	public NotEqualStatesException(String message) {super(message);}
	
	public NotEqualStatesException(String message, Throwable cause) {super(message, cause);}
	
	public NotEqualStatesException(Throwable cause) {super(cause);}
	
	public NotEqualStatesException(JSONObject s1, JSONObject s2, double step) {
		super("There has been a discrepancy between both \n" + s1 + " and \n" + s2 + " states, \n more specifically in step number " + step);
	}

}
