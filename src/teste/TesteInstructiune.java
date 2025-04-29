package src.teste;

import src.main.Livrare;
import src.main.ServiciuLivrare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//fiecare linie de cod executabilă este rulată cel puțin o dată într-un test
//Nu înseamnă că sunt verificate toate căile posibile, ci doar că nicio linie nu rămâne neatinsă.


public class TesteInstructiune {

    ServiciuLivrare serviciu = new ServiciuLivrare();

    // 👉 Acoperă ramura normală, fără excepții
    @Test
    void testCostLivrare_usor_neprioritar() {
        Livrare livrare = new Livrare(2.0, 10.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0 && cost < 20);
    }

    // 👉 Acoperă toate blocurile else, for, plafon, prioritara
    @Test
    void testCostLivrare_greu_prioritar_lunga() {
        Livrare livrare = new Livrare(12.0, 30.0, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);
    }

    // 👉 Acoperă aruncarea excepției pentru greutate negativă
    @Test
    void testCostLivrare_greutateNegativa_exceptie() {
        Livrare livrare = new Livrare(-1.0, 10.0, false);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(livrare));
    }

    // 👉 Acoperă aruncarea excepției pentru distanță zero
    @Test
    void testCostLivrare_distanțaZero_exceptie() {
        Livrare livrare = new Livrare(1.0, 0.0, false);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(livrare));
    }

    // 👉 Clasificare: ieftina
    @Test
    void testClasificare_Ieftina() {
        Livrare livrare = new Livrare(2.0, 10.0, false);
        assertEquals("Ieftina", serviciu.clasificaLivrare(livrare));
    }

    // 👉 Reducere: eligibil (greutate < 2 și neprioritara)
    @Test
    void testReducere_eligibil() {
        Livrare livrare = new Livrare(1.0, 5.0, false);
        assertTrue(serviciu.esteEligibilaReducere(livrare));
    }

    // 👉 Reducere: neeligibil (prioritara)
    @Test
    void testReducere_neeligibil_prioritara() {
        Livrare livrare = new Livrare(1.0, 5.0, true);
        assertFalse(serviciu.esteEligibilaReducere(livrare));
    }

    // 👉 Timp estimat: normal (2 zile)
    @Test
    void testEstimeazaTimp_neprioritara() {
        Livrare livrare = new Livrare(3.0, 15.0, false);
        assertEquals(2, serviciu.estimeazaTimpLivrare(livrare));
    }

    // 👉 Timp estimat: prioritara (scade cu 1)
    @Test
    void testEstimeazaTimp_prioritara() {
        Livrare livrare = new Livrare(3.0, 15.0, true);
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    // 👉 Timp estimat: minim 1 zi (if timp < 1)
    @Test
    void testEstimeazaTimp_minim1() {
        Livrare livrare = new Livrare(3.0, 1.0, true); // timp inițial ar fi 0
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    public void testCalculeazaCostLivrare_Ieftin() {
        Livrare livrare = new Livrare(3.0, 10.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost < 75);
    }

    @Test
    public void testCalculeazaCostLivrare_MaxPlafon() {
        Livrare livrare = new Livrare(100.0, 500.0, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(200.0, cost);
    }

    @Test
    public void testCalculeazaCostLivrare_ValoriInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(new Livrare(0.0, 5.0, false));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(new Livrare(2.0, -1.0, true));
        });
    }

    @Test
    public void testClasificaLivrare_Ieftina() {
        Livrare livrare = new Livrare(1.0, 5.0, false);
        assertEquals("Ieftina", serviciu.clasificaLivrare(livrare));
    }

    @Test
    public void testEsteEligibilaReducere_Adevarat() {
        Livrare livrare = new Livrare(1.0, 10.0, false);
        assertTrue(serviciu.esteEligibilaReducere(livrare));
    }

    @Test
    public void testEsteEligibilaReducere_Fals() {
        Livrare livrare1 = new Livrare(3.0, 10.0, false);
        Livrare livrare2 = new Livrare(1.5, 10.0, true);
        assertFalse(serviciu.esteEligibilaReducere(livrare1));
        assertFalse(serviciu.esteEligibilaReducere(livrare2));
    }

    @Test
    public void testEstimeazaTimpLivrare_Normal() {
        Livrare livrare = new Livrare(3.0, 35.0, false);
        assertEquals(4, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    public void testEstimeazaTimpLivrare_Prioritara() {
        Livrare livrare = new Livrare(3.0, 35.0, true);
        assertEquals(3, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    public void testEstimeazaTimpLivrare_Minim1() {
        Livrare livrare = new Livrare(3.0, 0.1, true);
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    public void testCalculeazaCostLivrare_GreutateZero() {
        // Test pentru greutate = 0, ar trebui să arunce excepție
        Livrare livrare = new Livrare(0.0, 10.0, false);
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
    }

    @Test
    public void testCalculeazaCostLivrare_DistantaZero() {
        // Test pentru distanță = 0, ar trebui să arunce excepție
        Livrare livrare = new Livrare(3.0, 0.0, false);
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
    }

    @Test
    public void testCalculeazaCostLivrare_GreutateMica_DistantaLunga_FaraFor() {
        // Test pentru greutate < 5 și distanță > 20, ar trebui să sară peste blocul for
        Livrare livrare = new Livrare(4.0, 30.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);  // Costul trebuie să fie calculat fără a intra în for
    }

    @Test
    public void testCalculeazaCostLivrare_GreutateMare_DistantaMica_FaraFor() {
        // Test pentru greutate > 10 și distanță <= 20, ar trebui să sară peste blocul for
        Livrare livrare = new Livrare(12.0, 15.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);  // Costul trebuie să fie calculat fără a intra în for
    }

    // Test pentru reducerea aplicabilă la livrări ușoare, non-prioritare
    @Test
    public void testCalculeazaCostLivrare_Reducere() {
        Livrare livrare = new Livrare(1.5, 10.0, false); // Greutate mică, non-prioritară
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost < 10);  // Ar trebui să aibă reducere aplicată
    }

    // Test pentru livrare cu cost mai mare de 200 (pentru a verifica plafonul)
    @Test
    public void testCalculeazaCostLivrare_Plafon_200() {
        Livrare livrare = new Livrare(50.0, 1000.0, false); // Greutate mare și distanță mare
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(200, cost, 0.01);  // Costul ar trebui să fie plafonat la 200
    }

    // Test pentru livrare prioritară (ar trebui să aplice factorul de 1.25)
    @Test
    public void testCalculeazaCostLivrare_Prioritara() {
        Livrare livrare = new Livrare(10.0, 20.0, true); // Livrare prioritară
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);  // Costul ar trebui să fie calculat cu factorul de prioritate
    }

    // Test pentru livrare non-prioritară (ar trebui să aplice factorul de 0.95)
    @Test
    public void testCalculeazaCostLivrare_NonPrioritara() {
        Livrare livrare = new Livrare(10.0, 20.0, false); // Livrare non-prioritară
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);  // Costul ar trebui să fie calculat cu reducerea de 5%
    }
}
