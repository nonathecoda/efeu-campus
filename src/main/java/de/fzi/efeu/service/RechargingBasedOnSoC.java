package de.fzi.efeu.service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import de.fzi.efeu.util.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

@Service
public class RechargingBasedOnSoC {


    @Autowired
    private OrderApi orderApi;

    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private BoxMountingDeviceApi boxMountingDeviceApi;

    @Autowired
    private ProcessMgmtApi processMgmtApi;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private AddressApi addressApi;

    @Autowired
    private BuildingApi buildingApi;

    @Value("${recharging.StateOfCharge}")
    private Integer soCVehicle;

    RechargingBasedOnSoC()
    {
        // And From your main() method or any other method
        Timer timer = new Timer();
        timer.schedule(new OneMinuteInterval(), 0, 60000);
    }
    // SoC monitor
    // TODO 1-min-interval
/*    private Map<String, Integer> mapVehicleSoC = new HashMap<>();

    public List<EfCaVehicle> listVehicle() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        return vehicles;
    }

    private int getVehicleSoC(final EfCaVehicle vehicle) throws ApiException {
        mapVehicleSoC.put(vehicle.getIdent(), checkSoCVehicle(vehicle));
        return mapVehicleSoC.get(vehicle.getIdent());
    } //Todo: will only the latest SoC be contained in the map
*/

    public int getVehicleSoC(final EfCaVehicle vehicle) throws ApiException {
        int vehicleSoC = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getRemainingRange(); //Assume: RemainingRange is meter.
        return vehicleSoC;
    }

    public class OneMinuteInterval extends TimerTask{
        public void run(){
            try {
                List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
                int curreuntSoc = 0;
                for (EfCaVehicle vehicle : vehicles) {
                    curreuntSoc = getVehicleSoC(vehicle);
                    if (curreuntSoc<50)
                    {
                        // TODO:
                    }
                }
            }
            catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}
