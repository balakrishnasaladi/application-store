package com.store.application;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.store.application.product.Product;

@SpringBootApplication
public class StartApplicationStore implements CommandLineRunner{

	private static final Logger LOG = LoggerFactory.getLogger(StartApplicationStore.class);

	@Autowired
	private ApplicationStore applicationStore;

	@Autowired
	private ProductLoader productLoader;

	@Autowired
	private OfferLoader offerLoader;

	@Autowired
	private PriceBasket basket;

	public static void main(String[] args) {

		SpringApplication.run(StartApplicationStore.class, args);   

	}

	@Override
	public void run(String... arg0) throws Exception {

	
		/*Subtotal: £3.10,Apple 10% Off: -0.10,Total: £3.00*/
		//String input="PriceBasket Apple Milk Bread";
		
		/*Subtotal: £2.75,,(No offers available),Total: £2.75*/
		//String input="PriceBasket Milk Bread Soup";
		
		/*Subtotal: £3.40 ,Bread 50% Off: -0.40,Total: £3.00*/
		//String input="PriceBasket Milk Bread Soup Soup";
		
		/*Subtotal: £5.40,Apple 10% Off: -0.20,Bread 50% Off: -0.400,Total: £4.80 */
		//String input="PriceBasket Milk Bread Soup Soup Apple Apple";
		
		/*Subtotal: £2.90,Bread 50% Off: -0.40,Total: £2.50*/
		String input="PriceBasket Bread Bread Soup Soup";
		
		
		//load basket
		basket.loadBasket(input);

		//load products
		Map<String,Product> productMap = productLoader.load();
		applicationStore.setStoreProducts(productMap);
		
		LOG.info("getStoreProducts"+applicationStore.getStoreProducts());
		//load offers
		applicationStore.setStoreOffers(offerLoader.load());
		LOG.info("getStoreOffers"+applicationStore.getStoreOffers());
		
		//calculate
		basket.calculateTotalBasketPrice();

	}


}
