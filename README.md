# 🚚 TSS-DeliveryServices

TSS-DeliveryServices este o aplicație Java care modelează un sistem de livrări, incluzând logica de calcul pentru costuri, timp de livrare și clasificare, pe baza unor reguli predefinite. Proiectul este structurat în două clase principale: `Livrare` și `ServiciuLivrare`.

---

## 📦 Clasa `Livrare`

Reprezintă o livrare individuală, cu atribute esențiale:

### 🔸 Atribute
- `double greutateKg`: greutatea coletului (kg)  
  > Dacă greutatea > 5 kg → cost suplimentar.
  
- `double distantaKm`: distanța de livrare (km)  
  > Dacă distanța > 20 km și greutatea > 10 kg → cost suplimentar pe distanță.

- `boolean prioritar`: indică dacă livrarea este prioritară  
  > - Livrare **prioritară**: costul este multiplicat cu `1.25`  
  > - Livrare **neprioritară**: costul este redus cu `5%` (factor `0.95`)

### 🔧 Constructor
```java
public Livrare(double greutateKg, double distantaKm, boolean prioritar)
```

### 🔍 Metode
- `getGreutateKg()`
- `getDistantaKm()`
- `estePrioritara()`

---

## 🧠 Clasa `ServiciuLivrare`

Această clasă implementează logica aplicabilă unei livrări:

### 1️⃣ `double calculeazaCostLivrare(Livrare livrare)`
Calculează costul livrării pe baza regulilor:

- Cost de bază: **10 lei**
- Dacă greutatea > 5 kg → + `2 lei` per kg peste 5
- Dacă distanța > 20 km **și** greutatea > 10 kg → + `1.5 lei` per fiecare 10 km
- Cost final:
  - `* 1.25` dacă e prioritară
  - `* 0.95` dacă NU e prioritară
- Plafon maxim: **200 lei**

---

### 2️⃣ `String clasificaLivrare(Livrare livrare)`
Clasifică livrarea în funcție de cost:

- **"Ieftină"**: sub 75 lei
- **"Standard"**: între 75 și 150 lei
- **"Scumpă"**: peste 150 lei

---

### 3️⃣ `boolean esteEligibilaReducere(Livrare livrare)`
Verifică dacă livrarea este eligibilă pentru reducere:

> Livrarea este eligibilă dacă:
> - greutatea < 2 kg
> - **și** livrarea NU este prioritară

---

### 4️⃣ `double estimeazaTimpLivrare(Livrare livrare)`
Estimează timpul de livrare (în ore):

- Timp de bază: `(distanta / 10) + 1`
- Dacă livrarea este prioritară → se scade 1 oră
- Timpul minim: **1 oră**

---
