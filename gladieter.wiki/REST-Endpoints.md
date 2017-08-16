## /auth/registration/
This endpoint creates a new user.

**REQUEST**
```
POST /auth/registration/ HTTP/1.1
Host: gladieter.herokuapp.com
Content-Type: application/json

{
    "username": "dummy",
    "email": "dummy@host.com",
    "password1": "dummy_password",
    "password2": "dummy_password"
}
```

**RESPONSE**
```
{
  "key": "a17afd9f649160167797e1da9cf5764953f192f7"
}
```

## /v1/product/{GTIN}
This endpoint retrieves information of the product specified by GTIN.

**REQUEST**
```
GET /v1/product/7434426593034340345 HTTP/1.1
Host: gladieter.herokuapp.com
Authorization: Token a17afd9f649160167797e1da9cf5764953f192f7
```

**RESPONSE**
```
{
  "gtin": 7434426593034340345,
  "display_name": "Incid",
  "manufacturing_datetime": "2015-06-27T13:38:15.751888Z",
  "cooking_time": 180,
  "expiry_date": "2012-07-13",
  "recalled": false,
  "nutritional_information": {
    "cholesterol": 20.45,
    "fat": 10.47,
    "sodium": 15.66,
    "protein": 11.09
  }
}
```

## /v1/me
This endpoint retrieves information of the currently authenticated user.

**REQUEST**
```
GET /v1/me HTTP/1.1
Host: gladieter.herokuapp.com
Authorization: Token a17afd9f649160167797e1da9cf5764953f192f7
```

**RESPONSE**
```
{
  "username": "dummy",
  "email": "dummy@host.com"
}
```

## /v1/me/consume
This endpoint consumes specified GTIN for currently authenticated user.

**REQUEST**
```
POST /v1/me/consume HTTP/1.1
Host: gladieter.herokuapp.com
Authorization: Token a17afd9f649160167797e1da9cf5764953f192f7
Content-Type: application/json

{
    "gtins": 7434426593034340345
}
```

**RESPONSE**
```
{
  "user": 4,
  "gtins": 7434426593034340345
}
```

## /v1/me/products/{SINCE}
This endpoint retrieves product consumption information of user since specified minutes.

**REQUEST**
```
GET /v1/me/products/5 HTTP/1.1
Host: gladieter.herokuapp.com
Authorization: Token a17afd9f649160167797e1da9cf5764953f192f7
Content-Type: application/json
```

**RESPONSE**
```
[
  {
    "consumption_datetime": "2016-12-14T18:06:43.247080Z",
    "product_information": {
      "gtin": 7434426593034340345,
      "display_name": "Incid",
      "manufacturing_datetime": "2015-06-27T13:38:15.751888Z",
      "cooking_time": 180,
      "expiry_date": "2012-07-13",
      "recalled": false,
      "nutritional_information": {
        "cholesterol": 20.45,
        "fat": 10.47,
        "sodium": 15.66,
        "protein": 11.09
      }
    }
  }
]
```