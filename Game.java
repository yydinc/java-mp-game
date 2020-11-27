package cava;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

import java.util.*;
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
	private static ArrayList<String> enemies = new ArrayList<String>();
	public Game(){
		addKeyListener(
				new KeyAdapter(){
					@Override
					public void keyPressed(KeyEvent e){
						if(e.getKeyCode() == KeyEvent.VK_UP){
							removeOldPos();
							characterY -= characterSpeed;
							updatePos();
							msg = (String.valueOf(characterX)+","+String.valueOf(characterY)+"-"+String.valueOf(number));
							pw.println(msg);
							pw.flush();
						}
						else if(e.getKeyCode() == KeyEvent.VK_DOWN){
						
							removeOldPos();
							characterY += characterSpeed;
							updatePos();
							msg = (String.valueOf(characterX)+","+String.valueOf(characterY)+"-"+String.valueOf(number));
							pw.println(msg);
							pw.flush();
						}
						else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
							removeOldPos();
							characterX += characterSpeed;
							updatePos();
							msg = (String.valueOf(characterX)+","+String.valueOf(characterY)+"-"+String.valueOf(number));
							pw.println(msg);
							pw.flush();
						}
						else if(e.getKeyCode() == KeyEvent.VK_LEFT){
							removeOldPos();
							characterX -= characterSpeed;
							updatePos();
							msg = (String.valueOf(characterX)+","+String.valueOf(characterY)+"-"+String.valueOf(number));
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
	public static void drawEnemy(String data,int enemyNumber,boolean isDrawnAlready,int indexInEnemies){
		String[] dataSplitted =data.split(",");
		int xOfEnemy;
		int yOfEnemy;

		xOfEnemy = Integer.parseInt(dataSplitted[0]); 
		yOfEnemy = Integer.parseInt(dataSplitted[1]);
		if(isDrawnAlready){
			int oldXOfEnemy = Integer.parseInt(enemies.get(indexInEnemies).split("-")[0].split(",")[0]);
			int oldYOfEnemy = Integer.parseInt(enemies.get(indexInEnemies).split("-")[0].split(",")[1]);
			g.setColor(UIManager.getColor("Panel.background"));  
        	g.fillRect(oldXOfEnemy,oldYOfEnemy,characterWidth,characterHeight);
			g.setColor(Color.RED);  
        	g.fillRect(xOfEnemy,yOfEnemy,characterWidth,characterHeight);
        	oldXOfEnemy= xOfEnemy;
        	oldYOfEnemy= yOfEnemy;
			enemies.set(indexInEnemies,(data+"-"+String.valueOf(enemyNumber)));
		}
		else{
			g.setColor(Color.RED);  
        	g.fillRect(xOfEnemy,yOfEnemy,characterWidth,characterHeight);
			enemies.add(data+"-"+String.valueOf(enemyNumber));
		}
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
			byte[] ipAddr = new byte[] {(byte)192,(byte)168,(byte)1,(byte)108};
			Socket so = new Socket(InetAddress.getByAddress(ipAddr),5050);
			InputStreamReader in = new InputStreamReader(so.getInputStream());
			BufferedReader bf = new BufferedReader(in);
			
			String number1 = bf.readLine();
			number = Integer.parseInt(number1);
			System.out.println(number);
			
			Thread ls = new Thread(new ListenServer(so));
			ls.start();
			/*
			try{
				Thread.sleep(300);
			}
			catch(Exception e){
				
			}
			*/
			pw = new PrintWriter(so.getOutputStream());
			
			SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        	});
			
		}
		catch(IOException e){
			System.out.println("Baglantı Kurulamadı !");
		}


	}
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game g = new Game();


		frame.add(g);
		frame.pack();
		
		frame.setSize(boardWidth,boardHeight);
		//frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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
					boolean isDrawnAlready = false;	
					String msg = bf.readLine();
					String[] data = msg.split("-");
					int i = 0;
					if(Integer.parseInt(data[1])!=number){
						while(i<enemies.size()){
							if(Integer.parseInt(enemies.get(i).split("-")[1]) == Integer.parseInt(data[1])){
								isDrawnAlready = true;
								break;
							}
							else{
								i++;
							}
						}

					drawEnemy(data[0],Integer.parseInt(data[1]),isDrawnAlready,i);
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
