package com.store.application.offers;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.store.application.ApplicationStore;
import com.store.application.TestUtil;
import com.store.application.exception.InvalidInputException;
import com.store.application.offers.OfferImpl.OfferType;
import com.store.application.product.Product;
import com.store.application.util.StoreUtil;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class OfferFactoryTest {
	//Apple10Percent,Apple,10,percent,bag,,,20/01/2019,28/01/2019
	@Mock
	private ApplicationStore applicationStore;
	@InjectMocks
	private OfferFactory offerFactory = new OfferFactory();
	
	@Test(expected=InvalidInputException.class)
	public void testGetOfferEmptyString()
	{
		offerFactory.getOffer("");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOfferWithMissingInputs()
	{
		offerFactory.getOffer("Apple10Percent,Apple,10,percent");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOfferWithProductNotExistInStore()
	{
		when(applicationStore.getStoreProducts()).thenReturn(TestUtil.getProductWithApple());
		offerFactory.getOffer("Banana10Percent,Banana,10,percent,bag,,,20/01/2019,28/01/2019");
		OfferImpl offer = offerFactory.getOffer("Apple10Percent,Apple,10,percent,bag,,,20/01/2019,28/01/2019");
	}
	
	@Test
	public void testGetOfferWithValidInputs()
	{
		Map<String,Product> map = TestUtil.getProductWithApple();
		when(applicationStore.getStoreProducts()).thenReturn(map);
		
		OfferImpl offer = offerFactory.getOffer("Apple10Percent,Apple,10,percent,bag,,,"+TestUtil.getYesterdayDate()+","+TestUtil.getTomorrowDate());
		assertTrue(offer instanceof PercentOffer);
		PercentOffer percentOffer = (PercentOffer)offer;
		assertEquals("Apple10Percent", percentOffer.getOfferName());
		assertEquals("Apple", percentOffer.getProduct().getProductName());
		assertEquals(StoreUtil.getDecimal(1.0d), percentOffer.getProduct().getPricePerUnit());
		
		assertEquals(10, percentOffer.getPercent());
		assertEquals(OfferType.PERCENT, percentOffer.getOfferType());
		assertEquals(LocalDate.now().minus(1, ChronoUnit.DAYS), percentOffer.getFromDate());
		assertEquals(LocalDate.now().plus(1, ChronoUnit.DAYS), percentOffer.getToDate());
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOfferWithExpireOffer()
	{
		Map<String,Product> map = TestUtil.getProductWithApple();
		when(applicationStore.getStoreProducts()).thenReturn(map);
		
		offerFactory.getOffer("Apple10Percent,Apple,10,percent,bag,,,"+TestUtil.getYesterdayDate()+","+TestUtil.getYesterdayDate());
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOfferWithMissingOfferName()
	{
		Map<String,Product> map = TestUtil.getProductWithApple();
		when(applicationStore.getStoreProducts()).thenReturn(map);
		
		OfferImpl offer = offerFactory.getOffer(",Apple,10,percent,bag,,,"+TestUtil.getYesterdayDate()+","+TestUtil.getTomorrowDate());
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOfferWithMissingProductName()
	{
		Map<String,Product> map = TestUtil.getProductWithApple();
		when(applicationStore.getStoreProducts()).thenReturn(map);
		
		OfferImpl offer = offerFactory.getOffer("Apple10Percent,,10,percent,bag,,,"+TestUtil.getYesterdayDate()+","+TestUtil.getTomorrowDate());
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOfferWithMissingPercent()
	{
		Map<String,Product> map = TestUtil.getProductWithApple();
		when(applicationStore.getStoreProducts()).thenReturn(map);
		
		OfferImpl offer = offerFactory.getOffer("Apple10Percent,Apple,,percent,bag,,,"+TestUtil.getYesterdayDate()+","+TestUtil.getTomorrowDate());
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOfferWithMissingFromDate()
	{
		Map<String,Product> map = TestUtil.getProductWithApple();
		when(applicationStore.getStoreProducts()).thenReturn(map);
		
		OfferImpl offer = offerFactory.getOffer("Apple10Percent,Apple,10,percent,bag,,,"+","+TestUtil.getTomorrowDate());
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testGetOfferWithMissingToDate()
	{
		Map<String,Product> map = TestUtil.getProductWithApple();
		when(applicationStore.getStoreProducts()).thenReturn(map);
		
		OfferImpl offer = offerFactory.getOffer("Apple10Percent,Apple,10,percent,bag,,,"+TestUtil.getYesterdayDate()+",");
		
	}
}
