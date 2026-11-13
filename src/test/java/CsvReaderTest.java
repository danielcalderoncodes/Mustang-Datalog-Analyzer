import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CsvReaderTest {

    @TempDir
    Path tempDir;

    private Path createCsv(String content)
            throws IOException {

        Path file = tempDir.resolve("test.csv");

        Files.writeString(file, content);

        return file;
    }

    @Test
    public void testReadValidCsv() throws IOException {

        Path file = createCsv("""
                Time,RPM,Speed,Throttle,Coolant
                0,2000,25,30,190
                1,3000,35,95,191
                2,4000,45,100,192
                """);

        CsvReadResult result =
                CsvReader.readVehicleFromCsv(
                        file.toString(), "Test Mustang");

        assertEquals(3, result.getValidReadings());

        assertEquals(0, result.getSkippedReadings());

        assertEquals(3,
                result.getVehicle().getNumberOfReadings());

        assertEquals(4000.0,
                result.getVehicle().getPeakRpm(),
                0.0001);
    }

    @Test
    public void testSkipMalformedRows() throws IOException {

        Path file = createCsv("""
                Time,RPM,Speed,Throttle,Coolant
                0,2000,25,30,190
                1,invalid,35,95,191
                2,4000,45,100
                3,5000,55,100,193
                """);

        CsvReadResult result =
                CsvReader.readVehicleFromCsv(
                        file.toString(), "Malformed Test");

        assertEquals(2, result.getValidReadings());

        assertEquals(2, result.getSkippedReadings());
    }

    @Test
    public void testSkipOutOfRangeValues()
            throws IOException {

        Path file = createCsv("""
                Time,RPM,Speed,Throttle,Coolant
                0,2000,25,30,190
                1,9000,35,95,191
                2,4000,250,100,192
                3,5000,55,110,193
                4,6000,65,100,350
                5,6500,75,100,195
                """);

        CsvReadResult result =
                CsvReader.readVehicleFromCsv(
                        file.toString(), "Range Test");

        assertEquals(2, result.getValidReadings());

        assertEquals(4, result.getSkippedReadings());
    }

    @Test
    public void testRejectDuplicateTimestamps()
            throws IOException {

        Path file = createCsv("""
                Time,RPM,Speed,Throttle,Coolant
                0,2000,25,30,190
                1,3000,35,95,191
                1,4000,45,100,192
                2,5000,55,100,193
                """);

        CsvReadResult result =
                CsvReader.readVehicleFromCsv(
                        file.toString(), "Duplicate Test");

        assertEquals(3, result.getValidReadings());

        assertEquals(1, result.getSkippedReadings());
    }

    @Test
    public void testRejectOutOfOrderTimestamps()
            throws IOException {

        Path file = createCsv("""
                Time,RPM,Speed,Throttle,Coolant
                0,2000,25,30,190
                2,3000,35,95,191
                1,4000,45,100,192
                3,5000,55,100,193
                """);

        CsvReadResult result =
                CsvReader.readVehicleFromCsv(
                        file.toString(), "Order Test");

        assertEquals(3, result.getValidReadings());

        assertEquals(1, result.getSkippedReadings());
    }

    @Test
    public void testEmptyCsv() throws IOException {

        Path file = createCsv("""
                Time,RPM,Speed,Throttle,Coolant
                """);

        CsvReadResult result =
                CsvReader.readVehicleFromCsv(
                        file.toString(), "Empty Test");

        assertEquals(0, result.getValidReadings());

        assertEquals(0, result.getSkippedReadings());

        assertEquals(0,
                result.getVehicle().getNumberOfReadings());
    }

    @Test
    public void testMissingFile(){

        Path missingFile =
                tempDir.resolve("does-not-exist.csv");

        assertThrows(IOException.class, () -> {

            CsvReader.readVehicleFromCsv(
                    missingFile.toString(),
                    "Missing File Test");
        });
    }
    @Test
    public void testRejectNonFiniteValues()
            throws IOException {

        Path file = createCsv("""
            Time,RPM,Speed,Throttle,Coolant
            0,2000,25,30,190
            1,NaN,35,95,191
            2,4000,NaN,100,192
            3,5000,55,NaN,193
            4,6000,65,100,NaN
            NaN,6500,75,100,195
            6,Infinity,80,100,196
            7,7000,85,100,197
            """);

        CsvReadResult result =
                CsvReader.readVehicleFromCsv(
                        file.toString(), "Non-Finite Test");

        assertEquals(2, result.getValidReadings());

        assertEquals(6, result.getSkippedReadings());
    }
}
