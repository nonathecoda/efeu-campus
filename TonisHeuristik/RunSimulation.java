package Dashboard;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.application.Application;

public class RunSimulation {

	// static DSCampus campus = new DSCampus(DSData.getNumberOfCustomers(),
	// DSData.getCampusSize());

	static ArrayList<Customer> knots;
	static ArrayList<Bot> bots;
	static int missedOrders;

	public RunSimulation(ArrayList<Customer> knots) {

		this.knots = knots;

		

		Customer depot = Campus.getDepot();
		ArrayList<Order> orders = Methods.createOrderList(knots, depot);
		
		DecimalFormat df = new DecimalFormat("0.00");

		int heuristicIterator = 0;

		if (View.noChargingButton.isSelected() == true) {
			View.exportLabel.setText("Export to file: resultsNoConstraints.csv");
			heuristicIterator = 0;
		} else if (View.intervalChargingButton.isSelected() == true) {
			View.exportLabel.setText("Export to file: resultsIdleTimeHeuristik.csv");
			heuristicIterator = 1;
		} else if (View.idleTimeChargingButton.isSelected() == true) {
			View.exportLabel.setText("Export to file: resultsIntervalHeuristik.csv");
			heuristicIterator = 2;
		}

		FileWriter csvWriter = null;
		try {

			switch (heuristicIterator) {
			case 0:
				csvWriter = new FileWriter("resultsNoConstraints.csv");

				break;
			case 1:
				csvWriter = new FileWriter("resultsIdleTimeHeuristik.csv");

				break;
			case 2:

				csvWriter = new FileWriter("resultsIntervalHeuristik.csv");

			}

			csvWriter.append("Order Nr.");
			csvWriter.append(';');
			csvWriter.append("PickUp");
			csvWriter.append(';');
			csvWriter.append("Delivery");
			csvWriter.append(';');
			csvWriter.append("StartingTime");
			csvWriter.append(';');
			csvWriter.append("Bot ID");
			csvWriter.append(';');
			csvWriter.append("Time of Execution");
			csvWriter.append(';');
			csvWriter.append("Execution Duration");
			csvWriter.append(';');
			csvWriter.append("State of charge");
			csvWriter.append(';');
			csvWriter.append("Charging Decision");
			csvWriter.append(';');
			csvWriter.append("SOC after charging");
			csvWriter.append(';');
			csvWriter.append("Charging Moment");
			csvWriter.append(';');
			csvWriter.append("Duration Charging Process");
			csvWriter.append('\n');
			ArrayList<Double> averageSocs = new ArrayList<>();
			this.bots = Methods.createBotList(depot);
			ArrayList<List<String>> results = new ArrayList<>();
			missedOrders = 0;

			// wir gehen jede order durch
			for (int i = 0; i < orders.size(); i++) {

				Order currentOrder = orders.get(i);

				int currentBotIndex = Methods.assignRobotToTask(currentOrder, bots);

				try {
					Bot currentBot = bots.get(currentBotIndex);

					currentBot.socList.add(currentBot.getSoc());
					averageSocs.add(currentBot.getSoc());
					int timeTrackerBeforeExecution = currentBot.getTimeTracker();

					double executionTime = Methods.calculateDistance(currentBot.getLocation(),
							currentOrder.getPickUpID(), currentOrder.getDeliveryID()) / DataDashboard.velocity;
					double batteryConsumption = executionTime * DataDashboard.dechargingSpeed;

					currentBot.setTimeTracker((int) (currentBot.getTimeTracker() + executionTime));
					currentBot.setLocation(currentOrder.getDeliveryID());
					if (heuristicIterator != 0) {
						currentBot.setSoc(currentBot.getSoc() - batteryConsumption);
					}

					int timeTrackerAfterExecution = currentBot.getTimeTracker();
					double socAfterExecution = currentBot.getSoc();
					double socAfterCharging = 000.0;
					int chargingMoment = -1;
					int durationChargingProcess = 0;
					String chargingDecision = "Not decided yet.";
					switch (heuristicIterator) {
					case 0:
						currentBot.setSoc(DataDashboard.batteryCapacity);

						chargingDecision = "No charging.";
						// keine Charging Heuristic
						break;
					case 1:

						try {
							Order nextOrder = orders.get(i + 1);
							chargingDecision = TonisHeuristic.chargingHeuristic(currentOrder, nextOrder, currentBot,
									batteryConsumption);
							if (chargingDecision.equals("No charging station in reach. Bot is removed.")) {
								missedOrders++;
								bots.remove(currentBot);
							} else {
								if (chargingDecision.equals("No charging.")) {
									chargingMoment = -1;
								} else {
									chargingMoment = currentBot.getTimeTracker();
								}

								durationChargingProcess = currentBot.getTimeTracker() - timeTrackerAfterExecution;
							}

						} catch (IndexOutOfBoundsException e) {
							chargingDecision = "Last Order, no charging process.";

						}
						socAfterCharging = currentBot.getSoc();
						break;
					case 2:
						// lade alle 30 min
						try {
							Order nextOrder = orders.get(i + 1);
							chargingDecision = ChargeInIntervals.chargingHeuristic(currentOrder, nextOrder,
									currentBot, batteryConsumption, timeTrackerBeforeExecution);
							if (chargingDecision.equals("No charging station in reach. Bot is removed.")) {
								missedOrders++;
								bots.remove(currentBot);
							} else {
								if (chargingDecision.equals("No charging.")) {
									chargingMoment = -1;
								} else {
									chargingMoment = currentBot.getTimeTracker();
								}

								durationChargingProcess = currentBot.getTimeTracker() - timeTrackerAfterExecution;
							}
						} catch (IndexOutOfBoundsException e) {
							chargingDecision = "Last Order, no charging process.";
						}
						socAfterCharging = currentBot.getSoc();
						break;
					}

					List<String> result = Arrays.asList(String.valueOf(i + 1),
							String.valueOf(currentOrder.getPickUpID().getID()),
							String.valueOf(currentOrder.getDeliveryID().getID()),
							String.valueOf(currentOrder.getStartingTime()), String.valueOf(currentBot.getId()),
							String.valueOf(timeTrackerBeforeExecution), String.valueOf(df.format(executionTime)),
							String.valueOf(df.format(socAfterExecution)), String.valueOf(chargingDecision),
							String.valueOf(df.format(socAfterCharging)), String.valueOf(chargingMoment),
							String.valueOf(durationChargingProcess));

					results.add(result);

				} catch (IndexOutOfBoundsException e) {
					// System.out.println("Order verpasst.");
					missedOrders++;
					List<String> result = Arrays.asList(String.valueOf(i + 1),
							String.valueOf(currentOrder.getPickUpID().getID()),
							String.valueOf(currentOrder.getDeliveryID().getID()),
							String.valueOf(currentOrder.getStartingTime()), "No Bot available.", "", "", "", "", "");

					results.add(result);
				}

			}

			Double averageSoc = 0.0;
			for (Double soc : averageSocs) {
				averageSoc = averageSoc + soc;
			}
			averageSoc = averageSoc / averageSocs.size();

			for (List<String> rowData : results) {
				csvWriter.append(String.join(";", rowData));
				csvWriter.append('\n');
			}
			csvWriter.append("MissedOrders:");
			csvWriter.append(';');
			csvWriter.append(String.valueOf(missedOrders));
			csvWriter.append('\n');

			Collections.sort(bots, Comparator.comparing(Bot::getId));
			String outputString = "<html><body>";
			for (Bot dummyBot : bots) {
				Double averageSocTest = 0.0;
				for (Double dummy : dummyBot.getSocList()) {
					averageSocTest += dummy;
				}
				averageSocTest = averageSocTest / dummyBot.socList.size();
				outputString = (outputString + "Average Soc Bot " + dummyBot.getId() + ": " + df.format(averageSocTest)
						+ " Wh<br>");
				csvWriter.append("Bot ID " + dummyBot.getId() + " - Average SOC before execution: ");
				csvWriter.append(';');
				csvWriter.append(String.valueOf(df.format(averageSocTest)));
				csvWriter.append('\n');
			}

			csvWriter.append("Average SOC before execution:");
			csvWriter.append(';');
			csvWriter.append(String.valueOf(df.format(averageSoc)));
			csvWriter.append('\n');

			outputString = outputString + "<br>Missed Orders: " + missedOrders + "</body></html>";
			View.outputLabel.setText(outputString);
			csvWriter.flush();
			csvWriter.close();
			// String.valueOf(df.format(averageSoc)));
		} catch (IOException e) {

		}

	}
}
