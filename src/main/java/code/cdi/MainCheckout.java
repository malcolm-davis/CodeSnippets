package code.cdi;

import javax.enterprise.inject.spi.CDI;

import org.jboss.weld.environment.se.Weld;

public class MainCheckout {

    public static void main(String[] args) {
        CDI<Object> container = new Weld().initialize();
        Checkout checkout = container.select(Checkout.class).get();
        checkout.finishCheckout();
    }

}
