package Dashboard;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Control {

	public Control() {
		DecimalFormat df = new DecimalFormat("0.00");
		ArrayList<Bot> bots = RunSimulation.bots;
		
		for (Bot dummyBot : bots) {
			Double averageSocTest = 0.0;
			for (Double dummy : dummyBot.getSocList()) {
				averageSocTest += dummy;
			}
			averageSocTest = averageSocTest / dummyBot.socList.size();
			View.outputLabel.setText("Average Soc Bot " + dummyBot.getId() + ": " + df.format(averageSocTest));
			
			System.out.println("Average Soc Bot " + dummyBot.getId() + ": " + df.format(averageSocTest));

		}

	}

}
