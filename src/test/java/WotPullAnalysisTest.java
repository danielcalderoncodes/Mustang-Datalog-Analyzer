import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class WotPullAnalysisTest {

    private Vehicle vehicle;
    private WotPullAnalysis analysis;

    @BeforeEach
    public void setUp(){

        vehicle = new Vehicle("Analysis Test");

        // Before first pull
        vehicle.addDataPoint(
                new DataPoint(0, 2000, 25, 30, 190));

        // Complete pull 1
        vehicle.addDataPoint(
                new DataPoint(1, 3000, 35, 100, 191));

        vehicle.addDataPoint(
                new DataPoint(2, 4000, 45, 100, 192));

        vehicle.addDataPoint(
                new DataPoint(3, 5000, 55, 100, 193));

        // End first pull
        vehicle.addDataPoint(
                new DataPoint(4, 3000, 56, 20, 194));

        // Complete pull 2
        vehicle.addDataPoint(
                new DataPoint(5, 3200, 40, 100, 195));

        vehicle.addDataPoint(
                new DataPoint(6, 4500, 55, 100, 196));

        vehicle.addDataPoint(
                new DataPoint(7, 5800, 70, 100, 197));

        vehicle.addDataPoint(
                new DataPoint(8, 7000, 85, 100, 198));

        // End second pull
        vehicle.addDataPoint(
                new DataPoint(9, 3500, 86, 20, 199));

        // Incomplete pull
        vehicle.addDataPoint(
                new DataPoint(10, 4000, 50, 100, 200));

        vehicle.addDataPoint(
                new DataPoint(11, 6000, 80, 100, 201));

        vehicle.addDataPoint(
                new DataPoint(12, 7500, 110, 100, 202));

        analysis = new WotPullAnalysis(vehicle);
    }

    @Test
    public void testNumberOfPulls(){

        assertEquals(3, analysis.getNumberOfPulls());
    }

    @Test
    public void testNumberOfCompletePulls(){

        assertEquals(2,
                analysis.getNumberOfCompletePulls());
    }

    @Test
    public void testNumberOfIncompletePulls(){

        assertEquals(1,
                analysis.getNumberOfIncompletePulls());
    }

    @Test
    public void testGetFastestPull(){

        WotPull fastest = analysis.getFastestPull();

        assertNotNull(fastest);

        assertEquals(15.0,
                fastest.getAverageAcceleration(), 0.0001);

        assertTrue(fastest.isComplete());
    }

    @Test
    public void testGetLongestPull(){

        WotPull longest = analysis.getLongestPull();

        assertNotNull(longest);

        assertEquals(3.0,
                longest.getDuration(), 0.0001);
    }

    @Test
    public void testGetHighestRpmPull(){

        WotPull highest = analysis.getHighestRpmPull();

        assertNotNull(highest);

        assertEquals(7000.0,
                highest.getPeakRpm(), 0.0001);
    }

    @Test
    public void testGetAveragePullAcceleration(){

        assertEquals(12.5,
                analysis.getAveragePullAcceleration(),
                0.0001);
    }

    @Test
    public void testNoCompletePulls(){

        Vehicle incompleteVehicle =
                new Vehicle("Incomplete Test");

        incompleteVehicle.addDataPoint(
                new DataPoint(0, 3000, 35, 100, 190));

        incompleteVehicle.addDataPoint(
                new DataPoint(1, 4000, 45, 100, 191));

        WotPullAnalysis incompleteAnalysis =
                new WotPullAnalysis(incompleteVehicle);

        assertEquals(0,
                incompleteAnalysis.getNumberOfCompletePulls());

        assertNull(incompleteAnalysis.getFastestPull());

        assertNull(incompleteAnalysis.getLongestPull());

        assertNull(incompleteAnalysis.getHighestRpmPull());

        assertEquals(0.0,
                incompleteAnalysis.getAveragePullAcceleration(),
                0.0001);
    }

    @Test
    public void testEmptyVehicle(){

        Vehicle emptyVehicle = new Vehicle("Empty Test");

        WotPullAnalysis emptyAnalysis =
                new WotPullAnalysis(emptyVehicle);

        assertEquals(0, emptyAnalysis.getNumberOfPulls());

        assertNull(emptyAnalysis.getFastestPull());

        assertEquals(0.0,
                emptyAnalysis.getAveragePullAcceleration(),
                0.0001);
    }
}
