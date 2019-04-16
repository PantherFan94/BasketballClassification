import java.util.ArrayList;
import java.io.*;

public class BruteForce {

	public static void readData() throws NumberFormatException, IOException {
		System.out.println("starting read data");
		ArrayList<ArrayList<Integer>> guards = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> forwards = new ArrayList<ArrayList<Integer>>();
		File forwardFile = new File ("F.csv");
		BufferedReader brf = new BufferedReader(new FileReader(forwardFile));
		String st;
		String line;
		while ((st = brf.readLine()) != null){
			line = brf.readLine(); 
			for (int j=0 ; j<5 ; j++) {
				ArrayList<Integer> playerStatF = new ArrayList<Integer>();
				for (int i=0 ; i<line.split(",").length ; i++) {
					playerStatF.add(Integer.parseInt(line.split(",")[i]));
				}
				forwards.add(playerStatF);
			}
		}
		
		File guardsFile = new File ("G.csv");
		BufferedReader brg = new BufferedReader(new FileReader(guardsFile));
		while ((st = brg.readLine()) != null) {
			line = brg.readLine();
			for (int x=0 ; x<5 ; x++) {
				ArrayList<Integer> playerStatG = new ArrayList<Integer>();
				for (int y=0 ; y<line.split(",").length ; y++) {
					playerStatG.add(Integer.parseInt(line.split(",")[y]));
				}
				guards.add(playerStatG);
			}
		}
		
		System.out.println(forwards);
		System.out.println(guards);
	}
	
	public static String bruteForceClassification (ArrayList<double[]> savedStatsF, ArrayList<double[]> savedStatsG, double[] inputStats) {
		//savedStatsF = arraylist of all of the Player object double[] stats for forwards
		//savedStatsG = arraylist of all of the Player object double[] stats for guards
		//inputStats = input data
		//Get an average for each stat category for both positions
		double ppgF = 0;
		double rpgF = 0;
		double ftpF = 0;
		double tppF = 0;
		for (double[] statGroup : savedStatsF) {
			ppgF +=statGroup[0];
			rpgF +=statGroup[1];
			ftpF +=statGroup[2];
			tppF +=statGroup[3];
		}
		ppgF = ppgF/savedStatsF.size();
		rpgF = rpgF/savedStatsF.size();
		ftpF = ftpF/savedStatsF.size();
		tppF = tppF/savedStatsF.size();
		
		double ppgG = 0;
		double rpgG = 0;
		double ftpG = 0;
		double tppG = 0;
		for (double[] statGroup : savedStatsG) {
			ppgG +=statGroup[0];
			rpgG +=statGroup[1];
			ftpG +=statGroup[2];
			tppG +=statGroup[3];
		}
		ppgG = ppgG/savedStatsG.size();
		rpgG = rpgG/savedStatsG.size();
		ftpG = ftpG/savedStatsG.size();
		tppG = tppG/savedStatsG.size();
		
		//see which position the input stats are closest to for each category
		String ppgEval;
		String rpgEval;
		String ftpEval;
		String tppEval;
		
		if (Math.abs(inputStats[0]-ppgG) > Math.abs(inputStats[0]-ppgF)) {
			ppgEval = "Forward";
		}
		else {
			ppgEval="Guard";
		}
		
		if (Math.abs(inputStats[1]-rpgG) > Math.abs(inputStats[1]-rpgF)) {
			rpgEval = "Forward";
		}
		else {
			rpgEval="Guard";
		}
		
		if (Math.abs(inputStats[2]-ftpG) > Math.abs(inputStats[2]-ftpF)) {
			ftpEval = "Forward";
		}
		else {
			ftpEval="Guard";
		}
		
		if (Math.abs(inputStats[3]-tppG) > Math.abs(inputStats[3]-tppF)) {
			tppEval = "Forward";
		}
		else {
			tppEval="Guard";
		}
		String[] results = new String[] {ppgEval, rpgEval, ftpEval, tppEval};
		
		//Pick the position with the closest cumulative stat difference
		int gCounter=0;
		int fCounter=0;
		for (String i : results) {
			if (i == "Guard") {
				gCounter+=1;
			}
			else {
				fCounter+=1;
			}
		}
		
		if (gCounter>fCounter) {
			return "Guard";
		}
		if (gCounter == fCounter){
			//tie breaker by cumulative distance
			double gCumulativeDistance= 
					Math.abs(inputStats[0]-ppgG) +
					Math.abs(inputStats[1]-rpgG)+
					Math.abs(inputStats[2]-ftpG)+
					Math.abs(inputStats[3]-tppG);
			
			double fCumulativeDistance = 
					Math.abs(inputStats[0]-ppgF) +
					Math.abs(inputStats[1]-rpgF) +
					Math.abs(inputStats[2]-ftpF) +
					Math.abs(inputStats[3]-tppF);
			
			if (gCumulativeDistance > fCumulativeDistance) {
				return "Guard";
			}
			else {
				return "Forward";
			}
			
		}
		else {
			return "Forward";
		}
		
	}

}
