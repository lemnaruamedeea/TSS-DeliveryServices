# TSS-DeliveryServices

## Clasa Livrare

Clasa Livrare reprezintă o livrare pe care o trimitem printr-un serviciu de transport, având caracteristici esențiale cum ar fi:
* Greutatea coletului
* Distanța pe care o parcurge livrarea
* Prioritatea livrării (dacă e prioritară sau nu)

Aceste trei proprietăți formează baza pentru calculul costului de livrare, timpul de livrare și alte informații importante.

1. Atributele clasei:
* greutateKg (tip double): Reprezintă greutatea coletului în kilograme.
* Regula este că dacă greutatea e mai mare de 5 kg, se adaugă costuri suplimentare.

* distantaKm (tip double): Reprezintă distanța, în kilometri, pe care trebuie să o parcurgă livrarea.
* Distanța mai mare de 20 km influențează costul, iar pentru anumite greutăți, se adaugă costuri suplimentare pentru distanțele mari.

* prioritar (tip boolean): Reprezintă dacă livrarea este prioritară sau nu.
* Dacă livrarea este prioritară, costul va fi multiplicat cu un factor de 1.25. Dacă nu este prioritară, va fi aplicat un factor de reducere (0.95).

2. Construcție:
Constructor: Permite crearea unui obiect Livrare cu greutate, distanță și prioritate specificate.
public Livrare(double greutateKg, double distantaKm, boolean prioritar)


3. Metodele clasei Livrare:
* getGreutateKg(): Returnează greutatea coletului.
* getDistantaKm(): Returnează distanța livrării.
* estePrioritara(): Verifică dacă livrarea este prioritară.







Mai jos sunt prezentate definițiile esențiale pentru modul în care aplicația gestionează procesul de livrare.

---

## 1. Costul de livrare
**Definiție:**  
Costul total pe care îl plătește clientul pentru livrarea unui colet, care include transportul, manipularea, asigurările, taxele suplimentare și eventualele costuri adiționale în funcție de greutate, distanță și prioritatea livrării.

**Componente:**
- **Cost de bază:** Cost fix pentru orice livrare.
- **Costuri suplimentare:** În funcție de greutatea coletului, distanța (pentru livrări peste un anumit număr de kilometri) sau prioritatea livrării.

---

## 2. Greutatea coletului
**Definiție:**  
Greutatea coletului este masa totală a acestuia și influențează direct costul de livrare. De obicei, costurile suplimentare sunt aplicate atunci când greutatea depășește o anumită limită prestabilită (de exemplu, 5 kg).

---

## 3. Distanța de livrare
**Definiție:**  
Distanța reprezintă distanța parcursă de colet de la punctul de plecare până la destinație.

**Importanță:**  
Distanța influențează direct costul livrării, de obicei prin taxe suplimentare pentru livrările ce depășesc o anumită distanță.  
În cazul distanțelor lungi, se pot aplica taxe adiționale pentru fiecare kilometru suplimentar.

---

## 4. Prioritatea livrării
**Definiție:**  
Prioritatea livrării se referă la un serviciu rapid și eficient, în care livrarea este tratată ca o urgență, adică trebuie să ajungă mai repede decât o livrare standard.

**Cum influențează costul:**  
- Livrările prioritare au un cost mai mare, de obicei, aplicându-se un factor de multiplicare a costului (ex: 1.25).
- Livrările **non-prioritare** sunt mai ieftine și au un factor de reducere (ex: 0.95).

---

## 5. Plafonul de cost
**Definiție:**  
Plafonul de cost este limita superioară pentru costul unei livrări, indiferent de greutate sau distanță. Dacă costul calculat depășește această limită, costul va fi ajustat astfel încât să nu depășească plafonul.

**Exemplu:**  
Dacă costul calculat pentru o livrare este 250 de lei, iar plafonul este 200 de lei, clientul va plăti **200 de lei**.

---

## 6. Estimare timp de livrare
**Definiție:**  
Timpul estimat pentru livrare este calculat pe baza distanței de livrare și a priorității acesteia. Livrările prioritare au timpi mai mici față de livrările standard.

**Cum se calculează:**  
- Timpul este estimat în funcție de distanța parcursă (de ex: 1 oră pentru fiecare 10 km).
- Pentru livrările prioritare, timpul de livrare poate fi redus cu 1 oră.

---

## 7. Reducere pentru livrările mici
**Definiție:**  
Livrările care sunt foarte mici (de ex: sub 2 kg) și care nu sunt prioritare pot beneficia de o reducere a costului de livrare.

**Cum funcționează:**  
Reducerea se aplică doar pentru livrările care îndeplinesc aceste condiții și poate scădea semnificativ costul final al livrării.

---


## 8. Servicii de livrare externe
**Definiție:**  
Se referă la serviciile externe de livrare utilizate de aplicație pentru a transporta produsele, cum ar fi **DHL**, **FedEx**, **UPS**, sau servicii locale (ex: **Posta Română** sau platformele de livrare rapidă precum **Glovo** sau **Uber Eats**).

**Importanță:**  
Aceste servicii pot avea tarife, timpi de livrare și condiții diferite, care trebuie integrate în aplicație pentru a oferi opțiuni optimizate utilizatorilor.

---

## 9. API-uri de transport
**Definiție:**  
Un **API (Application Programming Interface)** de transport este un set de instrumente care permite aplicației să acceseze datele și funcțiile externe pentru calcularea rutelor, distanțelor și timpurilor de livrare.

**Exemplu:**  
- **Google Maps API** și **OpenStreetMap API** pot fi utilizate pentru a calcula cele mai bune rute de livrare.

---




