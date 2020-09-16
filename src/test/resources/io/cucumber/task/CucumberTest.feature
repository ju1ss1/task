#Author: jussi.ruuska@gmail.com
#Feature: List of scenarios.
#Scenario: Business rule through list of steps.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
Feature: CucumberTest

  Scenario: test web page
    Given open "https://www.amazon.com" web page
    When test web page
    Then assert results
