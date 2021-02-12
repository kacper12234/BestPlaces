Feature: operations on the reaction

  Scenario: perform operations on the reaction
  When I save the reaction
  Then I want to change type
  Then change comment
  Then undo rate
  Then clear comment
  And finally delete reaction