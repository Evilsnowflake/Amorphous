package engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{
	
	Server server;
	Socket socket;
	GameState gs = null;
	int id;
	ObjectOutputStream oos;
	boolean loggedIn = false;
	String accountName = null;
	
	ServerThread(Socket sockett, Server serverr){
		socket = sockett;
		server = serverr;
	}
	
	public void run(){
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GameCommand gc;
			
			try {
				while((gc = (GameCommand)ois.readObject()) != null){
					System.out.println("received this command: " + gc.commandType);
					
					//login command
					if(gc.commandType.equals("accountInfo")){
						server.tryLogin(gc, this);
					}
					else if (gc.commandType.equals("regME")){
						server.tryRegister(gc,this);
					}
					//menu commands
					else if(loggedIn){
						System.out.println();
						if(gc.commandType.equals("makeGame")){
							System.out.println("making new game");
							gs = server.makeNewGame(this);
							
						}else if(gc.commandType.equals("joinGame")){
							System.out.println("connecting player to game");
							server.connectToGame(gc.n, this);
							
						}else if(gc.commandType.equals("refreshGames")){
							server.sendGamesList(this);
						
						//in-game commands
						}else if(gs != null){
							if(gc.commandType.equals("update")){
								System.out.println("server updating clients");
								gs.updateDisplays();
								
							}else if(gc.commandType.equals("concede")){
								server.concede(this, gs);
					
							}else if(gc.commandType.equals("summon")){
								gs.summonHandler.handle(gc, this);
					
							}else if(gc.commandType.equals("endTurn")){
								gs.endTurnHandler.handle(gc, this);
					
							}else if(gc.commandType.equals("attack")){
								gs.attackHandler.handle(gc, this);
					
							}else if(gc.commandType.equals("directAttack")){
								gs.directAttackHandler.handle(gc, this);
					
							}else if(gc.commandType.equals("affectTarget")){
								gs.affectSelectHandler.handle(gc,  this);
							}
					
						}
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
				
		}
	}

