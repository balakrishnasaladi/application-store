package com.store.application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import com.store.application.offers.OfferImpl;
import com.store.application.product.Product;

import lombok.Getter;
import lombok.Setter;
/*
 * Store contains list of Products and offers.
 * Here for simplicity, one product will not have more than one offer.
 * 
 */
@Component
public class ApplicationStore {

	@Setter@Getter
	private Map<String,Product> storeProducts = new ConcurrentHashMap<String,Product>();
	
	/*
	 * for one product having multiple offers
	@Setter@Getter
	private Map<String,Map<String,Offer>> storeOffers = new ConcurrentHashMap<String,Map<String,Offer>>();
	*/
	
	/*
	 * One product having one offer
	 */
	@Setter@Getter
	private Map<String,OfferImpl> storeOffers = new ConcurrentHashMap<String,OfferImpl>();
	
}
