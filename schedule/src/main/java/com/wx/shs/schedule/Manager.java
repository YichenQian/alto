package com.wx.shs.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Manager {
	NetworkModel networkModel;
	
	ConcurrentMap<String, List<String>> dependencies;
	
	public NetworkModel getNetworkModel() {
		return networkModel;
	}

	public void setNetworkModel(NetworkModel networkModel) {
		this.networkModel = networkModel;
	}

	ConcurrentMap<String, DataTransferTask> waitingDataTransferTaskMap;
	
	Scheduler scheduler;
	
	ConcurrentMap<String, List<DataTransferTask>> jobDataTransferTaskMap;
	
	ConcurrentMap<String, List<SyncTask>> jobSyncTaskMap;
	
	public Manager(){
		networkModel = new NetworkModel();
		scheduler = new Scheduler();
		waitingDataTransferTaskMap = new ConcurrentHashMap<String, DataTransferTask>();
		dependencies = new ConcurrentHashMap<String, List<String>>();
		jobDataTransferTaskMap = new ConcurrentHashMap<String, List<DataTransferTask>>();
		jobSyncTaskMap = new ConcurrentHashMap<String, List<SyncTask>>();
	}
	
	public void configNetworkModelFromLocal(){
		Port p1 = new Port();
		Port p2 = new Port();
		Port p3 = new Port();
		Port p4 = new Port();
		
		Endhost h1 = new Endhost("h1");
		Endhost h2 = new Endhost("h2");
		Endhost h3 = new Endhost("h3");
		Endhost h4 = new Endhost("h4");
		
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
		
	    int p1Id = networkModel.addPort(p1);
	    int p2Id = networkModel.addPort(p2);
	    int p3Id = networkModel.addPort(p3);
	    int p4Id = networkModel.addPort(p4);
	    
	    networkModel.updateCostMap(p1Id, p1Id, 0.0);
	    networkModel.updateCostMap(p1Id, p2Id, 5.0);
	    networkModel.updateCostMap(p1Id, p3Id, 9.0);
	    networkModel.updateCostMap(p1Id, p4Id, 4.0);
	    networkModel.updateCostMap(p2Id, p1Id, 5.0);
	    networkModel.updateCostMap(p2Id, p2Id, 0.0);
	    networkModel.updateCostMap(p2Id, p3Id, 6.0);
	    networkModel.updateCostMap(p2Id, p4Id, 7.0);
	    networkModel.updateCostMap(p3Id, p1Id, 9.0);
	    networkModel.updateCostMap(p3Id, p2Id, 6.0);
	    networkModel.updateCostMap(p3Id, p3Id, 0.0);
	    networkModel.updateCostMap(p3Id, p4Id, 1.0);
	    networkModel.updateCostMap(p4Id, p1Id, 4.0);
	    networkModel.updateCostMap(p4Id, p2Id, 7.0);
	    networkModel.updateCostMap(p4Id, p3Id, 1.0);
	    networkModel.updateCostMap(p4Id, p2Id, 0.0);
	}
	public String generateJob(){
		String jobId = "job-"+jobDataTransferTaskMap.size();
		List<DataTransferTask> lists = new ArrayList<DataTransferTask>();
		jobDataTransferTaskMap.put(jobId, lists);
		
		String jobId2 = "job-"+jobSyncTaskMap.size();
		List<SyncTask> lists2 = new ArrayList<SyncTask>();
		jobSyncTaskMap.put(jobId2, lists2);
		
		if(!jobId.equals(jobId2))return null;
		
		return jobId;
	}
	
	private String generateDataTransferTaskId(String jobId){
		String taskId = jobId+"-dtt-"+jobDataTransferTaskMap.get(jobId).size();
		return taskId;
	}
	
	private String generateSyncTaskId(String jobId){
		String taskId = jobId+"-st-"+jobSyncTaskMap.get(jobId).size();
		return taskId;
	}
	
	private DataTransferTask convertToDataTransferTask(DataTransferTaskForClient taskClient, String taskId){
		DataTransferTask task = new DataTransferTask(taskId);
		Endhost src = new Endhost(taskClient.getSrc());
		Endhost dst = new Endhost(taskClient.getDst());
		task.setSrc(src);
		task.setDst(dst);
		task.setFileSize(taskClient.getFileSize());
		return task;
	}
	
	private SyncTask convertToSyncTask(SyncTaskForClient taskClient, String taskId){
		SyncTask task = new SyncTask(taskId);
		task.setInputs(taskClient.getInputs());
		task.setOutputs(taskClient.getOutputs());
		return task;
	}
	
	public String handlerDataTransfrTaskAdd(String jobId, DataTransferTaskForClient task){
		String taskId = generateDataTransferTaskId(jobId);
		DataTransferTask taskConverted = convertToDataTransferTask(task, taskId);
		List<DataTransferTask> list = jobDataTransferTaskMap.get(jobId);
		list.add(taskConverted);
		jobDataTransferTaskMap.put(jobId, list);
		if(task.isLeaf()){
			waitingDataTransferTaskMap.put(taskId, taskConverted);
			addDataTransferTask(taskConverted);
		}else{
			System.out.println("put waitingDataTransferTaskMap:"+taskId);
			waitingDataTransferTaskMap.put(taskId, taskConverted);
		}
		return taskId;
	}
	
	public String handleSyncTaskAdd(String jobId, SyncTaskForClient task){
		String taskId = generateSyncTaskId(jobId);
		SyncTask taskConverted = convertToSyncTask(task, taskId);
		addSyncTask(taskConverted);
		List<String> outputTaskIds = taskConverted.getOutputs();
		for(String outputTaskId: outputTaskIds){
			DataTransferTask dataTransferTask = waitingDataTransferTaskMap.get(outputTaskId);//only one time per output task id
			if(dataTransferTask!=null){
				addDataTransferTask(dataTransferTask);
			}
		}
		return taskId;
	}

	public DataTransferTask getNextTaskForPortPair(PortPair pair){
		DataTransferTask result = null;
		List<DataTransferTask> tasks = pair.getWaitingQueue();
		if(tasks.size()==0){
			System.out.println("waiting task is null");
			return null;
		}
		System.out.println("waiting task size:"+tasks.size());
		result = scheduler.getNextTaskForPortPair(pair, networkModel, dependencies);
		System.out.println("result of getNextTaskForPortPair:"+((result==null)?"null":result.getId()));
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
			System.out.println("port null");
			networkModel.portMissing(task.getSrc());
			if(dst==null)networkModel.portMissing(task.getDst());
		}
		else{
		    networkModel.addDataTransferTaskForPortPair(src.getId(), dst.getId(), task);
		    //waitingDataTransferTaskMap.put(task.getId(), task);///////////////////////////////////////
		}
	}
	
	public boolean isScheduleNeedUpdate(){
		List<PortPair> ppList = networkModel.getPortPairs();
		for(PortPair pp: ppList){
			System.out.println("check pp:"+pp.getSrcId()+","+pp.getDstId());
			if(pp.getRunningTask()==null){
				System.out.println("check is null");
				return true;
			}else{
				String nextTaskId = getNextTaskForPortPair(pp).getId();
				if(!pp.getRunningTask().getId().equals(nextTaskId)){
					System.out.println("check not equal");
					return true;
				}
			}
			//if(pp.getRunningTask()==null || !pp.getRunningTask().getId().equals(getNextTaskForPortPair(pp).getId()))return true;
			/*String runningTaskId = pp.getRunningTask().getId();
			System.out.println("running id:"+runningTaskId);
			String nextTaskId = getNextTaskForPortPair(pp).getId();
			System.out.println("next id:"+nextTaskId);
			if(runningTaskId==null || !runningTaskId.equals(nextTaskId))return true;*/
		}
		return false;
	}
	
	public List<String> getNewStartTasks(){
		List<String> taskIds = new ArrayList<String>();
		for(PortPair pp: networkModel.getPortPairs()){
			if(pp.getRunningTask()==null){
				String nextTaskId = getNextTaskForPortPair(pp).getId();
				System.out.println("next task id:"+nextTaskId);
				taskIds.add(nextTaskId);
			}else{
				String nextTaskId = getNextTaskForPortPair(pp).getId();
				if(!pp.getRunningTask().getId().equals(nextTaskId)){
					System.out.println("next task id:"+nextTaskId);
					taskIds.add(nextTaskId);
				}
			}
			/*String runningTaskId = pp.getRunningTask().getId();
			String nextTaskId = getNextTaskForPortPair(pp).getId();
			if(runningTaskId==null || !runningTaskId.equals(nextTaskId)){
				taskIds.add(nextTaskId);
			}*/
		}
		return taskIds;
	}
	
	public String startTask(String taskId){//return the id of stopped task
		for(PortPair pp: networkModel.getPortPairs()){
			
			String nextTaskId = getNextTaskForPortPair(pp).getId();
			if(nextTaskId.equals(taskId)){
				String runningTaskId = null;
				if(pp.getRunningTask()!=null){
					runningTaskId = pp.getRunningTask().getId();
					if(waitingDataTransferTaskMap.get(nextTaskId)==null){
						System.out.println("waitingDataTransferTaskMap gets null for:"+nextTaskId);
					}
					pp.setRunningTask(waitingDataTransferTaskMap.get(nextTaskId));
					System.out.println("Running task id changed to:"+pp.getRunningTask().getId());
					return runningTaskId;
				}else{
					pp.setRunningTask(waitingDataTransferTaskMap.get(nextTaskId));
					if(waitingDataTransferTaskMap.get(nextTaskId)==null){
						System.out.println("waitingDataTransferTaskMap gets null for:"+nextTaskId);
					}
					System.out.println("Running task id changed to:"+pp.getRunningTask().getId());
					return runningTaskId;
				}
				
			}
		}
		return null;
	}
	
	public void removeTask(String jobId, String taskId){
		dependencies.remove(taskId);
		for(PortPair pp: networkModel.getPortPairs()){
			if(pp.getWaitingQueue().contains(waitingDataTransferTaskMap.get(taskId))){
				List<DataTransferTask> tempList = pp.getWaitingQueue();
				tempList.remove(waitingDataTransferTaskMap.get(taskId));
				pp.setWaitingQueue(tempList);
			}
		}
	}
	
	public List<String> closeJob(String jobId){
		List<String> result = new ArrayList<String>();
		for(Map.Entry<String, List<DataTransferTask>> entry: jobDataTransferTaskMap.entrySet()){
			if(entry.getKey().equals(jobId)){
				for(DataTransferTask task: entry.getValue()){
					removeTask(jobId, task.getId());
					result.add(task.getId());
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
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
		
		SyncTask stask1 = new SyncTask("s1");
		List<String> inputs1 = new ArrayList<String>();
		inputs1.add("1");
		List<String> outputs1 = new ArrayList<String>();
		outputs1.add("2");
		stask1.setInputs(inputs1);
		stask1.setOutputs(outputs1);
		
		SyncTask stask2 = new SyncTask("s2");
		List<String> inputs2 = new ArrayList<String>();
		inputs2.add("2");
		inputs2.add("3");
		List<String> outputs2 = new ArrayList<String>();
		outputs2.add("4");
		stask2.setInputs(inputs2);
		stask2.setOutputs(outputs2);
		
		SyncTask stask3 = new SyncTask("s3");
		List<String> inputs3 = new ArrayList<String>();
		inputs3.add("4");
		List<String> outputs3 = new ArrayList<String>();
		outputs3.add("5");
		stask3.setInputs(inputs3);
		stask3.setOutputs(outputs3);
		
		SyncTask stask4 = new SyncTask("s4");
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
