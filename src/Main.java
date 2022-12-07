import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static void main(String args[]) throws Exception{

		System.out.println("Choisir entre Groupleader1 ou GroupLeader2 et ses clients");
		System.out.println("1.  GroupLeader1");
		System.out.println("2.  GroupLeader2");
		System.out.println("3.  Clients");
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
		int choice = Integer.parseInt(br.readLine());
		
		if(choice == 1){		
			GroupLeader1 s = new GroupLeader1();
		}
		else if(choice == 2){
			GroupLeader2 c = new GroupLeader2();
		}
		else if(choice == 3){
			Clients c = new Clients();
		}
		else{
			System.out.println("Choix n'existe pas");
		}
	}
}
