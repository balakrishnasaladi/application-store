package com.store.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.store.application.product.Product;
/*
 * This class Reads input feed and converts them to products
 */
@Component
public class ProductLoader {

	private static final Logger LOG = LoggerFactory.getLogger(ProductLoader.class);
	private static final String PRODUCT_FEED_FILE_NAME="\\Users\\sairam\\workspace\\store-application\\src\\resource\\inputfeeds\\product-feed.csv";
	public Map<String,Product> load() throws IOException {
		LOG.info("Loadded products initaited");
		Map<String,Product> productMap = null;
		try (Stream<String> stream = Files.lines(Paths.get(PRODUCT_FEED_FILE_NAME))) {
			productMap = stream.map(p->new Product(p)).collect(Collectors.toConcurrentMap(Product::getProductName, q->q));
			LOG.info("All products loadded successfully");
			
		} catch (IOException e) {
			LOG.error("Invalid product file:"+PRODUCT_FEED_FILE_NAME);
			throw new IOException("Invalid product file:"+PRODUCT_FEED_FILE_NAME);
		}
		return productMap;
	}
	
	
	/*public static void main(String[] args) {
		try {
			System.out.println(new ProductLoader().load());;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
