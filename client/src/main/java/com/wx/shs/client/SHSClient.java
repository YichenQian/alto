package com.wx.shs.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SHSClient {
	
	IoConnector connector;
	
	IoSession session;
	
	TaskOperation taskOperation;
		
	private void setupConnector(String serverName, int port){
		connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(10 * 1000);
		
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
        connector.setHandler(new IoHandlerAdapter() {
			
			@Override
			public void sessionCreated(IoSession session) throws Exception {
				System.out.println("session created");
			}

			@Override
			public void sessionOpened(IoSession session) throws Exception {
				System.out.println("session opened");
			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
			}

			@Override
			public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
			}

			@Override
			public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
				session.close(true);
			}

			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				System.out.println("Received message " + message);
				String[] receivedMessage = message.toString().split(" ");
				boolean success = false;
				if(receivedMessage.length > 1){//Start taskId
					if(receivedMessage[0].equals("Start")){
						success = taskOperation.startDataTransferTask(receivedMessage[1]);
						if(success){
							MessageObject mo = new MessageObject(receivedMessage[0]);
							mo.setTaskId(receivedMessage[1]);;
							ObjectMapper mapper = new ObjectMapper();
							String messageToTransmit = "";
							try {
								messageToTransmit = mapper.writeValueAsString(mo);
								//System.out.println(message);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							session.write(messageToTransmit);
						}
					}else if(receivedMessage[0].equals("Stop")){
						success = taskOperation.stopDataTransferTask(receivedMessage[1]);//not need to update server
					}
					
				}
				
			}

			@Override
			public void messageSent(IoSession session, Object message) throws Exception {
				System.out.println("Sent message " + message);
			}
		});
	}
	
	public SHSClient(String serverName, int port){
		setupConnector(serverName, port);
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 10000));
			future.awaitUninterruptibly();
			session = future.getSession();
			session.getConfig().setUseReadOperation(true);
		} catch (RuntimeIoException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String registerJob(){
		String jobId = null;
		MessageObject mo = new MessageObject("RegisterJob");
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		try {
			message = mapper.writeValueAsString(mo);
			//System.out.println(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WriteFuture writeF = session.write(message);
		writeF.awaitUninterruptibly();
		
		if (writeF.getException() != null) {
            System.out.println(writeF.getException().getMessage());
        } else if (writeF.isWritten()) {
            System.out.println("rgst msg was sent!");
            ReadFuture readF = session.read();
            readF.awaitUninterruptibly(1000);
            if (readF.getException() != null) {
                System.out.println(readF.getException().getMessage());
            } else {
            	if(readF.getMessage()==null)System.out.println("read msg is null");
            	jobId = readF.getMessage().toString();
                System.out.println("[client]"+readF.getMessage().toString());
            }
        } else {
            System.out.println("error!");
        }
		return jobId;
	}

	public String addDataTransferTask(String jobId, DataTransferTaskForClient task){
		String taskId = null;
		MessageObject mo = new MessageObject("DataTransferTask");
		mo.setJobId(jobId);
		mo.setDataTransferTask(task);
		
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		try {
			message = mapper.writeValueAsString(mo);
			//System.out.println(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WriteFuture writeF = session.write(message);
		writeF.awaitUninterruptibly();
		
		if (writeF.getException() != null) {
            System.out.println(writeF.getException().getMessage());
        } else if (writeF.isWritten()) {
            System.out.println("dtt msg was sent!");
            ReadFuture readF = session.read();
            readF.awaitUninterruptibly(1000);
            if (readF.getException() != null) {
                System.out.println(readF.getException().getMessage());
            } else {
            	if(readF.getMessage()==null)System.out.println("read msg is null");
            	taskId = readF.getMessage().toString();
                System.out.println("[client]"+readF.getMessage().toString());
            }
        } else {
            System.out.println("error!");
        }
		
		return taskId;
	}
	
	public String addSyncTask(String jobId, SyncTaskForClient task){
		String taskId = null;
		MessageObject mo = new MessageObject("SyncTask");
		mo.setJobId(jobId);
		mo.setSyncTask(task);
		
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		try {
			message = mapper.writeValueAsString(mo);
			//System.out.println(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WriteFuture writeF = session.write(message);
		writeF.awaitUninterruptibly();
		
		if (writeF.getException() != null) {
            System.out.println(writeF.getException().getMessage());
        } else if (writeF.isWritten()) {
            System.out.println("st msg was sent!");
            ReadFuture readF = session.read();
            readF.awaitUninterruptibly(1000);
            if (readF.getException() != null) {
                System.out.println(readF.getException().getMessage());
            } else {
            	if(readF.getMessage()==null)System.out.println("read msg is null");
            	taskId = readF.getMessage().toString();
                System.out.println("[client]"+readF.getMessage().toString());
            }
        } else {
            System.out.println("error!");
        }
		
		return taskId;
	}
	
	public boolean removeTask(String jobId, String taskId){//include finish task
		MessageObject mo = new MessageObject("Remove");
		mo.setJobId(jobId);
		mo.setTaskId(taskId);
		
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		try {
			message = mapper.writeValueAsString(mo);
			//System.out.println(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.write(message);
		return true;
	}
	
	public boolean closeJob(String jobId){
		MessageObject mo = new MessageObject("Close");
		mo.setJobId(jobId);
		
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		try {
			message = mapper.writeValueAsString(mo);
			//System.out.println(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.write(message);
		return true;
	}
	
	public void setTaskOperation(TaskOperation taskOperation){
		this.taskOperation = taskOperation;
	}
	
	public void testSession(){
		MessageObject mo = new MessageObject("DataTransferTask");
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		try {
			message = mapper.writeValueAsString(mo);
			//System.out.println(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.write(message);
	}
	
	public static void main(String[] args) {
		//h1 --> h3 --> h4, h2 --> h4
		SHSClient c = new SHSClient("localhost", 10000);
		
		c.setTaskOperation(new TaskOperation(){

			@Override
			public boolean startDataTransferTask(String taskId) {
				System.out.println("start:"+taskId);
				return true;
			}

			@Override
			public boolean stopDataTransferTask(String taskId) {
				System.out.println("stop:"+taskId);
				return true;
			}
			
		});
		String jobId = c.registerJob();
		
		DataTransferTaskForClient d1 = new DataTransferTaskForClient();
		d1.setSrc("h1");
		d1.setDst("h3");
		d1.setFileSize(50.0);
		d1.setLeaf(true);
		DataTransferTaskForClient d2 = new DataTransferTaskForClient();
		d2.setSrc("h3");
		d2.setDst("h4");
		d2.setFileSize(50.0);
		d2.setLeaf(false);
		DataTransferTaskForClient d3 = new DataTransferTaskForClient();
		d3.setSrc("h2");
		d3.setDst("h4");
		d3.setFileSize(50.0);
		d3.setLeaf(true);
		
		String d1Id = c.addDataTransferTask(jobId, d1);
		String d2Id = c.addDataTransferTask(jobId, d2);
		String d3Id = c.addDataTransferTask(jobId, d3);
		
		SyncTaskForClient s1 = new SyncTaskForClient();
		List<String> inputsForS1 = new ArrayList<String>();
		inputsForS1.add(d1Id);
		List<String> outputsForS1 = new ArrayList<String>();
		outputsForS1.add(d2Id);
		s1.setInputs(inputsForS1);
		s1.setOutputs(outputsForS1);
		
		/*SyncTaskForClient s2 = new SyncTaskForClient();
		List<String> inputsForS2 = new ArrayList<String>();
		inputsForS2.add(d2Id);
		inputsForS2.add(d3Id);
		s2.setInputs(inputsForS2);*/
		
		String s1Id = c.addSyncTask(jobId, s1);
		//String s2Id = c.addSyncTask(jobId, s2);
		
		//c.testSession();
	}

}
