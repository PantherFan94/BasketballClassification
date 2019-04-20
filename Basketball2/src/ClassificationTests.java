import java.io.File;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.lang.AssertionError;
import org.junit.Test;

import junit.framework.TestCase;

public class ClassificationTests extends TestCase {
	ArrayList<double[]> forwardStats= new ArrayList<double[]>();
	ArrayList<double[]> guardStats= new ArrayList<double[]>();
	ArrayList<double[]> forwardTestStats= new ArrayList<double[]>();
	ArrayList<double[]> guardTestStats= new ArrayList<double[]>();
	BruteForce bf = new BruteForce();
	LVQ model;
	//LVQ lvq = new LVQ();
	ArrayList<String[]> analysis = new ArrayList <String[]> ();
	
	ClassificationTests(){
		File g = new File("G.csv"); //$NON-NLS-1$
		File f = new File("F.csv"); //$NON-NLS-1$
		File gTest = new File("GTestData.csv"); //$NON-NLS-1$
		File fTest = new File("FTestData.csv");
		double[] gStats = {22,5,.8,.3};
		double[] fStats = {20,10,.7,.2};
		Player guardCodeBook = new Player(gStats,"G");
		Player fowardCodeBook = new Player(fStats,"F");
		ArrayList<Player> cbv = new ArrayList<Player>();
		cbv.add(guardCodeBook);
		cbv.add(fowardCodeBook);
		ArrayList<Player> guardData = Main.readData(g);
		ArrayList<Player> fowardData = Main.readData(f);
		ArrayList<Player> guardTestData = Main.readData(gTest);
		ArrayList<Player> fowardTestData = Main.readData(fTest);
		ArrayList<Player> allData = new ArrayList<Player>();
		allData.addAll(guardData);
		allData.addAll(fowardData);
		double[] pGStats= new double[] {22.88, 5.175, 0.8255, 0.3558};
		double[] pFStats= new double[] {21.72, 12, 0.74365, 0.23925};
		double[] mFStats= new double[] {22.2, 9, 0.9, 0.3};
		double[] mGStats= new double[] {22.2, 8, 0.9, 0.3};


		Player pureGuard = new Player(pGStats,"G");
		Player pureFoward = new Player(pFStats,"F");
		Player comboFoward = new Player(mFStats,"F");
		Player comboGuard = new Player(mGStats,"G");
		ArrayList<Player> testData = new ArrayList<Player>();
		testData.add(pureGuard);
		testData.add(pureFoward);
		testData.add(comboFoward);
		testData.add(comboGuard);
		
		
		
		model = new LVQ(allData,cbv,testData);
		model.train(.3,2000);
		
		for (int i=0; i<20; i++) {
			forwardStats.add(fowardData.get(i).getStats());
			guardStats.add(guardData.get(i).getStats());
		}
		
		for (int i=0; i<10; i++) {
			forwardTestStats.add(fowardTestData.get(i).getStats());
			guardTestStats.add(guardTestData.get(i).getStats());
		}
	}
	
	@Test
	public void PureGuardShouldBeGuard() {
		double[] inputStats= new double[] {22.88, 5.175, 0.8255, 0.3558};
		String summary[] = new String[2];
		
		try {
			assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			summary[0]="Pass";
		}
		catch(Exception e) {
			summary[0]= "Fail";
		}
		
		try {
			assertEquals("G", model.classify(model.getTestData().get(0)));
			summary[1]="Pass";
		}
		catch(Exception e) {
			summary[1]= "Fail";
		}
		
		try {
			assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			summary[2]="Pass";
		}
		catch(Exception e) {
			summary[2]= "Fail";
		}
		//Repeat try catch blocks for lvq and k's with the same test data
	}
	
	public void PureForwardShouldBeForward() {
		double[] inputStats= new double[] {21.72, 12, 0.74365, 0.23925};
		String summary[] = new String[2];
		
		try {
			assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			summary[0]="Pass";
		}
		catch(Exception e) {
			summary[0]= "Fail";
		}
		
		try {
			assertEquals("F", model.classify(model.getTestData().get(1)));
			summary[1]="Pass";
		}
		catch(Exception e) {
			summary[1]= "Fail";
		}
		
		try {
			assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			summary[2]="Pass";
		}
		catch(Exception e) {
			summary[2]= "Fail";
		}
		//Repeat try catch blocks for lvq and k's with the same test data
	}
	
	public void MixedForwardDominantStatsShouldBeForward() {
		double[] inputStats= new double[] {22.2, 9, 0.9, 0.3};
		String summary[] = new String[2];
		
		try {
			assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			summary[0]="Pass";
		}
		catch(Exception e) {
			summary[0]= "Fail";
		}
		
		try {
			assertEquals("F", model.classify(model.getTestData().get(2)));
			summary[1]="Pass";
		}
		catch(Exception e) {
			summary[1]= "Fail";
		}
		
		try {
			assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			summary[2]="Pass";
		}
		catch(Exception e) {
			summary[2]= "Fail";
		}
		//Repeat try catch blocks for lvq and k's with the same test data
	}
	
	public void MixedGuardDominantStatsShouldBeGuard() {
		double[] inputStats= new double[] {22.2, 8, 0.9, 0.3};
		String summary[] = new String[2];
		
		try {
			assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			summary[0]="Pass";
		}
		catch(Exception e) {
			summary[0]= "Fail";
		}
		
		try {
			assertEquals("G", model.classify(model.getTestData().get(3)));
			summary[1]="Pass";
		}
		catch(Exception e) {
			summary[1]= "Fail";
		}
		
		try {
			assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			summary[2]="Pass";
		}
		catch(Exception e) {
			summary[2]= "Fail";
		}
		//Repeat try catch blocks for lvq and k's with the same test data
	}
	
	public void NCAAStatTestAnalysis() {
		double[] inputStats= new double[3];
		int bfGuardFailCount=0;
		int lvqGuardFailCount=0;
		int kGuardFailCount=0;
		
		int bfForwardFailCount=0;
		int lvqForwardFailCount=0;
		int kForwardFailCount=0;
		
		long bfMaxTime=0;
		long bfMinTime=100000;
		long lvqMaxTime=0;
		long lvqMinTime=100000;
		long kMaxtime=0;
		long kMinTime=100000;
		
		//for all 10 ncaa forwards
		for (stats in forwardTestStats) {
			inputStats=stats;
			
			//Get Run Times
			long bfStartTime = System.nanoTime();
			bf.bruteForceClassification(forwardStats, guardStats, inputStats);
			long bfEndTime = System.nanoTime();
			
			if (((bfEndTime-bfStartTime)/1000000)<bfMinTime) {
				bfMinTime=(bfEndTime-bfStartTime)/1000000;
			}
			
			if (((bfEndTime-bfStartTime)/1000000)>bfMaxTime) {
				bfMaxTime=(bfEndTime-bfStartTime)/1000000;
			}
			
			//Check for accuracy
			try {
				assertEquals("Forward", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			}
			catch(Exception e) {
				bfForwardFailCount+=1;
			}
			
			//Get Run Times
			long lvqStartTime = System.nanoTime();
			//run with current input data
			model.classify(model.getTestData().get(3));
			long lvqEndTime = System.nanoTime();
			
			if (((lvqEndTime-lvqStartTime)/1000000)<lvqMinTime) {
				lvqMinTime=(lvqEndTime-lvqStartTime)/1000000;
			}
			
			if (((lvqEndTime-lvqStartTime)/1000000)>lvqMaxTime) {
				lvqMaxTime=(lvqEndTime-lvqStartTime)/1000000;
			}
			
			//Check for accuracy
			try {
				assertEquals("F", model.classify(model.getTestData.get(3)));
			}
			catch(Exception e) {
				lvqForwardFailCount+=1;
			}
			
			//Get Run Times
			long kStartTime = System.nanoTime();
			//run k with current input data
			bf.bruteForceClassification(forwardStats, guardStats, inputStats);
			long kEndTime = System.nanoTime();
			
			if (((kEndTime-kStartTime)/1000000)<kMinTime) {
				kMinTime=(kEndTime-kStartTime)/1000000;
			}
			
			if (((kEndTime-kStartTime)/1000000)>kMaxTime) {
				kMaxTime=(kEndTime-kStartTime)/1000000;
			}
			
			//Check for accuracy
			try {
				assertEquals("Forward", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			}
			catch(Exception e) {
				kForwardFailCount!=1;
			}
		}
		
		//for all 10 ncaa guards
		for (stats in guardTestStats) {
			inputStats=stats;
			
			//Get Run Times
			long bfStartTime = System.nanoTime();
			bf.bruteForceClassification(forwardStats, guardStats, inputStats);
			long bfEndTime = System.nanoTime();
			
			if (((bfEndTime-bfStartTime)/1000000)<bfMinTime) {
				bfMinTime=(bfEndTime-bfStartTime)/1000000;
			}
			
			if (((bfEndTime-bfStartTime)/1000000)>bfMaxTime) {
				bfMaxTime=(bfEndTime-bfStartTime)/1000000;
			}
			
			//Check for accuracy
			try {
				assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			}
			catch(Exception e) {
				bfGuardFailCount+=1;
			}
			
			//Get Run Times
			long lvqStartTime = System.nanoTime();
			//run with current input data
			model.classify(model.getTestData.get(3));
			long lvqEndTime = System.nanoTime();
			
			if (((lvqEndTime-lvqStartTime)/1000000)<lvqMinTime) {
				lvqMinTime=(lvqEndTime-lvqStartTime)/1000000;
			}
			
			if (((lvqEndTime-lvqStartTime)/1000000)>lvqMaxTime) {
				lvqMaxTime=(lvqEndTime-lvqStartTime)/1000000;
			}
			
			//Check for accuracy
			try {
				assertEquals("G", model.classify(model.getTestData.get(3)));
			}
			catch(Exception e) {
				lvqGuardFailCount+=1;
			}
			
			//Get Run Times
			long kStartTime = System.nanoTime();
			//run k with current input data
			bf.bruteForceClassification(forwardStats, guardStats, inputStats);
			long kEndTime = System.nanoTime();
			
			if (((kEndTime-kStartTime)/1000000)<kMinTime) {
				kMinTime=(kEndTime-kStartTime)/1000000;
			}
			
			if (((kEndTime-kStartTime)/1000000)>kMaxTime) {
				kMaxTime=(kEndTime-kStartTime)/1000000;
			}
			
			//Check for accuracy
			try {
				assertEquals("Guard", bf.bruteForceClassification(forwardStats, guardStats, inputStats));
			}
			catch(Exception e) {
				kGuardFailCount!=1;
			}
		}
		
		throws IOException {
			BufferedWriter writer = new BufferedWriter(new FileWriter(ncaaTestResults));
			writer.write("Brute Force: \n "
					+ "Was incorrect " + bfGuardFailCount*10 + "% of the time when classifying guards \n "
					+ "Was incorrect "+ bfForwardFailCount*10 + "% of the time when classifying forwards \n"
					+ "Had a maximum run time of " + bfMaxTime + " milliseconds"
					+ "Had a minimum run time of " + bfMinTime + " milliseconds \n \n");   
			writer.write("Linear Vector Quantization: \n "
					+ "Was incorrect " + lvqGuardFailCount*10 + "% of the time when classifying guards \n "
					+ "Was incorrect "+ lvqForwardFailCount*10 + "% of the time when classifying forwards \n"
					+ "Had a maximum run time of " + lvqMaxTime + " milliseconds"
					+ "Had a minimum run time of " + lvqMinTime + " milliseconds \n \n");   
			writer.write("K's Nearest Neighbors: \n "
					+ "Was incorrect " + kGuardFailCount*10 + "% of the time when classifying guards \n "
					+ "Was incorrect "+ kForwardFailCount*10 + "% of the time when classifying forwards \n"
					+ "Had a maximum run time of " + kMaxTime + " milliseconds"
					+ "Had a minimum run time of " + kMinTime + " milliseconds");   
			writer.close();
		}
		
	}
}
