package com.store.application.offers;

import java.util.Map;

public interface Offer {
	public boolean isEligible(Map<String,Integer> cart);
	public double calculate(Map<String,Integer> cart);
}
