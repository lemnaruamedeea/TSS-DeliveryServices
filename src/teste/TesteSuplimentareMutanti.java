package src.teste;

import src.main.Livrare;
import src.main.ServiciuLivrare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TesteSuplimentareMutanti {
    private final ServiciuLivrare serviciu = new ServiciuLivrare();

    @Test
    public void testGreutateExact5NuAdaugaCost() {
        Livrare livrare = new Livrare(5.0, 10.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(9.5, cost, 0.01); // 10.0 * 0.95 (fără extra cost)
    }

//    @Test
//    public void testCostGreutatePeste5() {
//        Livrare livrare = new Livrare(5.0, 10.0, false);
//        double cost = serviciu.calculeazaCostLivrare(livrare);
//        // cost = 10 + 0.2 = 10.2 * 0.95
//        assertEquals(9.69, cost, 0.01);
//    }

//    @Test
//    public void testCostExactDistantaLimita20Greutate10() {
//        Livrare livrare = new Livrare(10.0, 20.0, false);
//        double cost = serviciu.calculeazaCostLivrare(livrare);
//        // cost = 10.0 * 0.95
//        assertEquals(9.5, cost, 0.01);
//    }
//
//    @Test
//    public void testCostDistanta21Greutate11() {
//        Livrare livrare = new Livrare(11.0, 21.0, false);
//        // costBaza: 10 + (11-5)*2 = 22 + 1.5 extra
//        // total = 23.5 * 0.95
//        assertEquals(22.325, serviciu.calculeazaCostLivrare(livrare), 0.01);
//    }

    @Test
    public void testClasificareCostExact150() {
        Livrare livrare = new Livrare(60.0, 20.0, true); // Configurează să obții cost exact 150
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(150.0, cost, 0.01);
        assertEquals("Scumpa", serviciu.clasificaLivrare(livrare));
    }

//    @Test
//    public void testClasificareCostSub150() {
//        Livrare livrare = new Livrare(14.9, 20.0, true); // Configurează ~149.99
//        double cost = serviciu.calculeazaCostLivrare(livrare);
//        assertTrue(cost < 150);
//        assertEquals("Standard", serviciu.clasificaLivrare(livrare));
//    }


    @Test
    public void testClasificareCostExact75() {
        Livrare livrare = new Livrare(30.0, 10.0, true); // Configurează să obții cost 75
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(75.0, cost, 0.01);
        assertEquals("Standard", serviciu.clasificaLivrare(livrare));
    }

    @Test
    public void testTimpLivrareSub1EsteCorectatLa1() {
        Livrare livrare = new Livrare(1.0, 5.0, true); // (5 / 10) + 1 = 1 - 1 = 0
        int timp = serviciu.estimeazaTimpLivrare(livrare);
        assertEquals(1, timp);
    }


//    @Test
//    public void testTimpLivrareDevineSub1SiEsteCorectatLa1() {
//        Livrare livrare = new Livrare(1.0, 0.1, true); // (0.1 / 10) + 1 = 1, -1 = 0
//        // trebuie să intre în if (timp < 1)
//        int timp = serviciu.estimeazaTimpLivrare(livrare);
//        assertEquals(1, timp);
//    }
//
//    @Test
//    public void testTimpLivrareExact1NuEsteModificat() {
//        Livrare livrare = new Livrare(1.0, 10.0, false); // timp = 0 + 1 = 1
//        int timp = serviciu.estimeazaTimpLivrare(livrare);
//        assertEquals(2, timp);
//    }
//
//    @Test
//    public void testTimpLivrareExact1NuEsteModificat1() {
//        Livrare livrare = new Livrare(1.0, 9.9, false); // timp = 0 + 1 = 1
//        int timp = serviciu.estimeazaTimpLivrare(livrare);
//        assertEquals(1, timp);
//    }

}
