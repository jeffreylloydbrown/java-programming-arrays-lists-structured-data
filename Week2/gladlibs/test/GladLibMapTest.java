import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GladLibMapTest {
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "src/data";

    @Test
    void useDefaultLocation() {
        GladLibMap gl = new GladLibMap();
        System.out.println("\nfrom default location");
        gl.makeStory();
    }

    @Test
    void useDataLong() {
        GladLibMap gl = new GladLibMap("src/datalong");
        System.out.println("\nfrom src/datalong");
        gl.makeStory();
    }

    @Test
    void useURL() {
        // I cannot use this test because the Duke site doesn't
        // have verb.txt or fruit.txt in their directory.
        //GladLibMap gl = new GladLibMap(dataSourceURL);
        //System.out.println("from URL "+dataSourceURL);
        //gl.makeStory();
    }

}