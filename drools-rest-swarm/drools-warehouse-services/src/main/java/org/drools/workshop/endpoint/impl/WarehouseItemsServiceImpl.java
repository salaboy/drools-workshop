package org.drools.workshop.endpoint.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.drools.workshop.endpoint.api.WarehouseItemsService;
import org.drools.workshop.endpoint.exception.BusinessException;
import org.drools.workshop.model.Item;
import org.keycloak.KeycloakPrincipal;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class WarehouseItemsServiceImpl implements WarehouseItemsService {

    private List<Item> items = new ArrayList<Item>(100);

    @Context
    SecurityContext context;

    public WarehouseItemsServiceImpl() {
    }

    @Override
    public void newItem(Item item) throws BusinessException {
        KeycloakPrincipal principal = (KeycloakPrincipal) context.getUserPrincipal();
        if (principal != null && principal.getKeycloakSecurityContext() != null) {
            items.add(item);
        } else {
            throw new BusinessException("You don't have the appropriate permession to access this service");
        }
    }

    @Override
    public void removeItem(String id) throws BusinessException {
        KeycloakPrincipal principal = (KeycloakPrincipal) context.getUserPrincipal();
        if (principal != null && principal.getKeycloakSecurityContext() != null) {
            for (Item i : items) {
                if (i.getId().equals(id)) {
                    items.remove(i);
                }
            }
        } else {
            throw new BusinessException("You don't have the appropriate permession to access this service");
        }
    }

    @Override
    public List<Item> getAllItems() throws BusinessException {
        KeycloakPrincipal principal = (KeycloakPrincipal) context.getUserPrincipal();
        if (principal != null && principal.getKeycloakSecurityContext() != null) {
            return items;
        } else {
            throw new BusinessException("You don't have the appropriate permession to access this service");
        }
    }

    @Override
    public void init() throws BusinessException {
        KeycloakPrincipal principal = (KeycloakPrincipal) context.getUserPrincipal();
        if (principal != null && principal.getKeycloakSecurityContext() != null) {
            for (int i = 0; i < 200; i++) {
                items.add(new Item(UUID.randomUUID().toString(), "Item " + i, (int) new Double(100 * Math.random()).intValue()));
            }
        } else {
            throw new BusinessException("You don't have the appropriate permession to access this service");
        }
    }

    @Override
    public Item getItem(String id) throws BusinessException {
        KeycloakPrincipal principal = (KeycloakPrincipal) context.getUserPrincipal();
        if (principal != null && principal.getKeycloakSecurityContext() != null) {
            for (Item i : items) {
                if (i.getId().equals(id)) {
                    return i;
                }
            }
        } else {
            throw new BusinessException("You don't have the appropriate permession to access this service");
        }
        return null;
    }
    
    

}
