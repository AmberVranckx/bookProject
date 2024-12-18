# BookProject
Naam: Amber Vranckx

## 1. Thema
Het thema van mijn project is boeken. Ik heb drie microservices, namelijk: book-service, user-service en readinglist-service. In de book-service, houd ik informatie bij over de boeken, zoals: de naam, de auteur, het isbn-nummer en de beschrijving. In de user-service, houd ik informatie bij over de gebruikers. Dit bevat onder andere de voornaam, achternaam, het e-mailadres en de geboortedatum. In de laatste microservice readinglist-service, houd ik bij welke gebruiker welk boek heeft gelezen en de rating en feedback van de gebruiker voor dit boek.

Daarnaast heb ik ook een api-gateway. Deze zorgt voor de routing en de security. Hieronder staat mijn deployment schema. Hier staan al mijn routes op. Ik heb één route die bereikbaar is voor iedereen, namelijk GET /books. Voor de andere routes heeft de gebruiker een authentication token nodig, want mijn api-gateway is geconnecteerd met het Google Cloud Platform OAuth2.

Dit is mijn schema:
![Book drawio](https://github.com/user-attachments/assets/f180ad13-6750-4baf-ae6e-c85ae1ed725f)




Na de basisvereisten, heb ik ook geprobeerd om een front-end-service op te zetten. Dit is niet volledig gelukt, maar ik kan wel de boeken opvragen omdat deze route werkt zonder authenticatie. Ik heb ook geprobeerd om een post te doen via de front-end-service, maar dit is niet gelukt omdat ik mijn authenticatie niet in orde heb gekregen. Dit geldt ook voor het opvragen van de users.

Dit is de index-pagina van mijn front-end:
![front-end index](https://github.com/user-attachments/assets/8ceff329-b51e-449d-aad7-c837cc4cb0a0)

Hier vraag ik al mijn boeken op in mijn front-end:
![front-end boeken](https://github.com/user-attachments/assets/3a6487dd-2cd1-4dc2-9c3c-427bf8006228)

Ik heb ook geprobeerd om een formulier te maken, zodat ik een nieuwe boek kan toevoegen. Het is niet gelukt om een nieuwe boek toe te voegen, omdat mijn authenticatie van mijn front-end niet werkt.
![front-end nieuwe boek](https://github.com/user-attachments/assets/7e8f7ca0-0f7f-4342-b957-719332e81728)


## 2. Endpoints
### 2.1 Books
#### 2.1.1 POST (AUTH)
Post request met een token:
![books_post_token](https://github.com/user-attachments/assets/d4d758a6-c5e0-4443-8b5d-6ea9fc504b4f)
![books_post_body](https://github.com/user-attachments/assets/f71998c4-d312-47b3-b2cd-94767d8d39b4)

Post request zonder een token:
![books_post_no_token](https://github.com/user-attachments/assets/60f1745b-5730-4bb3-bac2-7839b8a0a989)


#### 2.1.2 GET
Het get-endpoint van books is niet beveiligd. Iedereen kan dus de boekenlijst opvragen.
![books_get](https://github.com/user-attachments/assets/50fae576-042c-4823-8ae4-e9dd4bb497c8)


#### 2.1.3 PUT (AUTH)
Put request met een token:
![books_put_token](https://github.com/user-attachments/assets/a73e8e13-3963-4964-9539-b3f9164fe8e3)
![books_put_body](https://github.com/user-attachments/assets/79d77a5f-1008-4f6b-95bc-de538656faed)

Put request zonder een token:
![books_put_no_token](https://github.com/user-attachments/assets/c54d38e8-faa2-4f82-ba56-b1c5f102572a)


#### 2.1.4 DELETE (AUTH)
Delete request met een token:
![books_delete_token](https://github.com/user-attachments/assets/94e3805f-70e7-4651-8756-e00ca5feae4f)

Delete request zonder een token:
![books_delete_no_token](https://github.com/user-attachments/assets/8ede273a-8967-4587-b918-cf6a39030e29)

Resultaat get request na het delete request van hierboven:
![books_eindresultaat](https://github.com/user-attachments/assets/bf31aec4-5067-47b3-870a-28b76b5c0fd9)


### 2.2 Users
#### 2.2.1 POST (AUTH)
Post request met een token:
![users_post_token](https://github.com/user-attachments/assets/2575753d-eb3a-497c-bd64-2bcc81e03e1b)
![users_post_body](https://github.com/user-attachments/assets/b851d73b-f8c0-4f7f-b434-e0e4ff583b72)

Post request zonder een token:
![users_post_no_token](https://github.com/user-attachments/assets/aec070b7-04e0-4a72-aec6-7d03ffb23d6a)

#### 2.2.2 GET (AUTH)
Get request met een token:
![users_get_token](https://github.com/user-attachments/assets/b2bd28bb-4ba7-4729-b910-89066e62650f)

Get request zonder een token:
![users_get_no_token](https://github.com/user-attachments/assets/972120d0-9e16-4381-9bb4-65d93cf27683)


### 2.3 Readinglist
#### 2.3.1 POST (AUTH)
Post request met een token:
![readinglists_post_token](https://github.com/user-attachments/assets/41da766d-b34e-46fe-bc70-99dbe6109aa4)
![readinglists_post_body](https://github.com/user-attachments/assets/4cc63fa3-8023-45cd-b9d0-d61ba0521fb8)

Post request zonder een token:
![readinglists_post_no_token](https://github.com/user-attachments/assets/201b81b9-77d3-41f4-b49f-d2e50a9b4387)


#### 2.3.2 GET (AUTH)
Get request met een token:
![readinglists_get_token](https://github.com/user-attachments/assets/a68cde60-c2cb-4deb-9366-11b86be9fcc1)


Get request zonder een token:
![readinglists_get_no_token](https://github.com/user-attachments/assets/6f1ae806-9ac6-43f4-8592-dc237651fc03)

