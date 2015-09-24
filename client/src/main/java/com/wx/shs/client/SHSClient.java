package com.wx.shs.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.fasterxml.jackson.databind.ObjectMapper;


public class SHSClient {
	
	IoConnector connector;
	
	IoSession session;
		
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
				if (message.toString().equalsIgnoreCase("Bye")) {
					session.close(true);
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
		} catch (RuntimeIoException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String registerJob(){
		MessageObject mo = new MessageObject("rj");
		
		ObjectMapper mapper = new ObjectMapper();
		
		session.write("register job");
		return null;
	}

	public String addDataTransferTask(){
		return null;
		
	}
	
	public String addSyncTask(){
		return null;
		
	}
	
	public boolean removeTask(String jobId, String taskId){
		return false;
		
	}
	
	public boolean closeJob(String jobId){
		return false;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
