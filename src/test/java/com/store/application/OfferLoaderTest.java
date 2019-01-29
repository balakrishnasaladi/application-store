package com.store.application;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.store.application.exception.InvalidInputException;
import com.store.application.offers.Offer;
import com.store.application.offers.OfferFactory;
import com.store.application.offers.OfferImpl;
import com.store.application.product.Product;

import static junit.framework.Assert.assertTrue;

import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class OfferLoaderTest {
	@Mock
	private ApplicationStore applicationStore;

	
	private OfferLoader offerLoader = new OfferLoader();
	@InjectMocks
	private OfferFactory offerFactory = new OfferFactory();

	@Test
	public void testLoad() throws InvalidInputException,IOException{
		 Map<String,Product> productMap= new ProductLoader().load();	 
		 offerLoader.setOferFactory(offerFactory);		
		when(applicationStore.getStoreProducts()).thenReturn(productMap);
		Map<String,OfferImpl> offerMap= offerLoader.load();
		assertTrue(offerMap.containsKey("Apple"));
		assertTrue(offerMap.containsKey("Bread"));
		
	}
}
