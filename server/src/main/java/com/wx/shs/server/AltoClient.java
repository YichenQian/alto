package com.wx.shs.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.altoclient.AltoClient.AltoClientCostMap;
import org.altoclient.AltoClient.AltoClientCostMapSwapInfo;
import org.altoclient.AltoClient.CostMap;
import org.altoclient.AltoClient.CostType;

import com.wx.shs.schedule.Endhost;
import com.wx.shs.schedule.NetworkModel;
import com.wx.shs.schedule.Port;
import com.wx.shs.schedule.PortPair;

import RFC7285Model.RFC7285CostMap;

public class AltoClient {

	NetworkModel networkModel;
	
	String altoServerAddress;
	
	public AltoClient(){
		altoServerAddress = "";
		AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
        AltoClientCostMap altoClientCostMap;
	}
	
	public void setNetworkModel(NetworkModel networkModel){
		this.networkModel = networkModel;
	}
	
	public void registerSSE(){////////////////////alternative
		
	}
	
	private List<String> getSrcList(){//////assume pid
		List<String> srcList = new ArrayList<String>();
		List<PortPair> ppList = networkModel.getPortPairs();
		for(PortPair pp: ppList){
			srcList.add(String.valueOf(pp.getSrcId()));
		}
		return srcList;
	}
	
	private List<String> getDstList(){//////assume pid
		List<String> dstList = new ArrayList<String>();
		List<PortPair> ppList = networkModel.getPortPairs();
		for(PortPair pp: ppList){
			dstList.add(String.valueOf(pp.getDstId()));
		}
		return dstList;
	}
	
	public void updateNetworkModel(){
		AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
		
		List<String> srcList = getSrcList();
		List<String> dstList = getDstList();
		
		CostType postCostType = new CostType();
		
		postCostType.setName("rtg-num ");///////////bandwidth
        postCostType.setMode("numerical");
        postCostType.setMetric("routingcost");
        
        AltoClientCostMap altoClientCostMap = new AltoClientCostMap("alto.alcatel-lucent.com:8000", "costs/filtered", postCostType,
        		srcList, dstList, null);
        altoClientCostMap.getInfo(costMapInfo);
        
        List<CostMap> costMapList = costMapInfo.getCostMapList();
        
        for (CostMap loop : costMapList) {
            System.out.println(loop.toStringLinked());
            networkModel.updateCostMap(Integer.parseInt(loop.getSrcPID()), Integer.parseInt(loop.getSrcPID()), loop.getCost());
        }
        networkModel.setUpdated(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
