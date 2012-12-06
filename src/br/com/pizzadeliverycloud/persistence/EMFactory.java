package br.com.pizzadeliverycloud.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {

	private static final EntityManagerFactory emfInstance =
        Persistence.createEntityManagerFactory("pizzaDeliveryPersistenceUnit");

    private EMFactory() {}

    public static EntityManagerFactory getInstance() {
        return emfInstance;
    }
}
