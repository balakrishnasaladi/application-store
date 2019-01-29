package com.store.application.product;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import com.store.application.exception.InvalidInputException;
import com.store.application.util.StoreUtil;

import static com.store.application.util.StoreContants.COMMA;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
 * Product class contains details of product
 * For simplicity we are not adding product quantity
 */
@ToString@EqualsAndHashCode(of={"productName"})
public class Product{
	
	@Getter
	private String productName;
	@Getter
	private BigDecimal pricePerUnit;
	@Getter
	private Units units;
	@Setter
	private String productDesc;
	
	//private Offer offers;
	
	public double calculate(int quantity){
		return pricePerUnit.doubleValue()*quantity;
	}
	private Product(){}
	/*
	 * Values must be provided in below order
	 * ProductName,price,productUnits  
	 * 
	 */
	public Product(String str) throws InvalidInputException{
		
		String [] values= str.split(COMMA);
		if(values.length<3  ){
			throw new InvalidInputException("Invalid product details provided:"+str);
		}
		else if(values[0].isEmpty() || values[1].isEmpty() || values[2].isEmpty() ){
			throw new InvalidInputException("Invalid product details provided:"+str);
		}
		this.productName=values[0];
		double price =Double.parseDouble(values[1]);
		if(price <=0){
			throw new InvalidInputException("Invalid product price details provided:"+str);
		}
		this.pricePerUnit = StoreUtil.getDecimal(price);
		
	}
	
	static enum Units{
		TIN("tin"),LOAF("loaf"),BAG("bag"),BOTTLE("bottle");
		
		private String unit;
		Units(String arg){
			this.unit =arg;
		}
	}
}


