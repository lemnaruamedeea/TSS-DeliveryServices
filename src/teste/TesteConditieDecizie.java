/*
Teste unitare pentru verificarea logicii de calcul al costurilor de livrare
Sut testate ramurile (if/else) și conditiile complexe sunt evaluate corect.
Ele testează scenarii normale și exceptionale (ex. valori negative, livrare prioritara/neprioritara) pentru a garanta că aplicatia răspunde corect in orice situatie

Testele sunt împărțite in 2 secțiuni:
1. Teste pentru Condiții (cazurile cu greutate si distanta invalide, exceptii, etc.)
2. Teste pentru Decizii(logica pt livrari (non)prioritare, verificam livrarile ieftine)
 */

package src.teste;

import src.main.Livrare;
import src.main.ServiciuLivrare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TesteConditieDecizie {

    // clasa care contine logica de livrare
    ServiciuLivrare serviciu = new ServiciuLivrare();

    // TESTE CONDIȚII


    // test pentru cazul în care greutatea este 0 (posibila exceptie)
    @Test
    public void testGreutateZero() {
        Livrare livrare = new Livrare(0, 10.0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
        assertEquals("Greutatea si distanta trebuie sa fie pozitive.", exception.getMessage());
    }

    // test pentru cazul în care distanța este 0 (posibila exceptie)
    @Test
    public void testDistantaZero() {
        Livrare livrare = new Livrare(5.0, 0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
        assertEquals("Greutatea si distanta trebuie sa fie pozitive.", exception.getMessage());
    }

    // testam suplimentul pentru greutate mai mare decât 5
    @Test
    public void testGreutateMaiMareDecat5() {
        Livrare livrare = new Livrare(6.0, 10.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 10); // Ar trebui să reflecte suplimentul de greutate
    }

    // Test pentru greutate > 10 și distanță > 20 (logica din bucla for)
    @Test
    public void testDistantaSiGreutateMare() {
        Livrare livrare = new Livrare(12.0, 30.0, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);  // Costul trebuie să includă suplimentul din bucla for
    }

    // distanța < 20
    @Test
    public void testDistantaMaiMicaDe20() {
        Livrare livrare = new Livrare(10.0, 15.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0); // Costul de bază fără suplimente din for
    }

    // Testăm excepție pentru greutate negativa
    @Test
    public void testGreutateNegativa() {
        Livrare livrare = new Livrare(-1.0, 10.0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
        assertEquals("Greutatea si distanta trebuie sa fie pozitive.", exception.getMessage());
    }

    // Testăm excepție pentru distanță negativa
    @Test
    public void testDistantaNegativa() {
        Livrare livrare = new Livrare(5.0, -10.0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
        assertEquals("Greutatea si distanta trebuie sa fie pozitive.", exception.getMessage());
    }


    // TESTE DECIZII


    // test pentru livrare cu prioritate
    @Test
    public void testLivrarePrioritara() {
        Livrare livrare = new Livrare(12.0, 30.0, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 10); // costul ar trebui să fie crescut cu 20%
    }

    // test pentru livrare non-prioritară (reducerea de 5%)
    @Test
    public void testLivrareNonPrioritara() {
        Livrare livrare = new Livrare(12.0, 30.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost < 200); // ne asiguram ca este mai mic decât un prag
    }

    // test pentru declanșarea for-ului cu greutate > 10 și distanta > 20
    @Test
    public void testDistantaSiGreutateMaiMari() {
        Livrare livrare = new Livrare(15.0, 25.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 10); // bucla for ar trebui sa crească costul
    }

    // test pentru cat de ieftina e livrarea
    @Test
    public void testCostLivrareIeftina() {
        Livrare livrare = new Livrare(2.0, 5.0, false);
        String clasificare = serviciu.clasificaLivrare(livrare);
        assertEquals("Ieftina", clasificare);
    }

    // test pentru valoarea distanta = 20
    @Test
    public void testDistantaExact20() {
        Livrare livrare = new Livrare(12.0, 20.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0); // se verifică costul fara intrare în bucla for
    }

    // test pentru valoare greutate = 10
    @Test
    public void testGreutateExact10() {
        Livrare livrare = new Livrare(10.0, 25.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0); // depinde daca condiția for e strict > sau >=
    }
}
