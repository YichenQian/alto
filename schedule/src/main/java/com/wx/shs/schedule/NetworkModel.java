package com.wx.shs.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NetworkModel {
    Map<Integer, Map<Integer, Double>> costMap;// port cost
    
    boolean updated;
    
    List<Port> ports;
    
    List<PortPair> portPairs;
    
    public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}

	ConcurrentMap<String, PortPair> portPairMaps;//key is srcId_dstId
    
    public ConcurrentMap<String, PortPair> getPortPairMaps() {
		return portPairMaps;
	}

	public void setPortPairMaps(ConcurrentMap<String, PortPair> portPairMaps) {
		this.portPairMaps = portPairMaps;
	}

	public List<PortPair> getPortPairs() {
		return portPairs;
	}

	public void setPortPairs(List<PortPair> portPairs) {
		this.portPairs = portPairs;
	}

	public NetworkModel(){
    	costMap = new HashMap<Integer, Map<Integer, Double>>();
    	ports = new ArrayList<Port>();
    	portPairs = new ArrayList<PortPair>();
    	portPairMaps = new ConcurrentHashMap<String, PortPair>();
    }
	
	public void addDataTransferTaskForPortPair(int srcId, int dstId, DataTransferTask task){
		String key = srcId+" "+dstId;
		if(this.portPairMaps.containsKey(key)){
		    PortPair pp = this.portPairMaps.get(key);
		    pp.addDataTransferTask(task);
		    this.portPairMaps.put(key, pp);
		    this.portPairs.add(pp);
		}else{
			PortPair pp = new PortPair();
			pp.setSrcId(srcId);
			pp.setDstId(dstId);
			pp.waitingQueue.add(task);
			this.portPairMaps.put(key, pp);
			this.portPairs.add(pp);
		}
	}
    
    public void updateCostMap(int src, int dst, double cost){
    	if(costMap.containsKey(src)){
    		Map<Integer, Double> dstCostTemp = costMap.get(src);
    		dstCostTemp.put(dst, cost);
    		costMap.put(src, dstCostTemp);
    	}else{
    		Map<Integer, Double> dstCost = new HashMap<Integer, Double>();
    		dstCost.put(dst, cost);
    		costMap.put(src, dstCost);
    	}
    	//updateCostMap(dst, src, cost);
    }
    
    public void updateCostMapALL(){
    	
    }
    
    public void portMissing(Endhost endhost){
    	
    }
    
    public int addPort(Port p){
    	int id = this.ports.size();
    	p.setId(id);
    	this.ports.add(p);
    	for(Map.Entry<Integer, Map<Integer, Double>> entry: costMap.entrySet()){
    		Map<Integer, Double> dstsMap = entry.getValue();
    		dstsMap.put(id, 0.0);
    		entry.setValue(dstsMap);
    	}
    	Map<Integer, Double> tempMap = new HashMap<Integer, Double>();
    	for(int i = 0; i < ports.size(); i++){
    		Port tempP = ports.get(i);
    		tempMap.put(tempP.getId(), 0.0);
    	}
    	costMap.put(id, tempMap);
    	updateCostMapALL();
    	return id;
    }
    
    public void removePort(String portId){
    	
    }
    
    public Port getPortForEndhost(Endhost endhost){
    	for(int i = 0; i < ports.size(); i++){
    		Port p = ports.get(i);
    		for(Endhost e: p.getEndhosts()){
    			if(e.id.equals(endhost.id)){
    				System.out.println("port:"+p.id);
    				return p;
    			}
    		}
    	}
    	return null;
    }
    
    public double getCostForPorts(Port srcPort, Port dstPort){
    	double d = 0.0;
    	int srcId = srcPort.getId();
    	int dstId = dstPort.getId();
    	d = this.costMap.get(srcId).get(dstId);
    	return d;
    }
    
    public static void main(String[] args){
    	NetworkModel nm = new NetworkModel();
    	Port p1 = new Port();
    	Port p2 = new Port();
    	Port p3 = new Port();
    	Port p4 = new Port();
    	
    	int p1id = nm.addPort(p1);
    	int p2id = nm.addPort(p2);
    	int p3id = nm.addPort(p3);
    	int p4id = nm.addPort(p4);
    	
    	System.out.println(nm.costMap.get(p1id).size());
    	System.out.println(nm.costMap.get(p2id).size());
    	System.out.println(nm.costMap.get(p3id).size());
    	System.out.println(nm.costMap.get(p4id).size());
    	
    }
}
