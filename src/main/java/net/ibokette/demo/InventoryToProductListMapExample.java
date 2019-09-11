package net.ibokette.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class InventoryToProductListMapExample {
	@RequiredArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
	static class Product {
		final private String id;
		final private int inventory;
		private Optional<String> promotionId = Optional.empty();
	}

	static List<Product> productList = List.of(
			new Product("COLL_1-000001", 1000, Optional.of("promo1")),
			new Product("COLL_1-000002", 10000, Optional.of("promo2")),
			new Product("COLL_1-000003", 0, Optional.of("promo1")),
			new Product("COLL_1-000004", 1000000, Optional.of("promo2")),
			new Product("COLL_1-000005", 100000, Optional.of("promo1")),
			new Product("COLL_1-000006", 0),
			new Product("COLL_1-000007", 100000, Optional.of("promo2")),
			new Product("COLL_1-000008", 1000000, Optional.of("promo1"))
			);

	public static void main( String[] args ) { }
	
	//////////////////////////////////////////////////
	// Java7
	//////////////////////////////////////////////////
	static void foo1() {
		Map<Integer, List<Product>> inventoryToProductListMap = new HashMap<>();
		for (Product p : productList) {
			List<Product> productList = inventoryToProductListMap.get(p.getInventory());
			if (productList == null) {
				productList = new ArrayList<>();
				inventoryToProductListMap.put(p.getInventory(), productList);
			}
			productList.add(p);
		};
	}

	//////////////////////////////////////////////////
	// Java 8 - Optional
	//////////////////////////////////////////////////
	static void foo3() {
		Map<Integer, List<Product>> inventoryToProductListMap = new HashMap<>();
		productList.forEach(p -> {
			var list = Optional.ofNullable(inventoryToProductListMap.get(p.getInventory())).orElse(new ArrayList<>());
			inventoryToProductListMap.putIfAbsent(p.getInventory(), list);
			list.add(p);
		});
	}
	
	//////////////////////////////////////////////////
	// Java 8 - functional computeIfAbsent
	//////////////////////////////////////////////////
	static void foo2() {
		Map<Integer, List<Product>> inventoryToProductListMap = new HashMap<>();
		productList.forEach(p -> {
			inventoryToProductListMap.computeIfAbsent(p.getInventory(), inv -> new ArrayList<>()).add(p);
		});
	}
	
	//////////////////////////////////////////////////
	// Java 8 - streams
	//////////////////////////////////////////////////
	static void foo4() {
		var inventoryToProductListMap = productList.stream()
		.collect(Collectors.groupingBy(p -> p.getInventory(), Collectors.mapping(p -> p, Collectors.toList())));
		System.out.println(inventoryToProductListMap);
	}
}