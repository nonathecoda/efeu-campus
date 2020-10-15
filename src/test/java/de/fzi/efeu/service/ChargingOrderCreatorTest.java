package de.fzi.efeu.service;

import liquibase.pro.packaged.F;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;

@lombok.Getter
@lombok.Setter

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChargingOrderCreatorTest {

    @Autowired
    private RechargingBasedOnSoC_old chargingOrderCreator;

    @Test
    public void test() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(new Date()); // Get date of this day

        OffsetDateTime startTime1 = OffsetDateTime.parse(today + "T01:00");
        OffsetDateTime stopTime1 = OffsetDateTime.parse(today + "T02:00");
        OffsetDateTime startTime2 = OffsetDateTime.parse(today + "T06:00");
        OffsetDateTime stopTime2 = OffsetDateTime.parse(today + "T07:00");

        ChargingOrder order = chargingOrderCreator.createChargingOrder(startTime1, stopTime1, "abc");
        Assert.assertEquals(1, 1);
    }

    public static void main(String[] args) {

        // Daily Orders
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(new Date()); // Get date of this day

        OffsetDateTime startTime1 = OffsetDateTime.parse(today + "T01:00");
        OffsetDateTime stopTime1 = OffsetDateTime.parse(today + "T02:00");
        OffsetDateTime startTime2 = OffsetDateTime.parse(today + "T06:00");
        OffsetDateTime stopTime2 = OffsetDateTime.parse(today + "T07:00");

        for(int i=0; i<5; i++) {
            RechargingBasedOnSoC_old.createChargingOrder(startTime1, stopTime1, i);
            RechargingBasedOnSoC_old.createChargingOrder(startTime2, stopTime2, i);
        }

        // Other Orders
        Float vehSOC = 0.4f;
        if (RechargingBasedOnSoC_old.needToCreateChargingOrder(vehSOC))
            RechargingBasedOnSoC_old.createChargingOrder(startTime1, vehSOC, 1);
    }

}


