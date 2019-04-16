import java.util.ArrayList;

public class Player {
	
	private String pos;
	private double[] stats;
	
	public Player(double [] stats, String pos ) {
	
		this.pos = pos;
		this.stats = stats;
	}
	public double[] getStats() {
		return stats;
	}
	public String getPos() {
		return pos;
	}
	public void changePPG(double delta) {
		if(stats[0]+ delta < 0) {
			stats[0] = 0;
		} else if(stats[0] + delta > 1) {
			stats[0] = 1;
		}else {
			stats[0] += delta;

		}
	}
	public void changeRPG(double delta) {
		if(stats[1]+ delta < 0) {
			stats[1] = 0;
		} else if(stats[1] + delta > 1) {
			stats[1] = 1;
		}else {
			stats[1] += delta;

		}
	}
	
	public void changeFT(double delta) {
		if(stats[2]+ delta < 0) {
			stats[2] = 0;
		} else if(stats[2] + delta > 1) {
			stats[2] = 1;
		}else {
			stats[2] += delta;

		}
	}
	public void change3pt(double delta) {
		if(stats[3]+ delta < 0) {
			stats[3] = 0;
		} else if(stats[0] + delta > 1) {
			stats[3] = 1;
		}else {
			stats[3] += delta;

		}
	}
	public void setStats(int index,double val) {
		stats[index] = val;
	}
	public double getDistance(Player p) {
		double sum = 0;
		for(int i = 0; i<stats.length; i++) {
			sum += Math.abs(Math.pow((stats[i] - p.getStats()[i]), 2));
		}
		return Math.sqrt(sum);
	}
	public Player getClosest(ArrayList<Player> players) {
		double min = getDistance(players.get(0));
		Player closestPlayer = players.get(0);
		for(Player p: players) {
			if(getDistance(p) < min) {
				min = getDistance(p);
				closestPlayer = p;
			}
		}
		return closestPlayer;
	}
}
