package com.store.application.offers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import com.store.application.TestUtil;
import com.store.application.exception.InvalidInputException;

public class MultiPercentOfferTest {

	@Test
	public void testCalculateOfferForOneBread()throws InvalidInputException{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 1);
		cart.put("Soup", 2);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertEquals(0.75d, offer.calculate(cart));	
	}
	@Test
	public void testCalculateOfferForOneBreadThreeSoup()throws InvalidInputException{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 1);
		cart.put("Soup", 3);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertEquals(0.75d, offer.calculate(cart));	
	}
	@Test
	public void testCalculateOfferForTwoBread()throws InvalidInputException{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 2);
		cart.put("Soup", 2);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertEquals(2.25d, offer.calculate(cart));	
	}
	
	@Test
	public void testCalculateOfferForTwoBreadThreeSoup()throws InvalidInputException{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 2);
		cart.put("Soup", 3);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertEquals(2.25d, offer.calculate(cart));	
	}
	
	@Test
	public void testCalculateOfferForThreeBreadTwoSoup()throws InvalidInputException{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 3);
		cart.put("Soup", 2);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertEquals(3.75d, offer.calculate(cart));	
	}
	
	@Test
	public void testCalculateOfferForTwoBreadFourSoup()throws InvalidInputException{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 2);
		cart.put("Soup", 4);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertEquals(1.5d, offer.calculate(cart));	
	}
	
	@Test
	public void testIsEligibleOneBreadAndTwoSoup()
	{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 1);
		cart.put("Soup", 2);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertTrue(offer.isEligible(cart));
	}
	
	@Test
	public void testIsEligibleOneBreadAndOneSoup()
	{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 1);
		cart.put("Soup", 1);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertFalse(offer.isEligible(cart));
	}
	
	@Test
	public void testIsEligibleOneBreadAndThreeSoup()
	{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 1);
		cart.put("Soup", 3);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertTrue(offer.isEligible(cart));
	}
	/*
	 * defect
	 */
	@Test
	public void testIsEligibleTwoBreadAndTwoSoup()
	{
		Map<String,Integer> cart = new HashMap<String, Integer>();
		cart.put("Bread", 2);
		cart.put("Soup", 2);
		MultiPercentOffer offer = TestUtil.getMultiPercentOffer();
		assertTrue(offer.isEligible(cart));
	}
	
	
}
