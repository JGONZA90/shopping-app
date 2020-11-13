Feature: Total Cost Calculation
  Scenario: A customer w/Cart total less than $51, in a State w/Tax, wants Next Day Shipping
    Given Customer has 40 dollars worth of items in Cart
    And Customer lives in a State with Tax
    And Customer wants Next Day Shipping
    Then Customer Bill total is 68.9