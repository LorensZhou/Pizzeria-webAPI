# Pizzeria-webAPI - Installatie Handleiding
<br>

## Inhoudsopgave

1. Inleiding
2. Handleiding
3. Benodigdheden
4. Tabel met alle endpoints en toelichting

<br>

## 1. Inleiding

Deze installatiehandleiding is een onderdeel van onze eindopdracht die ik moet maken voor onze opleiding NOVI backend. In onze eindopdracht moet ik een applicatie ontwikkelen die een pizzeria (afhaal-bezorg)restaurant kan gebruiken om een order op te kunnen nemen en te kunnen verwerken. Deze applicatie kan ook worden gebruikt om voor een burger  of Chinese (afhaal-bezorg)restaurant. Dus het is niet specifiek bedoeld voor alleen pizza restaurants. Maar voor een concretere implementatie heb ik gekozen voor een pizzeria (afhaal-bezorg)restaurant. Het voordeel van een webapplicatie is dat het heel makkelijk is te benaderen via de web. De klant kan zelf inloggen op de website en zijn/haar bestelling/order bekijken. De klant hoeft dan het restaurant niet te bellen om het aan een medewerker te gaan vragen. De medewerkers hebben ook veel profijt van de applicatie. De medewerkers hoeven dan de orders niet meer op te schrijven op blocnotes of papier, zij kunnen de order uitprinten en doorgeven aan de keuken.  

De applicatie heeft de meeste standaard functionaliteiten zoals inloggen, order opvragen en orders aanpassen en uitprinten en facturatie. Later kan de applicatie worden uitgebreid naar meerdere functionaliteiten, zoals track en trace van bezorgstatus enz. Dus de applicatie heeft vele groeimogelijkheden in de toekomst, zodat de applicatie wordt uitgebreid. 
<br><br>

## 2. Handleiding

Dit document bevat uitleg over de installatie van Pizzaria-WebApi. Om de applicatie (API) te gebruiken moet je toegang hebben via de endpoints van de applicatie (in hoofdstuk 4 zijn alle endpoints weergegeven). Voor een uitgebreid uitgelegd van het gebruik van endpoints verwijs ik naar de API-documentatie. Er wordt in deze handleiding uitleg gegeven hoe je project kan opstarten d.m.v. installaties van de programma’s. De vereiste programma’s zijn IntelliJ, JDK 17, Postman en Postgresql. Er moet ook bepaalde dependencies worden geïnstalleerd in IntelliJ om de applicatie te kunnen runnen. 

De installatie wordt stap voor stap uitgelegd. De installatie is geschreven voor en uitgevoerd op het besturingssysteem van MacOs. Er kunnen andere programma’s worden gebruikt, maar het wordt niet besproken in deze handleiding.
<br><br>

## 3. Benodigdheden

### Gebruik alleen localhost 

De code voor deze applicatie is geen server toegewezen waar de applicatie en database zijn opgeslagen. De applicatie werkt op de computer en draait op de localhost/8080. Deze localhost wordt gebruikt voor de gehele handleiding.  

### Benodigde programma’s 

De benodigde programma’s zijn IntelliJ, JDK 17, Postman en PostgreSQL. Deze zijn vereist en moet geïnstalleerd worden op de computer. 

### Database configuratie

Stap 1 

Open het project pizzeria-webAPI in IntelliJJ. In FileStorageService.java hebben wij de waarde van de filepath genoemd als environment variabele : ${STORAGE_PATH}.

<img src="src/assets/Screenshot -DB-conf-stap1.png" width="700" alt="db configuration screenshot 1">

Stap 2 

Geef de database de naam “pizzeria-db” en de owner “postgres” en klik op “save”. 

<img src="src/assets/Screenshot -DB-conf-stap2.png" width="700" alt="db configuration screenshot 2">

Stap 3 

Open IntelliJ en navigeer naar de “application.properties” file in de code van de applicatie en stel volgende waarden in voor de datasource: 

spring.sql.init.platform=postgres 

spring.datasource.url=jdbc:postgresql://localhost:5432/pizzeria-db 

spring.datasource.username=postgres 

spring.datasource.password="eigen ingestelde password” 

spring.datasource.driver-class-name=org.postgresql.Driver 

<img src="src/assets/Screenshot -DB-conf-stap3.png" width="700" alt="db configuration screenshot 3">

De eigen ingestelde password van spring.datasource.password is hetzelfde password die je hebt opgegeven bij het installeren van PostgreSQL. Deze is wel belangrijk als je toegang wilt hebben tot de database.  

Stap 4 

Als je de applicatie opnieuw runt zouden er in de database de tabellen verschijnen als je in pgadmin gaat bekijken zie je de 10 tabellen: customers, employees, invoices, items, order_details, orders, profiles, roles, users en user_roles, door de database “pizzeria-db” te selecteren klapt het menu open, selecteer je “schema’s>public” en daarna klapmenu “tables”. Indien die tabellen niet beschikbaar zijn selecteer de database en klik op rechtermuisknop en kies ”refresh”. Als zij nog steeds niet verschijnen kun je in stap 3 kijken of de juiste gegevens zijn ingevuld bij de application.properties. 

### Pad instellen voor upload en download van files 

Stap 1 

Open het project pizzeria-webAPI in IntelliJJ. In FileStorageService.java hebben wij de waarde van de filepath genoemd als environment variabele : ${STORAGE_PATH}.  

<img src="src/assets/Screenshot -Pad-instellen-stap1.png" width="700" alt="pad instellen screenshot 1">

Stap 2 

Deze moeten wij ook een waarde geven in IntelliJ voor een environment variabel. Om dit in te stellen ga je naar “edit configuration” onder “PizzeriaWebApiApplication”. 

<img src="src/assets/Screenshot -pad-instellen-stap2.png" width="700" alt="pad instellen screenshot 2">

Stap 3 

Dan klik je op “Modify options”. 

<img src="src/assets/Screenshot -pad-instellen-stap3.png" width="700" alt="pad instellen screenshot 3">

Stap 4 

Dan vink je “environment variables” aan. En je komt dan in het edit veld van de environment variabelen. 

<img src="src/assets/Screenshot -pad-instellen-stap4.png" width="700" alt="pad instellen screenshot 4">

Stap 5 

Daar geef je de waarden van de environment variabelen op. Vervolgens druk je op “ok” om de aanpassing door te voeren.  

<img src="src/assets/Screenshot -pad-instellen-stap5.png" width="700" alt="pad instellen screenshot 5">

In mijn voorbeeld heb ik de environment variabele STORAGE_PATH ingesteld op /Users/storage. De folder storage heb ik zelf aangemaakt in Finder van mijn Macbook onder het mapje Users. Deze waarde kun je kopiëren door op de folder te gaan staan in de Finder onderaan en op rechtermuisknop te drukken. Dan selecteer je “Copy “storage” as Pathname”. Als je command-v doet, dan zul je de waarde “/Users/storage” krijgen. Deze vul je dan in bij de environment variabele als een waarde voor ”STORAGE_PATH”. Deze pad heb je nodig als je een bestand wilt uploaden of downloaden via PizzeriaWebApiApplication. 
<br><br>

## 4. Tabel met alle endpoints en toelichting

In dit hoofdstuk worden alle endpoints opgesomd in een tabel met toelichting. Voor een uitgebreide documentatie verwijs ik naar de API-documenatie. Daar staat alles in hoe je de endpoints en beveiligde endpoints kan benaderen.

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














