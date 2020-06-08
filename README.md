# WeatherApi
A Weather REST API built with Spring Boot

The API has been developed on the system specifications as below:
- Ubuntu 18.04 LTS
- PostgreSQL 12.2 (any version >= 10 shall work )
- OpenJDK 1.8.0_252
- Apache Maven 3.6.0 (Maven wrapper shall also work if provisioned)

## API documentation
Few notes about the usage:

The codebase supports interchangeable use of query params in the API requests: 
e.g. one can use `start` or `startDate`, `end` or `endDate`, `latitude` or `lat`,
`longitude` or `lon` & `dateRecorded` or `date`. 

The date format used for dates in the API request/response is: **`yyyy-MM-dd`**

Any of these parameters can be combined to build a valid combination of
query params, for e.g - http://localhost:8000/erase?start=2020-06-06&endDate=2020-06-08&latitude=13.78&lon=73.10
- renames the DTO fields.

### Request & response JSON objects
1.**Create object schema**   
A request JSON to create weather data point
```json
{
  "date": "2020-06-09",
  "location": {
    "city": "Pandharpur",
    "state": "Maharashtra",
    "lat": 17.2,
    "lon": 74.8
  },
  "temperature": [
    38.8,
    35.1,
    24.2,
    35.3,
    26.0,
    32.1,
    30.9,
    33.0,
    24.2
  ]
}
```

2.**Weather response object schema**   
Response JSON returned by endpoints like: `/weather, /weather/locations` etc.
```json
{
    "id": 33,
    "date": "2020-06-09",
    "location": {
        "id": 5,
        "city": "Pandharpur",
        "state": "Maharashtra",
        "lat": 17.2,
        "lon": 74.8
    },
    "temperature": [
        24.2,
        24.2,
        26.0,
        30.9,
        32.1,
        33.0,
        35.1,
        35.3,
        38.8
    ]
}
```

3.**Weather response object with lowest & highest temperature schema**  
Response JSON returned by endpoints like: `/weather?startDate=2020-06-06&end=2020-06-08&lat=17.2&lon=74.8, 
/weather/temperature?start=2020-06-06&end=2020-06-08` etc.
```json
{
  "city": "Pandharpur",
  "state": "Maharashtra",
  "lat": 17.2,
  "lon": 74.8,
  "date": "2020-06-07",
  "lowest": 20.9,
  "highest": 33.0
}
```   

### Create a weather data
- Method : `POST`
- Path: `/weather`
- Request body: `Create object schema`
- Response: 201 if successful, 400 if the weather data with given `id` already exists else 500
- Response body: the newly created weather data with `Weather response object schema`

E.g.  
**Input**
```json
{
  "date": "2020-06-09",
  "location": {
    "city": "Pandharpur",
    "state": "Maharashtra",
    "lat": 17.2,
    "lon": 74.8
  },
  "temperature": [
    38.8,
    35.1,
    24.2,
    35.3,
    26.0,
    32.1,
    30.9,
    33.0,
    24.2
  ]
}
```

**Output**
```json
{
    "id": 33,
    "date": "2020-06-09",
    "location": {
        "id": 5,
        "city": "Pandharpur",
        "state": "Maharashtra",
        "lat": 17.2,
        "lon": 74.8
    },
    "temperature": [
        24.2,
        24.2,
        26.0,
        30.9,
        32.1,
        33.0,
        35.1,
        35.3,
        38.8
    ]
}
```

### Get a single weather data point
- Method : `GET`
- Path: `/weather/{id}`
- Response: 200 if successful, 404 if the weather data with given `id` doesn't exist else 500
- Response body: `Weather response object schema`

E.g. `/weather/14`
**Output**
```json
{
    "id": 14,
    "date": "2020-06-07",
    "location": {
        "id": 5,
        "city": "Pandharpur",
        "state": "Maharashtra",
        "lat": 17.2,
        "lon": 74.8
    },
    "temperature": [
        20.9,
        22.0,
        22.1,
        23.1,
        23.8,
        24.2,
        24.2,
        25.1,
        25.3,
        26.0,
        31.5,
        33.0
    ]
}
```

### Get all the weather data 
- Method : `GET`
- Path: `/weather`
- Response: 200 if successful else 500
- Response body: List of `Weather response object schema`

E.g. `/weather`
**Output**
```json
[
    {
        "id": 14,
        "date": "2020-06-07",
        "location": {
            "id": 5,
            "city": "Pandharpur",
            "state": "Maharashtra",
            "lat": 17.2,
            "lon": 74.8
        },
        "temperature": [
            20.9,
            22.0,
            22.1,
            23.1,
            23.8,
            24.2,
            24.2,
            25.1,
            25.3,
            26.0,
            31.5,
            33.0
        ]
    },
    {
        "id": 15,
        "date": "2020-06-08",
        "location": {
            "id": 5,
            "city": "Pandharpur",
            "state": "Maharashtra",
            "lat": 17.2,
            "lon": 74.8
        },
        "temperature": [
            30.9,
            31.5,
            32.0,
            32.1,
            33.0,
            33.1,
            33.8,
            34.2,
            34.2,
            35.1,
            35.3,
            36.0
        ]
    },
    {
        "id": 30,
        "date": "2020-06-08",
        "location": {
            "id": 6,
            "city": "Pune",
            "state": "Maharashtra",
            "lat": 13.78,
            "lon": 73.1
        },
        "temperature": [
            30.8,
            30.9,
            32.1,
            33.0,
            34.2,
            34.2,
            35.1,
            35.3,
            36.0
        ]
    },
    {
        "id": 31,
        "date": "2020-06-08",
        "location": {
            "id": 6,
            "city": "Pune",
            "state": "Maharashtra",
            "lat": 13.78,
            "lon": 73.1
        },
        "temperature": [
            20.8,
            20.9,
            24.2,
            24.2,
            26.0,
            32.1,
            33.0,
            35.1,
            35.3
        ]
    },
    {
        "id": 32,
        "date": "2020-06-08",
        "location": {
            "id": 5,
            "city": "Pandharpur",
            "state": "Maharashtra",
            "lat": 17.2,
            "lon": 74.8
        },
        "temperature": [
            20.9,
            24.2,
            24.2,
            26.0,
            32.1,
            33.0,
            35.1,
            35.3,
            38.8
        ]
    },
    {
        "id": 33,
        "date": "2020-06-09",
        "location": {
            "id": 5,
            "city": "Pandharpur",
            "state": "Maharashtra",
            "lat": 17.2,
            "lon": 74.8
        },
        "temperature": [
            24.2,
            24.2,
            26.0,
            30.9,
            32.1,
            33.0,
            35.1,
            35.3,
            38.8
        ]
    }
]
```

### Get all weather data by location
- Method : `GET`
- Path: `/weather?lat={}&lon={}`
- Response: 200 if successful, 404 if the location with given `lat, lon` doesn't exist else 500
- Response body: List of `Weather response object schema`

E.g. `/weather?lat=13.78&lon=73.10`  
**Output**
```json
[
    {
        "id": 30,
        "date": "2020-06-08",
        "location": {
            "id": 6,
            "city": "Pune",
            "state": "Maharashtra",
            "lat": 13.78,
            "lon": 73.1
        },
        "temperature": [
            30.8,
            30.9,
            32.1,
            33.0,
            34.2,
            34.2,
            35.1,
            35.3,
            36.0
        ]
    },
    {
        "id": 31,
        "date": "2020-06-08",
        "location": {
            "id": 6,
            "city": "Pune",
            "state": "Maharashtra",
            "lat": 13.78,
            "lon": 73.1
        },
        "temperature": [
            20.8,
            20.9,
            24.2,
            24.2,
            26.0,
            32.1,
            33.0,
            35.1,
            35.3
        ]
    }
]
```

### Get all weather data in the given date range
- Method : `GET`
- Path: `weather?start={}&endDate={}`
- Response: 200 if successful else 500
- Response body: List of `Weather response object schema`  

E.g. `weather?start=2020-06-06&endDate=2020-06-07`  
**Output**
```json
[
    {
        "id": 14,
        "date": "2020-06-07",
        "location": {
            "id": 5,
            "city": "Pandharpur",
            "state": "Maharashtra",
            "lat": 17.2,
            "lon": 74.8
        },
        "temperature": [
            20.9,
            22.0,
            22.1,
            23.1,
            23.8,
            24.2,
            24.2,
            25.1,
            25.3,
            26.0,
            31.5,
            33.0
        ]
    }
]
```

### Get all weather data with lowest & highest temperatures in the given date range at a particular location
- Method : `GET`
- Path: `/weather?startDate={}&end={}&lat={}&lon={}`
- Response: 200 if successful else 500
- Response body: List of `Weather response object with lowest & highest temperature schema`  

E.g. `/weather?startDate=2020-06-06&end=2020-06-08&lat=17.2&lon=74.8`  
**Output**
```json
[
    {
        "city": "Pandharpur",
        "state": "Maharashtra",
        "lat": 17.2,
        "lon": 74.8,
        "date": "2020-06-07",
        "lowest": 20.9,
        "highest": 33.0
    },
    {
        "city": "Pandharpur",
        "state": "Maharashtra",
        "lat": 17.2,
        "lon": 74.8,
        "date": "2020-06-08",
        "lowest": 30.9,
        "highest": 36.0
    },
    {
        "city": "Pandharpur",
        "state": "Maharashtra",
        "lat": 17.2,
        "lon": 74.8,
        "date": "2020-06-08",
        "lowest": 20.9,
        "highest": 38.8
    }
]
```

### Get all weather data with lowest & highest temperatures in the given date range at all locations
- Method : `GET`
- Path: `/weather/temperature?start={}&end={}`
- Response: 200 if successful else 500
- Response body: List of `Weather response object with lowest & highest temperature schema`  

E.g. `/weather/temperature?start=2020-06-06&end=2020-06-08`  
**Output**
```json
[
    {
        "city": "Pandharpur",
        "state": "Maharashtra",
        "lat": 17.2,
        "lon": 74.8,
        "date": "2020-06-07",
        "lowest": 20.9,
        "highest": 33.0
    },
    {
        "city": "Pandharpur",
        "state": "Maharashtra",
        "lat": 17.2,
        "lon": 74.8,
        "date": "2020-06-08",
        "lowest": 30.9,
        "highest": 36.0
    },
    {
        "city": "Pune",
        "state": "Maharashtra",
        "lat": 13.78,
        "lon": 73.1,
        "date": "2020-06-08",
        "lowest": 30.8,
        "highest": 36.0
    },
    {
        "city": "Pune",
        "state": "Maharashtra",
        "lat": 13.78,
        "lon": 73.1,
        "date": "2020-06-08",
        "lowest": 20.8,
        "highest": 35.3
    },
    {
        "city": "Pandharpur",
        "state": "Maharashtra",
        "lat": 17.2,
        "lon": 74.8,
        "date": "2020-06-08",
        "lowest": 20.9,
        "highest": 38.8
    }
]
```

### Get all weather at a particular location on a given date
- Method : `GET`
- Path: `/weather/locations?date={}&lat={}&lon={}`
- Response: 200 if successful else 500
- Response body: List of `Weather response object schema`  

E.g. `/weather/locations?date=2020-06-08&lat=13.78&lon=73.10`  
**Output**
```json
[
    {
        "id": 30,
        "date": "2020-06-08",
        "location": {
            "id": 6,
            "city": "Pune",
            "state": "Maharashtra",
            "lat": 13.78,
            "lon": 73.1
        },
        "temperature": [
            30.8,
            30.9,
            32.1,
            33.0,
            34.2,
            34.2,
            35.1,
            35.3,
            36.0
        ]
    },
    {
        "id": 31,
        "date": "2020-06-08",
        "location": {
            "id": 6,
            "city": "Pune",
            "state": "Maharashtra",
            "lat": 13.78,
            "lon": 73.1
        },
        "temperature": [
            20.8,
            20.9,
            24.2,
            24.2,
            26.0,
            32.1,
            33.0,
            35.1,
            35.3
        ]
    }
]
```

### Delete a single weather data point
- Method : `DELETE`
- Path: `/erase/{id}`
- Response: 204 if successful, 404 if the weather data with given `id` doesn't exist else 500
- Response body: None

E.g. `/erase/26`

### Delete all the weather data
- Method : `DELETE`
- Path: `/erase`
- Response: 200 if successful else 500
- Response body: None

E.g. `/erase`

### Delete all the weather data in the given date range
- Method : `DELETE`
- Path: `/erase?startDate={}&end={}`
- Response: 200 if successful else 500
- Response body: None

E.g. `/erase?startDate=2020-06-06&end=2020-06-06`

### Delete all the weather data at a particular location
- Method : `DELETE`
- Path: `/erase?lat={}&longitude={}`
- Response: 200 if successful else 500
- Response body: None

E.g. `/erase?lat=13.78&longitude=73.10`

### Delete all the weather data at a particular location in the given date range
- Method : `DELETE`
- Path: `/erase?start={}&endDate={}&latitude={}&lon={}`
- Response: 200 if successful else 500
- Response body: None

E.g. `/erase?start=2020-06-06&endDate=2020-06-08&latitude=13.78&lon=73.10`









