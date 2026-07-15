Feature: JSONPlaceholder API Tests

        @API @Smoke
        Scenario: Verify GET post by ID returns correct data
            Given the API base URI is "https://jsonplaceholder.typicode.com"
             When I send a GET request to "/posts/1"
             Then the response status code should be 200
              And the response body field "id" should equal 1
              And the response body field "userId" should equal 1

        @API @Smoke
        Scenario: Verify GET all posts returns a non-empty list
            Given the API base URI is "https://jsonplaceholder.typicode.com"
             When I send a GET request to "/posts"
             Then the response status code should be 200
              And the response should contain more than 0 items

        @API
        Scenario: Verify POST creates a new resource
            Given the API base URI is "https://jsonplaceholder.typicode.com"
             When I send a POST request to "/posts" with the following body:
                  | title    | body                      | userId |
                  | BDD Test | Cucumber with RestAssured | 1      |
             Then the response status code should be 201
              And the response body field "title" should equal "BDD Test"

        @API
        Scenario: Verify GET user by ID returns correct email
            Given the API base URI is "https://jsonplaceholder.typicode.com"
             When I send a GET request to "/users/1"
             Then the response status code should be 200
              And the response body string field "email" should not be empty
