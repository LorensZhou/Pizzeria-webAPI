# Pizzeria-webAPI - Handleiding

## Inleiding

Deze installatiehandleiding is een onderdeel van onze eindopdracht die ik moet maken voor onze opleiding NOVI backend. In onze eindopdracht moet ik een applicatie ontwikkelen die een pizzeria (afhaal-bezorg)restaurant kan gebruiken om een order op te kunnen nemen en te kunnen verwerken. Deze applicatie kan ook worden gebruikt om voor een burger  of Chinese (afhaal-bezorg)restaurant. Dus het is niet specifiek bedoeld voor alleen pizza restaurants. Maar voor een concretere implementatie heb ik gekozen voor een pizzeria (afhaal-bezorg)restaurant. Het voordeel van een webapplicatie is dat het heel makkelijk is te benaderen via de web. De klant kan zelf inloggen op de website en zijn/haar bestelling/order bekijken. De klant hoeft dan het restaurant niet te bellen om het aan een medewerker te gaan vragen. De medewerkers hebben ook veel profijt van de applicatie. De medewerkers hoeven dan de orders niet meer op te schrijven op blocnotes of papier, zij kunnen de order uitprinten en doorgeven aan de keuken.  

De applicatie heeft de meeste standaard functionaliteiten zoals inloggen, order opvragen en orders aanpassen en uitprinten en facturatie. Later kan de applicatie worden uitgebreid naar meerdere functionaliteiten, zoals track en trace van bezorgstatus enz. Dus de applicatie heeft vele groeimogelijkheden in de toekomst, zodat de applicatie wordt uitgebreid. 

## Handleinding

Dit document bevat uitleg over de Pizzaria-WebApi en het gebruik van de endpoints. Om de applicatie (API) te gebruiken moet je toegang hebben via de endpoints van de applicatie. Er wordt in de handleiding uitleg gegeven hoe je project kan opstarten d.m.v. installaties van de programma’s. De vereiste programma’s zijn IntelliJ, JDK 17, Postman en Postgresql. Er moet ook bepaalde dependencies worden geïnstalleerd in IntelliJ om de applicatie te kunnen runnen. 

Daarnaast wordt er ook uitleg gegeven hoe je de authenticatie kan toepassen voor de endpoints van de gebruikers: EMPLOYEE, CUSTOMER en ADMIN. De wachtwoorden en inlog zijn al gereed in Postman-requests. Het is belangrijk om de hoofstukken: benodigdheden, PostGreSQL configuratie en lijst REST endpoints te lezen om de applicatie te kunnen gebruiken. 

De endpoints staan in het overzicht van hoofdstuk 10 in tabellen opgesomd. De installatie wordt stap voor stap uitgelegd. De installatie is geschreven voor en uitgevoerd op het besturingssysteem van MacOs. Er kunnen andere programma’s worden gebruikt, maar het wordt niet besproken in deze handleiding.

## Benodigdheden

### Gebruik alleen localhost 

De code voor deze applicatie is geen server toegewezen waar de applicatie en database zijn opgeslagen. De applicatie werkt op de computer en draait op de localhost/8080. Deze localhost wordt gebruikt voor de gehele handleiding.  

### Benodigde programma’s 

De benodigde programma’s zijn IntelliJ, JDK 17, Postman en PostgreSQL. Deze zijn vereist en moet geïnstalleerd worden op de computer. 

### Pad instellen voor upload en download van files 

Stap 1 

Open het project pizzeria-webAPI in IntelliJJ. In FileStorageService.java hebben wij de waarde van de filepath genoemd als environment variabel : ${STORAGE_PATH}.  


