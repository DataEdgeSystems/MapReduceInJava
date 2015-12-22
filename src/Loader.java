import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Loader {

	public static LinkedList<Integer>[] load(String s, int N){
		LinkedList<Integer>[] people = new LinkedList[N];
		for(int i=0; i<N; i++)
			people[i] = new LinkedList<Integer>();
		 try {

		        Scanner sc = new Scanner(new File(s));

		        while (sc.hasNextInt()) {
		        	
		        	int first=sc.nextInt();
		        	int second=sc.nextInt();
		        	people[first].add(second);
		        	people[second].add(first);
		        }
		        sc.close();
		    } 
		    catch (FileNotFoundException e) {
		        System.out.println("File not found!");
		    }
		 return people;
	}
}
