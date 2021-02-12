Feature: account registration

  Scenario: user set account
    Given The entered data is incorrect
    Then it should be rejected
    When data is valid
    Then account can be set up
    Then it should be activated
    And account can be removed