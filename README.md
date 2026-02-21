# Pizzeria-webAPI - Handleiding

<br>

## 1. Inleiding

Deze installatiehandleiding is een onderdeel van onze eindopdracht die ik moet maken voor onze opleiding NOVI backend. In onze eindopdracht moet ik een applicatie ontwikkelen die een pizzeria (afhaal-bezorg)restaurant kan gebruiken om een order op te kunnen nemen en te kunnen verwerken. Deze applicatie kan ook worden gebruikt om voor een burger  of Chinese (afhaal-bezorg)restaurant. Dus het is niet specifiek bedoeld voor alleen pizza restaurants. Maar voor een concretere implementatie heb ik gekozen voor een pizzeria (afhaal-bezorg)restaurant. Het voordeel van een webapplicatie is dat het heel makkelijk is te benaderen via de web. De klant kan zelf inloggen op de website en zijn/haar bestelling/order bekijken. De klant hoeft dan het restaurant niet te bellen om het aan een medewerker te gaan vragen. De medewerkers hebben ook veel profijt van de applicatie. De medewerkers hoeven dan de orders niet meer op te schrijven op blocnotes of papier, zij kunnen de order uitprinten en doorgeven aan de keuken.  

De applicatie heeft de meeste standaard functionaliteiten zoals inloggen, order opvragen en orders aanpassen en uitprinten en facturatie. Later kan de applicatie worden uitgebreid naar meerdere functionaliteiten, zoals track en trace van bezorgstatus enz. Dus de applicatie heeft vele groeimogelijkheden in de toekomst, zodat de applicatie wordt uitgebreid. 
<br><br>

## 2. Handleiding

Dit document bevat uitleg over de Pizzaria-WebApi en het gebruik van de endpoints. Om de applicatie (API) te gebruiken moet je toegang hebben via de endpoints van de applicatie. Er wordt in de handleiding uitleg gegeven hoe je project kan opstarten d.m.v. installaties van de programma’s. De vereiste programma’s zijn IntelliJ, JDK 17, Postman en Postgresql. Er moet ook bepaalde dependencies worden geïnstalleerd in IntelliJ om de applicatie te kunnen runnen. 

Daarnaast wordt er ook uitleg gegeven hoe je de authenticatie kan toepassen voor de endpoints van de gebruikers: EMPLOYEE, CUSTOMER en ADMIN. De wachtwoorden en inlog zijn al gereed in Postman-requests. Het is belangrijk om de hoofstukken: benodigdheden, PostGreSQL configuratie en lijst REST endpoints te lezen om de applicatie te kunnen gebruiken. 

De endpoints staan in het overzicht van hoofdstuk 10 in tabellen opgesomd. De installatie wordt stap voor stap uitgelegd. De installatie is geschreven voor en uitgevoerd op het besturingssysteem van MacOs. Er kunnen andere programma’s worden gebruikt, maar het wordt niet besproken in deze handleiding.
<br><br>

## 3. Benodigdheden

### Gebruik alleen localhost 

De code voor deze applicatie is geen server toegewezen waar de applicatie en database zijn opgeslagen. De applicatie werkt op de computer en draait op de localhost/8080. Deze localhost wordt gebruikt voor de gehele handleiding.  

### Benodigde programma’s 

De benodigde programma’s zijn IntelliJ, JDK 17, Postman en PostgreSQL. Deze zijn vereist en moet geïnstalleerd worden op de computer. 

### Database configuratie

Stap 1 

Open het project pizzeria-webAPI in IntelliJJ. In FileStorageService.java hebben wij de waarde van de filepath genoemd als environment variabele : ${STORAGE_PATH}.

<img src="src/assets/Screenshot -DB-conf-stap1.png" width="650" alt="db configuration screenshot 1">

Stap 2 

Geef de database de naam “pizzeria-db” en de owner “postgres” en klik op “save”. 

<img src="src/assets/Screenshot -DB-conf-stap2.png" width="650" alt="db configuration screenshot 2">

Stap 3 

Open IntelliJ en navigeer naar de “application.properties” file in de code van de applicatie en stel volgende waarden in voor de datasource: 

spring.sql.init.platform=postgres 

spring.datasource.url=jdbc:postgresql://localhost:5432/pizzeria-db 

spring.datasource.username=postgres 

spring.datasource.password="eigen ingestelde password” 

spring.datasource.driver-class-name=org.postgresql.Driver 

<img src="src/assets/Screenshot -DB-conf-stap3.png" width="650" alt="db configuration screenshot 3">

De eigen ingestelde password van spring.datasource.password is hetzelfde password die je hebt opgegeven bij het installeren van PostgreSQL. Deze is wel belangrijk als je toegang wilt hebben tot de database.  

Stap 4 

Als je de applicatie opnieuw runt zouden er in de database de tabellen verschijnen als je in pgadmin gaat bekijken zie je de 10 tabellen: customers, employees, invoices, items, order_details, orders, profiles, roles, users en user_roles, door de database “pizzeria-db” te selecteren klapt het menu open, selecteer je “schema’s>public” en daarna klapmenu “tables”. Indien die tabellen niet beschikbaar zijn selecteer de database en klik op rechtermuisknop en kies ”refresh”. Als zij nog steeds niet verschijnen kun je in stap 3 kijken of de juiste gegevens zijn ingevuld bij de application.properties. 

### Pad instellen voor upload en download van files 

Stap 1 

Open het project pizzeria-webAPI in IntelliJJ. In FileStorageService.java hebben wij de waarde van de filepath genoemd als environment variabele : ${STORAGE_PATH}.  

<img src="src/assets/Screenshot -Pad-instellen-stap1.png" width="650" alt="pad instellen screenshot 1">

Stap 2 

Deze moeten wij ook een waarde geven in IntelliJ voor een environment variabel. Om dit in te stellen ga je naar “edit configuration” onder “PizzeriaWebApiApplication”. 

<img src="src/assets/Screenshot -pad-instellen-stap2.png" width="650" alt="pad instellen screenshot 2">

Stap 3 

Dan klik je op “Modify options”. 

<img src="src/assets/Screenshot -pad-instellen-stap3.png" width="650" alt="pad instellen screenshot 3">

Stap 4 

Dan vink je “environment variables” aan. En je komt dan in het edit veld van de environment variabelen. 

<img src="src/assets/Screenshot -pad-instellen-stap4.png" width="650" alt="pad instellen screenshot 4">

Stap 5 

Daar geef je de waarden van de environment variabelen op. Vervolgens druk je op “ok” om de aanpassing door te voeren.  

<img src="src/assets/Screenshot -pad-instellen-stap5.png" width="650" alt="pad instellen screenshot 5">

In mijn voorbeeld heb ik de environment variabele STORAGE_PATH ingesteld op /Users/storage. De folder storage heb ik zelf aangemaakt in Finder van mijn Macbook onder het mapje Users. Deze waarde kun je kopiëren door op de folder te gaan staan in de Finder onderaan en op rechtermuisknop te drukken. Dan selecteer je “Copy “storage” as Pathname”. Als je command-v doet, dan zul je de waarde “/Users/storage” krijgen. Deze vul je dan in bij de environment variabele als een waarde voor ”STORAGE_PATH”. Deze pad heb je nodig als je een bestand wilt uploaden of downloaden via PizzeriaWebApiApplication. 
<br><br>

## 4. Gebruikersrollen en gegevens

In de inleiding hebben wij over de verschillende gebruikers van de applicatie namelijk employee, customer en admin. Employee zijn de medewerkers van het pizzeria restaurant, customer zijn de klanten die een bestelling willen plaatsen en admin is een manager van het restaurant die meeste bevoegdheden heeft om toegang te krijgen tot alle functionaliteiten van de applicatie.  
Zie hieronder de tabel. 

|Rol | Toelichting|
|----|------------|
|ROLE_EMPLOYEE|De medewerker heeft toegang tot de meeste functionaliteiten van de applicatie, zoals orders aanmaken en orders aanpassen. Daarnaast kan zij/hij factuur aanmaken en uitprinten. Andere bevoegdheden zijn beheren van klantgegevens en items (gerechten).|
|ROLE_CUSTOMER|De klant heeft zeer beperkte toegang tot de endpoint. Zij kunnen alleen hun eigen orders bekijken en niet aanpassen of verwijderen. Verder kan zij/hij de items alleen bekijken die aanwezig zijn in het systeem.|
|ROLE_ADMIN|De manager (hier admin) heeft de meeste bevoegdheden. Met zijn/haar inloggegevens kan zij/hij toegang hebben tot alle endpoints.|

### Inloggegevens  

Om toegang te hebben tot de endpoints hebben de gebruikers meestal inloggegevens nodig om in te loggen. De inloggegevens zijn voor de gebruikers: 

|Gebruikersnaam | Wachtwoord | Rol|
|---------------|------------|----|
|lorens | lorens | CUSTOMER |
|lorens1 | lorens1 | ADMIN |
|lorens2 | lorens2 | EMPLOYEE |

Deze gebruikers zijn al beschikbaar in de database met desbetreffende wachtwoord. Je kan zij gewoon gebruiken. Het is dan niet nodig om een nieuwe gebruiker aan te maken. Om de endpoint voor het aanmaken een nieuwe gebruiker te testen, kun je gewoon een nieuwe gebruiker aanmaken. 
<br><br>

## 5. Lijst van endpoints

Hier bespreek ik de endpoints die ik heb gemaakt voor de applicatie. De mapping van de endpoint wordt weergegeven en hoe de endpoints API request moet worden opgesteld.  

In de tabellen wordt er per controller de informatie weergegeven: 

1. Soort API request 

2. De mapping (URI) 

3. Toelichting van de endpoint 

In de collectie van Postman-request vind je ingevulde requests in mappen om direct gebruik te maken van de endpoints onder “pizzeria-webapi”. In de ingevulde requests zijn ook de body ingevuld die je kan gebruiken. Dus je hoeft zelf niet de body’s in json te verzinnen zelf. Als je de requests uitvoert, dan weet je al welke attributen je kan gebruiken om de endpoints te benaderen.  
<br><br>

## 6. Authenticatie en autorisatie

### Jwt token in postman verkrijgen 

Hieronder wordt er een stappenplan weergegeven voor het verkrijgen van de JWT token in Postman. Zodat je deze token kan gebruiken als een variabele in verschillende requests die een authenticatie vereisen.  

Stap1 

Maak eerst een nieuwe gebruiker aan door gebruik te maken van de aangeleverde postman collectie “Collectie Post Requests”. Kies daarin de endpoint-request auth>POST users. In de JSON body kun je invullen bijvoorbeeld zie hieronder. 

<img src="src/assets/Screenshot -auth-stap1.png" width="650" alt="authenticatie screenshot 1">

Als je op “send” drukt krijgt je een 201 created. Dan is een nieuwe user aangemaakt. Let op dat je altijd “roles” attribuut moet invullen. De keuzes zijn: ADMIN, EMPLOYEE of CUSTOMER. In de database zijn er al een user aangemaakt voor een ADMIN, EMPLOYEE en CUSTOMER. Die gebruikers kun je ook gebruiken dan hoef je deze stap niet te doen. Zie hoofdstuk 4 voor de inloggegevens. 

Stap 2 

Ga vervolgens naar de postman collectie localhost:8080/auth>POST auth. Vul dan in de body de credentials van de gebruiker in om in te loggen. Je kan ook de inloggegevens van hoofdstuk 4 gebruiken voor een bestaande gebruiker, bijvoorbeeld {“username”: “lorens1”, “password”:”lorens1”} voor een ADMIN gebruiker.  

<img src="src/assets/Screenshot -auth-stap2.png" width="650" alt="authenticatie screenshot 2">

Daarna druk je op de send-knop. Vervolgens krijg je een 200 ok melding terug. Onderste scherm zie je aan de linkerkant boven “body” tabblad staan. Als je daarop klikt, kun je ook een tabblad “headers” selecteren. Als je daarop klikt kom je in de headers section terecht. Rechts van de key Authorization zie je Bearer. ........ staan met string van letters en getallen staan. Dat is de bearer token of JWT token. Als je de string selecteert zonder het woord bearer, dan heb je de token. Deze kopieer je met command-c.  

Stap 3 

Rechtsboven de postman scherm zie je no environment tabblad staan. Daar klik je erop om een environment variabele te maken waarin je de token kan opslaan. Rechts van de tabblad van no environment staat een plus-teken. Als je daarop klikt, kun je een nieuwe environment variabele aanmaken.

<img src="src/assets/Screenshot -auth-stap3.png" width="650" alt="authenticatie screenshot 3.0">

Je geeft een de environment een naam bijvoorbeeld “collectie wachtwoord”.  

<img src="src/assets/Screenshot -auth-stap3-2.png" width="650" alt="authenticatie screenshot 3.2">

Daarna geef je de naam van de variabele op bijvoorbeeld: “wachtwoord”. En achter die veld onder de label “value” plak je de jwt token. Hierdoor wordt de jwt token opgeslagen in de variabele “wachtwoord”. 

Stap 4 

Ga naar een endpoint die beveiligd is met jwt token bijvoorbeeld end point voor getAllCustomers, GET localhost:8080/customers. Je gaat naar de tabblad Auth in postman. Je selecteert bij AuthType “Bearer Token”. Rechts van Token in het invulveld vul je in “{{wachtwoord}}”. Hierdoor wordt de waarde gebruikt van de variabele “wachtwoord” die opgeslagen is in de environment variabele van de environment “wachtwoord collectie”. Hierdoor hoef je niet steeds de jwt token te copy pasten als je een endpoint wilt benaderen met autorisatie beveiliging. 

<img src="src/assets/Screenshot -auth-stap4.png" width="650" alt="authenticatie screenshot 4">
<br><br>

## 7. File upload en download gerbuiksaanwijzing

In de eindopdracht wordt er gesteld dat er ook een functionaliteit moet komen voor het uploaden en downloaden van bestanden via de fileserver. Hier wordt er een stappenplan gegeven hoe je de functionaliteit kan gebruiken. Nb.: de upload en download file is een maximum ingesteld van een bestandsgrootte van max. 2gb. 

Voordat je de functionaliteit wilt gebruiken, moet je eerst de juiste pad instellen voor de file storageserver. Deze als het goed is al gedaan in hoofdstuk 3 bij het onderdeel met environment variabele: Pad instellen voor upload en download van files. 

### Uploaden van bestanden 

Stap 1 

Eerst moet je inloggen met de credentials, omdat de endpoint beveiligd is met autorisatie. Daarvoor heb je dus jwt token nodig. Als het goed is heb je de jwt token al opgeslagen in een environment variabele in hoofdstuk 6 “wachtwoord”. Ga naar de collectie postman filemanager>POST localhost:8080/upload-file. En ga naar tabblad “ Body”. Typ in onder het Key veld “file” voor de type body. In het scherm van postman selecteer “file” en niet “text”. Als je op select files klikt, dan klapt een menu open. Daar klik je op “New file from local machine” met het Plus teken. 

<img src="src/assets/Screenshot -upload-stap1.png" width="650" alt="upload screenshot 1">

Stap 2 

Door een bestand te selecteren in het venster wordt de “value” veld gevuld met de bestandsnaam. 

<img src="src/assets/Screenshot -upload-stap2.png" width="650" alt="upload screenshot 2">

Stap 3 

Vervolgens ga je naar de tabblad Authorization en typ je de variabele naam voor de JWT token in rechts van de label Token: “{{wachtwoord}}”. Tenslotte druk je op send om de post-request te initialiseren, zodat de file kan worden geüpload naar een specifieke opgegeven directory. In ons voorbeeld is de directory “/Users/storage”. 

### Downloaden van bestanden 

Stap 1 

Ga naar de collectie postman filemanager>GET localhost:8080/download file. En ga naar tab-blad Params, type in onder the key de parameternaam “file”. Zie afbeelding.

<img src="src/assets/Screenshot -download-stap1.png" width="650" alt="download screenshot 1">

Stap 2 

Zoek op de file naam die je wilt downloaden van de pad die je op hebt gegeven in de variabele “STORAGE_DIRECTORY” in ons voorbeeld is de pad “/Users/storage”. Als je die file hebt gevonden kopieer de file naam bijvoorbeeld “pgadmin4-8.14-x86_64.dmg” en plak je onder de “Value” veld.  

<img src="src/assets/Screenshot -download-stap2.png" width="650" alt="download screenshot 2">

Stap 3 

Deze endpoint is ook beveiligd met autorisatie. Ga naar tabblad Authorization. Selecteer bij Auth Type “Bearer Token” en typ in het veld naast de label Token “{{wachtwoord}}” om de waarde van de jwt token op te halen die is opgeslagen in de environment variabele.  

<img src="src/assets/Screenshot -download-stap3.png" width="650" alt="download screenshot 3">

Stap 4  

Vervolgens moet je bij endpoint download-file niet op de send-knop drukken, maar op het pijltje rechts van de send-knop. Dan zie je de optie “Send and Download” verschijnen. Daarop moet je klikken om de download te starten. 

<img src="src/assets/Screenshot -download-stap4.png" width="650" alt="download screenshot 4">

Stap 5  

Vervolgens krijg je een venster om de pad te selecteren waar je de gedownload file wilt gaan opslaan. Als je selectie hebt gedaan, dan is de download klaar. 


<img src="src/assets/Screenshot -download-stap5.png" width="650" alt="download screenshot 5">
<br><br>

## 8. Het testen van endpoint: /orders/auth-customer/{username}

Ik wil hier het testen van deze endpoint uitwerken. Want deze endpoint is wat ingewikkelder om te testen. Hier zijn de stappen dat je moet doorlopen om deze endpoint goed te kunnen testen.  

1. Je kan de bestaande gebruikers gebruiken om in te loggen voor authenticatie. Log in als een bestaande CUSTOMER. Je haalt de jwt token op en die vul je in environment variabele bijvoorbeeld {{wachtwoord1}} met customer role, voor credenials van een customer. Zie hoofdstuk 7. 

2. Het is vereist dat je eerst een Profile aanmaakt voor username (parameter) bijv. “lorens”, met de juiste name en lastname die correspondeert met een bestaande customer bijv. name: “Lorens” en lastname : “Zhou” in de body. De username "lorens" het komt dan als username voor in de user met een customer role en is tevens de username voor de Profile. Deze twee usernames moeten gelijk zijn. Dus: Profile.username = User.username (met customer role), Profile.name = Customer.name en Profile.lastname = Customer.lastname. Er is een link tussen Profile en User. Er is een link tussen Profile en Customer.

3. Daarna ga je naar authentication en typ je {{wachtwoord1}} in bij de Token om de jwt-token op te halen. Dan druk je op “send” bij endpoint (profiles) om een profiel aan te maken. 

4. Vervolgens haal je de token op voor ADMIN role en deze bewaar je als environment variabele {{wachtwoord1}}. Je gaat naar POST localhost:8080/orders en maakt twee orders aan met customerNum die correspondeert met name: “Lorens” en lastname: “Zhou”. Bij “Authentication” moet je in het veld van de token {{wachtwoord}} intypen, want alleen ADMIN kan een order aanmaken en niet een CUSTOMER {{wachtwoord1}}. 

5. Dan ga je naar GET localhost:8080/orders/auth-customer/lorens. “lorens” dat is de username die je moet opgeven als pathvariabele. Bij Authentication tabblad vul je in {{wachtwoord}} voor de jwt-token van de CUSTOMER. Vervolgens druk je op knop send. De twee orders worden dan opgehaald die zijn aangemaakt via ADMIN role. Bij deze test heb je CUSTOMER role en ADMIN rol nodig om deze endpoint te testen.
<br><br>

## 9. Tabel met alle endpoints en toelichting

User Controller 
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|POST    | /users <br> <br> Bereikbaar voor: iedereen  | Hier kan een nieuwe gebruiker worden aangemaakt. De gebruiker wordt als JSON via de body aangeleverd. Bij het aanmaken moet de juiste ROLE worden toegevoegd aan de JSON. |

Role Controller
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|GET  | /roles <br> <br> Bereikbaar voor: iedereen | Hier kan de gedefinieerde rol worden aangevraagd.|

Profile Controller 
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|GET   | /profiles/{username}| Hier kan de gebruikersnaam worden opgegeven door de gebruiker en kan de profiel worden aangevraagd. De profiel is alleen toegankelijk voor de gebruiker zelf.|
|POST  | /profiles | Hier kan een profiel aan worden gemaakt voor een gebruiker |
|       |         |    Bereikbaar voor: alle ingelogde gebruikers |

Auth Controller 
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|POST   | /auth <br><br> Bereikbaar voor: Iedereen | Hier kan de gebruiker inloggen met gebruikersnaam en wachtwoord. En kan diegene hierdoor toegang hebben tot een endpoint die autorisatie nodig heeft d.m.v. kopiëren van de bearer token die wordt gegenereerd door het systeem. |

Order Controller 
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|POST   | /orders | Hier kan een order worden aangemaakt door de gegevens van de order in te voeren in de body. |
|GET    | /orders | Opvragen van alle orders. |
|GET    | /orders/{id} | Opvragen een order a.h.v. een id. |
|GET    | /orders/auth-customer/{useranem} | De klanten hebben hier toegang toe, om de gegevens van hun eigen orders te bekijken. In de database is customer Lorens Zhou al aangemaakt met user username “lorens”. Alleen het profiel moet nog worden aangemaakt met die gegevens. Met die klantgegevens kan deze get request worden getest. De username is dan “lorens” voor deze endpoint. |
|PATCH  | /orders/{id}/addItem | Toevoegen van een item op een bepaalde order met de opgegeven order id. Er moet 2 parameters worden meegegeven met newItemId voor item id en quantity voor aantal. |
|PATCH  | /orders/updateQuantity | Aanpassen van de hoeveelheid van het item d.m.v. 2 parameters newItemId voor item id en quantity voor het aantal. |
|PATCH  | /orders/{id}/{action} | Pad variabele id staat voor order id en pad variabele action staat voor twee keuzes namelijk “orderCompleted” of “orderPaid”. OrderCompleted zet orderstatus op “completed” en orderPaid zet paymentstatus op “paid”. |
|DELETE | /orders/{id} | Het verwijderen van de order met het opgegeven id. |
|DELETE | /orders/{id}/{itemId} | Het verwijderen van order item met het opgegeven order id en item id. |
|       |                       | Bereikbaar voor : ADMIN, EMPLOYEE (m.u.v. auth-customer endpoint, deze is alleen bereikbaar voor CUSTOMER)|

Item Controller 
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|POST   | /items        | Het aanmaken een items (gerecht) met body gegevens in json. |
|DELETE | /items/[id}   | Het verwijderen van item met het opgegeven item id. |
|GET    | /items        | Het ophalen van alle items (gerechten). |
|GET    | /items/{id}   | Het ophalen van een item met het opgegeven item id. |
|PUT    | /items/{id}   | Het updaten van een item met het opgegeven item id. |
|       |               | Bereikbaar voor : ADMIN, EMPLOYEE |

Invoice Controller 
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|POST   | /invoices     | Het aanmaken van een factuur met body gegevens in json. |
|GET    | /invoices     | Het ophalen van alle aangemaakte facturen. |
|PATCH  | /invoices/{id}| Aanpassen van factuurbedrag van de factuur met factuur id en met parameter newInvoiceAmount wordt het nieuwe factuurbedrag opgegeven. |
|GET    | /invoices/{id}/printInvoice | Het ophalen van een factuur met opgegeven factuur id, waarbij ook de gegevens van de order wordt opgegehaald. Hierdoor kan met deze gegevens een factuur worden opgemaakt en worden geprint. |
|DELETE | /invoices/{id} | Het verwijderen van een factuur met opgegevens factuur id.|
|       |                | Bereikbaar voor : ADMIN, EMPLOYEE |

File Manager Controller 
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|POST   | /upload-file   | Het uploaden van een file naar een bepaalde pad.|
|GET    | /download-file | Het downloaden van een file van een bepaalde pad. |
|       |                | Bereikbaar voor: ADMIN, EMPLOYEE, CUSTOMER (alle ingelogd gebruikers) |

Employee Controller 
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|POST   | /employees     | Het aanmaken van een nieuwe medewerker via de body met jason. |
|GET    | /employees     | Alle medewerkers ophalen. |
|PUT    | /employees/{id}| Aanpassen van de gegevens van een medewerker voor het opgegeven medewerker id via de body. |
|DELETE | /employees/{id}| Verwijderen van een medewerker met het opgegeven medewerker id. |
|       |                | Bereikbaar voor : ADMIN, EMPLOYEE |

Customer Controller 
|Soort API | Mapping | Toelichting |
|----------|---------|-------------|
|POST   | /customers     | Aanmaken van een nieuwe klant met gegevens in de body via json. |
|GET    | /customers     | Alle klanten ophalen. |
|PUT    | /customers/{id}| Alle gegevens aanpassen voor een klant met opgegeven klant id via de body met de nieuwe gegevens . |
|DELETE | /customers/{id}| Verwijderen van een klant met het opgegeven klant id.|
|       |                | Bereikbaar voor : ADMIN, EMPLOYEE |














