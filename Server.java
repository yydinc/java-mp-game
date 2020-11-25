package cava;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable {

	//protected static Socket s[] = new Socket[10];
	private static int index = 0;
	private static ArrayList<Socket> clients = new ArrayList<Socket>();
	@Override
	public void run(){

	}

	public static void main(String[] args) throws IOException{
		ServerSocket server = new ServerSocket(5050);
		//byte[] ipAddr = new byte[] {(byte)192,(byte)168,(byte)1,(byte)110};
		//new InetSocketAddress(InetAddress.getByAddress(ipAddr),
		//server.bind(5050);
		System.out.println("Server started !");
		while(true){
			
			Socket so = server.accept();
			
			PrintWriter pw = new PrintWriter(so.getOutputStream());
			pw.println(index);
			pw.flush();
			System.out.println("A new user has been connected !");
			ListenThread lis = new ListenThread(so,(index+1));
			lis.setName(String.valueOf(index));
			Thread ls = new Thread(lis);
			clients.add(so);
			ls.start();
			/*
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){
				
			}
			*/
			//s[index] = so;
			index++;
			
			
		}
		


	}

	private static class ListenThread extends Thread implements Runnable{
		private static Socket cli;
		private static int x;
		@Override
		public void run(){
			
				listen();
			
		}

		public  void listen(){
			try{
				InputStreamReader in = new InputStreamReader(cli.getInputStream());
				BufferedReader bf = new BufferedReader(in);
				while(true){
					
					String msg = bf.readLine();
					if(msg!=null){
						shareToClients(msg+"-"+String.valueOf(this.getName()));
					}
					else{
						System.out.println("Disconnected! "+" --By: Thread-"+String.valueOf(this.getName()));
						while(true){
							try{
								Thread.sleep(999999);
							}
							catch(Exception ds){

							}
						}
											}
				}
			}
			catch(Exception e){
				System.out.println("Disconnected! "+" --By: Thread-"+String.valueOf(this.getName()));
			}
		}
		public ListenThread(Socket client,int name){
			cli = client;
			x = name;
		}
		
		private synchronized void shareToClients(String msg) throws IOException{
			for(int i = 0; i < clients.size(); i++){
				
				PrintWriter pw = new PrintWriter(clients.get(i).getOutputStream());
				pw.println(msg);
				pw.flush();
			
			}
		}
	
	}

}
