package com.store.application.product;

import org.junit.Test;

import com.store.application.exception.InvalidInputException;
import static junit.framework.Assert.assertEquals;

import java.math.BigDecimal;

public class ProductTest {
	
	@Test(expected=InvalidInputException.class)
	public void testProductWithEmptyInput() throws InvalidInputException{
		new Product("");
	}
	@Test(expected=InvalidInputException.class)
	public void testProductWithJustNameInput() throws InvalidInputException{
		new Product("Apple");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testProductWithNoTypeInput() throws InvalidInputException{
		new Product("Apple,1.23");
	}
	
	@Test
	public void testProductWithValidInput() throws InvalidInputException{
		Product product=new Product("Apple,1.23,bag");
		assertEquals("Apple", product.getProductName());
		assertEquals(new BigDecimal(1.23).setScale(2, BigDecimal.ROUND_HALF_UP), product.getPricePerUnit());
	}
	
	@Test(expected=NumberFormatException.class)
	public void testProductWithInvalidPrice() throws InvalidInputException{
		Product product=new Product("Apple,1.23we,bag");
		assertEquals("Apple", product.getProductName());
		assertEquals(new BigDecimal(1.23).setScale(2, BigDecimal.ROUND_HALF_UP), product.getPricePerUnit());
	}
	@Test
	public void testCalculate(){
		Product product=new Product("Apple,1.23,bag");
		assertEquals(1.23d,product.calculate(1));
		assertEquals(2.46d,product.calculate(2));
		assertEquals(12300d,product.calculate(10000));
	}
}
