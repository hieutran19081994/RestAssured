Feature: Get all users

  @Regression @Demo @DemoCreateUser
  Scenario: Demo create user
    Given User call api "DemoCreateUser" from file "${demo.postman.collection}" with params
      | end_point | ${demo.end.point}   |
      | name      | huyphan             |
      | job       | automation engineer |
    Then User verify response "${RESPONSE}" has code 201
    And User verify response "${RESPONSE}" against schema "${demo.create.user.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath | Value               | Compare Type |
      | $.name   | huyphan             | EQUALS       |
      | $.job    | automation engineer | EQUALS       |
