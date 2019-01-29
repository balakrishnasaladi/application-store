package com.store.application.offers;


import java.time.LocalDate;
import java.util.Map;
import com.store.application.product.Product;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import lombok.Setter;
import lombok.ToString;

@ToString@EqualsAndHashCode(of={"offerName"})
public abstract class OfferImpl implements Offer{
	
	
	@Setter@Getter
	private String offerName;	
	@Setter@Getter
	private LocalDate fromDate;
	@Setter@Getter
	private LocalDate toDate;
	@Setter@Getter
	private OfferType offerType;
	@Setter@Getter
	private Product product;
	
	public String getProductName(){
		return product.getProductName();
	}
	
	
	/*public boolean isEligible(Map<String,Integer> cart){
		return false;
	}*/

	public static enum OfferType{
		PERCENT,OFF,BOGO,MULTIPERCENT,MULTIOFF;
	}


	@Override
	public boolean isEligible(Map<String, Integer> cart) {
		return false;
	}


}


