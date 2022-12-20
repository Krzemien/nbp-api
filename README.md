
# GET
http://localhost:8080/api/currency/eur/2022-12-12

RESPONSE
{
	"date": "2022-12-12",
	"code": "eur",
	"mid": 4.6912
}

# POST
http://localhost:8080/api/currency/

REQUEST
{
    "date": "2022-12-12",
    "codes":["usd","eur","gbp"]
}

RESPONSE
{
    "date": "2022-12-12",
    "codes": {
        "EUR": 4.6912,
        "GBP": 5.453,
        "USD": 4.4424
    },
    "totalRate": 14.5866
}
