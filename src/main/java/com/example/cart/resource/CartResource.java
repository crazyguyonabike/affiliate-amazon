package com.example.cart.resource;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.log4j.Logger;
import org.apache.commons.collections4.CollectionUtils;

import java.net.URL;

import javax.inject.Inject;

import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.cart.service.ShoppingListProcessorService;
import com.example.cart.repository.ShoppingListRepository;
import com.example.cart.domain.ShoppingList;

@Path("/")
public class CartResource {
    static final Logger logger = Logger.getLogger(CartResource.class);

    @Inject
    private ShoppingListRepository shoppingListRepository;

    @Inject
    private ShoppingListProcessorService shoppingListProcessorService;

    @Path("cart/{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getData(@PathParam("id") String id) throws Exception {
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND);
        logger.debug("processing a shopping list with id " + id);
        ShoppingList shoppingList = shoppingListRepository.getShoppingList(id);
        if ((shoppingList != null) && CollectionUtils.isNotEmpty(shoppingList.getItems())) {
            URL url = shoppingListProcessorService.processShoppingList(shoppingList);
            if (url != null) {
                responseBuilder = Response.ok(url.toString());
            } else {
                logger.error("unable to process shopping list");
            }
        } else
            logger.error("no shopping list with id " + id + " found");
        return responseBuilder.build();
    }
}
