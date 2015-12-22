import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class Generator {
	private static final int N = 4039;
	private static final int blockSize = 25;
	private static final int totalThreads = (N/blockSize)+1;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CountDownLatch doneMapping = new CountDownLatch(totalThreads);
		CountDownLatch doneReducing = new CountDownLatch(totalThreads);
		System.out.println("Hello");
		LinkedList<Integer>[] people = Loader.load("facebook_combined.txt", N);

		Mapper m= null;
		Reducer n= null;
		for(int i=0; i<N; i=i+blockSize){
			m = new Mapper(i, i+blockSize, people, N, doneMapping);
		}
		try {
			doneMapping.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Mapping fisnished!");

		
		for(int i=0; i<N; i=i+blockSize){
			n = new Reducer(i, i+blockSize,doneReducing, blockSize);
		}
		
		try {
			doneReducing.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Reducing fisnished!");
	}

}
