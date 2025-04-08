public class Main {
    public static void main(String[] args) {
        ServiciuLivrare serviciu = new ServiciuLivrare();

        // Exemplu 1: livrare usoara, distanta mica, nu e prioritara
        Livrare livrare1 = new Livrare(1.5, 5, false);
        afiseazaInfoLivrare(serviciu, livrare1);

        // Exemplu 2: livrare grea si prioritara
        Livrare livrare2 = new Livrare(12.0, 30.0, true);
        afiseazaInfoLivrare(serviciu, livrare2);

        // Exemplu 3: livrare cu greutate 6 kg, distanta 25 km, neprioritara
        Livrare livrare3 = new Livrare(6.0, 25.0, false);
        afiseazaInfoLivrare(serviciu, livrare3);
    }

    private static void afiseazaInfoLivrare(ServiciuLivrare serviciu, Livrare livrare) {
        System.out.println("----- LIVRARE -----");
        System.out.println("Greutate: " + livrare.getGreutateKg() + " kg");
        System.out.println("Distanta: " + livrare.getDistantaKm() + " km");
        System.out.println("Prioritara: " + (livrare.estePrioritara() ? "DA" : "NU"));
        System.out.println("Cost: " + serviciu.calculeazaCostLivrare(livrare) + " lei");
        System.out.println("Clasificare: " + serviciu.clasificaLivrare(livrare));
        System.out.println("Eligibila reducere: " + (serviciu.esteEligibilaReducere(livrare) ? "DA" : "NU"));
        System.out.println("Timp estimat livrare: " + serviciu.estimeazaTimpLivrare(livrare) + " zile");
        System.out.println();
    }
}