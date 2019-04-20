import java.util.ArrayList;


public class KsNearestNeighbor {
	double[] max = {0,0,0,0};
	double[] min = {100,100,100,100};
	private ArrayList <Player> dataSet;
	private ArrayList <Player> normalizedDataSet;
	private ArrayList <Player> testData;
	private ArrayList <Player> all;
	private ArrayList <Player> allNormalized;

	public KsNearestNeighbor(ArrayList <Player> dataSet,ArrayList <Player> testData) {
		this.dataSet = dataSet;
		this.testData = testData;
		all = new ArrayList <Player> ();
		all.addAll(this.dataSet);
		all.addAll(this.testData);
		allNormalized = normalize(all);
	}
	public ArrayList <Player> getNormalized(){
		return allNormalized;
	}
	
	public ArrayList<Player> normalize(ArrayList<Player> data) {
		
		for(Player p:data) {
			for(int i = 0; i<p.getStats().length;i++) {
				
				if(p.getStats()[i] > max[i]) {
					max[i] = p.getStats()[i];
				}
			}
		}
		
		for(Player p:data) {
			for(int i = 0; i<p.getStats().length;i++) {
				if(p.getStats()[i] < min[i]) {
					min[i] = p.getStats()[i];
				}
			}
		}
		double z = 0;
		for(Player p:data) {
			for(int i = 0; i<p.getStats().length;i++) {
				z = (p.getStats()[i] - min[i])/(max[i] - min[i]);
				p.setStats(i, z);
			}
		}
		return data;
	}
	
	public ArrayList<Player> ClosestPlayers (Player p){
		return p.getAllClosest(allNormalized);
	}
	
	public void unNormalize(ArrayList<Player> data) {
		double x = 0;
		for(Player p:data) {
			for(int i = 0; i<p.getStats().length;i++) {
				x = (p.getStats()[i]*(max[i]-min[i])) + min[i];
				p.setStats(i, x);
			}
		}
		
	}
	

}
