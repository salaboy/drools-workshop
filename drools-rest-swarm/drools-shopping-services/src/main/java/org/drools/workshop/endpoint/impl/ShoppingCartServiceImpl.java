package org.drools.workshop.endpoint.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.drools.core.RuleBaseConfiguration;
import org.drools.workshop.endpoint.api.ShoppingCartService;
import org.drools.workshop.endpoint.exception.BusinessException;
import org.drools.workshop.model.Item;
import org.drools.workshop.model.ShoppingCart;
import org.drools.workshop.model.User;
import org.kie.api.KieBase;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.runtime.KieContainer;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Inject
    @KReleaseId(groupId = "org.drools.workshop", artifactId = "drools-shopping-kjar", version = "1.0-SNAPSHOT")
    private KieContainer kContainer;

    private Map<String, KieSession> shoppingCarts = new HashMap<String, KieSession>();
    private Map<String, String> userCarts = new HashMap<String, String>();

    public ShoppingCartServiceImpl() {
    }

    @Override
    public ShoppingCart newShoppingCart(User user) throws BusinessException {
        String cartId = UUID.randomUUID().toString();
        RuleBaseConfiguration conf = new RuleBaseConfiguration();
        conf.setAssertBehaviour(RuleBaseConfiguration.AssertBehaviour.EQUALITY);
        KieBase kBase = kContainer.newKieBase(conf);
        KieSession kSession = kBase.newKieSession();
        shoppingCarts.put(cartId, kSession);
        userCarts.put(cartId, user.getUserId());
        return new ShoppingCart(cartId);
    }

    @Override
    public void addItem(String id, Item item) throws BusinessException {
        if (shoppingCarts.get(id) == null) {
            throw new BusinessException("The cart Id is not valid!");
        }
        shoppingCarts.get(id).insert(item);
        shoppingCarts.get(id).fireAllRules();
    }

    @Override
    public void removeItem(String id, Item item) throws BusinessException {
        if (shoppingCarts.get(id) == null) {
            throw new BusinessException("The cart Id is not valid!");
        }
        FactHandle factHandle = shoppingCarts.get(id).getFactHandle(item);
        shoppingCarts.get(id).delete(factHandle);
        QueryResults results = shoppingCarts.get(id).getQueryResults("Get CatItems By Item", item);
        String[] keys = results.getIdentifiers();
        for (QueryResultsRow row : results) {
            shoppingCarts.get(id).delete(row.getFactHandle(keys[0]));
        }
        shoppingCarts.get(id).fireAllRules();
    }

    @Override
    public void checkout(String id) throws BusinessException {
        if (shoppingCarts.get(id) == null) {
            throw new BusinessException("The cart Id is not valid!");
        }
        System.out.println(">> Thanks for buying with us.");
        for (Object o : shoppingCarts.get(id).getObjects()) {
            System.out.println("\t\t > " + o);
        }
        shoppingCarts.get(id).dispose();
        shoppingCarts.remove(id);

    }

    @Override
    public void empty(String id) throws BusinessException {
        if (shoppingCarts.get(id) == null) {
            throw new BusinessException("The cart Id is not valid!");
        }
        KieSession kSession = kContainer.newKieSession();
        shoppingCarts.put(id, kSession);
    }

    @Override
    public List<Item> getItems(String id) throws BusinessException {
        if (shoppingCarts.get(id) == null) {
            throw new BusinessException("The cart Id is not valid!");
        }
        List<Item> items = new ArrayList<Item>();
        ObjectFilter filter = new ObjectFilter() {

            @Override
            public boolean accept(Object o) {
                return o instanceof Item;
            }

        };
        for (Object o : shoppingCarts.get(id).getObjects(filter)) {
            items.add((Item) o);
        }
        return items;
    }

    @Override
    public void removeShoppingCart(String id) throws BusinessException {
        if (shoppingCarts.get(id) == null) {
            throw new BusinessException("The cart Id is not valid!");
        }
        shoppingCarts.get(id).dispose();
        shoppingCarts.remove(id);
    }

    @Override
    public List<ShoppingCart> getShoppingCarts() throws BusinessException {
        List<ShoppingCart> carts = new ArrayList<ShoppingCart>(shoppingCarts.keySet().size());
        for (String id : shoppingCarts.keySet()) {
            QueryResults results = shoppingCarts.get(id).getQueryResults("Get CatItems Number");
            int size = 0;
            for (QueryResultsRow row : results) {
                size = (Integer) row.get("$size");
            }
            carts.add(new ShoppingCart(id, size, userCarts.get(id)));
        }
        return carts;
    }

}
