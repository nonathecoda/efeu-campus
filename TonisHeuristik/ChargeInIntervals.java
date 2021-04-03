package Dashboard;

public class ChargeInIntervals {

	public static String chargingHeuristic(Order currentOrder, Order nextOrder, Bot currentBot,
			double batteryConsumption) {
		String chargingDecision = "No charging.";

		if (currentBot.getSoc() < DataDashboard.getChargingGoal()
				&& ((currentBot.getTimeTracker() - currentBot.getLastChargingMoment()) >= DataDashboard
						.getChargingFrequency()
						|| currentBot.getSoc() < DataDashboard.batteryCapacity * DataDashboard.getEmergencyCharge())) {

			Customer chargingStation = Methods.chooseChargingStation(currentBot, nextOrder);

			double chargingTime = (DataDashboard.getChargingGoal() - currentBot.getSoc()) / DataDashboard.chargingSpeed;

			Methods.charge(currentBot, chargingTime, chargingStation);

			if ((currentBot.getTimeTracker() - currentBot.getLastChargingMoment()) >= DataDashboard
					.getChargingFrequency()) {

				chargingDecision = "Last charging " + DataDashboard.getChargingFrequency() + " min ago.";
			} else if (currentBot.getSoc() < DataDashboard.batteryCapacity * 0.3) {
				chargingDecision = "SOC under 30%.";
			}

			currentBot.setLastChargingMoment(currentBot.getTimeTracker());

		}
		return chargingDecision;
	}

}
