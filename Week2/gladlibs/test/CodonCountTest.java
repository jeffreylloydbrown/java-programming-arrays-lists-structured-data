import edu.duke.FileResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodonCountTest {
    @Test
    void buildCodonMapInvalidParameters() {
        CodonCount cc = new CodonCount();
        cc.buildCodonMap(-1, "CGT");
        cc.buildCodonMap(3, "CGT");
        cc.buildCodonMap(0, null);
        cc.buildCodonMap(0, "");
        cc.buildCodonMap( 1, "A");
        cc.buildCodonMap(2, "A");
        cc.buildCodonMap( 2, "AA");
    }

    @Test
    void buildCodonMap() {
        CodonCount cc = new CodonCount();
        String dna = "CGTTCAAGTTCAA";

        cc.buildCodonMap(0, dna);
        System.out.println("Expect CGT 1, TCA 2, AGT 1");
        cc.printCodonCounts(1, 5);

        cc.buildCodonMap(1, dna);
        System.out.println("\nExpect GTT 2, CAA 2");
        cc.printCodonCounts(5, 1);

        cc.buildCodonMap(2, dna);
        System.out.println("\nExpect TTC 2, AAG 1");
        cc.printCodonCounts(-1, 2000);
    }

    @Test
    void readFromFile() {
        String dna = new FileResource().asString().trim();
        CodonCount cc = new CodonCount();

        for (int frame = 0; frame <= 2; frame++) {
            cc.runFrame(frame, dna);
        }
    }

    @Test
    void practiceQuiz() {
        String dna = new FileResource("test/data/dnaMystery1.txt").asString().trim();
        CodonCount cc = new CodonCount();

        cc.buildCodonMap(1, dna);
        System.out.println("Q3.  "+cc.getMostCommonCodon());
    }

    @Test
    void finalQuiz() {
        String dna = new FileResource("test/data/dnaMystery2.txt").asString().trim();
        CodonCount cc = new CodonCount();

        System.out.println("Q9 follows:");
        cc.runFrame(1, dna);
        System.out.println();

        System.out.println("Q10 follows:");
        cc.runFrame(2, dna);
        System.out.println();

        System.out.println("Q11 follows:");
        cc.runFrame(0, dna);
        System.out.println("the answer for 7 to 7 counts:");
        cc.printCodonCounts(7, 7);
        System.out.println();
    }
}