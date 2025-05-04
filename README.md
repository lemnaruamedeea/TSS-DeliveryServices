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
