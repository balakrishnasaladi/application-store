package com.store.application.offers;

import static com.store.application.util.StoreContants.COMMA;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.store.application.ApplicationStore;
import com.store.application.exception.InvalidInputException;
import lombok.Getter;

@Component
public class OfferFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(OfferFactory.class);
	
	@Autowired@Getter
	private ApplicationStore applicationStore;

	public OfferImpl getOffer(String offerStr){
		OfferImpl offer = null;
		if(offerStr==null || offerStr.isEmpty())
		{
			throw new InvalidInputException("Invalid Offer details provided:"+offerStr);
		}
		String [] values= offerStr.split(COMMA);
		if(values.length<9  ){
			throw new InvalidInputException("Invalid Offer details provided:"+offerStr);
		}
		else if(values[0].isEmpty() || values[1].isEmpty() || values[2].isEmpty()  || values[3].isEmpty() || values[4].isEmpty()
				|| values[7].isEmpty() || values[8].isEmpty()){

			throw new InvalidInputException("Invalid Offer details provided:"+offerStr);
		}

		LocalDate fromDate = LocalDate.parse(values[7], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate toDate =	LocalDate.parse(values[8], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		/*
		 * TODO:
		 * Currently we are throwing exception for non active offers.
		 * we must ignore the invalid offer
		 */
		if(LocalDate.now().isBefore(fromDate)  || LocalDate.now().isAfter(toDate)){

			throw new InvalidInputException("Invalid Offer details provided:"+offerStr);
		}
		if(!applicationStore.getStoreProducts().containsKey(values[1])){
			throw new InvalidInputException("Invalid Product provided in Offer details :"+offerStr);
		}
		if(OfferImpl.OfferType.PERCENT.toString().equalsIgnoreCase(values[3])){
			//Apple10Percent,Apple,10,percent,bag,,,20/01/2019,28/01/2019
			
			PercentOffer percentOffer = new PercentOffer();
			percentOffer.setOfferName(values[0]);
			
			percentOffer.setProduct(applicationStore.getStoreProducts().get(values[1]));
			percentOffer.setOfferType(OfferImpl.OfferType.PERCENT);
			percentOffer.setPercent( Integer.parseInt(values[2]));
			percentOffer.setFromDate(fromDate);
			percentOffer.setToDate(toDate);
			offer = percentOffer;
		}
		else if(OfferImpl.OfferType.MULTIPERCENT.toString().equalsIgnoreCase(values[3])){
			//Bread50PercentMultiSoup,Bread,50,multipercent,tin,Soup,2,20/01/2019,28/01/2019
			MultiPercentOffer multiPercentOffer = new MultiPercentOffer();
			multiPercentOffer.setOfferName(values[0]);
			multiPercentOffer.setProduct(applicationStore.getStoreProducts().get(values[1]));
			multiPercentOffer.setOfferType(OfferImpl.OfferType.MULTIPERCENT);
			multiPercentOffer.setPercent( Integer.parseInt(values[2]));
			if(!applicationStore.getStoreProducts().containsKey(values[5])){
				throw new InvalidInputException("Invalid Product provided in Offer details :"+offerStr);
			}
			multiPercentOffer.setQualifingProduct(applicationStore.getStoreProducts().get(values[5]));
			multiPercentOffer.setQualifingQuantity(Integer.parseInt(values[6]));
			multiPercentOffer.setFromDate(fromDate);
			multiPercentOffer.setToDate(toDate);
			offer = multiPercentOffer;
		}
		else{
			LOG.error("Offer provided is not supported by application, please check:"+offerStr);
		}

		return offer;
	}
}
