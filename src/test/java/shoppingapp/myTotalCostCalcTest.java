package shoppingapp;

import edu.depaul.se433.shoppingapp.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class myTotalCostCalcTest {

    @Test
    @DisplayName("weakNormalOne")
    void weakNormal1(){
        PurchaseItem item = new PurchaseItem("Apples", 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        cart.addItem(item);
        Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);
        Double calcTotal = TotalCostCalculator.calculate(bill.getInitialCost(), "IL", shipping);
        assert(bill.total() == calcTotal);
    }

    @Test
    @DisplayName("weakNormalTwo")
    void weakNormal2(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        Bill bill = TotalCostCalculator.calculate(cart, "AZ", shipping);
        Double calcTotal = TotalCostCalculator.calculate(bill.getInitialCost(), "AZ", shipping);
        assert(bill.total() == calcTotal);
    }

    @Test
    @DisplayName("weakRobustOne")
    void weakRobust1(){
        ShoppingCart cart = null;
        ShippingType shipping = ShippingType.STANDARD;
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);
        });
    }

    @Test
    @DisplayName("weakRobustTwo")
    void weakRobust2(){
        PurchaseItem item = new PurchaseItem("Apples", 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        String state = null;
        cart.addItem(item);
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, state, shipping);
        });
    }

    @Test
    @DisplayName("weakRobustThree")
    void weakRobust3(){
        PurchaseItem item = new PurchaseItem("Apples", 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = null;
        cart.addItem(item);
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);
        });
    }

    @Test
    @DisplayName("strongNormalOne")
    void strongNormal1(){
        PurchaseItem item = new PurchaseItem("Apples", 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        cart.addItem(item);
        Bill bill = TotalCostCalculator.calculate(cart, "AZ", shipping);
        Double calcTotal = TotalCostCalculator.calculate(bill.getInitialCost(), "AZ", shipping);
        assert(bill.getTotal() == calcTotal);
    }

    @Test
    @DisplayName("strongNormalTwo")
    void strongNormal2(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);
        Double calcTotal = TotalCostCalculator.calculate(bill.getInitialCost(), "IL", shipping);
        assert(bill.getTotal() == calcTotal);
    }

    @Test
    @DisplayName("strongRobustOne")
    void strongRobust1(){
        PurchaseItem item = new PurchaseItem("Apples", 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        cart.addItem(item);
        Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);
        Double calcTotal = TotalCostCalculator.calculate(bill.getInitialCost(), "IL", shipping);
        assert(bill.getTotal() == calcTotal);
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
        Bill bill = TotalCostCalculator.calculate(cart, "AZ", shipping);
        Double calcTotal = TotalCostCalculator.calculate(bill.getInitialCost(), "AZ", shipping);
        assert(bill.getTotal() == calcTotal);
    }

    @Test
    @DisplayName("strongRobustThree")
    void strongRobust3(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);
        Double calcTotal = TotalCostCalculator.calculate(bill.getInitialCost(), "IL", shipping);
        assert(bill.getTotal() == calcTotal);
    }

    @Test
    @DisplayName("strongRobustFour")
    void strongRobust4(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        Bill bill = TotalCostCalculator.calculate(cart, "AZ", shipping);
        Double calcTotal = TotalCostCalculator.calculate(bill.getInitialCost(), "AZ", shipping);
        assert(bill.getTotal() == calcTotal);
    }

    @Test
    @DisplayName("strongRobustFive")
    void strongRobust5(){
        ShoppingCart cart = null;
        ShippingType shipping = ShippingType.NEXT_DAY;
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustSix")
    void strongRobust6(){
        ShoppingCart cart = null;
        ShippingType shipping = ShippingType.STANDARD;
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, "AZ", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustSeven")
    void strongRobust7(){
        ShoppingCart cart = null;
        ShippingType shipping = ShippingType.NEXT_DAY;
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, "AZ", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustEight")
    void strongRobust8(){
        PurchaseItem item = new PurchaseItem("Apples", 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        String state = null;
        cart.addItem(item);
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, state, shipping);
        });
    }

    @Test
    @DisplayName("strongRobustNine")
    void strongRobust9(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        String state = null;
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, state, shipping);
        });
    }

    @Test
    @DisplayName("strongRobustTen")
    void strongRobust10(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.NEXT_DAY;
        String state = null;
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, state, shipping);
        });
    }

    @Test
    @DisplayName("strongRobustEleven")
    void strongRobust11(){
        PurchaseItem item = new PurchaseItem("Apples", 1, 20);
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = null;
        cart.addItem(item);
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, "AZ", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustTwelve")
    void strongRobust12(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = null;
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, "IL", shipping);
        });
    }

    @Test
    @DisplayName("strongRobustThirteen")
    void strongRobust13(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = null;
        assertThrows(NullPointerException.class, ()->{
            Bill bill = TotalCostCalculator.calculate(cart, "AZ", shipping);
        });
    }

    @Test
    @DisplayName("Clears Cart and tests that itemCount reflects change")
    void cartClearCount(){
        ShoppingCart cart = new ShoppingCart();
        cart.clear();
        assert(cart.itemCount() == 0);
    }

    @Test
    @DisplayName("Checks that empty cart returns 0 for cost()")
    void cartCost(){
        ShoppingCart cart = new ShoppingCart();
        cart.clear();
        assert(cart.cost() == 0);
    }



    @Test
    @DisplayName("Bill for empty cart w/StandardShipping and IL tax")
    void emptyCartBill(){
        ShoppingCart cart = new ShoppingCart();
        ShippingType shipping = ShippingType.STANDARD;
        Bill x = TotalCostCalculator.calculate(cart, "IL", shipping);
        assert(x.equals(new Bill(0, 10, .6, 10.60)));
    }

    @Test
    @DisplayName("Computes cost for large purchase w/free shipping & no tax")
    void totalCostOver50(){
        ShippingType shipping = ShippingType.NEXT_DAY;
        Double x = TotalCostCalculator.calculate(125, "WA", shipping);
        assert(x == 125);
    }

    @ParameterizedTest
    @DisplayName("Tests that tax states return proper rate and total")
    @ValueSource(strings = {"CA", "IL", "NY"})
    void stateTax(String state){
        Double x =  TaxCalculator.calculate(25, state);
        assert(x == 1.5);
    }

    @Test
    @DisplayName("Tests that avg purchase is equal to purchase.getCost() avg")
    void testAgentAvgPurchase() throws IOException {
        PurchaseDBO realPDBO = new PurchaseDBO();
        PurchaseAgent testAgent = new PurchaseAgent(realPDBO);

        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName("Bob");
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(ShippingType.STANDARD.name());
        xP.setState("IL");

        Purchase yP = Purchase.make(xP.getCustomerName(), xP.getPurchaseDate(),
                xP.getCost(), xP.getState(), xP.getShipping());
        testAgent.save(xP);
        testAgent.save(yP);

        double avgP = testAgent.averagePurchase();
        double avgGetCost = (xP.getCost() + yP.getCost()) / 2;
        assertEquals(avgP, avgGetCost, 0.00001);
    }

    @Test
    @DisplayName("Tests the Purchase toString method")
    void purchaseToStringEquivalence() throws IOException {
        PurchaseDBO realPDBO = new PurchaseDBO();
        PurchaseAgent testAgent = new PurchaseAgent(realPDBO);

        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName("Bob");
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(ShippingType.STANDARD.name());
        xP.setState("IL");

        Purchase yP = Purchase.make(xP.getCustomerName(), xP.getPurchaseDate(),
                xP.getCost(), xP.getState(), xP.getShipping());
        testAgent.save(xP);
        testAgent.save(yP);
        assert(xP.toString().equals(yP.toString()));
    }

    @Test
    @DisplayName("Tests that getIdNum() returns the appropriate ID")
    void purchaseIDEquivalence() throws IOException {
        PurchaseDBO realPDBO = new PurchaseDBO();
        PurchaseAgent testAgent = new PurchaseAgent(realPDBO);

        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName("Bob");
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(ShippingType.STANDARD.name());
        xP.setState("IL");

        Purchase yP = Purchase.make(xP.getCustomerName(), xP.getPurchaseDate(),
                xP.getCost(), xP.getState(), xP.getShipping());
        yP.setIdNum(12314);
        testAgent.save(xP);
        testAgent.save(yP);
        assert (xP.getIdNum() == yP.getIdNum());
    }

    @Mock PurchaseDBO mockPDBO;

    @Test
    @DisplayName("Checks that testAgent.save() calls mock PurchaseDBO.save()")
    void mockPDBOSave(){
        mockPDBO = mock(PurchaseDBO.class);
        PurchaseAgent testAgent = new PurchaseAgent(mockPDBO);
        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName("Bob");
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(ShippingType.STANDARD.name());
        xP.setState("IL");
        testAgent.save(xP);
        verify(mockPDBO, times(1)).savePurchase(xP);
    }

    @Test
    @DisplayName("Checks that testAgent.getPurchases() calls mock PurchaseDBO.getPurchases()")
    void mockPDBOGetPurchases(){
        mockPDBO = mock(PurchaseDBO.class);
        PurchaseAgent testAgent = new PurchaseAgent(mockPDBO);
        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName("Bob");
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(ShippingType.STANDARD.name());
        xP.setState("IL");
        testAgent.save(xP);
        List<Purchase> purchaseList = testAgent.getPurchases();
        verify(mockPDBO, times(1)).getPurchases();
    }

    @Test
    @DisplayName("Checks that testAgent.avgPurchases() calls mock PurchaseDBO.getPurchases()")
    void mockPDBOGetForAvgPurchases(){
        mockPDBO = mock(PurchaseDBO.class);
        PurchaseAgent testAgent = new PurchaseAgent(mockPDBO);
        Purchase xP = new Purchase();
        xP.setCost(25);
        xP.setCustomerName("Bob");
        xP.setIdNum(12314);
        xP.setPurchaseDate(LocalDate.now());
        xP.setShipping(ShippingType.STANDARD.name());
        xP.setState("IL");
        testAgent.save(xP);
        Double avgP = testAgent.averagePurchase();
        verify(mockPDBO, times(1)).getPurchases();
    }
}
