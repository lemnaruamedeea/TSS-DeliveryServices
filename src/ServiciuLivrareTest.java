import main.Livrare;
import main.ServiciuLivrare;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiciuLivrareTest {

    ServiciuLivrare serviciu = new ServiciuLivrare();

    // === 1. Clase de echivalență ===

    @Test
    void testLivrareValidaIeftina() {
        Livrare l = new Livrare(1.5, 15, false);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertTrue(cost > 0);
        assertEquals("Ieftină", serviciu.clasificaLivrare(l));
    }

    @Test
    void testLivrareValidaStandard() {
        Livrare l = new Livrare(8, 25, true);
        String clasificare = serviciu.clasificaLivrare(l);
        assertEquals("Standard", clasificare);
    }

    @Test
    void testLivrareGreutateNegativa() {
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(new Livrare(-3, 10, true));
        });
    }

    @Test
    void testLivrareDistanțăZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(new Livrare(2, 0, false));
        });
    }

    // === 2. Valori de frontieră ===

    @Test
    void testGreutateLaLimitaZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(new Livrare(0, 10, false));
        });
    }

    @Test
    void testGreutateExact5() {
        double cost = serviciu.calculeazaCostLivrare(new Livrare(5, 10, false));
        assertEquals(10 * 0.95, cost, 0.01);
    }

    @Test
    void testGreutatePeste5() {
        double cost = serviciu.calculeazaCostLivrare(new Livrare(5.01, 10, false));
        assertTrue(cost > 10);
    }

    @Test
    void testDistanțăLaLimita20() {
        Livrare l = new Livrare(11, 20, false);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertTrue(cost > 10);
    }

    @Test
    void testDistanțăPeste20() {
        Livrare l = new Livrare(11, 20.01, false);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertTrue(cost > 10); // include taxa suplimentară
    }

    // === 3. Acoperire decizie și condiții ===

    @Test
    void testToateCazurileDecizie() {
        assertDoesNotThrow(() -> serviciu.calculeazaCostLivrare(new Livrare(4, 10, false)));
        assertDoesNotThrow(() -> serviciu.calculeazaCostLivrare(new Livrare(6, 10, true)));
        assertDoesNotThrow(() -> serviciu.calculeazaCostLivrare(new Livrare(11, 25, false)));
        assertDoesNotThrow(() -> serviciu.calculeazaCostLivrare(new Livrare(6, 25, false)));
    }

    // === 4. Test plafon maxim (pentru omorât mutantul cu Math.max) ===

    @Test
    void testCostPlafonMaxim() {
        Livrare l = new Livrare(100, 300, true);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertTrue(cost <= 200);
    }

    // === 5. Test multiplicator prioritar (pentru mutantul 1.25 -> 1.15) ===

    @Test
    void testPrioritarCalculCorect() {
        Livrare l = new Livrare(5, 10, true);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertEquals(10.0 * 1.25, cost, 0.01);
    }

    // === 6. Eligibil pentru reducere ===

    @Test
    void testEligibilReducereTrue() {
        Livrare l = new Livrare(1.5, 10, false);
        assertTrue(serviciu.esteEligibilaReducere(l));
    }

    @Test
    void testEligibilReducereFalseGreutate() {
        Livrare l = new Livrare(3, 10, false);
        assertFalse(serviciu.esteEligibilaReducere(l));
    }

    @Test
    void testEligibilReducereFalsePrioritar() {
        Livrare l = new Livrare(1.5, 10, true);
        assertFalse(serviciu.esteEligibilaReducere(l));
    }

    // === 7. Estimare timp de livrare ===

    @Test
    void testEstimeazaTimpNeprioritar() {
        Livrare l = new Livrare(2, 15, false);
        int timp = serviciu.estimeazaTimpLivrare(l);
        assertEquals(2, timp);
    }

    @Test
    void testEstimeazaTimpPrioritar() {
        Livrare l = new Livrare(2, 15, true);
        int timp = serviciu.estimeazaTimpLivrare(l);
        assertEquals(1, timp);
    }

    @Test
    void testEstimeazaTimpMinim() {
        Livrare l = new Livrare(2, 2, true);
        int timp = serviciu.estimeazaTimpLivrare(l);
        assertEquals(1, timp); // nu poate fi sub 1
    }
}
