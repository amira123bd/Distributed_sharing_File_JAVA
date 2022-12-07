
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

public class GroupLeader1
{
	//Array sui contient des info de types InformationFiles 
    public static ArrayList<InformationFiles> repertoiredefichier = new ArrayList<InformationFiles>();
 
    @SuppressWarnings("resource")
	
    //appel du constructeur
    public GroupLeader1() throws NumberFormatException, IOException
    {
    	
		// connect the server on port 1111
    	// //creation du  socket server object
    	ServerSocket serverSocket=null;
    	Socket socket = null;
    	try{
    			serverSocket = new ServerSocket(1111);//Port =1111
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
    		
    		new RechercheGroupleader1(socket).start();
    	}
    }
    
    
  
}










class RechercheGroupleader1 extends Thread
{
	protected Socket socket;
	//ArrayList<InformationFiles> repertoiredefichier;
	

	public RechercheGroupleader1(Socket clientSocket)
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
    		
    		
    	    System.out.println("Tous les fichiers sont indexés sur le groupLeader 1 ");      
    		
    	for(int i=0;i<filesList.size() ;i++)
    		{
    		GroupLeader1.repertoiredefichier.add(filesList.get(i));
    		
    		
    		}
    	
    		System.out.println("le repertoire contient " +GroupLeader1.repertoiredefichier.size());
    		
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
    			System.out.println("lancement du recherche a partir du groupleader 1 ");
    			
    	}
    	catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RechercheGroupleader2.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
    	
    	
        ArrayList<InformationFiles> Peers = new ArrayList<InformationFiles>();
        
           
        for(int j=0;j<GroupLeader1.repertoiredefichier.size();j++)
        {
        	InformationFiles fileInfo=GroupLeader1.repertoiredefichier.get(j);
           Boolean tf=fileInfo.fileName.equals(str);
           if(tf)
           {
        	   index = j;
        	   Peers.add(fileInfo);
           }
        }
        
        if(Peers.isEmpty())
        {  
        try {
			 System.out.println("Recherche lancé sur le groupe leader 2");
			  for(int j=0;j<GroupLeader2.repertoiredefichier.size();j++)
		        {
				  
		           InformationFiles fileInfo=GroupLeader2.repertoiredefichier.get(j);
		           Boolean tf=fileInfo.fileName.equals(str);
		           if(tf)
		           {
		        	   index = j;
		        	   Peers.add(fileInfo);
		           }
		        }
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
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
    

 
