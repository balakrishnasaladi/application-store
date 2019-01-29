package com.store.application;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.store.application.offers.OfferImpl;
import com.store.application.util.StoreUtil;
import lombok.Getter;
import lombok.Setter;

@Component
public class PriceBasket {
	
	private static final Logger LOG = LoggerFactory.getLogger(PriceBasket.class);

	@Autowired
	private ApplicationStore applicationStore;

	@Setter@Getter
	private Map<String,Integer> cart = new ConcurrentHashMap<String,Integer>();
	/*
	 * Calculated total price of all products with no offers
	 */
	public BigDecimal calculate(){
		double sum=cart.entrySet().stream().mapToDouble(p->applicationStore.getStoreProducts().get(p.getKey()).calculate(p.getValue())).sum();
		BigDecimal total = new BigDecimal(sum).setScale(2, BigDecimal.ROUND_HALF_UP);
		return total;
	}

	public BigDecimal calculateOfferPrice(){
		/*
		 * Offers available are offers available for all products in current cart
		 */
		double offerPriceSum = 0;
		double nonOfferPriceSum = 0;
		Set<OfferImpl> offerAvaiable =cart.entrySet().stream().map(p->applicationStore.getStoreOffers().get(p.getKey())).collect(Collectors.toSet());
		
		/*
		 * Offers eligible are offers that can be applied to current cart
		 */
		if(offerAvaiable == null || offerAvaiable.isEmpty())
		{
			LOG.info("(No offers available)");
			nonOfferPriceSum = calculate().doubleValue();
		}
		else
		{
			offerAvaiable.remove(null);
			List<OfferImpl> offerEligible  = offerAvaiable.stream().filter(p->p.isEligible(cart)).collect(Collectors.toList());
			if(offerEligible ==null || offerEligible.isEmpty())
			{
				LOG.info("(No offers available)");
				nonOfferPriceSum = calculate().doubleValue();
			}
			else{
				 offerPriceSum = offerEligible.stream().mapToDouble(p->applicationStore.getStoreOffers().get(p.getProduct().getProductName())
						.calculate(cart)).sum();
				
				for (Map.Entry<String, Integer> entry : cart.entrySet()) {
					if(offerEligible !=null){
						if(offerEligible.stream().noneMatch(p->p.getProduct().getProductName().equalsIgnoreCase(entry.getKey()))){
							nonOfferPriceSum += applicationStore.getStoreProducts().get(entry.getKey()).calculate(entry.getValue());
						}
					}
					
				}
			}
			
		}


		return StoreUtil.getDecimal(nonOfferPriceSum+offerPriceSum);
	}

	public void calculateTotalBasketPrice(){
		LOG.info("Subtotal: £"+calculate());

		LOG.info("Total: £"+calculateOfferPrice());
	}

	/*
	 * Add method adds product for the first time with count as 1. 
	 * If product already exist, increases the count by 1
	 */
	public void add(String productName){
		if(cart.containsKey(productName)){
			cart.put(productName, (new Integer(cart.get(productName)))+1);
		}
		else{
			cart.put(productName, 1);
		}
	}
	
	public void loadBasket(String input){
		String values[] = input.split(" ");
		for(String str : values){
			if(str.equalsIgnoreCase("PriceBasket")){
				continue;
			}
			else{
				this.add(str);
			}
		}
	}

}
