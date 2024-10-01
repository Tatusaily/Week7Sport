import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class SportLoggerTest {
    private SportLogger logger;
    private static final String TEST_FILE_NAME = "test_activities.txt";

    @BeforeEach
    void setUp() {
        logger = new SportLogger(TEST_FILE_NAME);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE_NAME));
    }

    @Test
    void testLogActivity() {
        logger.logActivity("Running", 30);
        assertEquals(1, logger.getActivities().size());
        assertEquals("Running", logger.getActivities().get(0).getName());
    }

    @Test
    void testLogDuplicateActivity() {
        logger.logActivity("Running", 30);
        logger.logActivity("Running", 45);
        assertEquals(1, logger.getActivities().size());
    }

    @Test
    void testViewActivities() {
        logger.logActivity("Running", 30);
        logger.logActivity("Swimming", 45);
        assertEquals(2, logger.getActivities().size());
    }

    @Test
    void testRemoveActivity() {
        logger.logActivity("Running", 30);
        logger.removeActivity("Running");
        assertEquals(0, logger.getActivities().size());
    }

    @Test
    void testCalculateTotalTime() {
        logger.logActivity("Running", 30);
        logger.logActivity("Swimming", 45);
        assertEquals(75, logger.calculateTotalTime());
    }

    @Test
    void testSaveAndLoadActivities() {
        logger.logActivity("Running", 30);
        logger.saveActivities();
        logger = new SportLogger(TEST_FILE_NAME);
        assertEquals(1, logger.getActivities().size());
        assertEquals("Running", logger.getActivities().get(0).getName());
    }
}