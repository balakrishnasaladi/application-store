package com.store.application.offers;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
public class PercentOffer extends OfferImpl{

	@Setter@Getter
	private int percent;
	
	private static final Logger LOG = LoggerFactory.getLogger(PercentOffer.class);
	
	public double calculate(Map<String,Integer> cart){
		int quantity = cart.get(this.getProduct().getProductName());
		BigDecimal offerPrice = (new BigDecimal(percent/100d)).multiply(getProduct().getPricePerUnit().multiply(new BigDecimal(quantity))).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		LOG.info(this.getProductName()+" "+this.getPercent() +"% Off: -"+offerPrice);
		return  (new BigDecimal((100-percent)/100d)).multiply(getProduct().getPricePerUnit().multiply(new BigDecimal(quantity))).doubleValue();
	}
	
	public boolean isEligible(Map<String,Integer> cart){
		return true;
	}
}
