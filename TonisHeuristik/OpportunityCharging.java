package Dashboard;

import Dashboard.DataDashboard;

public class OpportunityCharging {

	static String chargingHeuristic(Order currentOrder, Order nextOrder, Bot currentBot, double batteryConsumption) {

		String chargingDecision = "No charging.";
		try {

			Customer chargingStation = Methods.chooseClosestChargingStation(currentBot, nextOrder.getDeliveryID());

			double currentSoc = currentBot.getSoc();
			double executionTimeNextJob = (int) Math.round(Methods.calculateDistance(currentOrder.getDeliveryID(),
					nextOrder.getPickUpID(), nextOrder.getDeliveryID()) / DataDashboard.velocity);
			double batteryConsumptionNextJob = executionTimeNextJob * DataDashboard.dechargingSpeed;
			double minutesToChargingStation = Methods.calculateDistance(nextOrder.getDeliveryID(), chargingStation)
					/ DataDashboard.velocity;
			double batteryConsumptionToChargingStation = minutesToChargingStation * DataDashboard.dechargingSpeed;

			int idleTime = (int) Math.round(nextOrder.getLatest() - currentBot.getTimeTracker() - executionTimeNextJob
					- minutesToChargingStation);

//			System.out.println("BOT: " + currentBot.getId() + " Order: " + currentOrder.getId() + ", Next order: "
//					+ nextOrder.getId() + ", charging decision: " + chargingDecision + ", SOC: " + currentBot.getSoc()
//					+ ", batteryConsumptionNextJob: " + batteryConsumptionNextJob
//					+ ", batteryConsumptionToChargingStation: " + batteryConsumptionToChargingStation);

			boolean emergencyDefinition = DataDashboard.getEmergencyDefinition();
			if ((emergencyDefinition == true
					&& (currentBot.getSoc() - batteryConsumptionNextJob) <= (DataDashboard.batteryCapacity
							* DataDashboard.getEmergencyCharge()))
					|| (emergencyDefinition == false && (currentBot.getSoc() - batteryConsumptionNextJob
							- batteryConsumptionToChargingStation) <= (DataDashboard.batteryCapacity
									* DataDashboard.getEmergencyCharge()))) {

				chargingStation = Methods.chooseClosestChargingStation(currentBot, currentBot.getLocation());
				minutesToChargingStation = Methods.calculateDistance(currentBot.getLocation(), chargingStation)
						/ DataDashboard.velocity;
				batteryConsumptionToChargingStation = minutesToChargingStation * DataDashboard.dechargingSpeed;
				if (batteryConsumptionToChargingStation <= currentBot.getSoc()) {
					int chargingTime = (int) Math
							.round((DataDashboard.batteryCapacity - currentSoc) / DataDashboard.chargingSpeed);
					Methods.charge(currentBot, chargingTime, chargingStation);
					chargingDecision = "SOC not enough for next order.";
				} else {
					//System.out.println(
//							"Keine CS mehr erreicht. " + " currentOrder: " + currentOrder.getId() + "Current SOC: "
//									+ currentBot.getSoc() + ", batteryConsumptionNextJob: " + batteryConsumptionNextJob
//									+ ", batteryConsumptionToChargingStation: " + batteryConsumptionToChargingStation);
					currentBot.setTimeTracker(currentBot.getTimeTracker() + 20);
					int chargingTime = (int) Math
							.round((DataDashboard.batteryCapacity - currentSoc) / DataDashboard.chargingSpeed);
					Methods.charge(currentBot, chargingTime, chargingStation);
					
					chargingDecision = "BOT HAD TO BE BROUGHT TO CS MANUALLY";
				}

			}

			else if ((idleTime) > DataDashboard.getIdleTimeTrigger() && currentSoc < DataDashboard.getChargingGoal()) {
				chargingStation = Methods.chooseChargingStation(currentBot, nextOrder);
				int chargingTime = idleTime;
				Methods.charge(currentBot, chargingTime, chargingStation);
				chargingDecision = "Enough idletime (= " + idleTime + " min) to charge.";
			}
		} catch (Exception e) {

			chargingDecision = "No charging station in reach. Bot is removed.";
			e.printStackTrace();
		}

		return chargingDecision;
	}

}
