import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccelerationRunTest {

    @Test
    public void testGetDuration(){

        AccelerationRun run = new AccelerationRun(
                40, 70, 2.5, 5.5);

        assertEquals(3.0, run.getDuration(), 0.0001);
    }
}