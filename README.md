### Register User Api
```
    POST /api/v1/registration/register HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    Content-Length: 174

        {
            "emailAddress":"kabir@gmail.com",
            "firstName": "James",          
            "lastName": "Aduloju",
            "password":"1234"
        }
```
### Confirm User Token Api
```
POST /api/v1/registration/confirm HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 82

{
   "email": "kab@gmail.com",
   "token":"a0fbdbcd-36ca-4d1f-89d2-7683e8a17053"
}
```

### Confirm User Token Api
```
POST /api/v1/registration/confirm HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 82

{
   "email": "kab@gmail.com",
   "token":"a0fbdbcd-36ca-4d1f-89d2-7683e8a17053"
}
```


