public class Output {

	int missedOrders;
	double soc;

	public Output(int missedOrders, double soc) {
		this.missedOrders = missedOrders;
		this.soc = soc;
	}

	synchronized int getInt() {
		return this.missedOrders;
	}
	synchronized double getSoc() {
		return this.soc;
	}

}