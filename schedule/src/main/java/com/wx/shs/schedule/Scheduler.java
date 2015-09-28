package com.wx.shs.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler {

	private List<String> getLeafTaskId(Map<String, List<String>> dependencies){
		if(dependencies.isEmpty())return null;
		
		List<String> results = new ArrayList<String>();
		
		for(Map.Entry<String, List<String>> entry: dependencies.entrySet()){
			results.add(entry.getKey());
			for(String id: entry.getValue()){
				results.remove(id);
			}
		}
		for(Map.Entry<String, List<String>> entry: dependencies.entrySet()){
			for(String id: entry.getValue()){
				results.remove(id);
			}
		}
		System.out.println("leaf:"+results.get(0)+" "+results.get(1));
		return results;
	}
	private List<DataTransferTask> getAvailableTasks(PortPair p, Map<String, List<String>> dependencies){
		List<DataTransferTask> results = new ArrayList<DataTransferTask>();
		List<DataTransferTask> allTasks = p.getWaitingQueue();
		List<String> leafs = getLeafTaskId(dependencies);
		if(leafs == null){
			System.out.println("leaf is null at getAvailableTasks");
			return allTasks;
		}
		for(DataTransferTask task: allTasks){
			String id = task.getId();
			if(leafs.contains(id))results.add(task);
		}
		System.out.println("available:"+p.srcId+" "+p.dstId+" "+results.size());
		return results;
	}
	
	private List<DataTransferTask> getMaxSuccessorTasks(List<DataTransferTask> tasks, Map<String, List<String>> dependencies){
		int max = 0;
		List<DataTransferTask> results = new ArrayList<DataTransferTask>();
		for(DataTransferTask task: tasks){
			int successor = 0;
			if(!dependencies.containsKey(task.getId())){
				
			}else{
				successor = dependencies.get(task.getId()).size();
			}
			System.out.println("num of suc"+successor);
			if(successor == max){
				results.add(task);
			}else if(successor > max){
				max = successor;
				results.clear();
				results.add(task);
			}
		}
		System.out.println("max successor task:"+results.size());
		return results;
	}
	
	public DataTransferTask getNextTaskForPortPair(PortPair pp, NetworkModel nm, Map<String, List<String>> dependencies) {
        DataTransferTask result = null;
        List<DataTransferTask> candicates = getAvailableTasks(pp, dependencies);
        List<DataTransferTask> candicates2 = getMaxSuccessorTasks(candicates, dependencies);
        if(candicates2.size() > 1){
        	result = Collections.min(candicates2);
        }else if(candicates2.size() == 1){
        	result = candicates2.get(0);
        }
        return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scheduler s = new Scheduler();
		Map<String, List<String>> dependencies = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("4");
		list1.add("5");
		dependencies.put("2", list1);
		List<String> list2 = new ArrayList<String>();
		list2.add("2");
		List<String> list3 = new ArrayList<String>();
		list3.add("2");
		dependencies.put("1", list2);
		dependencies.put("3", list3);
		List<String> result = s.getLeafTaskId(dependencies);
		System.out.println(result);
	}

}
