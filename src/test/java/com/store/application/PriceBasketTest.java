package com.store.application;


import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.store.application.exception.InvalidInputException;
import com.store.application.offers.OfferFactory;
import com.store.application.offers.OfferImpl;
import com.store.application.product.Product;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class PriceBasketTest {

	@Mock
	private ApplicationStore applicationStore;

	private OfferLoader offerLoader = new OfferLoader();
	@InjectMocks
	private OfferFactory offerFactory = new OfferFactory();
	@InjectMocks
	PriceBasket priceBasket = new PriceBasket();

	@Test
	public void testCalculateWithAppleMilkBread() throws InvalidInputException,IOException{

		Map<String,Product> productMap= new ProductLoader().load();	 

		when(applicationStore.getStoreProducts()).thenReturn(productMap);
		offerLoader.setOferFactory(offerFactory);		

		Map<String,OfferImpl> offerMap= offerLoader.load();
		when(applicationStore.getStoreOffers()).thenReturn(offerMap);
		/*Subtotal: £3.10,Apple 10% Off: -0.10,Total: £3.00*/
		//String input="PriceBasket Apple Milk Bread";

		priceBasket.loadBasket("PriceBasket Apple Milk Bread");
		assertEquals(3.10, priceBasket.calculate().doubleValue());
		assertEquals(3.00, priceBasket.calculateOfferPrice().doubleValue());
	}
	
	@Test
	public void testCalculateWithMilkBreadSoup() throws InvalidInputException,IOException{

		/*Subtotal: £2.75,,(No offers available),Total: £2.75*/
		//String input="PriceBasket Milk Bread Soup";

		Map<String,Product> productMap= new ProductLoader().load();	 

		when(applicationStore.getStoreProducts()).thenReturn(productMap);
		offerLoader.setOferFactory(offerFactory);		

		Map<String,OfferImpl> offerMap= offerLoader.load();
		when(applicationStore.getStoreOffers()).thenReturn(offerMap);
		priceBasket.loadBasket("PriceBasket Milk Bread Soup");

		assertEquals(2.75, priceBasket.calculate().doubleValue());
		assertEquals(2.75, priceBasket.calculateOfferPrice().doubleValue());

	}
	
	@Test
	public void testCalculateWithMilkBreadTwoSoup() throws InvalidInputException,IOException{

		/*Subtotal: £3.40 ,Bread 50% Off: -0.40,Total: £3.00*/
		//String input="PriceBasket Milk Bread Soup Soup";

		Map<String,Product> productMap= new ProductLoader().load();	 

		when(applicationStore.getStoreProducts()).thenReturn(productMap);
		offerLoader.setOferFactory(offerFactory);		

		Map<String,OfferImpl> offerMap= offerLoader.load();
		when(applicationStore.getStoreOffers()).thenReturn(offerMap);
		priceBasket.loadBasket("PriceBasket Milk Bread Soup Soup");

		assertEquals(3.40, priceBasket.calculate().doubleValue());
		assertEquals(3.00, priceBasket.calculateOfferPrice().doubleValue());

	}
	@Test
	public void testCalculateWithMilkBreadTwoSoupTwoApple() throws InvalidInputException,IOException{

		/*Subtotal: £5.40,Apple 10% Off: -0.20,Bread 50% Off: -0.400,Total: £4.80 */
		//String input="PriceBasket Milk Bread Soup Soup Apple Apple";

		Map<String,Product> productMap= new ProductLoader().load();	 

		when(applicationStore.getStoreProducts()).thenReturn(productMap);
		offerLoader.setOferFactory(offerFactory);		

		Map<String,OfferImpl> offerMap= offerLoader.load();
		when(applicationStore.getStoreOffers()).thenReturn(offerMap);
		priceBasket.loadBasket("PriceBasket Milk Bread Soup Soup Apple Apple");

		assertEquals(5.40, priceBasket.calculate().doubleValue());
		assertEquals(4.80, priceBasket.calculateOfferPrice().doubleValue());

	}
	
	@Test
	public void testCalculateWithTwoBreadTwoSoup() throws InvalidInputException,IOException{
		
		/*Subtotal: £2.90,Bread 50% Off: -0.40,Total: £2.50*/
		Map<String,Product> productMap= new ProductLoader().load();	 

		when(applicationStore.getStoreProducts()).thenReturn(productMap);
		offerLoader.setOferFactory(offerFactory);		

		Map<String,OfferImpl> offerMap= offerLoader.load();
		when(applicationStore.getStoreOffers()).thenReturn(offerMap);
		priceBasket.loadBasket("PriceBasket Bread Bread Soup Soup");

		assertEquals(2.90, priceBasket.calculate().doubleValue());
		assertEquals(2.50, priceBasket.calculateOfferPrice().doubleValue());

	}
	
}
