import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class AccelerationRunComparisonTest {

    private AccelerationRun stockRun;
    private AccelerationRun tunedRun;
    private AccelerationRunComparison comparison;

    @BeforeEach
    public void setUp(){

        stockRun = new AccelerationRun(
                40, 70, 2.0, 6.0);

        tunedRun = new AccelerationRun(
                40, 70, 2.0, 5.0);

        comparison = new AccelerationRunComparison(
                stockRun, tunedRun);
    }

    @Test
    public void testDurationDifference(){

        assertEquals(-1.0,
                comparison.getDurationDifference(),
                0.0001);
    }

    @Test
    public void testPercentImprovement(){

        assertEquals(25.0,
                comparison.getPercentImprovement(),
                0.0001);
    }

    @Test
    public void testGetFasterRun(){

        assertSame(tunedRun,
                comparison.getFasterRun());
    }

    @Test
    public void testEqualDuration(){

        AccelerationRun equalRun =
                new AccelerationRun(40, 70, 3.0, 7.0);

        AccelerationRunComparison equalComparison =
                new AccelerationRunComparison(
                        stockRun, equalRun);

        assertEquals(0.0,
                equalComparison.getDurationDifference(),
                0.0001);

        assertEquals(0.0,
                equalComparison.getPercentImprovement(),
                0.0001);

        assertSame(stockRun,
                equalComparison.getFasterRun());
    }

    @Test
    public void testZeroOriginalDuration(){

        AccelerationRun zeroRun =
                new AccelerationRun(40, 70, 2.0, 2.0);

        AccelerationRunComparison zeroComparison =
                new AccelerationRunComparison(
                        zeroRun, tunedRun);

        assertTrue(Double.isNaN(
                zeroComparison.getPercentImprovement()));
    }
}
