# 🚚 TSS-DeliveryServices

TSS-DeliveryServices este o aplicație Java dezvoltată în contextul disciplinei Testarea Sistemelor Software. Aceasta simulează un sistem de livrări prin modelarea și evaluarea caracteristicilor unei livrări, precum costul, timpul estimat și eligibilitatea pentru reduceri. Scopul proiectului este de a oferi un model simplu al unui serviciu real, iar pe de altă parte, servește ca punct de plecare pentru aplicarea diverselor tehnici de testare unitară, testare structurală și testare funcțională.

<details>
  <summary> <b>📋 Enunțul și clasele de echivalență</summary></b> 

Se testează un program care gestionează livrări pe baza unor caracteristici introduse de utilizator.  
Mai precis, pentru o livrare, utilizatorul introduce:

- o valoare reală pozitivă pentru greutatea coletului (în kilograme),
- o valoare reală pozitivă pentru distanța până la destinație (în kilometri),
- un răspuns binar pentru caracterul prioritar al livrării: da pentru prioritar sau nu pentru non-prioritar.

Programul calculează:

- Costul livrării, ținând cont de greutate, distanță și prioritate, cu un plafon maxim aplicat;
- Clasificarea costului în: „Ieftină”, „Standard” sau „Scumpă”;
- Eligibilitatea pentru o reducere, disponibilă doar pentru livrările ușoare și neprioritare;
- Timpul estimat de livrare, exprimat în ore, influențat de distanță și prioritate.

---

### 1. Domeniul de intrări:

- **g** – greutatea (real pozitiv)  
- **d** – distanța (real pozitiv)  
- **p** – prioritate (da / nu)  

**Clase de echivalență:**

| Parametru | Clasa validă | Clasa invalidă |
|----------|---------------|----------------|
| g        | G₁ = { g > 0 } | G₂ = { g ≤ 0 } |
| d        | D₁ = { d > 0 } | D₂ = { d ≤ 0 } |
| p        | P₁ = { da }   | P₂ = { nu }    |

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
| C5  | 7        | 15       | true      | 20                                   | 
| C6  | 7        | 15       | false     | 15.2				       | 
| C7  | 12       | 45       | true      | 36.25                                | 
| C8  | 13       | 45       | false     | 29.45                                | 
| C9  | 12       | 15       | true      | 32.5                                 | 
| C10 | 12       | 15       | false     | 24.7                                 | 

---

### Testele pentru `clasificaLivrare`
| Nr. | Greutate | Distanță | Prioritar | Cost estimat     | Clasificare așteptată |
| --- | -------- | -------- | --------- | ---------------- | --------------------- |
| C11 | 2        | 10       | false     | 9.5              | Ieftina               |
| C12 | 50       | 100      | false     | ≈ 104.5          | Standard              |
| C13 | 100      | 200      | true      | > 200 (plafonat) | Scumpa                |

---

### Testele pentru `esteEligibilaReducere`
| Nr. | Greutate | Distanță | Prioritar | Așteptat |
| --- | -------- | -------- | --------- | -------- |
| C14 | 1.5      | 10       | false     | true     |
| C15 | 1.5      | 10       | true      | false    |
| C16 | 2.5      | 10       | false     | false    |
| C17 | 5        | 10       | true      | false    |

---

### Testele pentru `estimeazaTimpLivrare`
| Nr. | Greutate | Distanță | Prioritar | Așteptat |
| --- | -------- | -------- | --------- | -------- |
| C18 | 2        | 5        | false     | 1        |
| C19 | 2        | 5        | true      | 1        |
| C20 | 2        | 25       | false     | 3        |
| C21 | 2        | 25       | true      | 2        |
| C22 | 2        | 0.1      | false     | 1        |

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

---

### 🏷️ Teste pentru `clasificaLivrare`

|  Nr. test                                | Scop                              | g     | d   | p     | Clasificare Așteptată     |
|--------------------------------|-----------------------------------|-------|-----|-------|----------------------------|
| 1      | Cost scăzut                       | 1     | 5   | false | Ieftina                   |
| 2 | Cost spre 75                  | 10    | 40  | false | Ieftina                   |
| 3| Cost foarte mare                  | 100   | 200 | true  | Scumpa                    |

---

### 🎯 Teste pentru `esteEligibilaReducere`

|  Nr. test                                 | g     | p     | Așteptat |
|----------------------------------|-------|-------|----------|
| 1 | 1.99  | false | true     |
| 2     | 1.99  | true  | false    |
| 3   | 2.0   | false | false    |

---

### ⏱️ Teste pentru `estimeazaTimpLivrare`

|  Nr. test                             | d     | p     | Așteptat |
|------------------------------|-------|-------|----------|
| 1   | 9.9   | false | 1        |
| 2   | 10    | false | 2        |
| 3| 10    | true  | 1        |
| 4  | 0.5   | true  | 1        |

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

#### 1. `calculeazaCostLivrare` - linia 17

* **Descriere:** modificare a pragului `greutate <= 5`
* **Test suplimentar:** Adaugă un test pentru `greutate = 5`

#### 2. `calculeazaCostLivrare` - linia 21

* **Descriere:** modificare a pragului `distanta > 20`
* **Test suplimentar:** Testează `distanta = 20` cu `greutate > 10`

* **Descriere:** modificare a pragului `greutate > 10`
* **Test suplimentar:** Testează `greutate = 10` cu `distanta > 20`

#### 3. `calculeazaCostLivrare` - linia 23

* **Descriere:** modificare condiție `kmSuplimentari > 0`
* **Test suplimentar:** Verifică dacă nu se adaugă cost când `kmSuplimentari = 0`

#### 4. `clasificaLivrare` - linia 40

* **Descriere:** modificare a pragului `cost >= 150`
* **Test suplimentar:** Testează `cost = 150`

#### 5. `clasificaLivrare` - linia 41

* **Descriere:** modificare a pragului `cost >= 75`
* **Test suplimentar:** Testează `cost = 75`

* **Descriere:** modificare a pragului `cost < 150`

#### 6. `estimeazaTimpLivrare` - linia 57

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
  <summary> <b>🔄Detalii despre circuite independente </b> </summary>

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
 
e =muchii = 16 
n =noduri= 15     => V(G) = 16 - 15 + 2 = 3  
 
## Circuite independente:  
  
N1 → N2(false) → N4 → N5(false) → N7(false) → N11(true) → N12 → N14  
N1 → N2(false) → N4 → N5(true) → N6 → N7(true) → N8 → N9(true) → N10 → N11(false) → N13 → N14   
N1 → N2(true) → N3    
 
  
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
    
| C1 | C2 | C3 | C4 | C5 | C6 | C7 | E1 | E2 | E3 | E4 | E5 | E6 | 
| -- | -- | -- | -- | -- | -- | -- | -- | -- | -- | -- | -- | -- |   
| 1  | 0  | -  | -  | -  | -  | -  | 1  | 0  | 0  | 0  | 0  | 0  |    
| 0  | 1  | -  | -  | -  | -  | -  | 1  | 0  | 0  | 0  | 0  | 0  |  
| 0  | 0  | 0  | 0  | 0  | 0  | 1  | 0  | 0  | 0  | 1  | 0  | 1  |  
| 0  | 0  | 1  | 1  | 1  | 1  | 0  | 0  | 1  | 1  | 0  | 1  | 1  |  
  
### Relații:  
C1 ∨ C2 ⇒ E1   
C3 ⇒ E2   
(C4 ∧ C5 ∧ C6) ⇒ E3   
C7 ⇒ E4   
¬C7 ⇒ E5    
→ E6 întotdeauna   
![tss](https://github.com/user-attachments/assets/ad8d1219-d362-4681-8fe3-240c26ddb8be)


</details>

