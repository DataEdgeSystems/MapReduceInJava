import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;

public class Reducer implements Runnable{
	Thread t;
	private int start;
	private int end;
	Set<Integer> set1, set2;
	PrintWriter writer;
	Scanner in;
	Scanner im;
	CountDownLatch cdl;
	private static int blockSize;
	public Reducer(int s, int e, CountDownLatch c, int b){
		System.out.println("Reducer from "+ s + " to " + e);
		blockSize=b;
		cdl=c;
		t = new Thread(this);
		set1 = new HashSet<Integer>();
		set2 = new HashSet<Integer>();
		start = s;
		end = e;
		writer= null;
		in= null;
		im= null;
		t.start();
	}
	public void run(){		
		try {
			in = new Scanner(new FileReader("MAP_"+start+"_"+end));
			writer = new PrintWriter("REDUCE_"+start+"_"+end, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(in.hasNextLine()){
			String line = in.nextLine();
			StringTokenizer st = new StringTokenizer(line);
			String key = st.nextToken();
			while(st.hasMoreTokens()){
				int a =Integer.parseInt(st.nextToken());
				set1.add(a);
			}
			StringTokenizer keyScanner = new StringTokenizer(key,",");
			String token=null;
			if(keyScanner.hasMoreTokens())
				token=keyScanner.nextToken().substring(1);
			int first = Integer.parseInt(token);
			if(keyScanner.hasMoreTokens())
				token=keyScanner.nextToken();
			token=token.substring(0,token.length()-1);
			int second = Integer.parseInt(token);
			if(second<=first)
				continue;
			int count=blockSize;
			while(second > count){
				count=count+blockSize;
			}
			try {
				im = new Scanner(new FileReader("MAP_"+(count-blockSize)+"_"+count));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String cur = "";
			while(im.hasNextLine()){
				cur = im.nextLine();
				StringTokenizer sm = new StringTokenizer(cur);
				String key2 = sm.nextToken();
				StringTokenizer keyScanner2 = new StringTokenizer(key2,",");
				String token2=null;
				if(keyScanner2.hasMoreTokens())
					token2=keyScanner2.nextToken().substring(1);
				int first2 = Integer.parseInt(token);
				if(keyScanner2.hasMoreTokens())
					token2=keyScanner2.nextToken();
				token2=token2.substring(0,token2.length()-1);
				int second2 = Integer.parseInt(token2);
				if((first==first2 && second==second2) || (first==second2 && second==first2))
					break;
			}
			im.close();
			st = new StringTokenizer(cur);
			if(st.hasMoreTokens())
				st.nextToken();
			while(st.hasMoreTokens()){
				int a =Integer.parseInt(st.nextToken());
				set2.add(a);
			}
			set1.retainAll(set2);
			writer.print(key+ " ");
			writer.println(set1.toString());
			writer.flush();
		}
		writer.close();
		in.close();
		cdl.countDown();
	}
}
