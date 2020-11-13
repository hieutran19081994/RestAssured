Feature: Update user

  @Regression @Test @UpdateUser @UU01
  Scenario: Update user successfully
    Given User call api "Login" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user001                    |
      | my_password  | password01                 |
    Then User verify response "${RESPONSE}" has code 200
    When User collect values from response "${RESPONSE}"
      | Key      | Jsonpath |
      | ${token} | $.token  |
    And User call api "UpdateUser" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | token        | ${token}                   |
      | update_email | update_email@mail.com      |
      | update_phone | 0898155626                 |
    Then User verify response "${RESPONSE}" has code 200
    And User verify response "${RESPONSE}" against schema "${ts.basic.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath  | Value          | Compare Type |
      | $.status  | success        | EQUALS       |
      | $.message | update success | EQUALS       |

  @Regression @Test @UpdateUser @UU02
  Scenario: Update only email for user successfully
    Given User call api "Login" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user001                    |
      | my_password  | password01                 |
    Then User verify response "${RESPONSE}" has code 200
    When User collect values from response "${RESPONSE}"
      | Key      | Jsonpath |
      | ${token} | $.token  |
    And User call api "UpdateUserEmail" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | token        | ${token}                   |
      | update_email | update_email@mail.com      |
    Then User verify response "${RESPONSE}" has code 200
    And User verify response "${RESPONSE}" against schema "${ts.basic.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath  | Value          | Compare Type |
      | $.status  | success        | EQUALS       |
      | $.message | update success | EQUALS       |

  @Regression @Test @UpdateUser @UU03
  Scenario: Update only phone for user successfully
    Given User call api "Login" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user001                    |
      | my_password  | password01                 |
    Then User verify response "${RESPONSE}" has code 200
    When User collect values from response "${RESPONSE}"
      | Key      | Jsonpath |
      | ${token} | $.token  |
    And User call api "UpdateUserEmail" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | token        | ${token}                   |
      | update_phone | 0989155626                 |
    Then User verify response "${RESPONSE}" has code 200
    And User verify response "${RESPONSE}" against schema "${ts.basic.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath  | Value          | Compare Type |
      | $.status  | success        | EQUALS       |
      | $.message | update success | EQUALS       |

  @Regression @Test @UpdateUser @UU04
  Scenario: Update user with wrong token
    Given User call api "UpdateUser" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | token        | ${token}                   |
      | update_phone | invalid_phone              |
      | update_email | invalid_mail               |
    Then User verify response "${RESPONSE}" has code 400
    And User verify response "${RESPONSE}" against schema "${ts.error.400.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath  | Value         | Compare Type |
      | $.status  | forbidden     | EQUALS       |
      | $.message | invalid token | EQUALS       |

  @Regression @Test @UpdateUser @UU05
  Scenario: Update user with invalid data
    Given User call api "UpdateUser" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | token        | invalid_token              |
      | update_phone | 0989155626                 |
      | update_email | update_email@mail.com      |
    Then User verify response "${RESPONSE}" has code 403
    And User verify response "${RESPONSE}" against schema "${ts.basic.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath             | Value             | Compare Type |
      | $.status             | invalid           | EQUALS       |
      | $.message            | invalid parameter | EQUALS       |
      | $.detail.email       | detail error      | EQUALS       |
      | $.detail.phone_number | detail error      | EQUALS       |