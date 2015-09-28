package com.wx.shs.server;

import com.wx.shs.schedule.DataTransferTaskForClient;
import com.wx.shs.schedule.SyncTaskForClient;

public class MessageObject {

	String type;
	
	DataTransferTaskForClient dataTransferTask;
	
	SyncTaskForClient syncTask;
	
    String jobId;//for remove task
    
    String taskId;//fro remove task
    
    public MessageObject(){
    	
    }
    
    public MessageObject(String type){
    	this.type = type;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DataTransferTaskForClient getDataTransferTask() {
		return dataTransferTask;
	}

	public void setDataTransferTask(DataTransferTaskForClient dataTransferTask) {
		this.dataTransferTask = dataTransferTask;
	}

	public SyncTaskForClient getSyncTask() {
		return syncTask;
	}

	public void setSyncTask(SyncTaskForClient syncTask) {
		this.syncTask = syncTask;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
