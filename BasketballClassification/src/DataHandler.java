import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHandler {
private ArrayList<Player> players;
private ArrayList<Player> guards;
private ArrayList<Player> fowards;
private ArrayList<Player> trainingData;
private ArrayList<Player> testData;
private ArrayList<Player> codeBookVectors;
private ArrayList<Player> normalizedData;
private double[] min = {100,100,100,100};
private double[] max = {0,0,0,0};

public DataHandler(ArrayList<Player> cbv,ArrayList<Player> td) {
	File g = new File("G.csv");
	File f = new File("F.csv");
	codeBookVectors = cbv;
	guards = readData(g);
	fowards = readData(f);
	players.addAll(guards);
	players.addAll(fowards);
	players.addAll(td);
	normalizedData = normalize(players);
	trainingData = new ArrayList<Player>();
	for(int i =0; i<guards.size()+fowards.size();i++) {
		trainingData.add(players.get(i));
	}
}
public static ArrayList<Player> readData(File file) {
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<String> lines = new ArrayList<String>();
	String pos = "";
	if(file.getName().equals("F.csv")) {
		pos = "F";
	}else {
		pos = "G";
	}
	try {
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			lines.add(reader.nextLine());
		}
		String[] parts = new String[4];
		
		for(String line:lines) {
			double[] stats = {0,0,0,0};
			parts = line.split(",");
			
			for(int i = 0; i<stats.length;i++) {
				stats[i] = Double.parseDouble(parts[i]);
			}
			Player p = new Player(stats,pos);
			players.add(p);
		}
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return players;
}
public static double[] avg(double[] a, double[] b) {
	double[] avg = new double[a.length];
	
	
	for(int i = 0; i<a.length;i++) {
		avg[i] = 0;
	}
	for(int i = 0; i<a.length;i++) {
		avg[i] = a[i] + b[i];
	}
	for(int i = 0; i<a.length;i++) {
		avg[i] /= 2;
	}
	return avg;
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
public void unNormalize(ArrayList<Player> data) {
	double x = 0;
	for(Player p:data) {
		for(int i = 0; i<p.getStats().length;i++) {
			x = (p.getStats()[i]*(max[i]-min[i])) + min[i];
			p.setStats(i, x);
		}
	}
	
}
public ArrayList<Player> getCodeBook(){
	return codeBookVectors;
}
}
