Feature: Swiggy Search Product and Add to Cart Feature

#NegativeTestcase

Scenario: Check if the user is shown with Error message on giving invalid Location

Given The user is on the Webpage
When The user enters the invalid Delivery location and clicks on Find Food button
|	Location	|
|vxcx	|
Then Verify if the Error Message is displayed
|	ErrorMessage	|
|	Enter your delivery location |


#NegativeTestcase

Scenario: Check if the user is shown with Error message on giving no Location

Given The user is on the Webpage
When The user does not enter any Delivery location and clicks on Find Food button
Then Verify if the Error Message is displayed
|	ErrorMessage	|
|	Enter your delivery location |


#PositiveTestcase
Scenario: Check if the user is able to naviagte to Search Page and is able to Search for a product

Given The user is on the Webpage
When The user enters the Delivery location 
|	Location	|
| Noida Sector 19			|
Then The user selects the entered location from the dropdown
And The user is navigated on Products homepage 
Then Verify if the user selected location is displayed
|	Sector	| Location |
|	Sector 19 |  Noida, Uttar Pradesh 201301, India |
And The user clicks on the Search link
Then Verify if user is navigated on Search page
Then The user enters the Restaurant Name
|	Restaurant_Name	|
| Burger King	|
Then The searched restaurant is displayed
And The user clicks on the search result
Then Verify that user is navigated to restaurant Menu page
Then User selects a Product and clicks on ADD button
Then verify that product is added to cart and view cart icon is displayed
Then Verify that product and quantity dispalyed is same as what user selected
|	ProductName	| Quantity |
| Classic Cold Coffee	| 1|



