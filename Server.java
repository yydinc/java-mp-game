package cava;

import java.net.*;
import java.io.*;

public class Server {

	protected static Socket s[] = new Socket[10];
	private static int index = 0;

	public Server(){

	}

	public static void main(String[] args) throws IOException{
		ServerSocket server = new ServerSocket(5050);
		while(true){
			Socket so = server.accept();
			System.out.println("A new user has been connected !");
			ListenThread ls = new ListenThread(so);
			ls.start();
			s[index] = so;
			index++;
		}


	}

	private static class ListenThread extends Thread{
		private static Socket cli;
		
		@Override
		public void run(){
			try{
				while(true){
					InputStreamReader in = new InputStreamReader(cli.getInputStream());
					BufferedReader bf = new BufferedReader(in);
					
					String msg = bf.readLine();
					System.out.println(msg);
					//shareToClients(msg);
				}
			}
			catch(Exception e){

			}
		}
		public ListenThread(Socket client){
			cli = client;
		}
		private void shareToClients(String msg) throws IOException{
			for(Socket soc:s){
				PrintWriter pw = new PrintWriter(soc.getOutputStream());
				pw.println(msg);
				pw.flush();
			}
		}

	}

}