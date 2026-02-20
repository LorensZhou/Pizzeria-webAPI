# Pizzeria-webAPI - Handleiding

## 1. Inleiding

Deze installatiehandleiding is een onderdeel van onze eindopdracht die ik moet maken voor onze opleiding NOVI backend. In onze eindopdracht moet ik een applicatie ontwikkelen die een pizzeria (afhaal-bezorg)restaurant kan gebruiken om een order op te kunnen nemen en te kunnen verwerken. Deze applicatie kan ook worden gebruikt om voor een burger  of Chinese (afhaal-bezorg)restaurant. Dus het is niet specifiek bedoeld voor alleen pizza restaurants. Maar voor een concretere implementatie heb ik gekozen voor een pizzeria (afhaal-bezorg)restaurant. Het voordeel van een webapplicatie is dat het heel makkelijk is te benaderen via de web. De klant kan zelf inloggen op de website en zijn/haar bestelling/order bekijken. De klant hoeft dan het restaurant niet te bellen om het aan een medewerker te gaan vragen. De medewerkers hebben ook veel profijt van de applicatie. De medewerkers hoeven dan de orders niet meer op te schrijven op blocnotes of papier, zij kunnen de order uitprinten en doorgeven aan de keuken.  

De applicatie heeft de meeste standaard functionaliteiten zoals inloggen, order opvragen en orders aanpassen en uitprinten en facturatie. Later kan de applicatie worden uitgebreid naar meerdere functionaliteiten, zoals track en trace van bezorgstatus enz. Dus de applicatie heeft vele groeimogelijkheden in de toekomst, zodat de applicatie wordt uitgebreid. 

## 2. Handleiding

Dit document bevat uitleg over de Pizzaria-WebApi en het gebruik van de endpoints. Om de applicatie (API) te gebruiken moet je toegang hebben via de endpoints van de applicatie. Er wordt in de handleiding uitleg gegeven hoe je project kan opstarten d.m.v. installaties van de programma’s. De vereiste programma’s zijn IntelliJ, JDK 17, Postman en Postgresql. Er moet ook bepaalde dependencies worden geïnstalleerd in IntelliJ om de applicatie te kunnen runnen. 

Daarnaast wordt er ook uitleg gegeven hoe je de authenticatie kan toepassen voor de endpoints van de gebruikers: EMPLOYEE, CUSTOMER en ADMIN. De wachtwoorden en inlog zijn al gereed in Postman-requests. Het is belangrijk om de hoofstukken: benodigdheden, PostGreSQL configuratie en lijst REST endpoints te lezen om de applicatie te kunnen gebruiken. 

De endpoints staan in het overzicht van hoofdstuk 10 in tabellen opgesomd. De installatie wordt stap voor stap uitgelegd. De installatie is geschreven voor en uitgevoerd op het besturingssysteem van MacOs. Er kunnen andere programma’s worden gebruikt, maar het wordt niet besproken in deze handleiding.

## 3. Benodigdheden

### Gebruik alleen localhost 

De code voor deze applicatie is geen server toegewezen waar de applicatie en database zijn opgeslagen. De applicatie werkt op de computer en draait op de localhost/8080. Deze localhost wordt gebruikt voor de gehele handleiding.  

### Benodigde programma’s 

De benodigde programma’s zijn IntelliJ, JDK 17, Postman en PostgreSQL. Deze zijn vereist en moet geïnstalleerd worden op de computer. 

### Database configuratie

Stap 1 

Open het project pizzeria-webAPI in IntelliJJ. In FileStorageService.java hebben wij de waarde van de filepath genoemd als environment variabel : ${STORAGE_PATH}.

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

Open het project pizzeria-webAPI in IntelliJJ. In FileStorageService.java hebben wij de waarde van de filepath genoemd als environment variabel : ${STORAGE_PATH}.  

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

In mijn voorbeeld heb ik de environment variabel STORAGE_PATH ingesteld op /Users/storage. De folder storage heb ik zelf aangemaakt in Finder van mijn Macbook onder het mapje Users. Deze waarde kun je kopiëren door op de folder te gaan staan in de Finder onderaan en op rechtermuisknop te drukken. Dan selecteer je “Copy “storage” as Pathname”. Als je command-v doet, dan zul je de waarde “/Users/storage” krijgen. Deze vul je dan in bij de environment variabel als een waarde voor ”STORAGE_PATH”. Deze pad heb je nodig als je een bestand wilt uploaden of downloaden via PizzeriaWebApiApplication. 

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

## 5. Lijst van endpoints

Hier bespreek ik de endpoints die ik heb gemaakt voor de applicatie. De mapping van de endpoint wordt weergegeven en hoe de endpoints API request moet worden opgesteld.  

In de tabellen wordt er per controller de informatie weergegeven: 

1. Soort API request 

2. De mapping (URI) 

3. Toelichting van de endpoint 

In de collectie van Postman-request vind je ingevulde requests in mappen om direct gebruik te maken van de endpoints onder “pizzeria-webapi”. In de ingevulde requests zijn ook de body ingevuld die je kan gebruiken. Dus je hoeft zelf niet de body’s in json te verzinnen zelf. Als je de requests uitvoert, dan weet je al welke attributen je kan gebruiken om de endpoints te benaderen.  

## 6. Authenticatie en autorisatie

### Jwt token in postman verkrijgen 

Hieronder wordt er een stappenplan weergegeven voor het verkrijgen van de JWT token in Postman. Zodat je deze token kan gebruiken als variabele in verschillende requests die een authenticatie vereisen.  

Stap1 

Maak eerst een nieuwe gebruiker aan door gebruik te maken van de aangeleverde postman collectie “Collectie Post Requests”. Kies daarin de endpoint-request auth>POST users. In de JSON body kun je invullen bijvoorbeeld zie hieronder. 


<img src="src/assets/Screenshot -auth-stap1.png" width="650" alt="authenticatie screenshot 1">




