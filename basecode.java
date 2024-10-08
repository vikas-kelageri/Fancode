import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FanCodeTodoTest {

    // Base URL for the APIs
    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";

    @Test
    public void validateFanCodeUserTodoCompletion() {
        // Step 1: Fetch all users
        Response usersResponse = given()
                .baseUri(BASE_URL)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<Map<String, Object>> users = usersResponse.jsonPath().getList("");

        // Step 2: Filter users based on FanCode city criteria (lat between -40 to 5, long between 5 to 100)
        List<Map<String, Object>> fanCodeUsers = users.stream()
                .filter(user -> {
                    Map<String, String> geo = (Map<String, String>) ((Map<String, Object>) user.get("address")).get("geo");
                    double lat = Double.parseDouble(geo.get("lat"));
                    double lng = Double.parseDouble(geo.get("lng"));
                    return lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100;
                })
                .collect(Collectors.toList());

        // Step 3: For each FanCode user, validate that completed tasks are greater than 50%
        for (Map<String, Object> user : fanCodeUsers) {
            int userId = (int) user.get("id");

            // Step 4: Fetch user's todos
            Response todosResponse = given()
                    .baseUri(BASE_URL)
                    .queryParam("userId", userId)
                    .when()
                    .get("/todos")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            List<Map<String, Object>> todos = todosResponse.jsonPath().getList("");

            long completedTasks = todos.stream()
                    .filter(todo -> (boolean) todo.get("completed"))
                    .count();

            long totalTasks = todos.size();

            // Step 5: Validate that completed tasks are greater than 50%
            double completionPercentage = (double) completedTasks / totalTasks * 100;

            System.out.println("User ID: " + userId + ", Completed: " + completionPercentage + "%");

            assertTrue(completionPercentage > 50, "User " + userId + " should have more than 50% tasks completed");
        }
    }
}
