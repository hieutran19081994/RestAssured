Feature: Create new user

  @Regression @Test @CreateUser @CU01
  Scenario: Create new user successfully
    Given User call api "CreateUser" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user001                    |
      | my_password  | password01                 |
      | email        | user001@mail.com           |
      | phone_number | 0979155626                 |
    Then User verify response "${RESPONSE}" has code 200
    And User verify response "${RESPONSE}" against schema "${ts.create.user.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath  | Value           | Compare Type |
      | $.status  | success         | EQUALS       |
      | $.message | user is created | EQUALS       |

  @Regression @Test @CreateUser @CU02
  Scenario: Create new user with all field invalid
    Given User call api "CreateUser" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | invalid_user               |
      | my_password  | invalid_pass               |
      | email        | invalid_mail               |
      | phone_number | invalid_phone              |
    Then User verify response "${RESPONSE}" has code 400
    And User verify response "${RESPONSE}" against schema "${ts.error.400.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath             | Value             | Compare Type |
      | $.status             | invalid           | EQUALS       |
      | $.message            | invalid parameter | EQUALS       |
      | $.detail.user_name    | detail error      | EQUALS       |
      | $.detail.password    | detail error      | EQUALS       |
      | $.detail.email       | detail error      | EQUALS       |
      | $.detail.phone_number | detail error      | EQUALS       |

  @Regression @Test @CreateUser @CU03
  Scenario: Create new user with username invalid
    Given User call api "CreateUser" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | invalid_user               |
      | my_password  | password01                 |
      | email        | user001@mail.com           |
      | phone_number | 0979155626                 |
    Then User verify response "${RESPONSE}" has code 400
    And User verify response "${RESPONSE}" against schema "${ts.error.400.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath          | Value        | Compare Type |
      | $.status             | invalid           | EQUALS       |
      | $.message            | invalid parameter | EQUALS       |
      | $.detail.user_name | detail error | EQUALS       |

  @Regression @Test @CreateUser @CU04
  Scenario: Create new user with password invalid
    Given User call api "CreateUser" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user001                    |
      | my_password  | invalid_pass               |
      | email        | user001@mail.com           |
      | phone_number | 0979155626                 |
    Then User verify response "${RESPONSE}" has code 400
    And User verify response "${RESPONSE}" against schema "${ts.error.400.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath          | Value        | Compare Type |
      | $.status             | invalid           | EQUALS       |
      | $.message            | invalid parameter | EQUALS       |
      | $.detail.password | detail error | EQUALS       |

  @Regression @Test @CreateUser @CU05
  Scenario: Create new user with email invalid
    Given User call api "CreateUser" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user01                     |
      | my_password  | password01                 |
      | email        | invalid_email              |
      | phone_number | 0979155626                 |
    Then User verify response "${RESPONSE}" has code 400
    And User verify response "${RESPONSE}" against schema "${ts.error.400.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath       | Value        | Compare Type |
      | $.status             | invalid           | EQUALS       |
      | $.message            | invalid parameter | EQUALS       |
      | $.detail.email | detail error | EQUALS       |

  @Regression @Test @CreateUser @CU06
  Scenario: Create new user with phone invalid
    Given User call api "CreateUser" from file "${ts.assignment.collection}" with params
      | root         | ${ts.assignment.host.name} |
      | my_user_name | user001                    |
      | my_password  | password01                 |
      | email        | user001@mail.com           |
      | phone_number | invalid_phone              |
    Then User verify response "${RESPONSE}" has code 400
    And User verify response "${RESPONSE}" against schema "${ts.error.400.schema}"
    And User verify json data from response "${RESPONSE}"
      | Jsonpath       | Value        | Compare Type |
      | $.status             | invalid           | EQUALS       |
      | $.message            | invalid parameter | EQUALS       |
      | $.detail.phone | detail error | EQUALS       |