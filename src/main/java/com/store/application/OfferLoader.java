package com.store.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.store.application.offers.OfferFactory;
import com.store.application.offers.OfferImpl;

import lombok.Getter;
import lombok.Setter;

@Component
public class OfferLoader {

	private static final Logger LOG = LoggerFactory.getLogger(OfferLoader.class);
	private static final String OFFER_FEED_FILE_NAME="\\Users\\sairam\\workspace\\store-application\\src\\resource\\inputfeeds\\offer-feed.csv";
	@Autowired@Getter@Setter
	private OfferFactory oferFactory;
	
	public Map<String,OfferImpl> load() throws IOException {
		Map<String,OfferImpl>  offerMap = null;
		try (Stream<String> stream = Files.lines(Paths.get(OFFER_FEED_FILE_NAME))) {
			 List<OfferImpl> offerList=stream.map(p->oferFactory.getOffer(p)).collect(Collectors.toList());
			 LOG.debug("offerList==="+offerList);
			 
			 offerMap = offerList.stream().collect(Collectors.toConcurrentMap(p->p.getProduct().getProductName(),q->q));
			 LOG.debug("offer==="+offerMap);

		} catch (IOException e) {
			throw new IOException("Invalid Offer file:"+OFFER_FEED_FILE_NAME);
		}
		return offerMap;
	}

}
