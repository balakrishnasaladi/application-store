package com.store.application.offers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.store.application.product.Product;
import com.store.application.util.StoreUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
public class MultiPercentOffer extends OfferImpl{

	private static final Logger LOG = LoggerFactory.getLogger(MultiPercentOffer.class);

	@Setter@Getter
	private Product qualifingProduct;
	@Setter@Getter
	private int qualifingQuantity;
	@Setter@Getter
	private int percent;


	public double calculate(Map<String,Integer> cart){

		int quantity = cart.get(this.getProduct().getProductName());
		int cartQualifingQuantity=cart.get(this.getQualifingProduct().getProductName());
		int offerCanBeGivenQuantity = cartQualifingQuantity / this.qualifingQuantity;

		double nonOfferPrice  = 0;
		if(quantity>offerCanBeGivenQuantity){
			nonOfferPrice = this.getProduct().calculate(quantity-offerCanBeGivenQuantity);
		}
		//normal price 
		double offerPercent =(percent/100d)*getProduct().getPricePerUnit().doubleValue()*offerCanBeGivenQuantity;
		LOG.info(this.getProductName()+" "+this.getPercent() +"% Off: -"+StoreUtil.getDecimal(offerPercent));

		double offerPrice= ((100-percent)/100d)*getProduct().getPricePerUnit().doubleValue()*offerCanBeGivenQuantity;
		return offerPrice+nonOfferPrice;
	}

	public boolean isEligible(Map<String,Integer> cart){

		if(cart.containsKey(this.getQualifingProduct().getProductName()) 
				&& cart.get(this.getQualifingProduct().getProductName()) >= this.qualifingQuantity)
		{
			return true;

		}
		return false;
	}
}
