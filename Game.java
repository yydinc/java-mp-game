package cava;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
public class Game extends JPanel{
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
							pw.flush();						}
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
	public void removeOldPos(){
		g = getGraphics();
		g.setColor(UIManager.getColor("Panel.background"));  
        g.fillRect(characterX,characterY,characterWidth,characterHeight);
	}

	public static void main(String[] args) throws IOException {
		Socket so = new Socket("localhost",5050);
		ListenThread ls = new ListenThread(so);
		ls.start();
		pw = new PrintWriter(so.getOutputStream());

		JFrame frame = new JFrame("Game.java");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game g = new Game();


		frame.add(g);
		frame.pack();
		
		frame.setSize(boardWidth,boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


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
				}
			}
			catch(Exception e){
				
			}
		}
		public ListenThread(Socket client){
			cli = client;
		}

	}


}