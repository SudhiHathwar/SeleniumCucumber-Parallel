@smoke

Feature: Get a quote Feature

  Scenario: Validate get a quote flow

    Given User lands on home page
    When User selects insurance type, enters zipCode and click on Get a Quote button
    When User enters user info details
    When User enters vehicle details
    When User enters driver details
    When User enter discount details
    Then User must be displayed with special attrntion modal


