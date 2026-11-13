import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class WotPullTest {

    private WotPull pull;

    @BeforeEach
    public void setUp(){

        pull = new WotPull(
                2, 6,
                2800, 6500,
                6500,
                35, 72,
                true
        );
    }

    @Test
    public void testGetDuration(){

        assertEquals(4.0,
                pull.getDuration(), 0.0001);
    }

    @Test
    public void testGetSpeedGain(){

        assertEquals(37.0,
                pull.getSpeedGain(), 0.0001);
    }

    @Test
    public void testGetAverageAcceleration(){

        assertEquals(9.25,
                pull.getAverageAcceleration(), 0.0001);
    }

    @Test
    public void testGetRpmGain(){

        assertEquals(3700.0,
                pull.getRpmGain(), 0.0001);
    }

    @Test
    public void testGetRpmGainPerSecond(){

        assertEquals(925.0,
                pull.getRpmGainPerSecond(), 0.0001);
    }

    @Test
    public void testIsComplete(){

        assertTrue(pull.isComplete());
    }
}