package stepDefinitions;

import apiEngine.EndPoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import modals.requests.AddBookRequest;
import modals.requests.AuthRequest;
import modals.requests.CollectionOfIsbn;
import modals.requests.RemoveBookRequest;
import modals.response.*;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class Steps {
    private String BASE_URL = "https://bookstore.toolsqa.com";
    private String userName = "iop89";
    private String password = "Test@@123";
    private String userId;
    private String token;
    private String isbn;
    EndPoints endpoints = new EndPoints(BASE_URL);
    Response response;

    @Given(": I am an authorized user")
    public void i_am_an_authorized_user() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUserName(userName);
        authRequest.setPassword(password);
        response = endpoints.createUser(authRequest);

        System.out.println("The create user status code is" + response.getStatusCode());

        Assert.assertEquals(201, response.getStatusCode());
        CreateUserResponse createUserResponse = new CreateUserResponse();
        CreateUserResponse response1 = response.getBody().as(CreateUserResponse.class);
        userId = response1.getUserID();
        System.out.println("The userId id " + userId);
        Response generateTokenResponse = endpoints.generateToken(authRequest);
        Assert.assertEquals(200, generateTokenResponse.getStatusCode());

        Token tokenResponse = generateTokenResponse.getBody().as(Token.class);
        token = tokenResponse.getToken();
        System.out.println("The token generated is " + tokenResponse.getToken());
        Assert.assertEquals("Success", tokenResponse.getStatus());

    }

    @Given(": A list of books are available")
    public void a_list_of_books_are_available() {
        response = endpoints.getBooksFromList(token);
        Assert.assertEquals(200, response.getStatusCode());
        BookResponse bookResponse = response.getBody().as(BookResponse.class);
        Assert.assertEquals(false, bookResponse.getBooks().isEmpty());
        Book book = bookResponse.getBooks().get(0);
        System.out.println("the books is" + book.getTitle());
        Assert.assertEquals(true, bookResponse.getBooks().size() > 0);


    }

    @When(": I add a book")
    public void i_add_a_book() {

        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setUserId(userId);


        CollectionOfIsbn collectionOfIsbn = new CollectionOfIsbn();
        collectionOfIsbn.setIsbn("9781449325862");

        List<CollectionOfIsbn> collectionOfIsbns = new ArrayList<>();
        collectionOfIsbns.add(collectionOfIsbn);


        addBookRequest.setCollectionOfIsbns(collectionOfIsbns);
        response = endpoints.addBooks(addBookRequest, token);

        Assert.assertEquals(201, response.getStatusCode());
        BookResponse bookResponse = response.getBody().as(BookResponse.class);
        Book book = bookResponse.getBooks().get(0);
        System.out.println("The isbn is " + book.getIsbn());
        isbn = book.getIsbn();
    }

    @Then(": the book is added")
    public void the_book_is_added() {
        response = endpoints.getAddedBook(token, isbn);
        Assert.assertEquals(200, response.getStatusCode());
        Book book = response.getBody().as(Book.class);
        System.out.println("The author is " + book.getAuthor());


    }

    @When(": I remove a book")
    public void i_remove_a_book() {

        RemoveBookRequest removeBookRequest = new RemoveBookRequest();
        removeBookRequest.setIsbn(isbn);
        removeBookRequest.setUserId(userId);
        response = endpoints.removeBook(removeBookRequest, token);
        Assert.assertEquals(204, response.getStatusCode());

    }

    @Then(": the book is removed")
    public void the_book_is_removed() {
        response = endpoints.verifyRemovedBook(userId, token);
        Assert.assertEquals(200, response.getStatusCode());
        UserAccount userAccount = response.getBody().as(UserAccount.class);
        String username = userAccount.getUsername();
        System.out.println("the username is " + userAccount.getUsername());
    }

}
