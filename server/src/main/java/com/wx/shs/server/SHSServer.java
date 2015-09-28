package com.wx.shs.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wx.shs.schedule.DataTransferTaskForClient;
import com.wx.shs.schedule.Manager;
import com.wx.shs.schedule.SyncTaskForClient;

public class SHSServer {
	private static final Logger logger = LoggerFactory.getLogger(SHSServer.class);
	
	String addr = "localhost";
	
	int port = 10000;
	
	Manager shsManager;
	
	ConcurrentMap<String, IoSession> taskIDIoSessionMap;
	
	AltoClient altoClient;

	public SHSServer(){
		shsManager = new Manager();
		taskIDIoSessionMap = new ConcurrentHashMap<String, IoSession>();
		
		altoClient = new AltoClient();
		
		altoClient.setNetworkModel(shsManager.getNetworkModel());
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				altoClient.updateNetworkModel();
				
				if(shsManager.getNetworkModel().isUpdated()){
					if(shsManager.isScheduleNeedUpdate()){
						System.out.println("need schedule");
						List<String> tasks = shsManager.getNewStartTasks();
						for(String taskId: tasks){
							System.out.println("notify task id:"+taskId);
							notifyClientToStartTask(taskId);
						}
					}
				}
				shsManager.getNetworkModel().setUpdated(false);
			}
			
		}, 1000, 60*1000);
	}
	
	public void start(){
		shsManager.configNetworkModelFromLocal();/////////////for test
		
		IoAcceptor acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

		acceptor.setHandler(new IoHandlerAdapter() {
			
			@Override
			public void sessionCreated(IoSession session) throws Exception {
			}
			
			@Override
			public void sessionOpened(IoSession session) throws Exception {
			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
			}

			@Override
			public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
			}

			@Override
			public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
				logger.error(cause.getMessage(), cause);
				session.close(true);
			}

			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				System.out.println("Received message " + message);
				ObjectMapper mapper = new ObjectMapper();
				MessageObject mo = null;
				try{
				    mo = mapper.readValue(message.toString(), MessageObject.class);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				String type = mo.getType();
				System.out.println("type:"+type);
				if(type.equals("RegisterJob")){
					
					try{
						String jobId = shsManager.generateJob();
						session.write(jobId);
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
					System.out.println("register job");
				}else if(type.equals("DataTransferTask")){
					try{
						DataTransferTaskForClient task = mo.getDataTransferTask();
						String taskId = shsManager.handlerDataTransfrTaskAdd(mo.getJobId(), task);
						//String taskId = "test";
						System.out.println("send to client:"+taskId);
						session.write(taskId);
						taskIDIoSessionMap.put(taskId, session);
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
					if(shsManager.isScheduleNeedUpdate()){
						System.out.println("need schedule");
						List<String> tasks = shsManager.getNewStartTasks();
						for(String taskId: tasks){
							System.out.println("notify task id:"+taskId);
							notifyClientToStartTask(taskId);
						}
					}
				}else if(type.equals("SyncTask")){
					try{
						SyncTaskForClient task = mo.getSyncTask();
						String taskId = shsManager.handleSyncTaskAdd(mo.getJobId(), task);
						System.out.println("send to client:"+taskId);
						session.write(taskId);
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
					if(shsManager.isScheduleNeedUpdate()){
						System.out.println("need schedule");
						List<String> tasks = shsManager.getNewStartTasks();
						for(String taskId: tasks){
							notifyClientToStartTask(taskId);
						}
					}
				}else if(type.equals("Start")){
					String stoppedTaskId = shsManager.startTask(mo.getTaskId());
					if(stoppedTaskId!=null){
						notifyClientToStopTask(stoppedTaskId);
					}
				}else if(type.equals("Remove")){
					shsManager.removeTask(mo.getJobId(), mo.getTaskId());
					taskIDIoSessionMap.remove(mo.getTaskId());
				}else if(type.equals("Close")){
					List<String> tasks = shsManager.closeJob(mo.getJobId());
					for(String taskId: tasks){
						taskIDIoSessionMap.remove(taskId);
					}
				}
				//System.out.println("Received message2 " + messageString);
			}

			@Override
			public void messageSent(IoSession session, Object message) throws Exception {
				logger.info("Sent message " + message);
				System.out.println("Sent message " + message);
			}
		});
		
		try {
			acceptor.bind(new InetSocketAddress(addr,port));
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
	
	public void notifyClientToStartTask(String taskId){
		IoSession session = taskIDIoSessionMap.get(taskId);
		session.write("Start "+taskId);
		//taskIDIoSessionMap.remove(taskId);
	}
	
	public void notifyClientToStopTask(String taskId){
		IoSession session = taskIDIoSessionMap.get(taskId);
		session.write("Stop "+taskId);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SHSServer server = new SHSServer();
	    server.start();
	    /*
	    while(server.taskIDIoSessionMap.isEmpty()){
	    	System.out.println("circle");
	    }*/
	    //server.notifyClientToStartTask("test");
	}

}
