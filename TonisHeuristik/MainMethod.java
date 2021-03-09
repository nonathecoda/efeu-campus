package BachelorarbeitAntonia;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class MainMethod {

	static Campus campus = new Campus(Data.numberOfCustomers, Data.campusSize);
	static ArrayList<Customer> knots = campus.getKnots();
	static Customer depot = campus.getDepot();
	static ArrayList<Order> orders = Methods.createOrderList(knots, depot);

	public static void main(String[] args) {

		DecimalFormat df = new DecimalFormat("0.00");

		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter("resultsBA.csv");

			csvWriter.append("Order Nr.");
			csvWriter.append(';');
			csvWriter.append("PickUp");
			csvWriter.append(';');
			csvWriter.append("Delivery");
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
			csvWriter.append("Duration Charging Process");
			csvWriter.append('\n');

			for (int heuristicIterator = 0; heuristicIterator < 3; heuristicIterator++) {

				ArrayList<Double> averageSocs = new ArrayList<>();
				ArrayList<Bot> bots = Methods.createBotList(depot);
				ArrayList<List<String>> results = new ArrayList<>();
				int missedOrders = 0;

				switch (heuristicIterator) {
				case 0:
					csvWriter.append('\n');
					csvWriter.append("Not considering battery constraints");
					csvWriter.append('\n');

					break;
				case 1:
					csvWriter.append('\n');
					csvWriter.append("Toni's heuristic");
					csvWriter.append('\n');

					break;
				case 2:

					csvWriter.append('\n');
					csvWriter.append("Charge every 30 minutes and emergency charging");
					csvWriter.append('\n');

				}

				// wir gehen jede order durch
				for (int i = 0; i < orders.size(); i++) {

					Order currentOrder = orders.get(i);

					int currentBotIndex = Methods.assignRobotToTask(currentOrder, bots);

					try {
						Bot currentBot = bots.get(currentBotIndex);
						averageSocs.add(currentBot.getSoc());
						int timeTrackerBeforeExecution = currentBot.getTimeTracker();
 
						double executionTime = Methods.calculateDistance(currentBot.getLocation(),
								currentOrder.getPickUpID(), currentOrder.getDeliveryID()) / Data.velocity;
						double batteryConsumption = executionTime * Data.dechargingSpeed;

						currentBot.setTimeTracker((int) (currentBot.getTimeTracker() + executionTime));
						currentBot.setLocation(currentOrder.getDeliveryID());
						currentBot.setSoc(currentBot.getSoc() - batteryConsumption);

						int timeTrackerAfterExecution = currentBot.getTimeTracker();
						double socAfterExecution = currentBot.getSoc();
						double socAfterCharging = 000.0;
						int durationChargingProcess = 0;
						String chargingDecision = "Not decided yet.";
						switch (heuristicIterator) {
						case 0:
							currentBot.setSoc(Data.batteryCapacity);
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
								String.valueOf(currentBot.getId()), String.valueOf(timeTrackerBeforeExecution),
								String.valueOf(df.format(executionTime)), String.valueOf(df.format(socAfterExecution)),
								String.valueOf(chargingDecision), String.valueOf(df.format(socAfterCharging)),
								String.valueOf(durationChargingProcess));

						results.add(result);

					} catch (IndexOutOfBoundsException e) {
						// System.out.println("Order verpasst.");
						missedOrders++;
						List<String> result = Arrays.asList(String.valueOf(i + 1),
								String.valueOf(currentOrder.getPickUpID().getID()),
								String.valueOf(currentOrder.getDeliveryID().getID()),
								String.valueOf(currentOrder.getStartingTime()), "No Bot available.", "", "", "", "",
								"");

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
				csvWriter.append("Average SOC before execution:");
				csvWriter.append(';');
				csvWriter.append(String.valueOf(df.format(averageSoc)));
				csvWriter.append('\n');

				System.out.println("Missed Orders: " + missedOrders);
				System.out.println("Average Soc (before execution): " + String.valueOf(df.format(averageSoc)));
			}
			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
		}
	}
}
