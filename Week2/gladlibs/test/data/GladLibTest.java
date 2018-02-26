import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GladLibTest {
    @Test
    void makeStory() {
        GladLib gl = new GladLib("src/data");
        gl.makeStory();
    }

}