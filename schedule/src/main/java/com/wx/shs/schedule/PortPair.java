package com.wx.shs.schedule;

import java.util.ArrayList;
import java.util.List;

public class PortPair {
	int srcId;
	
	int dstId;
	
	List<DataTransferTask> waitingQueue;
	
	DataTransferTask runningTask;
	
	public PortPair(){
		waitingQueue = new ArrayList<DataTransferTask>();
	}

	public int getSrcId() {
		return srcId;
	}

	public void setSrcId(int srcId) {
		this.srcId = srcId;
	}

	public int getDstId() {
		return dstId;
	}

	public void setDstId(int dstId) {
		this.dstId = dstId;
	}

	public List<DataTransferTask> getWaitingQueue() {
		return waitingQueue;
	}

	public void setWaitingQueue(List<DataTransferTask> waitingQueue) {
		this.waitingQueue = waitingQueue;
	}

	public DataTransferTask getRunningTask() {
		return runningTask;
	}

	public void setRunningTask(DataTransferTask runningTask) {
		this.runningTask = runningTask;
	}
	
	public void addDataTransferTask(DataTransferTask task){
		this.waitingQueue.add(task);
	}
}
