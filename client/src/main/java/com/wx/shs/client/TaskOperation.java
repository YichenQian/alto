package com.wx.shs.client;

public interface TaskOperation {
	
	public boolean startDataTransferTask(String taskId);
	
	public boolean stopDataTransferTask(String taskId);
}
