package net.ibokette.demo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class StreamAPISection {

	@RequiredArgsConstructor @Getter @Setter
	static class Product {
		final private String id;
		final private int inventory;
		public void publish() {
			System.out.printf("Publishing product id=%s, inventory=%s\n", id, inventory);
		}
	}
	static Map<String, Product> productMap = Map.of(
			"COLL_1-000001", new Product("COLL_1-000001", 1000),
			"COLL_1-000002", new Product("COLL_1-000002", 10000),
			"COLL_1-000003", new Product("COLL_1-000003", 0),
			"COLL_1-000004", new Product("COLL_1-000004", 1000000),
			"COLL_1-000005", new Product("COLL_1-000005", 100000),
			"COLL_1-000006", new Product("COLL_1-000006", 0),
			"COLL_1-000007", new Product("COLL_1-000007", 100000),
			"COLL_1-000008", new Product("COLL_1-000008", 1000000)
			);

	//////////////////////////////////////////////////
	// map(), filter(), reduce(), IntStream
	//////////////////////////////////////////////////
	static void foo3() {
		int evenNumberTotal = Stream.of("1","2","3","3","4","5","6","7","8","9")
				.map(Integer::parseInt)
				.filter(x -> x % 2 == 0)
				.reduce((a, b) -> a + b).orElse(0);
		System.out.println(evenNumberTotal); // 2+4+6+8 = 20
	}

	static void foo4() {
		var list = List.of("1","2","3","3","4","5","6","7","8","9");
		int evenNumberTotal = list.stream()
				.mapToInt(Integer::parseInt)
				.filter(x -> x % 2 == 0)
				.sum();
		System.out.println(evenNumberTotal); // 2+4+6+8 = 20
	}
	
	//////////////////////////////////////////////////
	// forEach(), filter()
	//////////////////////////////////////////////////
	static void foo5() {
		var collectionIdsToPublish = List.of("000001", "000003", "000005", "000007","000009");
		
		// Publish matching collections with inventory > 0
		collectionIdsToPublish.stream()
		.map(id -> "COLL_1-" + id)
		.map(productMap::get)
		.filter(Objects::nonNull)
		.filter(product -> product.getInventory() > 0)
		.forEach(product -> {
			product.publish();
		});
	}

	//////////////////////////////////////////////////
	// flatMap(), collect()
	//////////////////////////////////////////////////
	@AllArgsConstructor static class Category {
		@Getter @Setter private String id;
		@Getter @Setter private List<String> validProductIdList;
	}

	static void foo6 () {
		var categoryList = List.of(
				new Category ("A", List.of("Product1", "Product2")),
				new Category("B", List.of("Product3", "Product4")));
		
		var allValidProductIds = categoryList.stream()
				.map(Category::getValidProductIdList)
				.flatMap(List::stream)
				.collect(Collectors.toSet());
		
		allValidProductIds.forEach(System.out::println);
	}
	
	//////////////////////////////////////////////////
	// anyMatch(), noneMatch(), findFirst()
	//////////////////////////////////////////////////
	static List<Product> productList = List.of(
			new Product("COLL_1-000001", 1000),
			new Product("COLL_1-000002", 10000),
			new Product("COLL_1-000003", 0), 
			new Product("COLL_1-000004", 1000000),
			new Product("COLL_1-000005", 100000),
			new Product("COLL_1-000006", 0),
			new Product("COLL_1-000007", 100000),
			new Product("COLL_1-000008", 1000000)
			);
	static void pr(String string) {
		System.out.println(string);
	}
	static Stream<Product> strm() { return productList.stream(); }
	static void foo8() {
		pr("Any have inventory of 33 is " + strm().anyMatch(p -> p.getInventory() == 33));
		pr("None have inventory of 33 is " + strm().noneMatch(p -> p.getInventory() == 33));
		pr("First with inventory of 100000 is " + strm().filter(p -> p.getInventory() == 100000).findFirst().orElse(new Product("nada", 0)));
		pr("First with inventory of 33 is " + strm().filter(p -> p.getInventory() == 33).findFirst().orElse(new Product("nada", 0)));
	}
	

	//////////////////////////////////////////////////
	// IntStreamExample
	//////////////////////////////////////////////////
	static void foo10() {
		int max = IntStream.of(20, 98, 12,7, 35)
				.max()
				.getAsInt();
		System.out.println(max);
	}

	static void foo11() {
		double avg = IntStream.of(20, 98, 12,7, 35)
				.average()
				.getAsDouble();
		System.out.println(avg);
	}
}