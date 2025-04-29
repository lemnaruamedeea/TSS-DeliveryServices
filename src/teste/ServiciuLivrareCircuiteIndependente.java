package teste;

public class ServiciuLivrareCircuiteIndependente {

    private final ServiciuLivrare serviciu = new ServiciuLivrare();

    @Test
    public void testCircuit1_GreutateInvalida() {
        Livrare livrare = new Livrare(0, 10, false);

        assertThrows(IllegalArgumentException.class, () -> {
            serviciu.calculeazaCostLivrare(livrare);
        });
    }

    @Test
    public void testCircuit2_GreutateMica_DistantaMica_Prioritar() {
        Livrare livrare = new Livrare(3, 10, true);

        double cost = serviciu.calculeazaCostLivrare(livrare);

        // 10 * 1.25 = 12.5
        assertEquals(12.5, cost, 0.001);
    }

    @Test
    public void testCircuit3_GreutateMare_FaraDistantaMare_NonPrioritar() {
        Livrare livrare = new Livrare(8, 15, false);

        double cost = serviciu.calculeazaCostLivrare(livrare);

        // Cost: 10 + (8-5)*2 = 16
        // 16 * 0.95 = 15.2
        assertEquals(15.2, cost, 0.001);
    }

    @Test
    public void testCircuit4_GreutateMare_DistantaMare_BuclaExecutata() {
        Livrare livrare = new Livrare(15, 40, false);

        double cost = serviciu.calculeazaCostLivrare(livrare);

        // Cost:
        // Baza: 10 + (15-5)*2 = 30
        // Bucla: (40/10) = 4 iteratii * 1.5 = 6
        // Total = 36
        // 36 * 0.95 = 34.2
        assertEquals(34.2, cost, 0.001);
    }
}
