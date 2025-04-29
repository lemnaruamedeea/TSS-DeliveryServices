package src.teste;

import src.main.Livrare;
import src.main.ServiciuLivrare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ServiciuLivrareValoriFrontiera {
    private final ServiciuLivrare serviciu = new ServiciuLivrare();

    /////////// metoda calculeazaCostLivrare ///////////////


    @Test
    void testGreutateLaZero() {
        Livrare livrare = new Livrare(0, 10, false);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(livrare));
    }

    @Test
    void testGreutateMinimaValida() {
        Livrare livrare = new Livrare(0.1, 10, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);
    }

    @Test
    void testGreutateLimita5() {
        Livrare livrare = new Livrare(5, 10, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(9.5, cost, 0.01);
    }

    @Test
    void testGreutatePeste5() {
        Livrare livrare = new Livrare(5.01, 10, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(9.52, cost, 0.01); // 10 + 0.02 * 0.95
    }

    @Test
    void testGreutateLimita10_FaraTaxaKm() {
        Livrare livrare = new Livrare(10, 20, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(19.0, cost, 0.01);
    }

    @Test
    void testGreutatePeste10_CuTaxaKm() {
        Livrare livrare = new Livrare(10.01, 30, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(20.44, cost, 0.01); // 10 + 10.02 + 1.5 => * 0.95
    }

    @Test
    void testDistantaLaZero() {
        Livrare livrare = new Livrare(5, 0, false);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(livrare));
    }

    @Test
    void testDistantaMinimaValida() {
        Livrare livrare = new Livrare(5, 0.1, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);
    }

    @Test
    void testDistantaLimita20() {
        Livrare livrare = new Livrare(11, 20, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(20.9, cost, 0.01); // 10 + 12 * 0.95
    }

    @Test
    void testDistantaPeste20_CuKmExtra() {
        Livrare livrare = new Livrare(11, 30, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(22.325, cost, 0.01); // 10 + 12 + 1.5 => * 0.95
    }
    @Test
    void testPrioritaraTrue() {
        Livrare livrare = new Livrare(5, 10, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(12.5, cost, 0.01); // 10 * 1.25
    }

    @Test
    void testPrioritaraFalse() {
        Livrare livrare = new Livrare(5, 10, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(9.5, cost, 0.01); // 10 * 0.95
    }

    /////////// metoda clasificaLivrare ///////////////

    @Test
    void testClasificareIeftina() {
        Livrare livrare = new Livrare(1, 5, false);
        assertEquals("Ieftina", serviciu.clasificaLivrare(livrare));
    }

    @Test
    void testClasificareStandardLaLimita() {
        Livrare livrare = new Livrare(10, 40, false); // costul trece de 75
        assertEquals("Iefitina", serviciu.clasificaLivrare(livrare));
    }

    @Test
    void testClasificareScumpaPeste150() {
        Livrare livrare = new Livrare(100, 200, true); // fortam cost mare
        assertEquals("Scumpa", serviciu.clasificaLivrare(livrare));
    }


    /////////////////// metoda esteEligibilaReducere //////////////////////

    @Test
    void testReducereEligibil_Sub2kg_NonPrioritar() {
        Livrare livrare = new Livrare(1.99, 10, false);
        assertTrue(serviciu.esteEligibilaReducere(livrare));
    }

    @Test
    void testReducereNeeligibil_Prioritar() {
        Livrare livrare = new Livrare(1.99, 10, true);
        assertFalse(serviciu.esteEligibilaReducere(livrare));
    }

    @Test
    void testReducereNeeligibil_GreutateFix2kg() {
        Livrare livrare = new Livrare(2.0, 10, false);
        assertFalse(serviciu.esteEligibilaReducere(livrare));
    }

    //////////////////////// metoda estimeazaTimpLivrare ///////////////////////////////

    @Test
    void testTimpLivrare_Sub10km() {
        Livrare livrare = new Livrare(1, 9.9, false);
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    void testTimpLivrare_La10km() {
        Livrare livrare = new Livrare(1, 10, false);
        assertEquals(2, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    void testTimpLivrare_Prioritara() {
        Livrare livrare = new Livrare(1, 10, true);
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    void testTimpLivrare_Minim1h() {
        Livrare livrare = new Livrare(1, 0.5, true); // => timp = 1 - 1 => 0 -> returnează 1
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }


}
