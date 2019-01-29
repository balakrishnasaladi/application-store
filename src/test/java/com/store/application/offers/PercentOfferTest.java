package com.store.application.offers;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.store.application.TestUtil;
import com.store.application.exception.InvalidInputException;
import com.store.application.offers.PercentOffer;

public class PercentOfferTest {

	@Test
	public void testPercentOfferForOneApple()throws InvalidInputException{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Apple", 1);
		cart.put("Soup", 2);
		PercentOffer offer = TestUtil.getPercentOffer();
		assertEquals(0.9d, offer.calculate(cart));	
	}
	
	@Test
	public void testPercentOfferForTwoApple()throws InvalidInputException{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Apple", 2);
		cart.put("Soup", 2);
		PercentOffer offer = TestUtil.getPercentOffer();
		assertEquals(1.8d, offer.calculate(cart));	
	}
	
}
