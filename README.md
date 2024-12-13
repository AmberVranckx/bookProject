# BookProject
Naam: Amber Vranckx

## 1. Thema
Het thema van mijn project zijn boeken. Ik heb drie entiteiten, namelijk: Book, User en Readinglist. In het eniteit 'Boek', houd ik informatie bij over de boeken, zoals: de naam, de auteur, het isbn-nummer en de beschrijving. In het entiteit 'User', houd ik informatie bij over de gebruikers. Dit bevat onder andere de voornaam, achternaam, het e-mailadres en de geboortedatum. In het laatste entiteit 'Readinglist', houd ik bij welke gebruiker welke boek heeft gelezen en de rating en feedback van de gebruiker voor dit boek.

Ik heb dus drie microservices, namelijk: user-service, book-service en readinglist-service. Ik heb ook een api-gateway met security.

Dit is mijn schema:
![Book drawio](https://github.com/user-attachments/assets/f180ad13-6750-4baf-ae6e-c85ae1ed725f)



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

