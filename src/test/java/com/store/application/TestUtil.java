package com.store.application;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.store.application.exception.InvalidInputException;
import com.store.application.offers.MultiPercentOffer;
import com.store.application.offers.OfferImpl;
import com.store.application.offers.PercentOffer;
import com.store.application.product.Product;

public class TestUtil {

	public static Product getApple() throws InvalidInputException{
		Product apple = new Product("Apple,1.0,bag");
		return apple;
	}
	
	public static Product getBread() throws InvalidInputException{
		Product bread = new Product("Bread,1.5,loaf");
		return bread;
	}
	
	public static Product getSoup()throws InvalidInputException{
		Product soup = new Product("Soup,.65,tin");
		return soup;
	}
	
	public static PercentOffer getPercentOffer()throws InvalidInputException{
		PercentOffer offer = new PercentOffer();
		offer.setOfferName("APPLE10PERCENTOFF");
		offer.setProduct(getApple());
		offer.setPercent(10);
		offer.setOfferType(OfferImpl.OfferType.PERCENT);
		
		return offer;
	}
	
	public static MultiPercentOffer getMultiPercentOffer()throws InvalidInputException{
		MultiPercentOffer offer = new MultiPercentOffer();
		offer.setOfferName("BREAD50PERCENTOFF");
		offer.setProduct(getBread());
		offer.setPercent(50);
		offer.setOfferType(OfferImpl.OfferType.MULTIPERCENT);
		offer.setQualifingProduct(getSoup());
		offer.setQualifingQuantity(2);
		return offer;
	}
	
	
	public static Map<String,Product> getProductWithApple(){
		 Map<String,Product> productMap = new ConcurrentHashMap<String, Product>();
		 Product apple =getApple();
		 productMap.put(apple.getProductName(),  apple);
		 return productMap;
	}
	
	public static String getTodayDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		return LocalDate.now().format(formatter);
	}
	
	public static String getYesterdayDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		return LocalDate.now().minus(1, ChronoUnit.DAYS).format(formatter);
	}
	
	public static String getTomorrowDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		return LocalDate.now().plus(1, ChronoUnit.DAYS).format(formatter);
	}
}
