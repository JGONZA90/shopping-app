package shoppingapp.step_definitions;

import edu.depaul.se433.shoppingapp.ShippingType;
import edu.depaul.se433.shoppingapp.TotalCostCalculator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class totalCostCalcSteps {

    private int cartTotal = 0;
    private String taxState = "";
    private ShippingType shipping;

    @Given("Customer has {int} dollars worth of items in Cart")
    public void customer_has_dollars_worth_of_items_in_cart(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        cartTotal = int1;
    }

    @Given("Customer lives in a State with Tax")
    public void customer_lives_in_illinois_a_state_with_tax() {
        // Write code here that turns the phrase above into concrete actions
        List<String> statesWithTax = new ArrayList<String>();
        statesWithTax.add("CA");
        statesWithTax.add("IL");
        statesWithTax.add("NY");
        taxState = statesWithTax.get(new Random().nextInt(statesWithTax.size()));
    }

    @Given("Customer wants Next Day Shipping")
    public void customer_wants_next_day_shipping() {
        // Write code here that turns the phrase above into concrete actions
        shipping = ShippingType.NEXT_DAY;
    }

    @Then("Customer Bill total is {double}")
    public void customer_bill_total_is(Double double1) {
        // Write code here that turns the phrase above into concrete actions
        int totalCost = (int)TotalCostCalculator.calculate(cartTotal, taxState, shipping);
        int inputTotal = double1.intValue();
        assert(totalCost == inputTotal);
    }
}
