/*
Testele la nivel de instrucțiune asigura acoperirea fiecarei linii de cod din metodele testate
Ele verifica funcționarea corecta a fiecarei instructiuni in diferite situatii,
pentru a detecta eventuale erori sau comportamente neasteptate.

fiecare linie de cod executabilă este rulată cel puțin o data intr-un test
Nu înseamna că sunt verificate toate căile posibile, ci doar că nicio linie nu ramane neatinsa
 */

package src.teste;

import src.main.Livrare;
import src.main.ServiciuLivrare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TesteInstructiune {

    ServiciuLivrare serviciu = new ServiciuLivrare();

    // test pentru cazul normal, livrare usoara, non-prioritara
    @Test
    void testCostLivrare_usor_neprioritar() {
        Livrare livrare = new Livrare(2.0, 10.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        // Costul trebuie sa fie pozitiv si rezonabil
        assertTrue(cost > 0 && cost < 20);
    }

    // test pentru livrare grea, prioritara, distanta mare
    @Test
    void testCostLivrare_greu_prioritar_lunga() {
        Livrare livrare = new Livrare(12.0, 30.0, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        // costul trebuie sa fie calculat corect
        assertTrue(cost > 0);
    }

    // test pentru greutate negativa (exceptie)
    @Test
    void testCostLivrare_greutateNegativa_exceptie() {
        Livrare livrare = new Livrare(-1.0, 10.0, false);
        // exceptie pentru greutate invalida
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(livrare));
    }

    // test pentru distanta zero (exceptie)
    @Test
    void testCostLivrare_distanțaZero_exceptie() {
        Livrare livrare = new Livrare(1.0, 0.0, false);
        // exceptie pentru distanta invalida
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(livrare));
    }

    // test pentru clasificare livrare ieftina
    @Test
    void testClasificare_Ieftina() {
        Livrare livrare = new Livrare(2.0, 10.0, false);
        assertEquals("Ieftina", serviciu.clasificaLivrare(livrare));
    }

    // test pentru reducere eligibila (livrare usoara si non-prioritara)
    @Test
    void testReducere_eligibil() {
        Livrare livrare = new Livrare(1.0, 5.0, false);
        assertTrue(serviciu.esteEligibilaReducere(livrare));
    }

    // test pentru reducere neeligibila (livrare prioritara)
    @Test
    void testReducere_neeligibil_prioritara() {
        Livrare livrare = new Livrare(1.0, 5.0, true);
        assertFalse(serviciu.esteEligibilaReducere(livrare));
    }

    // test pentru timp estimat livrare non-prioritara
    @Test
    void testEstimeazaTimp_neprioritara() {
        Livrare livrare = new Livrare(3.0, 15.0, false);
        assertEquals(2, serviciu.estimeazaTimpLivrare(livrare));
    }

    // ttest pentru timp estimat livrare prioritara
    @Test
    void testEstimeazaTimp_prioritara() {
        Livrare livrare = new Livrare(3.0, 15.0, true);
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    // test pentru timp estimat minim 1 zi
    @Test
    void testEstimeazaTimp_minim1() {
        Livrare livrare = new Livrare(3.0, 1.0, true);
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    // test pentru livrare ieftina
    @Test
    public void testCalculeazaCostLivrare_Ieftin() {
        Livrare livrare = new Livrare(3.0, 10.0, false);
        assertTrue(serviciu.calculeazaCostLivrare(livrare) < 75);
    }

    // test pentru cost plafonat la 200
    @Test
    public void testCalculeazaCostLivrare_MaxPlafon() {
        Livrare livrare = new Livrare(100.0, 500.0, true);
        assertEquals(200.0, serviciu.calculeazaCostLivrare(livrare));
    }

    // teste pentru valori invalide: greutate zero si distanta negativa
    @Test
    public void testCalculeazaCostLivrare_ValoriInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(new Livrare(0.0, 5.0, false));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(new Livrare(2.0, -1.0, true));
        });
    }

    // test pentru clasificare livrare: ar trebui sa fie "Ieftina" pentru greutate si distanta mici
    @Test
    public void testClasificaLivrare_Ieftina() {
        Livrare livrare = new Livrare(1.0, 5.0, false);
        assertEquals("Ieftina", serviciu.clasificaLivrare(livrare));
    }

    // test pentru eligibilitate la reducere: livrare usoara si non-prioritara -> ar trebui sa fie eligibila
    @Test
    public void testEsteEligibilaReducere_Adevarat() {
        Livrare livrare = new Livrare(1.0, 10.0, false);
        assertTrue(serviciu.esteEligibilaReducere(livrare));
    }

    // test pentru cazuri neeligibile la reducere: greutate mare si livrare prioritara
    @Test
    public void testEsteEligibilaReducere_Fals() {
        Livrare livrare1 = new Livrare(3.0, 10.0, false);
        Livrare livrare2 = new Livrare(1.5, 10.0, true);
        assertFalse(serviciu.esteEligibilaReducere(livrare1));
        assertFalse(serviciu.esteEligibilaReducere(livrare2));
    }

    // test pentru timp estimat de livrare normal (fara prioritate)
    @Test
    public void testEstimeazaTimpLivrare_Normal() {
        Livrare livrare = new Livrare(3.0, 35.0, false);
        assertEquals(4, serviciu.estimeazaTimpLivrare(livrare));
    }

    // test pentru timp estimat de livrare prioritara (timp scazut cu 1 zi)
    @Test
    public void testEstimeazaTimpLivrare_Prioritara() {
        Livrare livrare = new Livrare(3.0, 35.0, true);
        assertEquals(3, serviciu.estimeazaTimpLivrare(livrare));
    }

    // test pentru timp estimat: minimul acceptat este 1 zi
    @Test
    public void testEstimeazaTimpLivrare_Minim1() {
        Livrare livrare = new Livrare(3.0, 0.1, true);
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    // test pentru exceptie: greutate zero nu este acceptata
    @Test
    public void testCalculeazaCostLivrare_GreutateZero() {
        Livrare livrare = new Livrare(0.0, 10.0, false);
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
    }

    // test pentru exceptie: distanta zero nu este acceptata
    @Test
    public void testCalculeazaCostLivrare_DistantaZero() {
        Livrare livrare = new Livrare(3.0, 0.0, false);
        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
    }

    // test pentru caz in care greutatea este mica si distanta este mare -> nu intra in blocul for
    @Test
    public void testCalculeazaCostLivrare_GreutateMica_DistantaLunga_FaraFor() {
        Livrare livrare = new Livrare(4.0, 30.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);
    }

    // test pentru caz in care greutatea este mare si distanta este mica -> nu intra in blocul for
    @Test
    public void testCalculeazaCostLivrare_GreutateMare_DistantaMica_FaraFor() {
        Livrare livrare = new Livrare(12.0, 15.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);
    }

    // test pentru aplicarea reducerii la livrare usoara si non-prioritara
    @Test
    public void testCalculeazaCostLivrare_Reducere() {
        Livrare livrare = new Livrare(1.5, 10.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost < 10);
    }

    // test pentru plafonarea costului la 200 cand este depasit
    @Test
    public void testCalculeazaCostLivrare_Plafon_200() {
        Livrare livrare = new Livrare(50.0, 1000.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(200, cost, 0.01);
    }

    // test pentru costul unei livrari prioritare (cu factor de multiplicare 1.25)
    @Test
    public void testCalculeazaCostLivrare_Prioritara() {
        Livrare livrare = new Livrare(10.0, 20.0, true);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);
    }

    // test pentru costul unei livrari non-prioritare (cu reducere 5%)
    @Test
    public void testCalculeazaCostLivrare_NonPrioritara() {
        Livrare livrare = new Livrare(10.0, 20.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);
    }
}