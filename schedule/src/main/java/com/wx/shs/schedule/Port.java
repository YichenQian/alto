package com.wx.shs.schedule;

import java.util.ArrayList;
import java.util.List;

public class Port {
	int id;
	
	List<Endhost> endhosts;
	
	public Port(){
		endhosts = new ArrayList<Endhost>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Endhost> getEndhosts() {
		return endhosts;
	}

	public void setEndhosts(List<Endhost> endhosts) {
		this.endhosts = endhosts;
	}

}
