package Dashboard;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CSVWriter {

	static FileWriter startCSV() {
		FileWriter csvWriter = null;

		try {
			switch (DataDashboard.getHeuristicIterator()) {
			case 0:
				csvWriter = new FileWriter("resultsNoConstraints.csv");
				break;
			case 1:
				csvWriter = new FileWriter("resultsOpportunityHeuristik.csv");
				break;
			case 2:
				csvWriter = new FileWriter("resultsIntervalHeuristik.csv");
				break;
			case 3:
				csvWriter = new FileWriter("resultsEmergencyHeuristik.csv");
			}

			csvWriter.append("Order ID");
			csvWriter.append(';');
			csvWriter.append("PickUp");
			csvWriter.append(';');
			csvWriter.append("Delivery");
			csvWriter.append(';');
			csvWriter.append("Time Window");
			csvWriter.append(';');
			csvWriter.append("Bot ID");
			csvWriter.append(';');
			csvWriter.append("TimeTracker Bot");
			csvWriter.append(';');
			csvWriter.append("State of charge before Execution");
			csvWriter.append(';');
			csvWriter.append("Execution Duration");
			csvWriter.append(';');
			csvWriter.append("Charging Decision");
			csvWriter.append(';');
			csvWriter.append("SOC after charging");
			csvWriter.append(';');
			csvWriter.append("Charging Moment");
			csvWriter.append(';');
			csvWriter.append("Duration Charging Process");
			csvWriter.append('\n');

		} catch (Exception e) {
			e.printStackTrace();
		}
		return csvWriter;
	}

	static void endCSV(FileWriter csvWriter, ArrayList<Double> averageSocList, ArrayList<Integer> execDurationPerBot,
			ArrayList<Integer> chargingDurationPerBot) {

		DecimalFormat df = new DecimalFormat("0.00");

		try {
			for (List<String> rowData : TestRun.results) {
				csvWriter.append(String.join(";", rowData));
				csvWriter.append('\n');
			}

			csvWriter.append("Late Orders:");
			csvWriter.append(';');
			csvWriter.append(String.valueOf(TestRun.lateOrders));
			csvWriter.append('\n');

			csvWriter.append("Missed Orders:");
			csvWriter.append(';');
			csvWriter.append(String.valueOf(TestRun.missedOrders));
			csvWriter.append('\n');

			for (int i = 0; i < averageSocList.size(); i++) {
				csvWriter.append("Bot ID " + i + " - Average SOC before execution: ");
				csvWriter.append(';');
				csvWriter.append(String.valueOf(df.format(averageSocList.get(i))));
				csvWriter.append('\n');
			}
			csvWriter.append('\n');
			for (int i = 0; i < execDurationPerBot.size(); i++) {
				csvWriter.append("Bot ID " + i + " - Pure execution time: ");
				csvWriter.append(';');
				csvWriter.append(String.valueOf(df.format(execDurationPerBot.get(i))));
				csvWriter.append('\n');
			}
			csvWriter.append('\n');
			for (int i = 0; i < chargingDurationPerBot.size(); i++) {
				csvWriter.append("Bot ID " + i + " - Charging duration: ");
				csvWriter.append(';');
				csvWriter.append(String.valueOf(df.format(chargingDurationPerBot.get(i))));
				csvWriter.append('\n');
			}

			csvWriter.flush();
			csvWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
