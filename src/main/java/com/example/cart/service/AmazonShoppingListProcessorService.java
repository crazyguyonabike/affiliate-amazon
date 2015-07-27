package com.example.cart.service;

import java.net.URL;
import java.math.BigInteger;

import com.example.cart.domain.Item;
import com.example.cart.domain.ShoppingList;
import com.example.cart.resolver.AwsHandlerResolver;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.InitializingBean;

import org.apache.log4j.Logger;

public class AmazonShoppingListProcessorService implements ShoppingListProcessorService, InitializingBean {
    private String awsAPIKey;
    private String awsSecretKey;
    private String awsAssociateTag;
    private com.ECS.client.jax.AWSECommerceServicePortType servicePort = null;

    private Logger logger = Logger.getLogger(AmazonShoppingListProcessorService.class);

    public void afterPropertiesSet() throws Exception {
        com.ECS.client.jax.AWSECommerceService service  = new com.ECS.client.jax.AWSECommerceService();
        service.setHandlerResolver(new AwsHandlerResolver(awsSecretKey));
        this.servicePort = service.getAWSECommerceServicePort();
    }

    public URL processShoppingList(ShoppingList shoppingList) throws Exception {
        com.ECS.client.jax.CartCreateRequest cartCreateRequest = new com.ECS.client.jax.CartCreateRequest();
        com.ECS.client.jax.CartCreateRequest.Items cartCreateRequestItems = new com.ECS.client.jax.CartCreateRequest.Items();

        for (Item item : shoppingList.getItems()) {
            logger.debug("processing shopping item " + item.getName());
            com.ECS.client.jax.CartCreateRequest.Items.Item cartCreateRequestItem = new com.ECS.client.jax.CartCreateRequest.Items.Item();
            cartCreateRequestItem.setASIN(item.getId());
            cartCreateRequestItem.setQuantity(BigInteger.valueOf(item.getQuantity()));
            cartCreateRequestItem.setAssociateTag(awsAssociateTag);
            cartCreateRequestItems.getItem().add(cartCreateRequestItem);
        }
        cartCreateRequest.setItems(cartCreateRequestItems);

        com.ECS.client.jax.CartCreate cartCreate = new com.ECS.client.jax.CartCreate();
        cartCreate.setAWSAccessKeyId(awsAPIKey);
        cartCreate.setAssociateTag(awsAssociateTag);
        cartCreate.getRequest().add(cartCreateRequest);

        com.ECS.client.jax.CartCreateResponse cartCreateResponse = servicePort.cartCreate(cartCreate);
        logger.debug("created " + cartCreateResponse.getCart().size() + " carts");
        for (com.ECS.client.jax.Cart cart : cartCreateResponse.getCart()) {
            logger.debug("purchase URL: " + cart.getPurchaseURL());
        }
        return new URL(cartCreateResponse.getCart().get(0).getPurchaseURL());
    }

    @Required
    public void setAwsAPIKey(String awsAPIKey) {
        this.awsAPIKey = awsAPIKey;
    }

    @Required
    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
    }

    @Required
    public void setAwsAssociateTag(String awsAssociateTag) {
        this.awsAssociateTag = awsAssociateTag;
    }
}
