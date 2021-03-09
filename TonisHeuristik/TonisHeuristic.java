package BachelorarbeitAntonia;

public class TonisHeuristic {

	static String chargingHeuristic(Order currentOrder, Order nextOrder, Bot currentBot, double batteryConsumption) {

		String chargingDecision = "No charging.";
		try {

			Customer chargingStation = Methods.chooseChargingStation(currentBot, nextOrder);

			double currentSoc = currentBot.getSoc();
			double executionTimeNextJob = Methods.calculateDistance(currentOrder.getDeliveryID(),
					nextOrder.getPickUpID(), nextOrder.getDeliveryID()) / Data.velocity;
			double batteryConsumptionNextJob = executionTimeNextJob * Data.dechargingSpeed;
			int minutesToChargingStation = (int) (Methods.calculateDistance(currentBot.getLocation(), chargingStation)
					/ Data.velocity);

			int idleTime = nextOrder.getStartingTime() - currentBot.getTimeTracker() - minutesToChargingStation;

			if (currentSoc < Data.batteryCapacity * 0.3) {
				double chargingTime = (Data.chargingGoal - currentSoc) / Data.chargingSpeed;
				Methods.charge(currentBot, chargingTime, chargingStation);
				chargingDecision = "SOC under 30%.";

			} /*
				 * else if (currentSoc < (batteryConsumptionNextJob + Data.batteryCapacity *
				 * 0.3)) { double chargingTime = (Data.batteryCapacity - currentSoc) /
				 * Data.chargingSpeed; Methods.charge(currentBot, chargingTime,
				 * chargingStation); chargingDecision = "SOC not enough for next order."; }
				 */
			else if ((idleTime) > 30 && currentSoc != Data.batteryCapacity) {
				double chargingTime = idleTime;
				Methods.charge(currentBot, chargingTime, chargingStation);
				chargingDecision = "Enough idletime (= " + idleTime + " min) to charge.";
			}
		} catch (Exception e) {
			chargingDecision = "No charging station in reach. Bot is removed.";
		}

		return chargingDecision;
	}

}
