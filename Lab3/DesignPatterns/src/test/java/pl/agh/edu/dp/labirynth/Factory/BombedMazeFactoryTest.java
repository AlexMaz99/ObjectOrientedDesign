package pl.agh.edu.dp.labirynth.Factory;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class BombedMazeFactoryTest {

    @Test
    void getInstance() {
        MazeFactory factory = EnchantedMazeFactory.getInstance();

        assertEquals(factory, EnchantedMazeFactory.getInstance());
        assertEquals(factory, EnchantedMazeFactory.getInstance());
        assertEquals(factory, EnchantedMazeFactory.getInstance());

        MazeFactory factory2 = BombedMazeFactory.getInstance();

        assertEquals(factory2, BombedMazeFactory.getInstance());
        assertNotEquals(factory, factory2);
    }
}