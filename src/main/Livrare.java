package src.main;

public class Livrare {
    private double greutateKg;
    private double distantaKm;
    private boolean prioritar;

    public Livrare(double greutateKg, double distantaKm, boolean prioritar) {
        this.greutateKg = greutateKg;
        this.distantaKm = distantaKm;
        this.prioritar = prioritar;
    }

    public double getGreutateKg() {
        return greutateKg;
    }

    public double getDistantaKm() {
        return distantaKm;
    }

    public boolean estePrioritara() {
        return prioritar;
    }
}
