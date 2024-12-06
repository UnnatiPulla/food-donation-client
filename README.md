# 4156 Client App — ASE Aces

## Purpose of our app

Our app is designed to serve **homeless shelters and individuals** in need by providing a platform to browse "food listings" posted by restaurants, grocery stores, and other establishments with surplus food. It provides a user-friendly platform where people can create an account, sign in, and browse available food listings nearby. Users can either enter their location manually or navigate via an integrated Google Maps interface to find listings in their area. Once they locate a suitable listing, they can view details about the available food and submit a request to claim it.

Without our app, this surplus food would likely go to waste, as there is no simple way for individuals to discover available resources or for establishments to share their excess food effectively.

## How we use the service

Our app uses the _recipient_ endpoints of the service since our app is for those receiving food, not providing. We register user accounts in the service with one account IDs per user. Then, we store these account IDs alongside the user data to establish the 1:1 mapping.

Our app utilizes the food listings API to find nearby food listings that a user can request food from. Then, a user can request a certain quantity of food using the food requests API.

## Demo URL
http://35.230.167.49:8080

## How to build, run and test
* To install the necessary dependencies: run `mvn clean install` with the working directory set to the one containing the `pom.xml` file
* To run the app: `mvn spring-boot:run`
* To run all tests: `mvn test`
* To see the test coverage: `mvn jacoco:report` and open target/site/jacoco/index.html in a web browser
* To run the style checker: `mvn checkstyle:check`
* To do static analysis with PMD: `mvn pmd:check`

# End-To-End testing 

Before doing any end-to-end tests, follow these steps

1. Do `mvn spring-boot:run` in a terminal with the client repository as the working directory
2. Open a browser and go to http://localhost:`port` based on the `port` that the application is using, which given in a message like `Tomcat started on port 8081 (http) with context path '/'` in the terminal

### User authentication/registration

1. Click on `Don't have an account? Register`
2. Make up a username `name` and password `pw` and register an account under these credentials
3. Check in the Firestore console that an entry was saved under `name` and `pw`, with a corresponding `accountId`
4. Log in using the credentials from step 2
5. The application should respond with a JavaScript alert `Log-in successful!`
6. Click on `OK`, and you should be redirected to the home page

### Searching for nearby listings and making a request

1. Enter `nyc` and select the first drop down from the search bar
2. Click on `Search`
3. You should see 2 listings, `Fruit Basket` and `Cereal`
4. Click on `Select` for `Fruit Basket`, and enter `1`.
5. You should see `Successfully made the food request.`
6. Check that a new entry was added to the `FoodRequest` table in the service database, and check that the foreign key `listingId` of this entry is the `listingId` for `Cereal`, 14.

### Making a request for more than what is listed

First, follow steps 1-6 in the instructons for `User authentication/registration`

1. Enter `nyc` and select the first drop down from the search bar
2. Click on `Select` for `Cereal`, and enter `1`.
3. You should see `Failed to submit.` in a red box above the text box.
4. Click on `Back to search` to navigate to the previous page with the listings
5. You should see that the quantity listed for `Cereal` is still 0.
