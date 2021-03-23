package Dashboard;

import java.io.*;
import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GraficChargingOperationsPerHour extends Application {

	@Override
	public void start(Stage s) throws Exception {

		String icRow;
		ArrayList<Integer> chargingMomentsIc = new ArrayList<>();
		int iCfirstH = 0;
		int iCsecondH = 0;
		int iCthirdH = 0;
		int iCfourthH = 0;
		int iCfifthH = 0;
		int iCsixthH = 0;
		int iCseventhH = 0;
		int iCeighthH = 0;

		BufferedReader csvReaderIC;
		try {
			csvReaderIC = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsIntervalHeuristik.csv"));

			while ((icRow = csvReaderIC.readLine()) != null) {
				String[] data = icRow.split(";");

				try {
					chargingMomentsIc.add(Integer.parseInt(data[10]));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderIC.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < chargingMomentsIc.size(); i++) {

			int chargingMoment = chargingMomentsIc.get(i);

			if (chargingMoment >= 0 && chargingMoment < 60) {
				iCfirstH++;
			} else if (chargingMoment >= 60 && chargingMoment < 120) {
				iCsecondH++;
			} else if (chargingMoment >= 120 && chargingMoment < 180) {
				iCthirdH++;
			} else if (chargingMoment >= 180 && chargingMoment < 240) {
				iCfourthH++;
			} else if (chargingMoment >= 240 && chargingMoment < 300) {
				iCfifthH++;
			} else if (chargingMoment >= 300 && chargingMoment < 360) {
				iCsixthH++;
			} else if (chargingMoment >= 360 && chargingMoment < 420) {
				iCseventhH++;
			} else if (chargingMoment >= 420 && chargingMoment < 480) {
				iCeighthH++;
			}

		}

		int sumIc = iCfirstH + iCsecondH + iCthirdH + iCfourthH + iCfifthH + iCsixthH + iCseventhH + iCeighthH;

		String tcRow;
		ArrayList<Integer> chargingMomentsTc = new ArrayList<>();
		int tcFirstH = 0;
		int tcSecondH = 0;
		int tcThirdH = 0;
		int tcFourthH = 0;
		int tcFifthH = 0;
		int tcSixthH = 0;
		int tcSeventhH = 0;
		int tcEighthH = 0;
		BufferedReader csvReaderTC;
		try {
			csvReaderTC = new BufferedReader(
					new FileReader("/Users/Toni/eclipse-workspace/Dashboard/resultsIdleTimeHeuristik.csv"));

			while ((tcRow = csvReaderTC.readLine()) != null) {
				String[] data = tcRow.split(";");

				try {
					chargingMomentsTc.add(Integer.parseInt(data[10]));
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			csvReaderTC.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < chargingMomentsTc.size(); i++) {

			int chargingMoment = chargingMomentsTc.get(i);

			if (chargingMoment >= 0 && chargingMoment < 60) {
				tcFirstH++;
			} else if (chargingMoment >= 60 && chargingMoment < 120) {
				tcSecondH++;
			} else if (chargingMoment >= 120 && chargingMoment < 180) {
				tcThirdH++;
			} else if (chargingMoment >= 180 && chargingMoment < 240) {
				tcFourthH++;
			} else if (chargingMoment >= 240 && chargingMoment < 300) {
				tcFifthH++;
			} else if (chargingMoment >= 300 && chargingMoment < 360) {
				tcSixthH++;
			} else if (chargingMoment >= 360 && chargingMoment < 420) {
				tcSeventhH++;
			} else if (chargingMoment >= 420 && chargingMoment < 480) {
				tcEighthH++;
			}

		}

		int sumTc = tcFirstH + tcSecondH + tcThirdH + tcFourthH + tcFifthH + tcSixthH + tcSeventhH + tcEighthH;

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Hour");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Number of charging operations");
		BarChart<String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);

		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName("Interval Charging");
		XYChart.Data<String, Number> iC1 = createData("1", iCfirstH, sumIc);
		XYChart.Data<String, Number> iC2 = createData("2", iCsecondH, sumIc);
		XYChart.Data<String, Number> iC3 = createData("3", iCthirdH, sumIc);
		XYChart.Data<String, Number> iC4 = createData("4", iCfourthH, sumIc);
		XYChart.Data<String, Number> iC5 = createData("5", iCfifthH, sumIc);
		XYChart.Data<String, Number> iC6 = createData("6", iCsixthH, sumIc);
		XYChart.Data<String, Number> iC7 = createData("7", iCseventhH, sumIc);
		XYChart.Data<String, Number> iC8 = createData("8", iCeighthH, sumIc);
		series1.getData().addAll(iC1, iC2, iC3, iC4, iC5, iC6, iC7, iC8);

		XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
		series2.setName("T.Heuristik Charging");
		XYChart.Data<String, Number> hour12 = createData("1", tcFirstH, sumTc);
		XYChart.Data<String, Number> hour22 = createData("2", tcSecondH, sumTc);
		XYChart.Data<String, Number> hour32 = createData("3", tcThirdH, sumTc);
		XYChart.Data<String, Number> hour42 = createData("4", tcFourthH, sumTc);
		XYChart.Data<String, Number> hour52 = createData("5", tcFifthH, sumTc);
		XYChart.Data<String, Number> hour62 = createData("6", tcSixthH, sumTc);
		XYChart.Data<String, Number> hour72 = createData("7", tcSeventhH, sumTc);
		XYChart.Data<String, Number> hour82 = createData("8", tcEighthH, sumTc);
		series2.getData().addAll(hour12, hour22, hour32, hour42, hour52, hour62, hour72, hour82);

		Scene scene = new Scene(chart);
		chart.getData().addAll(series1, series2);

		s.setTitle("Frequency of charging operations per hour");
		s.setScene(scene);
		s.show();

	}

	private XYChart.Data createData(String country, double value, double sum) {

		DecimalFormat df = new DecimalFormat("0.0");

		XYChart.Data data = new XYChart.Data(country, value);

		double percentage = value * 100 / sum;

		String text = df.format(percentage) + "%";

		StackPane node = new StackPane();
		Label label = new Label(text);
		label.setRotate(-90);
		Group group = new Group(label);
		StackPane.setAlignment(group, Pos.BOTTOM_CENTER);
		StackPane.setMargin(group, new Insets(0, 0, 5, 0));
		node.getChildren().add(group);
		data.setNode(node);

		return data;
	}

}
