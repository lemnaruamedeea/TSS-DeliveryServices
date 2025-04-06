# TSS-DeliveryServices

## Clasa Livrare

Clasa Livrare reprezintă o livrare pe care o trimitem printr-un serviciu de transport, având caracteristici esențiale cum ar fi:
⋅⋅* Greutatea coletului
⋅⋅* Distanța pe care o parcurge livrarea
⋅⋅* Prioritatea livrării (dacă e prioritară sau nu)

Aceste trei proprietăți formează baza pentru calculul costului de livrare, timpul de livrare și alte informații importante.

1. Atributele clasei:
⋅⋅* greutateKg (tip double): Reprezintă greutatea coletului în kilograme.
⋅⋅* Regula este că dacă greutatea e mai mare de 5 kg, se adaugă costuri suplimentare.

⋅⋅* distantaKm (tip double): Reprezintă distanța, în kilometri, pe care trebuie să o parcurgă livrarea.
⋅⋅* Distanța mai mare de 20 km influențează costul, iar pentru anumite greutăți, se adaugă costuri suplimentare pentru distanțele mari.

⋅⋅* prioritar (tip boolean): Reprezintă dacă livrarea este prioritară sau nu.
⋅⋅* Dacă livrarea este prioritară, costul va fi multiplicat cu un factor de 1.25. Dacă nu este prioritară, va fi aplicat un factor de reducere (0.95).

2. Construcție:
Constructor: Permite crearea unui obiect Livrare cu greutate, distanță și prioritate specificate.
public Livrare(double greutateKg, double distantaKm, boolean prioritar)


3. Metodele clasei Livrare:
⋅⋅* getGreutateKg(): Returnează greutatea coletului.
⋅⋅* getDistantaKm(): Returnează distanța livrării.
⋅⋅* estePrioritara(): Verifică dacă livrarea este prioritară.


