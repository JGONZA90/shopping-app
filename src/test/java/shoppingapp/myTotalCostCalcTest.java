package shoppingapp;

import edu.depaul.se433.shoppingapp.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class myTotalCostCalcTest {
    final static transient String apples = "Apples";
    final static transient String custS = "Bob";
    final static transient String shipSS = "STANDARD";
    final static transient String dEqString = "Double Equality w/Delta";

    @Test
    @DisplayName("weakNormalOne")
    void weakNormal1(){
        PurchaseItem item = new PurchaseItem(apples, 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        cart.addItem(item);
        double tax = TaxCalculator.calculate(cart.cost(), "IL");
        double shipCost = TotalCostCalculator.getShippingCost(shipping, cart.cost());
        double calcTotal = TotalCostCalculator.calculate(cart.cost(), "IL", shipping);
        Bill bill = new Bill(cart.cost(), shipCost, tax, calcTotal);
        assertEquals(bill.total(), calcTotal, 0.00001, dEqString);
    }

    @Test
    @DisplayName("weakNormalTwo")
    void weakNormal2(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        double tax = TaxCalculator.calculate(cart.cost(), "AZ");
        double shipCost = TotalCostCalculator.getShippingCost(shipping, cart.cost());
        double calcTotal = TotalCostCalculator.calculate(cart.cost(), "AZ", shipping);
        Bill bill = new Bill(cart.cost(), shipCost, tax, calcTotal);
        assertEquals(bill.total(), calcTotal, 0.00001, dEqString);
    }

    @Test
    @DisplayName("weakRobustOne")
    void weakRobust1(){
        ShoppingCart cart = null;
        ShippingType shipping = ShippingType.STANDARD;
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, "IL", shipping);
        });
    }

    @Test
    @DisplayName("weakRobustTwo")
    void weakRobust2(){
        PurchaseItem item = new PurchaseItem(apples, 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        String state = null;
        cart.addItem(item);
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, state, shipping);
        });
    }

    @Test
    @DisplayName("weakRobustThree")
    void weakRobust3(){
        PurchaseItem item = new PurchaseItem(apples, 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = null;
        cart.addItem(item);
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, "IL", shipping);
        });
    }

    @Test
    @DisplayName("strongNormalOne")
    void strongNormal1(){
        PurchaseItem item = new PurchaseItem(apples, 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        cart.addItem(item);
        double tax = TaxCalculator.calculate(cart.cost(), "AZ");
        double shipCost = TotalCostCalculator.getShippingCost(shipping, cart.cost());
        double calcTotal = TotalCostCalculator.calculate(cart.cost(), "AZ", shipping);
        Bill bill = new Bill(cart.cost(), shipCost, tax, calcTotal);
        assertEquals(bill.total(), calcTotal, 0.00001, dEqString);
    }

    @Test
    @DisplayName("strongNormalTwo")
    void strongNormal2(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        double tax = TaxCalculator.calculate(cart.cost(), "CA");
        double shipCost = TotalCostCalculator.getShippingCost(shipping, cart.cost());
        double calcTotal = TotalCostCalculator.calculate(cart.cost(), "CA", shipping);
        Bill bill = new Bill(cart.cost(), shipCost, tax, calcTotal);
        assertEquals(bill.total(), calcTotal, 0.00001, dEqString);
    }

    @Test
    @DisplayName("strongRobustOne")
    void strongRobust1(){
        PurchaseItem item = new PurchaseItem(apples, 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        cart.addItem(item);
        double tax = TaxCalculator.calculate(cart.cost(), "NY");
        double shipCost = TotalCostCalculator.getShippingCost(shipping, cart.cost());
        double calcTotal = TotalCostCalculator.calculate(cart.cost(), "NY", shipping);
        Bill bill = new Bill(cart.cost(), shipCost, tax, calcTotal);
        assertEquals(bill.total(), calcTotal, 0.00001, dEqString);
    }

    @Test
    @DisplayName("strongRobustTwo")
    void strongRobust2(){
        PurchaseItem greenApples = new PurchaseItem();
        greenApples.setName("Green Apples");
        greenApples.setUnitPrice(1.0);
        greenApples.setQuantity(20);
        PurchaseItem greenApplesSmall = new PurchaseItem(greenApples.getName() + " Small",
                greenApples.getUnitPrice() + 3, greenApples.getQuantity());
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        cart.addItem(greenApples);
        cart.addItem(greenApplesSmall);
        double tax = TaxCalculator.calculate(cart.cost(), "AZ");
        double shipCost = TotalCostCalculator.getShippingCost(shipping, cart.cost());
        double calcTotal = TotalCostCalculator.calculate(cart.cost(), "AZ", shipping);
        Bill bill = new Bill(cart.cost(), shipCost, tax, calcTotal);
        assertEquals(bill.total(), calcTotal, 0.00001, dEqString);
    }

    @Test
    @DisplayName("strongRobustThree")
    void strongRobust3(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        double tax = TaxCalculator.calculate(cart.cost(), "IL");
        double shipCost = TotalCostCalculator.getShippingCost(shipping, cart.cost());
        double calcTotal = TotalCostCalculator.calculate(cart.cost(), "IL", shipping);
        Bill bill = new Bill(cart.cost(), shipCost, tax, calcTotal);
        assertEquals(bill.total(), calcTotal, 0.00001, dEqString);
    }

    @Test
    @DisplayName("strongRobustFour")
    void strongRobust4(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        double tax = TaxCalculator.calculate(cart.cost(), "AZ");
        double shipCost = TotalCostCalculator.getShippingCost(shipping, cart.cost());
        double calcTotal = TotalCostCalculator.calculate(cart.cost(), "AZ", shipping);
        Bill bill = new Bill(cart.cost(), shipCost, tax, calcTotal);
        assertEquals(bill.total(), calcTotal, 0.00001, dEqString);
    }

    @Test
    @DisplayName("strongRobustFive")
    void strongRobust5(){
        ShoppingCart cart = null;
        ShippingType shipping = ShippingType.NEXT_DAY;
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, "IL", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustSix")
    void strongRobust6(){
        ShoppingCart cart = null;
        ShippingType shipping = ShippingType.STANDARD;
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, "AZ", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustSeven")
    void strongRobust7(){
        ShoppingCart cart = null;
        ShippingType shipping = ShippingType.NEXT_DAY;
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, "AZ", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustEight")
    void strongRobust8(){
        PurchaseItem item = new PurchaseItem(apples, 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        String state = null;
        cart.addItem(item);
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, state, shipping);
        });
    }

    @Test
    @DisplayName("strongRobustNine")
    void strongRobust9(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        String state = null;
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, state, shipping);
        });
    }

    @Test
    @DisplayName("strongRobustTen")
    void strongRobust10(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        String state = null;
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, state, shipping);
        });
    }

    @Test
    @DisplayName("strongRobustEleven")
    void strongRobust11(){
        PurchaseItem item = new PurchaseItem(apples, 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = null;
        cart.addItem(item);
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, "AZ", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustTwelve")
    void strongRobust12(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = null;
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, "IL", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustThirteen")
    void strongRobust13(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = null;
        assertThrows(NullPointerException.class, ()->{
            TotalCostCalculator.calculate(cart, "AZ", shipping);
        });
    }

    @Test
    @DisplayName("Clears Cart and tests that itemCount reflects change")
    void cartClearCount(){
        ShoppingCart cart = new ShoppingCart();
        cart.clear();
        assertEquals(0, cart.itemCount(), "Expecting Zero");
    }

    @Test
    @DisplayName("Checks that empty cart returns 0 for cost()")
    void cartCost(){
        ShoppingCart cart = new ShoppingCart();
        cart.clear();
        assertEquals(0, cart.cost(), "Expecting Zero");
    }



    @Test
    @DisplayName("Bill for empty cart w/StandardShipping and IL tax")
    void emptyCartBill(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        Bill x = TotalCostCalculator.calculate(cart, "IL", shipping);
        assertEquals(new Bill(0, 10, .6, 10.60), x, "Equality");
    }

    @Test
    @DisplayName("Computes cost for large purchase w/free shipping & no tax")
    void totalCostOver50(){
        ShippingType shipping = ShippingType.NEXT_DAY;
        double x = TotalCostCalculator.calculate(125, "WA", shipping);
        assertEquals(125, x, 0.00001, dEqString);
    }

    @ParameterizedTest
    @DisplayName("Tests that tax states return proper rate and total")
    @ValueSource(strings = {"CA", "IL", "NY"})
    void stateTax(String state){
        double x =  TaxCalculator.calculate(25, state);
        assertEquals(1.5, x, 0.00001, dEqString);
    }

    @Test
    @DisplayName("Tests that avg purchase is equal to purchase.getCost() avg")
    void testAgentAvgPurchase() throws IOException {
        PurchaseDBO realPDBO = new PurchaseDBO();
        PurchaseAgent testAgent = new PurchaseAgent(realPDBO);

        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName(custS);
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(shipSS);
        xP.setState("IL");

        Purchase yP = new Purchase();
        yP.setCost(xP.getCost());
        yP.setCustomerName(xP.getCustomerName());
        yP.setIdNum(98765);
        yP.setPurchaseDate(xP.getPurchaseDate());
        yP.setShipping(xP.getShipping());
        yP.setState(xP.getState());

        testAgent.save(xP);
        testAgent.save(yP);

        double avgP = testAgent.averagePurchase();
        double avgGetCost = (xP.getCost() + yP.getCost()) / 2;
        assertEquals(avgP, avgGetCost, 0.00001, dEqString);
    }

    @Test
    @DisplayName("Tests the Purchase toString method")
    void purchaseToStringEquivalence() throws IOException {
        PurchaseDBO realPDBO = new PurchaseDBO();
        PurchaseAgent testAgent = new PurchaseAgent(realPDBO);

        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName(custS);
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(shipSS);
        xP.setState("IL");

        Purchase yP = new Purchase();
        yP.setCost(xP.getCost());
        yP.setCustomerName(xP.getCustomerName());
        yP.setIdNum(98765);
        yP.setPurchaseDate(xP.getPurchaseDate());
        yP.setShipping(xP.getShipping());
        yP.setState(xP.getState());

        testAgent.save(xP);
        testAgent.save(yP);
        assertEquals(xP.toString(), yP.toString(), "Purchase toString Equality");
    }

    @Test
    @DisplayName("Tests that getIdNum() returns the appropriate ID")
    void purchaseIDEquivalence() throws IOException {
        PurchaseDBO realPDBO = new PurchaseDBO();
        PurchaseAgent testAgent = new PurchaseAgent(realPDBO);

        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName(custS);
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(shipSS);
        xP.setState("IL");

        Purchase yP = new Purchase();
        yP.setCost(xP.getCost());
        yP.setCustomerName(xP.getCustomerName());
        yP.setIdNum(12314);
        yP.setPurchaseDate(xP.getPurchaseDate());
        yP.setShipping(xP.getShipping());
        yP.setState(xP.getState());

        testAgent.save(xP);
        testAgent.save(yP);
        assertEquals(xP.getIdNum(), yP.getIdNum(), "ID Equality");
    }

    @Mock transient PurchaseDBO mockPDBO;

    @Test
    @DisplayName("Checks that testAgent.save() calls mock PurchaseDBO.save()")
    void mockPDBOSave(){
        mockPDBO = mock(PurchaseDBO.class);
        PurchaseAgent testAgent = new PurchaseAgent(mockPDBO);
        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName(custS);
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(shipSS);
        xP.setState("IL");
        testAgent.save(xP);
        verify(mockPDBO).savePurchase(xP);
    }

    @Test
    @DisplayName("Checks that testAgent.getPurchases() calls mock PurchaseDBO.getPurchases()")
    void mockPDBOGetPurchases(){
        mockPDBO = mock(PurchaseDBO.class);
        PurchaseAgent testAgent = new PurchaseAgent(mockPDBO);
        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName(custS);
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(shipSS);
        xP.setState("IL");
        testAgent.save(xP);
        testAgent.getPurchases();
        verify(mockPDBO).getPurchases();
    }

    @Test
    @DisplayName("Checks that testAgent.avgPurchases() calls mock PurchaseDBO.getPurchases()")
    void mockPDBOGetForAvgPurchases(){
        mockPDBO = mock(PurchaseDBO.class);
        PurchaseAgent testAgent = new PurchaseAgent(mockPDBO);
        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName(custS);
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(shipSS);
        xP.setState("IL");
        testAgent.save(xP);
        testAgent.averagePurchase();
        verify(mockPDBO).getPurchases();
    }
}
