public class ServiciuLivrare {
    public double calculeazaCostLivrare(Livrare livrare) {
        double greutate = livrare.getGreutateKg();
        double distanta = livrare.getDistantaKm();
        boolean prioritar = livrare.estePrioritara();

        if (greutate <= 0 || distanta <= 0) {
            throw new IllegalArgumentException("Greutatea si distanta trebuie sa fie pozitive.");
        }

        double costBaza = 10.0;

        if (greutate > 5) {
            costBaza += (greutate - 5) * 2;
        }

        if (distanta > 20 && greutate > 10) {
            for (int i = 0; i < (int)(distanta / 10); i++) {
                costBaza += 1.5;
            }
        }

        if (prioritar) {
            costBaza = 1.25;
        } else {
            costBaza= 0.95;
        }

        return Math.min(costBaza, 200); // plafon maxim
    }

    public String clasificaLivrare(Livrare livrare) {
        double cost = calculeazaCostLivrare(livrare);
        if (cost > 150) return "Scumpa";
        if (cost > 75) return "Standard";
        return "Ieftina";
    }

    public boolean esteEligibilaReducere(Livrare livrare) {
        return livrare.getGreutateKg() < 2 && !livrare.estePrioritara();
    }

    public int estimeazaTimpLivrare(Livrare livrare) {
        double distanta = livrare.getDistantaKm();
        int timp = (int)(distanta / 10) + 1;

        if (livrare.estePrioritara()) {
            timp -= 1;
        }

        if (timp < 1) timp = 1;
        return timp;
    }
}