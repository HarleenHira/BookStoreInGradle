Feature: End to End testing of bookstore api's
@smokeTest
  Background: User generates token for authorization
   Given : I am an authorized user
@EndToEndTest
    Scenario: Authorized user is able to add and remove a book
     Given : A list of books are available
      When : I add a book
      Then : the book is added
      When : I remove a book
      Then : the book is removed