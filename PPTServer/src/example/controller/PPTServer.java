package example.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class PPTServer {
	private final static int RIGHT = 1;
	private final static int LEFT = 2;
	private final static int SHIFTF5 = 0;
	private final static int ESC = 3;
	
	private static int key;
	
	private static ObjectInputStream fromClient;
	private static ObjectOutputStream fromServer;
	
	public static void main(String[] args) throws IOException,
									ClassNotFoundException, AWTException, InterruptedException{
		
		
		ServerSocket sSocket = new ServerSocket(12345);
		System.out.println("waiting a connection from the client");
		
		Robot robot = new Robot();
		
		Socket sock = sSocket.accept();
		System.out.println("recv a connection");
		                                               
		fromClient = new ObjectInputStream(sock.getInputStream());
		fromServer = new ObjectOutputStream(sock.getOutputStream());
		
		do{
			Choices choice = (Choices)fromClient.readObject();
			System.out.println("the flag is " + choice.getKey());
			key = choice.getKey();
			switch(key){
			
			case SHIFTF5:
				robot.keyPress(KeyEvent.VK_SHIFT);
				Thread.sleep(20);
				robot.keyPress(KeyEvent.VK_F5);
				Thread.sleep(10);
				robot.keyRelease(KeyEvent.VK_F5);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				Thread.sleep(10);
				break;
				
			case LEFT:
				robot.keyPress(KeyEvent.VK_LEFT);
				Thread.sleep(10);
				robot.keyRelease(KeyEvent.VK_LEFT);
				Thread.sleep(10);
				break;
				
			case RIGHT:
				robot.keyPress(KeyEvent.VK_RIGHT);
				Thread.sleep(10);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				Thread.sleep(10);
				break;
				
			case ESC:
				robot.keyPress(KeyEvent.VK_ESCAPE);
				Thread.sleep(10);
				robot.keyPress(KeyEvent.VK_ESCAPE);
				Thread.sleep(10);
				break;
				
				default:
					break;
			}
		}while(key != -1);
		System.out.println("exit the app");
		fromClient.close();
		fromServer.close();
		sock.close();
		sSocket.close();
	}
}
