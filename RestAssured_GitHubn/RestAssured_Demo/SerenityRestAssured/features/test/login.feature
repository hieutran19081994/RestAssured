Feature: Login

  @Regression @Test @Login @L01
  Scenario: Login successfully
    Given User call api "Login" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user001                    |
      | my_password  | password01                 |
    Then User verify response "${RESPONSE}" has code 200
    And User verify response "${RESPONSE}" against schema "${ts.login.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath  | Value         | Compare Type |
      | $.status  | success       | EQUALS       |
      | $.message | login success | EQUALS       |

  @Regression @Test @Login @L02
  Scenario: Login with wrong username
    Given User call api "Login" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | wrong_username             |
      | my_password  | password01                 |
    Then User verify response "${RESPONSE}" has code 401
    And User verify response "${RESPONSE}" against schema "${ts.error.401.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath  | Value                              | Compare Type |
      | $.status  | unauthorized                       | EQUALS       |
      | $.message | user_name or password is incorrect | EQUALS       |

  @Regression @Test @Login @L04
  Scenario: Login with wrong password
    Given User call api "Login" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user001                    |
      | my_password  | wrong_password             |
    Then User verify response "${RESPONSE}" has code 401
    And User verify response "${RESPONSE}" against schema "${ts.error.401.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath  | Value                              | Compare Type |
      | $.status  | unauthorized                       | EQUALS       |
      | $.message | user_name or password is incorrect | EQUALS       |

  @Regression @Test @Login @L05
  Scenario: Login with wrong both username and password
    Given User call api "Login" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | wrong_username             |
      | my_password  | wrong_password             |
    Then User verify response "${RESPONSE}" has code 401
    And User verify response "${RESPONSE}" against schema "${ts.error.401.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath  | Value                              | Compare Type |
      | $.status  | unauthorized                       | EQUALS       |
      | $.message | user_name or password is incorrect | EQUALS       |

  @Regression @Test @Login @L06
  Scenario: Rate Limit
    Given User login 10 times
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user001             |
      | my_password  | wrong_password             |
    Then User verify response "${RESPONSE}" has code 429
    And User verify response "${RESPONSE}" against schema "${ts.error.429.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath       | Value                               | Compare Type |
      | $.status       | lock                                | EQUALS       |
      | $.message      | too many requests failed, try again | EQUALS       |
      | $.lock_seconds | 300                                 | EQUALS       |