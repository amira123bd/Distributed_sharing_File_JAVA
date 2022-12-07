
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Clients
{
	@SuppressWarnings({ "unchecked", "rawtypes", "resource", "unused" })
	
	
	//public static void main(String args[]) throws Exception
	
	
	
	
	// appel du constructeur par défaut pour chaque client
	public Clients()
	{  
		Socket socket;
		ObjectInputStream ois ;
		ObjectOutputStream oos ;	
		ArrayList al;  
		Scanner scanner = new Scanner(System.in);  
		String string;
		Object o,b;
		String direcPath=null;
		int peerServerPort=0;
		int Port=0;
		ArrayList<InformationFiles> arrList = new ArrayList<InformationFiles>();  //va contenir les listes des fichiers
		try
		{   // to write a block a character
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
  			
			System.out.println("Client lancé:");
			System.out.println(" ");
			System.out.println(">>> taper le port du client : 1111 appartient au groupleader1 2222 appartient au groupleader2");
			Port=Integer.parseInt(br.readLine());
			//repertoire du fichier
			System.out.println(">>> Entrer le repertoire de ce client : ");
			direcPath=br.readLine();
			//id du repertoire
			System.out.println(">>> Enter L'id pour cette repertoire");
			int readpid=Integer.parseInt(br.readLine());
			//server port of the peer
			System.out.println(">>> entrer le port sur lequel ce client est considéré comme serveur");
			peerServerPort=Integer.parseInt(br.readLine());
  
			//appeler la classe Download
			Download objServerDownload = new Download(peerServerPort,direcPath);
			objServerDownload.start();
			
			
			
			
		
			
			
			socket = new Socket("localhost",Port);
			System.out.println("la connexion est établie ");
			
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream()); 
			
		
  
			File fichier = new File(direcPath); 
			File[] listofFiles = fichier.listFiles();
			InformationFiles currentFile;
			File file;
			
			
			//effectuer un parcours dans toute la reprt
			for (int i = 0; i < listofFiles.length; i++) {
				currentFile= new InformationFiles();
				file = listofFiles[i];
				currentFile.fileName=file.getName();  
				currentFile.peerid=readpid;
				currentFile.portNumber=peerServerPort;
				arrList.add(currentFile);
			}
			
			
			
			//Envoyer la liste des fichiers au groupLeader
			oos.writeObject(arrList);
			
			
			
			
			System.out.println(">>> Entrer le nom du fichier à importer");
			String fileToDownload = br.readLine();
			oos.writeObject(fileToDownload);
			
			System.out.println("Message envoyé au serveur");
			
			
			
			
			ArrayList<InformationFiles> peer= new ArrayList<InformationFiles>();
			peer = (ArrayList<InformationFiles>)ois.readObject();
			
			System.out.println("la liste des paires qui contiennent ce fichier");
			
			for(int i=0;i<peer.size();i++)
			{  
				int result = peer.get(i).peerid;
				int port = peer.get(i).portNumber;
				System.out.println("le fichier est sur le pair d'ID " +result+ " avec port  "+port);
			}
			
			
			
			System.out.println(">>> entrer le port de l'un des paires :");
			int clientAsServerPortNumber = Integer.parseInt(br.readLine());
			
			System.out.println(">>> entrer l'id correspondant");
			int clientAsServerPeerid = Integer.parseInt(br.readLine());
			
			
			clientAsServer(clientAsServerPeerid,clientAsServerPortNumber,fileToDownload,direcPath);  
			
		}
		catch(Exception e)
		{
			System.out.println("Erreur de connexion entre serveur et client ");
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void clientAsServer(int clientAsServerPeerid, int clientAsServerPortNumber, String fileNamedwld, String directoryPath) throws ClassNotFoundException
	{   
		try {
				@SuppressWarnings("resource")
				Socket clientAsServersocket = new Socket("localhost",clientAsServerPortNumber);
				
				ObjectOutputStream clientAsServerOOS = new ObjectOutputStream(clientAsServersocket.getOutputStream());
				ObjectInputStream clientAsServerOIS = new ObjectInputStream(clientAsServersocket.getInputStream());
				
				clientAsServerOOS.writeObject(fileNamedwld);
				
				int readBytes=(int) clientAsServerOIS.readObject();
				
				System.out.println("nombre de bits transféres ::"+readBytes);
				
				byte[] b=new byte[readBytes];
				clientAsServerOIS.readFully(b);
				OutputStream  fileOPstream = new FileOutputStream(directoryPath+"//"+fileNamedwld);
				
				@SuppressWarnings("resource")
				
				BufferedOutputStream BOS = new BufferedOutputStream(fileOPstream);
				BOS.write(b, 0,(int) readBytes);
				
				System.out.println("le fichier  "+fileNamedwld+ ", est sur votre repertoire :  "+directoryPath);
				
				
				BOS.flush();
		} 
		catch (IOException ex) {
        Logger.getLogger(Clients.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

