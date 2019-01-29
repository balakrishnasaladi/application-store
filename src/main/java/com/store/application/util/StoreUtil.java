package com.store.application.util;

import java.math.BigDecimal;

public class StoreUtil {

	public static BigDecimal getDecimal(double value){
		return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
