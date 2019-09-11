package net.ibokette.demo;

import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class OptionalSection {
	public static void main( String[] args ) { }
	
	@RequiredArgsConstructor @AllArgsConstructor @Getter @Setter
	static class Product {
		final private String id;
		final private int inventory;
		private Optional<String> promotionId = Optional.empty();
	}
	static Map<String, Product> productMap = Map.of(
			"COLL_1-000001", new Product("COLL_1-000001", 1000, Optional.of("promo1")),
			"COLL_1-000002", new Product("COLL_1-000002", 10000, Optional.of("promo2")),
			"COLL_1-000003", new Product("COLL_1-000003", 0, Optional.of("promo1")),
			"COLL_1-000004", new Product("COLL_1-000004", 1000000, Optional.of("promo2")),
			"COLL_1-000005", new Product("COLL_1-000005", 100000, Optional.of("promo1")),
			"COLL_1-000006", new Product("COLL_1-000006", 0),
			"COLL_1-000007", new Product("COLL_1-000007", 100000, Optional.of("promo2")),
			"COLL_1-000008", new Product("COLL_1-000008", 1000000, Optional.of("promo1"))
			);

	//////////////////////////////////////////////////
	// Optional.ifPresentOrElse
	//////////////////////////////////////////////////
	static void foo1() {
		productMap.entrySet().forEach(entry -> {
			Product p = entry.getValue();
			p.getPromotionId().ifPresentOrElse(
					// true
					promo -> System.out.printf("For Product %s, got promo %s\n", p.getId(), promo), 
					// false
					() -> System.out.printf("For Product %s, no promo!!!")
					);
		});
	}

	//////////////////////////////////////////////////
	// Optional.ifPresent
	//////////////////////////////////////////////////
	static void foo2() {
		productMap.entrySet().forEach(entry -> {
			Product p = entry.getValue();
			p.getPromotionId().ifPresent(
					// true
					promo -> System.out.printf("For Product %s, got promo %s\n", p.getId(), promo)
					);
		});
	}

	//////////////////////////////////////////////////
	// Optional.orElse
	//////////////////////////////////////////////////
	static void foo10() {
		String DEFAULT_PROMO_ID = "promo99";
		productMap.entrySet().forEach(entry -> {
			Product p = entry.getValue();
			String promoId = p.getPromotionId().orElse(DEFAULT_PROMO_ID);
			System.out.println(promoId);
		});
	}

	//////////////////////////////////////////////////
	// Optional.orElseThrow
	//////////////////////////////////////////////////
	static void foo7() {
		productMap.entrySet().forEach(entry -> {
			Product p = entry.getValue();
			String promoId = p.getPromotionId().orElseThrow();
			System.out.println(promoId);
		});
	}

	//////////////////////////////////////////////////
	// Calling Code
	//////////////////////////////////////////////////
	static void foo12() {
		productMap.entrySet().forEach(entry -> {
			Product p = entry.getValue();
			String id = Optional.ofNullable(p.getId()).orElseThrow(() -> new RuntimeException("No ID"));
			System.out.println(id);
		});
	}
}