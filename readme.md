# Energy Contract REST API
The app is developed using Spring Boot, Spring Security, Spring Data, REST, Jackson, Lombok, PostgreSQL. This app handles the customers and their contracts of gas and electricity distribution company.

| HTTP Method | Endpoint | Function |
| --- | --- | --- |
| | /api/v1 | API Root |
| GET | /user/{userId}/contracts | Retrieves all contracts of one user |
| GET | /contracts | Gets all contracts depending on filters chosen |
| POST | /contracts | Creates new contract |

---
#### To retrieve contracts of one user you need to use user id. This link retrieves the contracts for user with user id 1:
```
/api/v1/user/1/contracts
```
---
#### To retrieve all contracts use:
```
/api/v1/contracts
```
*Filtering:*
- By name `?name=john`, 
- By date the contract was created `&createdOn=yyyy-MM-dd` 
- By contract type `&contractType=gas` 
- By user type `&userType=private`

Note: User type can be either `private` or `business`. Contract type can be `gas` `electricity` or `gas and electricity`.

*Sorting:*
- By contract id `&sort=id`
- By contract type `&sort=contractType`
- By date the contract was created `&sort=createdOn`
- By user id `&sort=userId`
- By name `&sort=userName`

*Pagination:*
- To set page number use `&page=0` (0 by default)
- To set page size use `&pageSize=5` (5 by default)

---
#### To add contracts use:

*You must be authorized as Business or Private user to create a new contract*
```
/api/v1/contracts
```
POST:
```
{
  "contractTypeId" : 1
}
```
Note: 1 for `Gas`, 2 for `Electricity`, 3 for `Gas and Electricity`