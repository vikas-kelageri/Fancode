# Fancode
Task:
Scenario: All users from the city "FanCode" should have more than half of their todos tasks completed.
Steps:
Fetch Users: Retrieve all users from /users API.
Filter Users: Identify users from the city "FanCode" (latitude between -40 and 5, longitude between 5 and 100).
Fetch Todos: For each user identified as belonging to FanCode, fetch their todos from the /todos API.
Validate Task Completion: Ensure that each user has completed more than 50% of their todos.
APIs Used:
/users: Fetches users data, including geographical information.
/todos: Fetches todo tasks assigned to users.

Setup Instructions
1. Clone the Repository
Clone the project repository to your local machine:
git clone https://github.com/yourusername/FanCodeCityApiAutomation.git
cd FanCodeCityApiAutomation
2. Install Dependencies
Use Maven to install the required dependencies. Ensure you're in the project root directory.
mvn clean install
This will download all the necessary dependencies, such as Rest Assured, JUnit, and Jackson.

3. Run the Tests
To run the automated test, execute the following Maven command:
mvn test
This will run the test case that validates users from the city "FanCode" and their completed todos.

4. Test Report
By default, Maven generates a test report after the test run. You can find the report in the target/surefire-reports directory. To view the results:
open target/surefire-reports/index.html
