import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
public class Mapper implements Runnable{
	Thread t;
	private int start;
	private int end;
	private LinkedList<Integer>[] list;
	private final int N;
	CountDownLatch cdl;
	public Mapper(int s, int e, LinkedList<Integer>[] l, int n, CountDownLatch c){
		t = new Thread(this);
		System.out.println("Mapper from "+ s + " to " + e);
		start = s;
		end = e;
		list = l;
		cdl=c;
		N = n;
		t.start();
	}
	public void run(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("MAP_"+start+"_"+end, "UTF-8");
			for(int i=start; (i<end && i<N); i++){
				for (int k=0; k<list[i].size(); k++){
					
					writer.print("("+i+","+list[i].get(k)+") ");
					for (int l=0; l<list[i].size(); l++){
						writer.print(list[i].get(l)+" ");
					}
					writer.println();
				}
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cdl.countDown();
	}
}
