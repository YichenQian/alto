package com.wx.shs.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataTransferTask implements Comparable<DataTransferTask> {
    String id;
	
    Endhost src;
    
    Endhost dst;
    
    double fileSize;

    public DataTransferTask(String id){
    	this.id = id;
    }
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Endhost getSrc() {
		return src;
	}

	public void setSrc(Endhost src) {
		this.src = src;
	}

	public Endhost getDst() {
		return dst;
	}

	public void setDst(Endhost dst) {
		this.dst = dst;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public int compareTo(DataTransferTask o) {
		Double fileSize = o.getFileSize();
		Double thisFileSize = this.getFileSize();
		return thisFileSize.compareTo(fileSize);
	}
	
	public static void main(String[] args){
		List<DataTransferTask> list = new ArrayList<DataTransferTask>();
		DataTransferTask task1 = new DataTransferTask("1");
		task1.setFileSize(100);
		DataTransferTask task2 = new DataTransferTask("2");
		task2.setFileSize(50);
		DataTransferTask task3 = new DataTransferTask("3");
		task3.setFileSize(80);
		list.add(task1);
		list.add(task2);
		list.add(task3);
		System.out.println(Collections.min(list).getFileSize());
	}
}
