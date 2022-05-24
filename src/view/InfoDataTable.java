package view;

public class InfoDataTable {
	String id;
	String value;
	String desc;
	
	
	public InfoDataTable(String i, String v, String d) {
		id = i;
		value = v;
		desc = d;
	}


	public String getId() {
		return id;
	}


	public String getValue() {
		return value;
	}


	public String getDesc() {
		return desc;
	}


	public void setValue(String u) {
		value = u;
		
	}
	

	
	
	
}
