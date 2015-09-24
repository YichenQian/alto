package com.wx.shs.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {
	NetworkModel networkModel;
	
	Map<String, List<String>> dependencies;
	
	Map<String, DataTransferTask> idDataTransferTaskMap;
	
	Scheduler scheduler;
	
	public Manager(){
		networkModel = new NetworkModel();
		scheduler = new Scheduler();
		idDataTransferTaskMap = new HashMap<String, DataTransferTask>();
		dependencies = new HashMap<String, List<String>>();
	}

	public DataTransferTask getNextTaskForPortPair(PortPair pair){
		DataTransferTask result = null;
		List<DataTransferTask> tasks = pair.getWaitingQueue();
		if(tasks.size()==0){
			System.out.println("waiting task is null");
			return null;
		}
		System.out.println(tasks.size());
		result = scheduler.getNextTaskForPortPair(pair, networkModel, dependencies);
		return result;
	}
	
	public void addSyncTask(SyncTask task){
		List<String> inputs = task.getInputs();
		List<String> outputs = task.getOutputs();
		for(String taskId: inputs){
			dependencies.put(taskId, outputs);
			System.out.println("dependencies:"+taskId+" "+outputs.get(0));
		}
	}
	
	public void addDataTransferTask(DataTransferTask task){
		Port src = networkModel.getPortForEndhost(task.getSrc());
		Port dst = networkModel.getPortForEndhost(task.getDst());
		if(src==null){
			//System.out.println("port null");
			networkModel.portMissing(task.getSrc());
			if(dst==null)networkModel.portMissing(task.getDst());
		}
		else{
		    networkModel.addDataTransferTaskForPortPair(src.getId(), dst.getId(), task);
		    idDataTransferTaskMap.put(task.getId(), task);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Manager manager = new Manager();
		Port p1 = new Port();
		Port p2 = new Port();
		Port p3 = new Port();
		Port p4 = new Port();
		int p1Id = manager.networkModel.addPort(p1);
		int p2Id = manager.networkModel.addPort(p2);
		int p3Id = manager.networkModel.addPort(p3);
		int p4Id = manager.networkModel.addPort(p4);
		System.out.println(p4Id);//////////////3
		manager.networkModel.updateCostMap(p1Id, p2Id, 10);
		manager.networkModel.updateCostMap(p1Id, p3Id, 10);
		manager.networkModel.updateCostMap(p1Id, p4Id, 10);
		manager.networkModel.updateCostMap(p2Id, p3Id, 10);
		manager.networkModel.updateCostMap(p2Id, p4Id, 10);
		manager.networkModel.updateCostMap(p3Id, p4Id, 10);
		Endhost h1 = new Endhost("e1");
		Endhost h2 = new Endhost("e2");
		Endhost h3 = new Endhost("e3");
		Endhost h4 = new Endhost("e4");
		List<Endhost> h1List = new ArrayList<Endhost>();
		h1List.add(h1);
		List<Endhost> h2List = new ArrayList<Endhost>();
		h2List.add(h2);
		List<Endhost> h3List = new ArrayList<Endhost>();
		h3List.add(h3);
		List<Endhost> h4List = new ArrayList<Endhost>();
		h4List.add(h4);
		p1.setEndhosts(h1List);
		p2.setEndhosts(h2List);
		p3.setEndhosts(h3List);
		p4.setEndhosts(h4List);
		DataTransferTask task1 = new DataTransferTask("1");
		task1.setSrc(h1);
		task1.setDst(h3);
		task1.setFileSize(10);
		DataTransferTask task2 = new DataTransferTask("2");
		task2.setSrc(h1);
		task2.setDst(h4);
		task2.setFileSize(10);
		DataTransferTask task3 = new DataTransferTask("3");
		task3.setSrc(h1);
		task3.setDst(h3);
		task3.setFileSize(5);
		DataTransferTask task4 = new DataTransferTask("4");
		task4.setSrc(h1);
		task4.setDst(h4);
		task4.setFileSize(10);
		DataTransferTask task5 = new DataTransferTask("5");
		task5.setSrc(h2);
		task5.setDst(h3);
		task5.setFileSize(10);
		DataTransferTask task6 = new DataTransferTask("6");
		task6.setSrc(h2);
		task6.setDst(h4);
		task6.setFileSize(10);
		
		SyncTask stask1 = new SyncTask();
		List<String> inputs1 = new ArrayList<String>();
		inputs1.add("1");
		List<String> outputs1 = new ArrayList<String>();
		outputs1.add("2");
		stask1.setInputs(inputs1);
		stask1.setOutputs(outputs1);
		
		SyncTask stask2 = new SyncTask();
		List<String> inputs2 = new ArrayList<String>();
		inputs2.add("2");
		inputs2.add("3");
		List<String> outputs2 = new ArrayList<String>();
		outputs2.add("4");
		stask2.setInputs(inputs2);
		stask2.setOutputs(outputs2);
		
		SyncTask stask3 = new SyncTask();
		List<String> inputs3 = new ArrayList<String>();
		inputs3.add("4");
		List<String> outputs3 = new ArrayList<String>();
		outputs3.add("5");
		stask3.setInputs(inputs3);
		stask3.setOutputs(outputs3);
		
		SyncTask stask4 = new SyncTask();
		List<String> inputs4 = new ArrayList<String>();
		inputs4.add("5");
		List<String> outputs4 = new ArrayList<String>();
		outputs4.add("6");
		stask4.setInputs(inputs4);
		stask4.setOutputs(outputs4);
		
		manager.addDataTransferTask(task1);
		manager.addDataTransferTask(task2);
		manager.addDataTransferTask(task3);
		manager.addDataTransferTask(task4);
		manager.addDataTransferTask(task5);
		manager.addDataTransferTask(task6);
		
		manager.addSyncTask(stask1);
		manager.addSyncTask(stask2);
		manager.addSyncTask(stask3);
		manager.addSyncTask(stask4);

		for(Map.Entry<String, PortPair> entry: manager.networkModel.portPairMaps.entrySet()){
			PortPair pp = entry.getValue();
			if(manager.getNextTaskForPortPair(pp)==null)continue;
			System.out.println(pp.srcId+" "+pp.dstId+" "+manager.getNextTaskForPortPair(pp).getId());//manager.getNextTaskForPortPair(pp).getId()
		}
	}

}
