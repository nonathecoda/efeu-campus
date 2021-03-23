package Dashboard;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;

public class MainMethodDashboard {

	public static void main(String[] args) {

		View frameDashboard = new View();
		frameDashboard.setTitle("Dashboard");
		frameDashboard.setSize(1000, 800);
		frameDashboard.setResizable(true);
		frameDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameDashboard.setVisible(true);

	}
}
