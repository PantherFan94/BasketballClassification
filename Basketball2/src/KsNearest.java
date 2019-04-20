import java.util.ArrayList;


public class KsNearestNeighbor {
	double[] max = {0,0,0,0};
	double[] min = {100,100,100,100};
	private ArrayList <Player> dataSet;
	private ArrayList <Player> testData;

	public KsNearestNeighbor(ArrayList <Player> dataSet,ArrayList <Player> testData) {
		this.dataSet = dataSet;
		this.testData = testData;

	}

	public String ClosestPlayers (Player p){
		ArrayList <Player> players = new ArrayList <Player>();
		players.addAll(dataSet); // copy of ArrayList
		ArrayList <Player> closest = new ArrayList <Player>();
		for(int k=0; k<3; k++) {
		closest.add(p.getClosest(players));
		players.remove(players.indexOf(p.getClosest(players))); // removes closest player from copy
		int fCount = 0;
		int gCount = 0;
		for(Player x: closest) {
			if(x.getPos()=="G") {
				gCount++;
			}
			else {
				fCount++;
			}
			if(gCount > fCount) {
				return "G";
			}
			else {
				return "F";
			}
		}
	}
	
	

}
}
