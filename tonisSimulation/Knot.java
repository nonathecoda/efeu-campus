package testYourCode;
public class Knot {
	double x, y;
	int knotID, character;

	Knot(double x, double y, int knotID, int character) {
		this.x = x;
		this.y = y;
		this.knotID = knotID;
		this.character = character;
	}

	double getX() {
		double x = this.x;
		return x;
	}

	double getY() {
		double y = this.y;
		return y;
	}

//Depot: 1, Ladestationen: -1, keine Lademöglichkeit: 0
	void setCharacter(int character) {
		this.character = character;
	}

	int getCharacter() {
		int character = this.character;
		return character;
	}

	void setID(int ID) {
		this.knotID = ID;
	}

	int getID() {
		int iD = this.knotID;
		return iD;
	}
}