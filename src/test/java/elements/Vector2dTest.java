package elements;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class Vector2dTest {

    @Test
    public void equalsTest() {
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(2, 1);
        Vector2d v3 = new Vector2d(3,1);
        assertTrue(v1.equals(v2));
        assertFalse(v1.equals(v3));
    }

    @Test
    public void toStringTest() {
        Vector2d v1 = new Vector2d(2,1);
        assertEquals(v1.toString(),"(2, 1)");
    }

    @Test
    public void precedesTest() {
        Vector2d v1 = new Vector2d(2,1);
        Vector2d v2 = new Vector2d(1,0);
        assertTrue(v1.precedes(v2));
        assertFalse(v2.precedes(v1));
    }

    @Test
    public void followsTest() {
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(1,0);
        assertTrue(v2.follows(v1));
        assertFalse(v1.follows(v2));
    }

    @Test
    public void addTest() {
        Vector2d v1 = new Vector2d(2,1);
        Vector2d v2 = new Vector2d(0,-1);
        Vector2d v3 = new Vector2d(2,0);
        assertEquals(v1.add(v2),v3);
    }

    @Test
    public void subtractTest() {
        Vector2d v1 = new Vector2d(2,1);
        Vector2d v2 = new Vector2d(1,3);
        Vector2d v3 = new Vector2d(1,-2);
        assertEquals(v1.subtract(v2),v3);
    }
}