package src.teste;

import src.main.Livrare;
import src.main.ServiciuLivrare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TesteSuplimentareMutanti {
    private final ServiciuLivrare serviciu = new ServiciuLivrare();

    @Test
    void testGreutateFix5() {
        ServiciuLivrare serviciu = new ServiciuLivrare();
        Livrare livrare = new Livrare(5.0, 20.0, false);

        double cost = serviciu.calculeazaCostLivrare(livrare);

        assertEquals(9.5, cost, 0.001); // 10 * 0.95
    }

    @Test
    public void testClasificareCostExact150() {
        Livrare livrare = new Livrare(60.0, 20.0, true); // Configurează să obții cost exact 150
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(150.0, cost, 0.01);
        assertEquals("Scumpa", serviciu.clasificaLivrare(livrare));
    }


    @Test
    public void testClasificareCostExact75() {
        Livrare livrare = new Livrare(30.0, 10.0, true); // Configurează să obții cost 75
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(75.0, cost, 0.01);
        assertEquals("Standard", serviciu.clasificaLivrare(livrare));
    }

    @Test
    void testDistantaFix20GreutatePeste10() {
        Livrare livrare = new Livrare(11.0, 20.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        // cost = 10 + (11-5)*2 = 22.0 * 0.95
        assertEquals(20.9, cost, 0.01);
    }

    @Test
    void testDistantaPeste20GreutateFix10() {
        Livrare livrare = new Livrare(10.0, 30.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        // nu se adaugă cost suplimentar pentru distanță pentru că greutatea nu e >10
        assertEquals(19.0, cost, 0.01);
    }

    @Test
    void testKmSuplimentariZero() {
        Livrare livrare = new Livrare(11.0, 24.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(20.9, cost, 0.01);
    }

    @Test
    void testTimpLivrareMinim() {
        Livrare livrare = new Livrare(1.0, 5.0, true);
        int timp = serviciu.estimeazaTimpLivrare(livrare);
        assertEquals(1, timp);
    }

}
