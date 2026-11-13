import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    private Vehicle vehicle;

    @BeforeEach
    public void setUp(){

        vehicle = new Vehicle("Test Mustang");

        vehicle.addDataPoint(
                new DataPoint(0, 2000, 25, 30, 190));

        vehicle.addDataPoint(
                new DataPoint(1, 2800, 35, 95, 191));

        vehicle.addDataPoint(
                new DataPoint(2, 3800, 45, 100, 192));

        vehicle.addDataPoint(
                new DataPoint(3, 4800, 55, 100, 193));

        vehicle.addDataPoint(
                new DataPoint(4, 5800, 65, 100, 194));

        vehicle.addDataPoint(
                new DataPoint(5, 6800, 75, 100, 195));

        vehicle.addDataPoint(
                new DataPoint(6, 3500, 76, 20, 196));
    }

    @Test
    public void testDetectWotPull(){

        ArrayList<WotPull> pulls = vehicle.getWotPulls();

        assertEquals(1, pulls.size());

        WotPull pull = pulls.get(0);

        assertTrue(pull.isComplete());

        assertEquals(4.0,
                pull.getDuration(), 0.0001);

        assertEquals(6800.0,
                pull.getPeakRpm(), 0.0001);

        assertEquals(40.0,
                pull.getSpeedGain(), 0.0001);
    }

    @Test
    public void testAccelerationRun(){

        ArrayList<AccelerationRun> runs =
                vehicle.getAccelerationRuns(40, 70);

        assertEquals(1, runs.size());

        AccelerationRun run = runs.get(0);

        assertEquals(1.5,
                run.getStartTime(), 0.0001);

        assertEquals(4.5,
                run.getEndTime(), 0.0001);

        assertEquals(3.0,
                run.getDuration(), 0.0001);
    }
    @Test
    public void testRejectSingleReadingPull(){

        Vehicle testVehicle = new Vehicle("Spike Test");

        testVehicle.addDataPoint(
                new DataPoint(0, 2000, 25, 30, 190));

        testVehicle.addDataPoint(
                new DataPoint(1, 3000, 35, 100, 191));

        testVehicle.addDataPoint(
                new DataPoint(2, 3100, 36, 30, 192));

        assertEquals(0, testVehicle.getWotPulls().size());
    }

    @Test
    public void testIncompletePullAtEnd(){

        Vehicle testVehicle = new Vehicle("Incomplete Test");

        testVehicle.addDataPoint(
                new DataPoint(0, 2000, 25, 30, 190));

        testVehicle.addDataPoint(
                new DataPoint(1, 3000, 35, 100, 191));

        testVehicle.addDataPoint(
                new DataPoint(2, 4000, 45, 100, 192));

        testVehicle.addDataPoint(
                new DataPoint(3, 5000, 55, 100, 193));

        ArrayList<WotPull> pulls = testVehicle.getWotPulls();

        assertEquals(1, pulls.size());

        assertFalse(pulls.get(0).isComplete());

        assertEquals(2.0,
                pulls.get(0).getDuration(), 0.0001);
    }

    @Test
    public void testIncompletePullAtStart(){

        Vehicle testVehicle = new Vehicle("Start Test");

        testVehicle.addDataPoint(
                new DataPoint(0, 3000, 35, 100, 190));

        testVehicle.addDataPoint(
                new DataPoint(1, 4000, 45, 100, 191));

        testVehicle.addDataPoint(
                new DataPoint(2, 5000, 55, 100, 192));

        testVehicle.addDataPoint(
                new DataPoint(3, 3500, 56, 20, 193));

        ArrayList<WotPull> pulls = testVehicle.getWotPulls();

        assertEquals(1, pulls.size());

        assertFalse(pulls.get(0).isComplete());
    }

    @Test
    public void testRejectAccelerationAcrossGap(){

        Vehicle testVehicle = new Vehicle("Gap Test");

        testVehicle.addDataPoint(
                new DataPoint(0, 2000, 25, 30, 190));

        testVehicle.addDataPoint(
                new DataPoint(1, 2800, 35, 95, 191));

        testVehicle.addDataPoint(
                new DataPoint(2, 3800, 45, 100, 192));

        testVehicle.addDataPoint(
                new DataPoint(3, 4800, 55, 100, 193));

        testVehicle.addDataPoint(
                new DataPoint(9, 6000, 75, 100, 194));

        testVehicle.addDataPoint(
                new DataPoint(10, 6500, 85, 100, 195));

        testVehicle.addDataPoint(
                new DataPoint(11, 3500, 86, 20, 196));

        ArrayList<AccelerationRun> runs =
                testVehicle.getAccelerationRuns(40, 70);

        assertTrue(runs.isEmpty());
    }

    @Test
    public void testWotPullSplitAcrossGap(){

        Vehicle testVehicle = new Vehicle("WOT Gap Test");

        testVehicle.addDataPoint(
                new DataPoint(0, 2000, 25, 30, 190));

        testVehicle.addDataPoint(
                new DataPoint(1, 3000, 35, 100, 191));

        testVehicle.addDataPoint(
                new DataPoint(2, 4000, 45, 100, 192));

        testVehicle.addDataPoint(
                new DataPoint(8, 5000, 65, 100, 193));

        testVehicle.addDataPoint(
                new DataPoint(9, 6000, 75, 100, 194));

        testVehicle.addDataPoint(
                new DataPoint(10, 3500, 76, 20, 195));

        ArrayList<WotPull> pulls = testVehicle.getWotPulls();

        assertEquals(2, pulls.size());

        assertFalse(pulls.get(0).isComplete());

        assertFalse(pulls.get(1).isComplete());

        assertEquals(1.0,
                pulls.get(0).getDuration(), 0.0001);

        assertEquals(1.0,
                pulls.get(1).getDuration(), 0.0001);
    }
}

