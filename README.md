# String Analyzer API
<hr>

## Getting Started

This is an API that is used to get information about strings. 
It accepts a string, stores it,
and provides the following properties:

* Length
* If it's a palindrome
* Number of unique characters
* Number of words
* Character frequency map
* Its SHA-256 hash value

It also allows for retrieval of strings that have been stored.
These strings can be retrieved through the string itself,
by query filtering, and by natural language search.


## Endpoints
This API exposes one POST endpoint, one DELETE endpoint,
and three GET endpoints.

* ### POST (`/strings`)
    This endpoint receives a JSON string with a single property,
"value", which contains the string to be processed. It returns
a json string with the properties of the string and stores
the string in the system. Depending on what occurs, the
endpoint sends one of three status codes:
  * 201 (Created): Successfully stored the string
  * 400 (Bad Request): Invalid request body or missing "value"
  field
  * 409 (Conflict): String already exists
  * 422 (Unprocessable Entity): Invalid data type for "value"

* ### GET (`/strings/{string_value}`)
    This end point gets a string directly by its string value.
It returns a JSON string with the properties of the string on
successful retrieval. One of two status cods can be sent:
  * 200 (OK)
  * 404 (Not Found): String does not exist
* ### GET (`/strings`)
    Rather than search for a single string, this endpoint
makes a general search for strings that meet certain criteria.
These criteria are defined using URL parameters. The five
parameters supported by this endpoint are:
  * is_palindrome (boolean)
  * min_length (integer)
  * max_length (integer)
  * word_count (integer)
  * contains_character (string)
  
  The endpoint can also return one of the following codes:
  * 200 (OK)
  * 400 (Bad Request): Invalid query parameters values or
  types
* ### GET (`/strings/filter-by-natural-language`)
    This endpoint uses basic Natural Language Processing (NLP)
to allow for string search through natural language. It takes
a natural language string and parses it into filters by which
it returns search results. The filters it parses into are the
same as above. The status codes this endpoint sends are:
  * 200 (OK)
  * 400 (Bad Request): Unable to parse natural language query
  * 422 (Unprocessable Entity): Query parsed but resulted in
  conflicting filters
* ### DELETE (`/strings/{string_value}`)
    This endpoint deletes the string that is indicated by the
"string_value" parameter. It returns one of two status codes:
  * 204 (Success)
  * 404 (Not Found): String doesn't exist


## Setup Instructions

### Requirements
* Java Development Kit (JDK) 17 or above
* Apache Maven 3.7 or above

If you're running this project from an Intellij, there is no need to install Maven on your computer

### Instructions for Running Locally
* Clone this repository using `git clone`
* Open cmd (Windows) or Terminal (macOS and Linux)
* Open the project folder using `cd <path-to-project>`
* Run `mvn spring-boot:run`

### Dependencies
This project has the following dependencies:
* Spring Boot Web
* Spring Boot Test
* Spring Boot Actuator

However, these dependencies will be automatically installed by maven when the run command is executed
