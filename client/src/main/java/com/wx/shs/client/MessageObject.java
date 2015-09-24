package com.wx.shs.client;

public class MessageObject {

	String type;
	
	DataTransferTask dataTransferTask;
	
	SyncTask syncTask;
	
    String jobId;
    
    String taskId;
    
    public MessageObject(String type){
    	this.type = type;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DataTransferTask getDataTransferTask() {
		return dataTransferTask;
	}

	public void setDataTransferTask(DataTransferTask dataTransferTask) {
		this.dataTransferTask = dataTransferTask;
	}

	public SyncTask getSyncTask() {
		return syncTask;
	}

	public void setSyncTask(SyncTask syncTask) {
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
