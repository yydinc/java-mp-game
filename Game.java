package cava;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
public class Game extends JPanel implements Runnable{
	private static final int characterWidth = 35;
	private static final int characterHeight = 35;
	private static final int boardWidth = 850;
	private static final int boardHeight = 550;
	private static final int characterSpeed = 20;
	private static int characterX = 0;
	private static int characterY = 0;
	private static Graphics g ;
	private static PrintWriter pw;
	private static String msg;
	private static int number;
	public Game(){
		addKeyListener(
				new KeyAdapter(){
					@Override
					public void keyPressed(KeyEvent e){
						if(e.getKeyCode() == KeyEvent.VK_UP){
							removeOldPos();
							characterY -= characterSpeed;
							updatePos();
							msg = (String.valueOf(characterX)+","+String.valueOf(characterY));
							pw.println(msg);
							pw.flush();
						}
						else if(e.getKeyCode() == KeyEvent.VK_DOWN){
						
							removeOldPos();
							characterY += characterSpeed;
							updatePos();
							msg = (String.valueOf(characterX)+","+String.valueOf(characterY));
							pw.println(msg);
							pw.flush();
						}
						else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
							removeOldPos();
							characterX += characterSpeed;
							updatePos();
							msg = (String.valueOf(characterX)+","+String.valueOf(characterY));
							pw.println(msg);
							pw.flush();
						}
						else if(e.getKeyCode() == KeyEvent.VK_LEFT){
							removeOldPos();
							characterX -= characterSpeed;
							updatePos();
							msg = (String.valueOf(characterX)+","+String.valueOf(characterY));
							pw.println(msg);
							pw.flush();	
						}
					}
				}

			);
		setFocusable(true);
		requestFocus();
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);  
         g.setColor(Color.BLACK);  
         g.fillRect(characterX,characterY,characterWidth,characterHeight);  
	}
	public void updatePos(){
		g = getGraphics();
		g.setColor(Color.BLACK);  
        g.fillRect(characterX,characterY,characterWidth,characterHeight);
	}
	public static void drawEnemy(String data,int enemyNumber){
		String[] dataSplitted =data.split(",");
		int xOfEnemy = Integer.parseInt(dataSplitted[0]); 
		int yOfEnemy = Integer.parseInt(dataSplitted[1]);
		g.setColor(Color.RED);  
        g.fillRect(xOfEnemy,yOfEnemy,characterWidth,characterHeight);
	
	}
	public void removeOldPos(){
		g = getGraphics();
		g.setColor(UIManager.getColor("Panel.background"));  
        g.fillRect(characterX,characterY,characterWidth,characterHeight);
	}

	@Override
	public void run(){
		
	}

	public static void main(String[] args) throws IOException {
		try{
			byte[] ipAddr = new byte[] {(byte)192,(byte)168,(byte)1,(byte)110};
			Socket so = new Socket(InetAddress.getByAddress(ipAddr),5050);
			Thread ls = new Thread(new ListenServer(so));
			ls.start();
			pw = new PrintWriter(so.getOutputStream());
			InputStreamReader in = new InputStreamReader(so.getInputStream());
			BufferedReader bf = new BufferedReader(in);
			
			String number1 = bf.readLine();
			number = Integer.parseInt(number1);
			System.out.println(number);
			JFrame frame = new JFrame("Game");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Game g = new Game();


			frame.add(g);
			frame.pack();
			
			frame.setSize(boardWidth,boardHeight);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
		catch(IOException e){

		}


	}
	private static class ListenServer extends Thread implements Runnable{
		private static Socket cli;
		
		@Override
		public void run(){
			listen();
		}

		public void listen(){
			try{
				InputStreamReader in = new InputStreamReader(cli.getInputStream());
				BufferedReader bf = new BufferedReader(in);
				while(true){
					
					String msg = bf.readLine();
					String[] data = msg.split("-");
					if(Integer.parseInt(data[1]) !=number){
						drawEnemy(data[0],Integer.parseInt(data[1]));
					}
				}
			}
			catch(Exception e){
				
			}
			
		}

		public ListenServer(Socket client){
			cli = client;
		}

	}


}
