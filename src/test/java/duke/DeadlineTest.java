package duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void testGetTaskIcon() {
        try {
            assertEquals("D", new Deadline("run around", "2019-01-01").getTaskIcon());
        } catch (NeroException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToString() {
        try {
            assertEquals("[D][ ] eat food by: 1 Jan 2019",
                    new Deadline("eat food", "2019-01-01").toString());
        } catch (NeroException e) {
            e.printStackTrace();
        }
    }
}
