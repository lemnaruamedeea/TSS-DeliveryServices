# 🚚 TSS-DeliveryServices

TSS-DeliveryServices este o aplicație Java dezvoltată în contextul disciplinei Testarea Sistemelor Software. Aceasta simulează un sistem de livrări prin modelarea și evaluarea caracteristicilor unei livrări, precum costul, timpul estimat și eligibilitatea pentru reduceri. Scopul proiectului este de a oferi un model simplu al unui serviciu real, iar pe de altă parte, servește ca punct de plecare pentru aplicarea diverselor tehnici de testare unitară, testare structurală și testare funcțională.

<details> 
  
  <summary><b>📚 Enunțul problemei</summary></b> 

<br>     
Se testează un program care gestionează livrări pe baza unor caracteristici introduse de utilizator.  
Mai precis, pentru o livrare, utilizatorul introduce:

- o valoare reală pozitivă pentru greutatea coletului (în kilograme),
- o valoare reală pozitivă pentru distanța până la destinație (în kilometri),
- un răspuns binar pentru caracterul prioritar al livrării: da pentru prioritar sau nu pentru non-prioritar.

Programul calculează:

1. Costul livrării, ținând cont de greutate, distanță și prioritate, cu un plafon maxim aplicat, după cum urmează:

- Cost de bază: 10.0 lei
- înmulțim cu 2 lei pentru fiecare kg peste 5 kg
- adunăm 1.5 lei pentru fiecare 10 km peste 20 km (numai dacă greutatea > 10 kg)
- înmulțim cu 1.25 dacă este livrare prioritară sau cu 0.95 dacă nu este livrare prioritară
- Plafon maxim: 200 lei

2. Clasificarea costului în: „Ieftină”, „Standard” sau „Scumpă”:

- "Scumpă" dacă cost ≥ 150
- "Standard" dacă 75 ≤ cost < 150
- "Ieftină" dacă cost < 75

3. Eligibilitatea pentru o reducere, disponibilă doar pentru livrările ușoare și neprioritare;

- Este eligibilă pentru reducere doar dacă: greutate < 2 kg și livrarea NU este prioritară

4. Timpul estimat de livrare, exprimat în ore, influențat de distanță și prioritate.

- Timp de bază: ⌊distanta / 10⌋ + 1
- CU 1 oră mai puțin dacă este prioritară
- Timpul minim este de 1 oră


## 📽️ Prezentare video

👉 Vezi prezentarea video [AICI](https://youtu.be/zyChLN-exds)

---


</details>

<details>
  <summary> <b>📋 Clasele de echivalență</summary></b> 

### 1. Domeniul de intrări:

- **g** – greutatea (real pozitiv)  
- **d** – distanța (real pozitiv)  
- **p** – prioritate (da / nu)  

**Clase de echivalență:**

| Parametru | Clasa validă | Clasa invalidă |
|----------|---------------|----------------|
| g        | G₁ = { g > 0 } | G₂ = { g ≤ 0 } |
| d        | D₁ = { d > 0 } | D₂ = { d ≤ 0 } |
| p        | P₁ = { da, nu }   |               |



---

### 2. Domeniul de ieșiri:
Ieșirea programului conține:

- Costul livrării (valoare numerică)
- Clasificare: „Ieftină” | „Standard” | „Scumpă”
- Mesaj privind eligibilitatea la reducere: „Da” | „Nu”
- Timp estimat: număr întreg (ore)
- Sau un mesaj de eroare pentru valori invalide

---

### Testele pentru `calculeazaCostLivrare`
| Nr. | Greutate | Distanță | Prioritar | Așteptat                             | 
| --- | -------- | -------- | --------- | ------------------------------------ | 
| C1  | 0        | 10       | false     | Exception (IllegalArgumentException) | 
| C2  | 3        | 0        | true      | Exception (IllegalArgumentException) | 
| C3  | 2        | 10       | true      | 12.5                                 | 
| C4  | 2        | 10       | false     | 9.5                                  | 
| C5  | 7        | 15       | true      | 17                                   | 
| C6  | 7        | 15       | false     | 13.3				                         | 
| C7  | 12       | 45       | true      | 33.75                                | 
| C8  | 12       | 45       | false     | 25.65                                | 
| C9  | 12       | 15       | true      | 30                                   | 
| C10 | 12       | 15       | false     | 22.8                                 | 

```
 // C1: greutate ≤ 0
    @Test
    public void testGreutateZero() {
        Livrare l = new Livrare(0, 10, false);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(l));
    }

    // C2: distanta ≤ 0
    @Test
    public void testDistantaZero() {
        Livrare l = new Livrare(3, 0, true);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(l));
    }

    // C3: greutate mică, distanță mică, prioritar
    @Test
    public void testLivrareIeftinaPrioritara() {
        Livrare l = new Livrare(2, 10, true);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertEquals(12.5, cost, 0.01); // 10 * 1.25
    }

    // C4: greutate mică, distanță mică, neprioritar
    @Test
    public void testLivrareIeftinaNeprioritara() {
        Livrare l = new Livrare(2, 10, false);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertEquals(9.5, cost, 0.01); // 10 * 0.95
    }
```

---

### Testele pentru `clasificaLivrare`
| Nr. | Greutate | Distanță | Prioritar | Cost estimat     | Clasificare așteptată |
| --- | -------- | -------- | --------- | ---------------- | --------------------- |
| C11 | 2        | 10       | false     | 9.5              | Ieftina               |
| C12 | 50       | 100      | false     | ≈ 104.5          | Standard              |
| C13 | 100      | 200      | true      | > 200 (plafonat) | Scumpa                |


```
 @Test
    public void testClasificareIeftina() {
        Livrare l = new Livrare(2, 10, false); // cost = 9.5
        assertEquals("Ieftina", serviciu.clasificaLivrare(l));
    }

    @Test
    public void testClasificareStandard() {
        Livrare l = new Livrare(20, 30, false);
        // costBaza = 10 + 30 + (int)(30/10)*1.5 = 10+30+4.5 = 44.5 * 0.95 ≈ 42.275
        // asta e sub 75, deci nu e bun → trebuie să generăm un cost > 75
        l = new Livrare(50, 100, false); // cost = 110 * 0.95 = 104.5
        assertEquals("Standard", serviciu.clasificaLivrare(l));
    }

    @Test
    public void testClasificareScumpa() {
        Livrare l = new Livrare(100, 200, true); // cost depășește plafonul 200
        assertEquals("Scumpa", serviciu.clasificaLivrare(l));
    }
```

---

### Testele pentru `esteEligibilaReducere`
| Nr. | Greutate | Distanță | Prioritar | Așteptat |
| --- | -------- | -------- | --------- | -------- |
| C14 | 1.5      | 10       | false     | true     |
| C15 | 1.5      | 10       | true      | false    |
| C16 | 2.5      | 10       | false     | false    |
| C17 | 5        | 10       | true      | false    |


```
 @Test
    public void testEligibilReducere() {
        Livrare l = new Livrare(1.5, 10, false);
        assertTrue(serviciu.esteEligibilaReducere(l));
    }

    @Test
    public void testNuEsteEligibilPrioritara() {
        Livrare l = new Livrare(1.5, 10, true);
        assertFalse(serviciu.esteEligibilaReducere(l));
    }

    @Test
    public void testNuEsteEligibilGreutateMare() {
        Livrare l = new Livrare(2.5, 10, false);
        assertFalse(serviciu.esteEligibilaReducere(l));
    }

    @Test
    public void testNuEsteEligibilAmbele() {
        Livrare l = new Livrare(5, 10, true);
        assertFalse(serviciu.esteEligibilaReducere(l));
    }
```

---

### Testele pentru `estimeazaTimpLivrare`
| Nr. | Greutate | Distanță | Prioritar | Așteptat |
| --- | -------- | -------- | --------- | -------- |
| C18 | 2        | 5        | false     | 1        |
| C19 | 2        | 5        | true      | 1        |
| C20 | 2        | 25       | false     | 3        |
| C21 | 2        | 25       | true      | 2        |
| C22 | 2        | 0.1      | false     | 1        |


```
   @Test
    public void testTimpLivrareMicNeprioritar() {
        Livrare l = new Livrare(2, 5, false);
        assertEquals(1, serviciu.estimeazaTimpLivrare(l));
    }

    @Test
    public void testTimpLivrareMicPrioritar() {
        Livrare l = new Livrare(2, 5, true);
        assertEquals(1, serviciu.estimeazaTimpLivrare(l)); // nu scade sub 1
    }

    @Test
    public void testTimpLivrareMediuNeprioritar() {
        Livrare l = new Livrare(2, 25, false);
        assertEquals(3, serviciu.estimeazaTimpLivrare(l)); // 25/10 = 2.5 -> int = 2 + 1 = 3
    }
```

</details>



<details>
  <summary><b>🚧 Analiza testelor de frontieră</b></summary>

### 🧪 Obiectiv
Această analiză vizează testarea comportamentului serviciului de livrare în apropierea limitelor permise ale intrărilor, acolo unde este cel mai probabil să apară erori.

---

### 📥 Domeniul intrărilor:
- `g` – greutatea coletului (`double`, trebuie să fie > 0)
- `d` – distanța de livrare (`double`, trebuie să fie > 0)
- `p` – livrare prioritară (`boolean`)

---

### 🔧 Teste pentru `calculeazaCostLivrare`

| Nr. test                          | Scop                              | g     | d   | p     | Rezultat așteptat         |
|--------------------------------|-----------------------------------|-------|-----|-------|----------------------------|
| 1         | Limită invalidă                   | 0     | 10  | false | Excepție (`IllegalArgument`) |
| 2    | Limită validă                     | 0.1   | 10  | false | Cost > 0                  |
| 3        | Prag de reducere                  | 5     | 10  | false | 9.5                       |
| 4        | Ușor peste prag                   | 5.01  | 10  | false | 9.52                      |
| 5 | Prag pentru taxa de km          | 10    | 20  | false | 19.0                      |
| 6| Suprataxă aplicată                | 10.01 | 30  | false | 20.44                     |
| 7       | Distanță invalidă                 | 5     | 0   | false | Excepție (`IllegalArgument`) |
| 8    | Limită validă                     | 5     | 0.1 | false | Cost > 0                  |
| 9       | Prag pentru cost suplimentar      | 11    | 20  | false | 20.9                      |
| 10| Distanță peste limită            | 11    | 30  | false | 22.325                    |
| 11        | Cost prioritar                    | 5     | 10  | true  | 12.5                      |
| 12         | Cost non-prioritar                | 5     | 10  | false | 9.5                       |

```
 @Test
    void testGreutateLaZero() {
        Livrare livrare = new Livrare(0, 10, false);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(livrare));
    }

    @Test
    void testGreutateMinimaValida() {
        Livrare livrare = new Livrare(0.1, 10, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertTrue(cost > 0);
    }

    @Test
    void testGreutateLimita5() {
        Livrare livrare = new Livrare(5, 10, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(9.5, cost, 0.01);
    }

    @Test
    void testGreutatePeste5() {
        Livrare livrare = new Livrare(5.01, 10, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(9.52, cost, 0.01); // 10 + 0.02 * 0.95
    }
```

---

### 🏷️ Teste pentru `clasificaLivrare`

|  Nr. test                                | Scop                              | g     | d   | p     | Clasificare Așteptată     |
|--------------------------------|-----------------------------------|-------|-----|-------|----------------------------|
| 1      | Cost scăzut                       | 1     | 5   | false | Ieftina                   |
| 2 | Cost spre 75                  | 10    | 40  | false | Ieftina                   |
| 3| Cost foarte mare                  | 100   | 200 | true  | Scumpa                    |


```
 @Test
    void testClasificareIeftina() {
        Livrare livrare = new Livrare(1, 5, false);
        assertEquals("Ieftina", serviciu.clasificaLivrare(livrare));
    }

    @Test
    void testClasificareStandardLaLimita() {
        Livrare livrare = new Livrare(10, 40, false); // costul trece de 75
        assertEquals("Ieftina", serviciu.clasificaLivrare(livrare));
    }

    @Test
    void testClasificareScumpaPeste150() {
        Livrare livrare = new Livrare(100, 200, true); // fortam cost mare
        assertEquals("Scumpa", serviciu.clasificaLivrare(livrare));
    }

```

---

### 🎯 Teste pentru `esteEligibilaReducere`

|  Nr. test                                 | g     | p     | Așteptat |
|----------------------------------|-------|-------|----------|
| 1 | 1.99  | false | true     |
| 2     | 1.99  | true  | false    |
| 3   | 2.0   | false | false    |


```
  @Test
    void testReducereEligibil_Sub2kg_NonPrioritar() {
        Livrare livrare = new Livrare(1.99, 10, false);
        assertTrue(serviciu.esteEligibilaReducere(livrare));
    }

    @Test
    void testReducereNeeligibil_Prioritar() {
        Livrare livrare = new Livrare(1.99, 10, true);
        assertFalse(serviciu.esteEligibilaReducere(livrare));
    }

    @Test
    void testReducereNeeligibil_GreutateFix2kg() {
        Livrare livrare = new Livrare(2.0, 10, false);
        assertFalse(serviciu.esteEligibilaReducere(livrare));
    }

```
---

### ⏱️ Teste pentru `estimeazaTimpLivrare`

|  Nr. test                             | d     | p     | Așteptat |
|------------------------------|-------|-------|----------|
| 1   | 9.9   | false | 1        |
| 2   | 10    | false | 2        |
| 3| 10    | true  | 1        |
| 4  | 0.5   | true  | 1        |


```
 @Test
    void testTimpLivrare_Sub10km() {
        Livrare livrare = new Livrare(1, 9.9, false);
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    void testTimpLivrare_La10km() {
        Livrare livrare = new Livrare(1, 10, false);
        assertEquals(2, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    void testTimpLivrare_Prioritara() {
        Livrare livrare = new Livrare(1, 10, true);
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }

    @Test
    void testTimpLivrare_Minim1h() {
        Livrare livrare = new Livrare(1, 0.5, true); // => timp = 1 - 1 => 0 -> returnează 1
        assertEquals(1, serviciu.estimeazaTimpLivrare(livrare));
    }
```

</details>


<details>
<summary> <b>📄 Detalii testare la nivel de instrucțiune </b> </summary>

## 📌 Cerințe aplicație

- Greutatea trebuie să fie **mai mare decât 0**
- Distanța trebuie să fie **mai mare decât 0**
- Costul este calculat în funcție de greutate, distanță și dacă este prioritar
- Se aplică o **reducere** dacă livrarea este ușoară (<2kg) și **neprioritară**
- Costul total este **plafonat la 200**

---

## 🧪 Domeniul de intrări

Aplicația primește:

- `greutate`: număr real pozitiv
- `distanta`: număr real pozitiv
- `prioritara`: boolean (true/false)

### Clase de echivalență

#### Greutate
- **G1**: 0 < greutate ≤ 2 (ușoară)
- **G2**: 2 < greutate ≤ 10 (medie)
- **G3**: greutate > 10 (grea)
- **G4**: greutate ≤ 0 (invalidă)

#### Distanță
- **D1**: 0 < distanță ≤ 20 (scurtă)
- **D2**: distanță > 20 (lungă)
- **D3**: distanță ≤ 0 (invalidă)

#### Prioritate
- **P1**: true
- **P2**: false

---

## 🧾 Domeniul de ieșiri

- Cost calculat corect (pozitiv)
- Cost cu reducere aplicată
- Cost plafonat la 200
- Excepții pentru date invalide

---

## 🔄 Clase de echivalență combinate

| Cod     | Descriere                                              |
|---------|--------------------------------------------------------|
| C_111   | G1, D1, P2 – cu reducere                               |
| C_112   | G1, D1, P1 – prioritar                                 |
| C_121   | G2, D2, P2 – cost standard                             |
| C_122   | G2, D2, P1 – cost crescut                              |
| C_131   | G3, D2, P1 – plafonare cost                            |
| C_141   | G4, D1, P2 – excepție greutate                         |
| C_142   | G2, D3, P2 – excepție distanță                         |

---

## 🧩 Set de date de test (exemple concrete)

| Clasă   | Apel                            | Rezultat Așteptat                     |
|---------|----------------------------------|----------------------------------------|
| c_111   | Livrare(1.5, 10.0, false)        | reducere aplicată, cost sub 10        |
| c_112   | Livrare(1.5, 10.0, true)         | fără reducere, cost mai mare          |
| c_121   | Livrare(4.0, 30.0, false)        | cost normal                           |
| c_122   | Livrare(4.0, 30.0, true)         | cost mai mare                         |
| c_131   | Livrare(100.0, 500.0, true)      | cost plafonat la 200                  |
| c_141   | Livrare(0.0, 10.0, false)        | excepție: greutate invalidă           |
| c_142   | Livrare(3.0, 0.0, false)         | excepție: distanță invalidă           |

---

## 🧪 Acoperire la nivel de instrucțiune

Testele din clasa `TesteInstructiune` acoperă:

- toate instrucțiunile ramificate (`if`, `throw`, calcule, return)
- cazuri normale și excepționale
- aplicarea reducerii și a plafonării costului

✅ Tabel pentru acoperirea instrucțiunilor

| ID  | Instrucțiune verificată                                                       | Tip logică          | Test(e) care o acoperă                           | Comportament testat                                           |
|-----|--------------------------------------------------------------------------------|---------------------|--------------------------------------------------|--------------------------------------------------------------|
| I1  | `if (livrare.greutate <= 0 || livrare.distanta <= 0)`                         | Validare date       | `testGreutateZero()`, `testDistantaZero()`        | Validare date corespunzătoare livrării                       |
| I2  | Calcul cost de bază (`greutate * distanta`)                                    | Inițializare cost   | `testCostLivrare_usor_neprioritar`, `testCalculeazaCostLivrare_Prioritara` | Cost de bază corect calculat                                  |
| I3  | `if (livrare.greutate > 10)`                                                   | Decizie condițională | `testCostLivrare_greu_prioritar_lunga`           | Cost suplimentar pentru greutate mare                         |
| I4  | `if (livrare.distanta > 20)`                                                  | Decizie condițională | `testCalculeazaCostLivrare_GreutateMica_DistantaLunga_FaraFor`, `testCalculeazaCostLivrare_MaxPlafon` | Cost suplimentar pentru distanță mare |
| I5  | Buclă `for` pentru adăugare cost suplimentar distanță mare                     | Buclă               | `testCostLivrare_greu_prioritar_lunga`           | Repetarea suplimentului per 10 km peste 20 km                 |
| I6  | `if (prioritar)`                                                              | Decizie prioritate  | `testCalculeazaCostLivrare_Prioritara`           | Aplicare multiplicator prioritar (ex: *1.2)                    |
| I7  | `else` pentru prioritate                                                       | Ramură alternativă  | `testCalculeazaCostLivrare_NonPrioritara`        | Aplicare reducere 5%                                          |
| I8  | `if (cost > 200)`                                                             | Limită superioară   | `testCalculeazaCostLivrare_MaxPlafon`            | Cost limitat la maxim 200                                     |
| I9  | `return cost;`                                                                | Returnare rezultat  | Toate testele de cost                             | Costul final e returnat                                       |
| I10 | `if (cost < 50)`                                                              | Clasificare ieftină | `testClasificare_Ieftina`, `testClasificaLivrare_Ieftina` | Returnează „Ieftina”                                           |
| I11 | `else if (cost <= 150)`                                                       | Clasificare medie   | `testCalculeazaCostLivrare_NonPrioritara`        | Returnează „Standard”                                         |
| I12 | `else` pentru clasificare                                                     | Clasificare scumpă  | `testCalculeazaCostLivrare_MaxPlafon`            | Returnează „Scumpă”                                          |
| I13 | `if (livrare.greutate < 2 && !livrare.prioritar)`                             | Reducere eligibilă  | `testReducere_eligibil`, `testEsteEligibilaReducere_Adevarat` | Returnează `true`                                             |
| I14 | `else` reducere neeligibilă                                                   | Ramură alternativă  | `testReducere_neeligibil_prioritara`, `testEsteEligibilaReducere_Fals` | Returnează `false`                                            |
| I15 | Formula timp estimat livrare                                                  | Calcul timp         | `testEstimeazaTimp_prioritara`, `testEstimeazaTimp_neprioritara` | Calculează numărul de zile                                   |
| I16 | Timp estimat minim 1 zi (`Math.max(...)`)                                     | Protecție minim     | `testEstimeazaTimp_minim1`, `testEstimeazaTimpLivrare_Minim1` | Returnează minim 1 zi                                         |
| I17 | `return timp;` din estimare timp                                              | Returnare timp      | Toate testele de estimare timp                    | Returnează timpul estimat                                    |


```
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
```


</details>

<details>
<summary> <b>🔀 Acoperire la nivel de decizie (ramuri) </b> </summary>

## 🎯 Ce acoperim aici?

Verificăm că fiecare **ramură** din structura de control (`if`/`else`, `throw`, etc.) este parcursă cel puțin o dată.

## ✅ Ramuri acoperite

1. `if (greutate <= 0 || distanta <= 0)` – se testează atât ramura **true** (excepție), cât și **false** (execuție normală)
2. `if (prioritara)` – ambele ramuri:
   - `true` → costul se dublează
   - `false` → costul rămâne neschimbat
3. `if (greutate < 2 && !prioritara)` – reducere se aplică doar dacă:
   - greutate < 2 (**true**)
   - prioritara == false (**true**)
4. `if (cost > 200)` – testăm atât plafonarea, cât și lipsa ei

## 🧪 Date de test relevante

| Test        | Ramură testată                          |
|-------------|------------------------------------------|
| greutate = 0 | aruncă excepție (`if` true)             |
| prioritara = true | dublează costul (`if (prioritara)`) |
| greutate = 1.5, prioritara = false | aplică reducere  |
| cost > 200  | plafonare la 200                         |

✅ Tabel pentru acoperirea deciziilor (ramuri)

| ID  | Decizie testată                                    | Tip logică         | Clase acoperite             | Exemplu test                 | Rezultat așteptat              |
|-----|----------------------------------------------------|---------------------|------------------------------|------------------------------|-------------------------------|
| D1  | Aruncă excepție dacă greutate sau distanță invalidă| if (g <= 0 || d <= 0)| C₁, C₂                       | testGreutateZero()           | Excepție aruncată             |
| D2  | Cost suplimentar dacă g > 10 și d > 20             | if în `for`         | C₃, C₄                       | testDistantaSiGreutateMare() | Cost crescut                  |
| D3  | Nu intră în supliment dacă d ≤ 20                  | ramură else         | C₆, C₇, C₈                   | testDistantaMaiMicaDe20()    | Cost neschimbat               |
| D4  | Aplică +20% dacă prioritar                         | if (prioritara)     | C₃, C₅, C₇, C₉               | testLivrarePrioritara()      | Cost cu 20% mai mare          |
| D5  | Aplică -5% dacă neprioritar                        | else                | C₄, C₆, C₈, C₁₀              | testLivrareNonPrioritara()   | Cost cu 5% mai mic            |
| D6  | Clasificare „Ieftină”                              | decizie clasificare | —                            | testCostLivrareIeftina()     | Etichetă: Ieftină             |


```
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
```


</details>



<details>
<summary> <b>🔍 Acoperire la nivel de condiție </b> </summary>

## 🧠 Ce este acoperirea pe condiții?

Verificăm că fiecare **condiție individuală** (ex: `greutate <= 0`, `distanta <= 0`, `prioritara == true`) influențează comportamentul codului în ambele direcții (adevărat/fals), indiferent de rezultatul întregii expresii compuse.

## ✅ Condiții testate individual

### 1. `greutate <= 0 || distanta <= 0`
- Test `greutate = 0, distanta = 10` → prima condiție **adevărată**
- Test `greutate = 2, distanta = 0` → a doua condiție **adevărată**
- Test `greutate = 2, distanta = 10` → ambele **false**

### 2. `prioritara`
- Test `prioritara = true` → cost se dublează
- Test `prioritara = false` → cost rămâne nemodificat

### 3. `greutate < 2 && !prioritara`
- Test `greutate = 1.5, prioritara = false` → ambele **adevărate**
- Test `greutate = 3.0, prioritara = false` → prima condiție **falsă**
- Test `greutate = 1.5, prioritara = true` → a doua condiție **falsă**


✅ Tabel pentru acoperirea condițiilor (condiții atomice)

| ID  | Condiție testată        | Tip condiție        | Clasă echivalență | Exemplu test         | Rezultat așteptat                         |
|-----|--------------------------|----------------------|--------------------|----------------------|-------------------------------------------|
| C1  | `greutate <= 0`         | validare/negativă    | G₃                | (0, 10, false)       | Excepție: „Greutatea și distanța…”       |
| C2  | `distanta <= 0`         | validare/negativă    | D₃                | (5, 0, false)        | Excepție: „Greutatea și distanța…”       |
| C3  | `greutate > 10`         | limită pozitivă      | G₁                | (12, 15, false)      | Cost suplimentar                          |
| C4  | `distanta > 20`         | limită pozitivă      | D₁                | (5, 25, false)       | Cost suplimentar                          |
| C5  | `prioritar == true`     | bifurcare            | P₁                | (8, 30, true)        | Cost +20%                                 |
| C6  | `prioritar == false`    | bifurcare            | P₂                | (8, 30, false)       | Cost -5%                                  |


```
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
```

</details>

<details>
<summary> <b>🧬 Raport Testare Mutațională </b> </summary>
Acest raport evidențiază impactul testelor suplimentare asupra eficienței testării mutaționale, concentrându-se pe eliminarea mutanților supraviețuitori generați inițial și analiza celor rămași.

## ⚙️ Configurație

* **Tool utilizat**: [PIT (Pitest)](https://pitest.org/)
* **Clasă testată**: `ServiciuLivrare`
* **Mutatori folosiți**:

  * `ConditionalsBoundaryMutator`
  * `MathMutator`
  * `NegateConditionalsMutator`
  * `PrimitiveReturnsMutator`
  * `BooleanTrueReturnValsMutator`
  * `IncrementsMutator`
  * `EmptyObjectReturnValsMutator`

---

## 📊 Comparație Rezultate - Înainte vs După

|                          | Înainte Teste Suplimentare | După Teste Suplimentare |
| ------------------------ | -------------------------- | ----------------------- |
| Total mutații generate   | 43                         | 43                      |
| Mutații omorâte (killed) | 35                         | 38                      |
| Mutații supraviețuitoare | 8                          | 5                       |
| Putere testare           | 81%                        | **88%**                 |
| Linii acoperite          | 28/28 (100%)               | 28/28 (100%)            |
| Teste rulate             | 222                        | **300**                 |

---

## ❌ Mutanți Rămași în Viață

Toți cei **5 mutanți** supraviețuitori provin din `ConditionalsBoundaryMutator`. Aceștia afectează condiții de margine și nu au fost eliminați de testele existente.

### 📌 Detalii Mutanți

#### 1. `calculeazaCostLivrare`

* **Descriere:** modificare a pragului `greutate <= 5`
* **Test suplimentar:** Adaugă un test pentru `greutate = 5`

#### 2. `calculeazaCostLivrare`

* **Descriere:** modificare a pragului `distanta > 20`
* **Test suplimentar:** Testează `distanta = 20` cu `greutate > 10`

* **Descriere:** modificare a pragului `greutate > 10`
* **Test suplimentar:** Testează `greutate = 10` cu `distanta > 20`

```
@Test
    void testDistantaPeste20GreutateFix10() {
        Livrare livrare = new Livrare(10.0, 30.0, false);
        double cost = serviciu.calculeazaCostLivrare(livrare);
        // nu se adaugă cost suplimentar pentru distanță pentru că greutatea nu e >10
        assertEquals(19.0, cost, 0.01);
    }
```

#### 3. `calculeazaCostLivrare`

* **Descriere:** modificare condiție `kmSuplimentari > 0`
* **Test suplimentar:** Verifică dacă nu se adaugă cost când `kmSuplimentari = 0`

#### 4. `clasificaLivrare`

* **Descriere:** modificare a pragului `cost >= 150`
* **Test suplimentar:** Testează `cost = 150`

```
@Test
    public void testClasificareCostExact150() {
        Livrare livrare = new Livrare(60.0, 20.0, true); // Configurează să obții cost exact 150
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(150.0, cost, 0.01);
        assertEquals("Scumpa", serviciu.clasificaLivrare(livrare));
    }
```

#### 5. `clasificaLivrare`

* **Descriere:** modificare a pragului `cost >= 75`
* **Test suplimentar:** Testează `cost = 75`

```
@Test
    public void testClasificareCostExact75() {
        Livrare livrare = new Livrare(30.0, 10.0, true); // Configurează să obții cost 75
        double cost = serviciu.calculeazaCostLivrare(livrare);
        assertEquals(75.0, cost, 0.01);
        assertEquals("Standard", serviciu.clasificaLivrare(livrare));
    }
```

#### 6. `estimeazaTimpLivrare`

* **Descriere:** modificare condiție `timp < 1`
* **Test suplimentar:** Verifică `timp = 1`

---

## 📈 Efecte Observate

* 🔹 **3 mutanți suplimentari eliminați** (din 8 → 5)
* 🔹 Creștere a **puterii de testare cu 7%**, de la 81% la 88%
* 🔹 Timpul de execuție al testelor a crescut moderat (de la 222 la 300)

---

## 🏁 Concluzie

* Testele adăugate și-au atins scopul: eliminarea mutanților sensibili la condiții logice de margine
* Toți mutanții generați de ceilalți mutatori (alții decât `ConditionalsBoundaryMutator`) au fost omorâți încă din faza inițială
* Focusul rămâne pe rafinarea logicii condiționale și a testelor de margine

</details>

<details> 
  <summary> <b>🔄 Detalii despre circuite independente </b> </summary>

### Graful de flux de control - calculeazaCostLivrare
N1: Start    
N2: Validare greutate și distanță (if (greutate <= 0 || distanta <= 0))   
N3: Aruncare excepție   
N4: Inițializare costBaza = 10.0    
N5: if (greutate > 5)   
N6: costBaza += (greutate - 5) * 2      
N7: if (distanta > 20 && greutate > 10)    
N8: kmSuplimentari = (distanta - 20)/10    
N9: if (kmSuplimentari > 0)    
N10: costBaza += kmSuplimentari * 1.5    
N11: if (prioritar)    
N12: costBaza *= 1.25   
N13: else: costBaza *= 0.95    
N14: Return Math.min(costBaza, 200)   
N15: End     
 
McCabe e - n + 2 = 5
Muchii(e) = 18 
Noduri(n) = 15     

 
## Circuite independente:  
  
C1: 
N1->N2(true)->N3(exceptie input invalid)
C2:
N1->N2(false)->N4 ->N5(false)->N7(false) -> N11(true) ->N12 ->N14 ->N15
C3:
N1 -> N2(false) -> N4 -> N5(true) -> N6 -> N7(false) ->N11(false) -> N13 -> N14 -> N15
C4:
N1 -> N2 -> N4 -> N5(true) -> N6 -> N7 -> N8 -> N9(true) -> N10 -> N11(true) -> N12 -> N14 -> N15
C5:
N1 -> N2 -> N4 -> N5(true) -> N6 -> N7 -> N8 -> N9(false) -> N11(false) -> N13 -> N14 -> N15 
 
  
### Cauze:  
C1: greutate <= 0  
C2: distanță <= 0   
C3: greutate > 5         
C4: distanță > 20   
C5: greutate > 10  
C6: kmSuplimentari > 0  
C7: livrare este prioritară  
  
### Efecte:  
E1: Excepție IllegalArgumentException  
E2: costBaza += (greutate - 5) * 2   
E3: costBaza += kmSuplimentari * 1.5   
E4: costBaza *= 1.25   
E5: costBaza *= 0.95   
E6: return min(cost, 200)  
    

| C1 | C2 | C3 | C4 | C5 | C6 | C7 | E1 | E2 | E3 | E4 | E5 | E6 | Test             |
|----|----|----|----|----|----|----|----|----|----|----|----|----|------------------|
| 1  | 0  | -  | -  | -  | -  | -  | 1  | 0  | 0  | 0  | 0  | 0  | InputInvalid     |
| 0  | 0  | 0  | 0  | 0  | 0  | 1  | 0  | 0  | 0  | 1  | 0  | 1  | PrioritarSimplu  |
| 0  | 0  | 1  | 0  | 0  | 0  | 0  | 0  | 1  | 0  | 0  | 1  | 1  | GreutateMareNonP |
| 0  | 0  | 1  | 1  | 1  | 1  | 1  | 0  | 1  | 1  | 1  | 0  | 1  | ForLoopP         |
| 0  | 0  | 1  | 1  | 1  | 0  | 0  | 0  | 1  | 0  | 0  | 1  | 1  | FaraLoop1Step    |

### Relații:  
C1 ∨ C2 ⇒ E1   
C3 ⇒ E2   
(C4 ∧ C5 ∧ C6) ⇒ E3   
C7 ⇒ E4   
¬C7 ⇒ E5    
→ E6 întotdeauna   
![tss](https://github.com/user-attachments/assets/a9e6aa36-139e-4d39-80fc-992651556e61)


</details>

<details> 
  <summary> <b>🤖 Raport AI </b> </summary>

## 🎯 Scopul activității

Scopul acestei activități a fost validarea automată a funcționalității aplicației `TSS-DeliveryServices` prin teste unitare dezvoltate în Java. Testele au fost scrise atât manual, de către echipa noastră, cât și cu sprijinul unui model AI (ChatGPT), pentru a obține o acoperire cât mai extinsă a codului și comportamentului aplicației.

---

## 📐 Clase de echivalență

🔎 Prompt exemplu: *"Cum am putea împărți metoda `calculeazaCostLivrare` în clase de echivalență?"*

### 🔍 Metoda: `calculeazaCostLivrare`

| Clasa | Greutate | Distanță | Prioritar | Observații                            |
| ----- | -------- | -------- | --------- | ------------------------------------- |
| CE1   | ≤ 0      | orice    | orice     | Greutate invalidă                     |
| CE2   | orice    | ≤ 0      | orice     | Distanță invalidă                     |
| CE3   | ≤ 5      | ≤ 20     | false     | Cost de bază × 0.95                   |
| CE4   | ≤ 5      | ≤ 20     | true      | Cost de bază × 1.25                   |
| CE5   | > 5      | ≤ 20     | false     | Cost suplimentar greutate             |
| CE6   | > 5      | ≤ 20     | true      | Cost suplimentar greutate + prioritar |
| CE7   | > 10     | > 20     | false     | Cost suplimentar distanță + greutate  |
| CE8   | > 10     | > 20     | true      | Cost complet + prioritar              |
| CE9   | orice    | orice    | orice     | Cost plafonat la 200                  |

---

## ✅ Exemple de teste JUnit pe clase de echivalență

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiciuLivrareEquivalenceTest {
    private final ServiciuLivrare serviciu = new ServiciuLivrare();

    // CE1 - Greutate invalidă
    @Test
    void testGreutateInvalida() {
        Livrare l = new Livrare(0, 10, false);
        assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(l));
    }

    // CE3 - Greutate ≤ 5, Distanță ≤ 20, Non-prioritar
    @Test
    void testCostBazaNonPrioritar() {
        Livrare l = new Livrare(3, 10, false);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertEquals(9.5, cost, 0.001);
    }

    // CE4 - Cost de bază + prioritar
    @Test
    void testCostBazaPrioritar() {
        Livrare l = new Livrare(4, 15, true);
        double cost = serviciu.calculeazaCostLivrare(l);
        assertEquals(12.5, cost, 0.001);
    }

    // CE7 - Cost cu distanță suplimentară și greutate
    @Test
    void testCostCombinat() {
        Livrare l = new Livrare(12, 40, false);
        double cost = serviciu.calculeazaCostLivrare(l);
        // 10 + (12-5)*2 + 3*1.5 = 10 + 14 + 4.5 = 28.5 * 0.95 = 27.075
        assertEquals(27.075, cost, 0.001);
    }
}
```

📌 Comparând exemplele oferite de ChatGPT cu clasele identificate de echipă, am reușit să implementăm o gamă variată de teste care acoperă complet spațiul de echivalență al metodei `calculeazaCostLivrare`.

---

## 🧪 Teste de condiție și decizie

🔎 Prompt exemplu: *"Poți să îmi dai niște exemple de teste de condiție/decizie?"*

### 🔍 1. Acoperire pe condiții (Condition Coverage)

Se testează fiecare sub-condiție dintr-un `if` logic:

```java
if (greutate <= 0 || distanta <= 0)
```

Trebuie să verificăm:

* greutate ≤ 0 și distanță > 0 → true
* greutate > 0 și distanță ≤ 0 → true
* greutate > 0 și distanță > 0 → false

```java
@Test
void testGreutateInvalida() {
    Livrare livrare = new Livrare(0, 10, false);
    assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(livrare));
}

@Test
void testDistantaInvalida() {
    Livrare livrare = new Livrare(2, 0, true);
    assertThrows(IllegalArgumentException.class, () -> serviciu.calculeazaCostLivrare(livrare));
}

@Test
void testGreutateSiDistantaValide() {
    Livrare livrare = new Livrare(2, 5, false);
    double cost = serviciu.calculeazaCostLivrare(livrare);
    assertTrue(cost > 0);
}
```

### 🔍 2. Acoperire pe decizii (Decision Coverage)

Se asigură că fiecare `if` e evaluat atât pe ramura true cât și pe ramura false.

#### Exemplu: `if (distanta > 20 && greutate > 10)`

* distanță ≤ 20 → fals
* distanță > 20, greutate ≤ 10 → fals
* distanță > 20, greutate > 10 → adevărat

```java
@Test
void testDistantaSub20GreutateSub10() {
    Livrare livrare = new Livrare(5, 15, false);
    double cost = serviciu.calculeazaCostLivrare(livrare);
    assertTrue(cost < 20);
}

@Test
void testDistantaPeste20GreutateSub10() {
    Livrare livrare = new Livrare(8, 30, false);
    double cost = serviciu.calculeazaCostLivrare(livrare);
    assertTrue(cost < 25);
}

@Test
void testDistantaPeste20GreutatePeste10() {
    Livrare livrare = new Livrare(12, 45, false);
    double cost = serviciu.calculeazaCostLivrare(livrare);
    assertEquals(25.65, cost, 0.01);
}
```

📌 La fel ca în cazul claselor de echivalență, am combinat exemplele generate de AI cu scenarii gândite de echipă, pentru a obține o acoperire completă.

---

## 📊 Concluzii și beneficii

✔️ Combinarea testării manuale cu generarea automată prin ChatGPT a condus la:

* ✅ O acoperire structurală completă a codului: instrucțiune, decizie, condiție, circuite independente;
* ✅ Detectarea unor posibile cazuri-limită;
* ✅ Creșterea calității generale a codului și a robusteții aplicației;

📌 Observăm că modelul AI oferă o excelentă bază teoretică și propuneri rapide, dar are nevoie de completări umane pentru a atinge exhaustivitatea testării.

> Acest raport oferă o imagine de ansamblu asupra procesului de testare, subliniind avantajele colaborării om-AI în dezvoltarea de software de calitate.
