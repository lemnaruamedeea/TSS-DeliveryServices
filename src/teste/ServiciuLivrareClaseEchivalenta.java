package src.teste;

import src.main.Livrare;
import src.main.ServiciuLivrare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiciuLivrareClaseEchivalenta {
    private final ServiciuLivrare serviciu = new ServiciuLivrare();

    /////////// metoda calculeazaCostLivrare ///////////////

    // C1: greutate ≤ 0
    @Test
    public void testGreutateZero() {
        Livrare l = new Livrare(0, 10, false);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(l));
    }

    // C2: distanta ≤ 0
    @Test
    public void testDistantaZero() {
        Livrare l = new Livrare(3, 0, true);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(l));
    }

    // C3: greutate mică, distanță mică, prioritar
    @Test
    public void testLivrareIeftinaPrioritara() {
        Livrare l = new Livrare(2, 10, true);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertEquals(12.5, cost, 0.01); // 10 * 1.25
    }

    // C4: greutate mică, distanță mică, neprioritar
    @Test
    public void testLivrareIeftinaNeprioritara() {
        Livrare l = new Livrare(2, 10, false);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertEquals(9.5, cost, 0.01); // 10 * 0.95
    }

    // C5: greutate 7kg, distanță 15km, prioritar
    @Test
    public void testGreutateMediePrioritara() {
        Livrare l = new Livrare(7, 15, true); // extra 4 lei pentru greutate
        double cost = serviciu.calculeazaCostLivrare(l);
        assertEquals(17.5, cost, 0.01); // (10 + 4) * 1.25
    }

    // C6: greutate 7kg, distanță 15km, neprioritar
    @Test
    public void testGreutateMedieNeprioritara() {
        Livrare l = new Livrare(7, 15, false); // extra 4 lei
        double cost = serviciu.calculeazaCostLivrare(l);
        assertEquals(13.3, cost, 0.01); // (10 + 4) * 0.95
    }

    // C7: greutate mare, distanță mare, prioritar
    @Test
    public void testGreutateMareDistantMarePrioritar() {
        Livrare l = new Livrare(12, 45, true);
        double cost = serviciu.calculeazaCostLivrare(l);
        // costBaza = 10 + 14 (greutate) + 2 * 1.5 = 27 -> *1.25 = 33.75
        assertEquals(33.75, cost, 0.01);
    }

    // C8: greutate mare, distanță mare, neprioritar
    @Test
    public void testGreutateMareDistantaMareNeprioritar() {
        Livrare l = new Livrare(12, 45, false);
        double cost = serviciu.calculeazaCostLivrare(l);
        // costBaza = 10 + 14 + 3 = 27 -> *0.95 = 25.65
        assertEquals(25.65, cost, 0.01);
    }

    // C9: greutate mare, distanță mică, prioritar
    @Test
    public void testGreutateMareDistantaMicaPrioritar() {
        Livrare l = new Livrare(12, 15, true);
        double cost = serviciu.calculeazaCostLivrare(l);
        // costBaza = 10 + 14 = 24 -> *1.25 = 30
        assertEquals(30.0, cost, 0.01);
    }

    // C10: greutate mare, distanță mică, neprioritar
    @Test
    public void testGreutateMareDistantaMicaNeprioritar() {
        Livrare l = new Livrare(12, 15, false);
        double cost = serviciu.calculeazaCostLivrare(l);
        // costBaza = 10 + 14 = 24 -> *0.95 = 22.8
        assertEquals(22.8, cost, 0.01);
    }

    /////////// metoda clasificaLivrare ///////////////

    @Test
    public void testClasificareIeftina() {
        Livrare l = new Livrare(2, 10, false); // cost = 9.5
        assertEquals("Ieftina", serviciu.clasificaLivrare(l));
    }

    @Test
    public void testClasificareStandard() {
        Livrare l = new Livrare(20, 30, false);
        // costBaza = 10 + 30 + (int)(30/10)*1.5 = 10+30+4.5 = 44.5 * 0.95 ≈ 42.275
        // asta e sub 75, deci nu e bun → trebuie să generăm un cost > 75
        l = new Livrare(50, 100, false); // cost = 110 * 0.95 = 104.5
        assertEquals("Standard", serviciu.clasificaLivrare(l));
    }

    @Test
    public void testClasificareScumpa() {
        Livrare l = new Livrare(100, 200, true); // cost depășește plafonul 200
        assertEquals("Scumpa", serviciu.clasificaLivrare(l));
    }

    /////////// metoda esteEligibilaReducere ///////////////
    @Test
    public void testEligibilReducere() {
        Livrare l = new Livrare(1.5, 10, false);
        assertTrue(serviciu.esteEligibilaReducere(l));
    }

    @Test
    public void testNuEsteEligibilPrioritara() {
        Livrare l = new Livrare(1.5, 10, true);
        assertFalse(serviciu.esteEligibilaReducere(l));
    }

    @Test
    public void testNuEsteEligibilGreutateMare() {
        Livrare l = new Livrare(2.5, 10, false);
        assertFalse(serviciu.esteEligibilaReducere(l));
    }

    @Test
    public void testNuEsteEligibilAmbele() {
        Livrare l = new Livrare(5, 10, true);
        assertFalse(serviciu.esteEligibilaReducere(l));
    }

    /////////// metoda estimeazaTimpLivrare ///////////////

    @Test
    public void testTimpLivrareMicNeprioritar() {
        Livrare l = new Livrare(2, 5, false);
        assertEquals(1, serviciu.estimeazaTimpLivrare(l));
    }

    @Test
    public void testTimpLivrareMicPrioritar() {
        Livrare l = new Livrare(2, 5, true);
        assertEquals(1, serviciu.estimeazaTimpLivrare(l)); // nu scade sub 1
    }

    @Test
    public void testTimpLivrareMediuNeprioritar() {
        Livrare l = new Livrare(2, 25, false);
        assertEquals(3, serviciu.estimeazaTimpLivrare(l)); // 25/10 = 2.5 -> int = 2 + 1 = 3
    }

    @Test
    public void testTimpLivrareMediuPrioritar() {
        Livrare l = new Livrare(2, 25, true);
        assertEquals(2, serviciu.estimeazaTimpLivrare(l)); // 3 - 1 = 2
    }

    @Test
    public void testTimpLivrareZeroKm() {
        Livrare l = new Livrare(2, 0.1, false);
        assertEquals(1, serviciu.estimeazaTimpLivrare(l));
    }

}
