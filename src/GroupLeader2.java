
import java.io.*;
import java.net.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;
import java.lang.Runnable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Integer;


@SuppressWarnings("unused")

public class GroupLeader2
{
	//globalarray fih des infos de types FileInfo peerId,FileName,PortNumber
    public static ArrayList<InformationFiles> repertoiredefichier = new ArrayList<InformationFiles>();
 
    @SuppressWarnings("resource")
	//public static void main(String args[])
    //appel du constructeur
    public GroupLeader2() throws NumberFormatException, IOException
    {
    	
		// connect the server on port 7799
    	// //create the socket server object
    	ServerSocket serverSocket=null;//socket pour le serveur pour repondre au client
    	Socket socket = null;
    	try{
    			serverSocket = new ServerSocket(2222);//meme port que le client
    			System.out.println("le serveur est lancé ");
    			System.out.println(" ");
    			System.out.println(">>> exécuter les clients");
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    	
    	
    	
    	
    	while(true)
    	{
    		try{
                //attendre les clients
    				socket = serverSocket.accept();
    				
    		}
    		catch(IOException e)
    		{
    			System.out.println("I/O error: " +e);
    		}
    		
    		new RechercheGroupleader2(socket).start();
    	}
    }
}












class RechercheGroupleader2 extends Thread
{
	protected Socket socket;
	//ArrayList<InformationFiles> repertoiredefichier;
	

	public RechercheGroupleader2(Socket clientSocket)
	{
		this.socket=clientSocket;
		//this.repertoiredefichier=repertoiredefichier;
	}
	
	

	ArrayList<InformationFiles> filesList=new ArrayList<InformationFiles>();
   	ObjectOutputStream oos;
	ObjectInputStream ois;
	String str;
	int index;

    @SuppressWarnings("unchecked")
	public void run()
    {
    	try
    	{  //read from socket to ObjectInputStream object

    		InputStream is=socket.getInputStream();
    		
    		oos = new ObjectOutputStream(socket.getOutputStream());
    		
    		ois = new ObjectInputStream(is);     
    		
    		System.out.println("listes de fichiers est envoyé par le client ");
    		filesList=(ArrayList<InformationFiles>)ois.readObject();
    		
    		
    	    System.out.println("Tous les fichiers sont indexés sur le groupLeader 2 ");      
    		
    	for(int i=0;i<filesList.size() ;i++)
    		{
    		GroupLeader2.repertoiredefichier.add(filesList.get(i));
    		}
    	
    		System.out.println("le repertoire contient " +GroupLeader2.repertoiredefichier.size());
    		
    	}
    	
    	
    	catch(IndexOutOfBoundsException e){
    		System.out.println("Index out of bounds exception");
    	}
    	catch(IOException e){
    		System.out.println("I/O exception");
    	}
    	catch(ClassNotFoundException e){
    		System.out.println("Class not found exception");
    	}
    	
    	
    	
    	
    	try {
    		
    			str = (String) ois.readObject();
    			System.out.println("lancement du recherche a partir du groupleader 2 ");
    			
    	}
    	catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RechercheGroupleader2.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
    	
    	
        ArrayList<InformationFiles> Peers = new ArrayList<InformationFiles>();
        
           
        for(int j=0;j<GroupLeader2.repertoiredefichier.size();j++)
        {
        	InformationFiles fileInfo=GroupLeader2.repertoiredefichier.get(j);
        	System.out.println("recherche gleader2:"+fileInfo.fileName);
           Boolean tf=fileInfo.fileName.equals(str);
           if(tf)
           {
        	   index = j;
        	   Peers.add(fileInfo);
           }
        }
        
        if(Peers.isEmpty())
        {  System.out.println("Recherche lancé sur le group leader 1");
        try {   
			
			  for(int j=0;j<GroupLeader1.repertoiredefichier.size();j++)
		        {
				  System.out.println(GroupLeader1.repertoiredefichier.size());
		           InformationFiles fileInfo=GroupLeader1.repertoiredefichier.get(j);
		           Boolean tf=fileInfo.fileName.equals(str);
		           if(tf)
		           {
		        	   index = j;
		        	   Peers.add(fileInfo);
		           }
		        }
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		}
        
        
        }
        try {
        	System.out.println("destination du  fichier est envoyé ");
        	oos.writeObject(Peers);
        } 
        catch (IOException ex) {
         Logger.getLogger(RechercheGroupleader2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    

 



















