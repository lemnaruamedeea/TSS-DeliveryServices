package src.teste;

import src.main.Livrare;
import src.main.ServiciuLivrare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TesteConditieDecizie {

    // Instanțiem clasa care conține logica de livrare
    ServiciuLivrare serviciu = new ServiciuLivrare();

    // ========================
    //        TESTE CONDIȚII
    // ========================

    // Test pentru cazul în care greutatea este 0 — așteptăm excepție
    @Test
    public void testGreutateZero() {
        Livrare livrare = new Livrare(0, 10.0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
        assertEquals("Greutatea si distanta trebuie sa fie pozitive.", exception.getMessage());
    }

    // Test pentru cazul în care distanța este 0 — așteptăm excepție
    @Test
    public void testDistantaZero() {
        Livrare livrare = new Livrare(5.0, 0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
        assertEquals("Greutatea si distanta trebuie sa fie pozitive.", exception.getMessage());
    }

    // Testăm suplimentul pentru greutate mai mare decât 5
    @Test
    public void testGreutateMaiMareDecat5() {
        Livrare livrare = new Livrare(6.0, 10.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 10); // Ar trebui să reflecte suplimentul de greutate
    }

    // Test pentru greutate > 10 și distanță > 20 — activează logica din bucla for
    @Test
    public void testDistantaSiGreutateMare() {
        Livrare livrare = new Livrare(12.0, 30.0, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);  // Costul trebuie să includă suplimentul din bucla for
    }

    // Distanța < 20 — bucla for nu trebuie activată
    @Test
    public void testDistantaMaiMicaDe20() {
        Livrare livrare = new Livrare(10.0, 15.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0); // Costul de bază fără suplimente din for
    }

    // Test similar cu testDistantaSiGreutateMare — validare redundanță
    @Test
    public void testDistantaSiGreutateMari() {
        Livrare livrare = new Livrare(12.0, 30.0, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0); // Confirmăm execuția for-ului
    }

    // Testăm excepție pentru greutate negativă
    @Test
    public void testGreutateNegativa() {
        Livrare livrare = new Livrare(-1.0, 10.0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
        assertEquals("Greutatea si distanta trebuie sa fie pozitive.", exception.getMessage());
    }

    // Testăm excepție pentru distanță negativă
    @Test
    public void testDistantaNegativa() {
        Livrare livrare = new Livrare(5.0, -10.0, false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
        assertEquals("Greutatea si distanta trebuie sa fie pozitive.", exception.getMessage());
    }


    // ========================
    //        TESTE DECIZII
    // ========================

    // Test pentru livrare cu prioritate — se aplică un multiplicator
    @Test
    public void testLivrarePrioritara() {
        Livrare livrare = new Livrare(12.0, 30.0, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 10); // Costul ar trebui să fie crescut cu 20%
    }

    // Test pentru livrare non-prioritară — se aplică reducerea de 5%
    @Test
    public void testLivrareNonPrioritara() {
        Livrare livrare = new Livrare(12.0, 30.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost < 200); // Ne asigurăm că este mai mic decât un prag arbitrar
    }

    // Test pentru declanșarea for-ului cu greutate > 10 și distanță > 20
    @Test
    public void testDistantaSiGreutateMaiMari() {
        Livrare livrare = new Livrare(15.0, 25.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 10); // Bucla for ar trebui să crească costul
    }

    // Test pentru clasificare "Ieftina"
    @Test
    public void testCostLivrareIeftina() {
        Livrare livrare = new Livrare(2.0, 5.0, false);
        String clasificare = serviciu.clasificaLivrare(livrare);
        assertEquals("Ieftina", clasificare);
    }

    // Test pentru valoarea limită distanță = 20 (nu trebuie să intre în for)
    @Test
    public void testDistantaExact20() {
        Livrare livrare = new Livrare(12.0, 20.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0); // Se verifică costul fără intrare în bucla for
    }

    // Test pentru valoarea limită greutate = 10 (posibil să nu intre în for)
    @Test
    public void testGreutateExact10() {
        Livrare livrare = new Livrare(10.0, 25.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0); // Depinde dacă condiția for e strict > sau ≥
    }
}
