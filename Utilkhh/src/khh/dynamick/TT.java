package khh.dynamick;

import khh.debug.LogK;

public class TT {
	private DynamicInputTest test;
	LogK log = LogK.getInstance();
	private String id;
	
	public TT() {
	}

	public TT(DynamicInputTest test) {
		log.debug("new TT   con    Dynamin  "+test);
		this.test = test;
	}
	public TT(String id) {
		log.debug("new TT   con    String  "+ id);
		this.id=id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
