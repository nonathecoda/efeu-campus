package Dashboard;

import Dashboard.DataDashboard;

public class OpportunityCharging {

	static String chargingHeuristic(Order currentOrder, Order nextOrder, Bot currentBot, double batteryConsumption) {

		String chargingDecision = "No charging.";
		try {

			Customer chargingStation = Methods.chooseClosestChargingStation(currentBot);

			double currentSoc = currentBot.getSoc();
			double executionTimeNextJob = (int) Math.round(Methods.calculateDistance(currentOrder.getDeliveryID(),
					nextOrder.getPickUpID(), nextOrder.getDeliveryID()) / DataDashboard.velocity);
			double batteryConsumptionNextJob = executionTimeNextJob * DataDashboard.dechargingSpeed;
			int minutesToChargingStation = (int) (Methods.calculateDistance(currentBot.getLocation(), chargingStation)
					/ DataDashboard.velocity);
			double batteryConsumptionToChargingStation = minutesToChargingStation * DataDashboard.dechargingSpeed;

			int idleTime = (int) Math.round(nextOrder.getLatest() - currentBot.getTimeTracker() - executionTimeNextJob
					- minutesToChargingStation);

			boolean emergencyDefinition = DataDashboard.getEmergencyDefinition();
			if ((emergencyDefinition == true
					&& (currentBot.getSoc() - batteryConsumptionNextJob) <= (DataDashboard.batteryCapacity
							* DataDashboard.getEmergencyCharge()))
					|| (emergencyDefinition == false && (currentBot.getSoc() - batteryConsumptionNextJob
							- batteryConsumptionToChargingStation) <= (DataDashboard.batteryCapacity
									* DataDashboard.getEmergencyCharge()))) {
				int chargingTime = (int) Math
						.round((DataDashboard.batteryCapacity - currentSoc) / DataDashboard.chargingSpeed);
				Methods.charge(currentBot, chargingTime, chargingStation);
				chargingDecision = "SOC not enough for next order.";
			}

			else if ((idleTime) > 10 && currentSoc < DataDashboard.getChargingGoal()) {
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
