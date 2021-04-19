package Dashboard;

public class ChargeInIntervals {

	public static String chargingHeuristic(Order currentOrder, Order nextOrder, Bot currentBot,
			double batteryConsumption) {
		String chargingDecision = "No charging.";

		Customer chargingStation = Methods.chooseClosestChargingStation(currentBot, nextOrder.getDeliveryID());

		double currentSoc = currentBot.getSoc();
		double executionTimeNextJob = (int) Math.round(Methods.calculateDistance(currentOrder.getDeliveryID(),
				nextOrder.getPickUpID(), nextOrder.getDeliveryID()) / DataDashboard.velocity);
		double batteryConsumptionNextJob = executionTimeNextJob * DataDashboard.dechargingSpeed;
		double minutesToChargingStation = Methods.calculateDistance(nextOrder.getDeliveryID(), chargingStation)
				/ DataDashboard.velocity;
		double batteryConsumptionToChargingStation = minutesToChargingStation * DataDashboard.dechargingSpeed;

		boolean emergencyDefinition = DataDashboard.getEmergencyDefinition();
		if ((emergencyDefinition == true && (currentBot.getSoc()
				- batteryConsumptionNextJob) <= (DataDashboard.batteryCapacity * DataDashboard.getEmergencyCharge()))
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
				// System.out.println(
//						"Keine CS mehr erreicht. " + " currentOrder: " + currentOrder.getId() + "Current SOC: "
//								+ currentBot.getSoc() + ", batteryConsumptionNextJob: " + batteryConsumptionNextJob
//								+ ", batteryConsumptionToChargingStation: " + batteryConsumptionToChargingStation);
				currentBot.setTimeTracker(currentBot.getTimeTracker() + 20);
				int chargingTime = (int) Math
						.round((DataDashboard.batteryCapacity - currentSoc) / DataDashboard.chargingSpeed);
				Methods.charge(currentBot, chargingTime, chargingStation);
				System.out.println("BOT HAD TO BE BROUGHT TO CS MANUALLY");
				chargingDecision = "BOT HAD TO BE BROUGHT TO CS MANUALLY";
			}
		}

		else if (currentBot.getSoc() < DataDashboard.getChargingGoal()
				&& ((currentBot.getTimeTracker() - currentBot.getLastChargingMoment()) >= DataDashboard
						.getChargingFrequency())) {

			chargingStation = Methods.chooseChargingStation(currentBot, nextOrder);

			double chargingTime = (DataDashboard.getChargingGoal() - currentBot.getSoc()) / DataDashboard.chargingSpeed;

			Methods.charge(currentBot, chargingTime, chargingStation);

			chargingDecision = "Last charging " + DataDashboard.getChargingFrequency() + " min ago.";

			currentBot.setLastChargingMoment(currentBot.getTimeTracker());

		}
		return chargingDecision;
	}

}
