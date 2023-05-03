package otherDirectory;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
    Position position = new Position(3,4);
    /**
     * getX()
     */
    @Test
    public void getXTest() {
        assertEquals(3,position.getX());
    }

    /**
     * getY()
     */
    @Test
    public void getYTest() {
        assertEquals(4,position.getY());
    }

    /**
     * setX(int x)
     */
    @Test
    public void setXTest() {
        position.setX(4);
        assertEquals(4,position.getX());
    }

    /**
     * setY(int y)
     */
    @Test
    public void setYTest() {
        position.setY(5);
        assertEquals(5,position.getY());
    }

    /**
     * equals(Object obj)
     */
    @Test
    public void equalsTest() {
        Position pos1 = new Position(3,4);
        Position pos2 = new Position(1,3);

        assertEquals(position, pos1);
        assertNotEquals(position, pos2);
    }
}
