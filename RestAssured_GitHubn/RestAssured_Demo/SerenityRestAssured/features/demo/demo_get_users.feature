Feature: Create User

  @Regression @Demo @GetUsers
  Scenario: Demo get all users
    Given User call api "DemoGetUsers" from file "${demo.postman.collection}" with params
      | end_point | ${demo.end.point} |
    Then User verify response "${RESPONSE}" has code 200
    And User verify response "${RESPONSE}" against schema "${demo.get.users.schema}"
    When User collect values from response "${RESPONSE}"
      | Key      | Jsonpath |
      | ${total} | $.total  |
    Then User verify json data from response "${RESPONSE}"
      | Jsonpath | Value    | Compare Type |
      | $.page   | 2        | EQUALS       |
      | $.total  | ${total} | EQUALS       |
