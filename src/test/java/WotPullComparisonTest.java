import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class WotPullComparisonTest {

    private WotPull stockPull;
    private WotPull tunedPull;
    private WotPullComparison comparison;

    @BeforeEach
    public void setUp(){

        stockPull = new WotPull(
                2, 6,
                2800, 6500,
                6500,
                35, 75,
                true
        );

        tunedPull = new WotPull(
                2, 6,
                2800, 7000,
                7000,
                35, 85,
                true
        );

        comparison = new WotPullComparison(
                stockPull, tunedPull);
    }

    @Test
    public void testCompareAcceleration(){

        assertEquals(2.5,
                comparison.compareAcceleration(),
                0.0001);
    }

    @Test
    public void testComparePeakRpm(){

        assertEquals(500.0,
                comparison.comparePeakRpm(),
                0.0001);
    }

    @Test
    public void testCompareSpeedGain(){

        assertEquals(10.0,
                comparison.compareSpeedGain(),
                0.0001);
    }

    @Test
    public void testCompareDuration(){

        assertEquals(0.0,
                comparison.compareDuration(),
                0.0001);
    }

    @Test
    public void testAccelerationPercentChange(){

        assertEquals(25.0,
                comparison.getAccelerationPercentChange(),
                0.0001);
    }

    @Test
    public void testPeakRpmPercentChange(){

        assertEquals(7.6923077,
                comparison.getPeakRpmPercentChange(),
                0.0001);
    }

    @Test
    public void testSpeedGainPercentChange(){

        assertEquals(25.0,
                comparison.getSpeedGainPercentChange(),
                0.0001);
    }

    @Test
    public void testDurationPercentChange(){

        assertEquals(0.0,
                comparison.getDurationPercentChange(),
                0.0001);
    }

    @Test
    public void testZeroOriginalAcceleration(){

        WotPull zeroPull = new WotPull(
                2, 6,
                2800, 6500,
                6500,
                35, 35,
                true
        );

        WotPullComparison zeroComparison =
                new WotPullComparison(
                        zeroPull, tunedPull);

        assertTrue(Double.isNaN(
                zeroComparison.getAccelerationPercentChange()));
    }
}