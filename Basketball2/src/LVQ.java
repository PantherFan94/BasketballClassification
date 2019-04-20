import java.awt.List;
import java.util.ArrayList;

public class LVQ {
	private ArrayList<Player> trainingData;
	private ArrayList<Player> codeBookVects;
	private ArrayList<Player> testData;
	private double alpha;
	private int maxEpoch;
	double[] max = {0,0,0,0};
	double[] min = {100,100,100,100};
	
	public LVQ(ArrayList<Player> td,ArrayList<Player> cbv,ArrayList<Player> testData) {
		trainingData = td;
		this.testData = testData;
		codeBookVects = cbv;
	}
		
	
	public ArrayList<Player> getTestData(){
		return this.testData;
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

		
	
	public ArrayList<Player> getCodeBook(){
		return codeBookVects;
	}

	
	
	public void train(double alpha,double maxEpoch) {
		double learningRate = 0;
		for(int epoch = 0; epoch < maxEpoch;epoch++) {
			

			learningRate = alpha*(1-((double) epoch/(double) maxEpoch));

			for(Player p:trainingData) {
				Player currentCBV = p.getClosest(codeBookVects);
				if(currentCBV.getPos().equals(p.getPos())){
					currentCBV.changePPG(learningRate*(p.getStats()[0] - currentCBV.getStats()[0]));
					currentCBV.changeRPG(learningRate*(p.getStats()[1] - currentCBV.getStats()[1]));
					currentCBV.changeFT(learningRate*(p.getStats()[2] - currentCBV.getStats()[2]));
					currentCBV.change3pt(learningRate*(p.getStats()[3] - currentCBV.getStats()[3]));
					
//					System.out.println(learningRate*(p.getStats()[0] - currentCBV.getStats()[0]) + " "+ p.getStats()[0] + " "+currentCBV.getStats()[0]);
				} else {
					currentCBV.changePPG(-1*(learningRate*(p.getStats()[0] - currentCBV.getStats()[0])));
					

					currentCBV.changeRPG(-1*(learningRate*(p.getStats()[1] - currentCBV.getStats()[1])));
					currentCBV.changeFT(-1*(learningRate*(p.getStats()[2] - currentCBV.getStats()[2])));
					currentCBV.change3pt(-1*(learningRate*(p.getStats()[3] - currentCBV.getStats()[3])));
				}
			}
		}
	}
	public String classify(Player p) {
		Player bmu = p.getClosest(codeBookVects);
		return bmu.getPos();
	}

}
