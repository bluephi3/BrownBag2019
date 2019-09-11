package net.ibokette.demo;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;




public class LambdaAndFunctional {
	public static void main( String[] args ) { }
	
	//////////////////////////////////////////////////
	// Lambda Expressions
	//////////////////////////////////////////////////
	static void foo1() {
		
		Function<String, Integer> f1 = str -> {
			return Integer.parseInt(str);
		};
		
		Function<String, Integer> f2 = str -> Integer.parseInt(str);
		
		Function<String, Integer> f3 = Integer::parseInt;

		System.out.println(f1.apply("3") + f2.apply("3") + f3.apply("3")); // 9
	}
	
	static void foo2() {

		BiFunction<String, Integer, Integer> f1 = (str, add) -> {
			return Integer.parseInt(str) + add;
		};
		
		BiFunction<String, Integer, Integer> f2 = (str, add) -> Integer.parseInt(str) + add;
		
		System.out.println(f1.apply("3", 5) + f2.apply("3", 5)); // 16
	}
	
	
	//////////////////////////////////////////////////
	// Functional Interfaces
	//////////////////////////////////////////////////
	static void foo15() {

		UnaryOperator<Integer> add1 = x -> x + 1;
		UnaryOperator<Integer> mul3 = x -> x * 3;
		Function<Integer,List<Integer>> add1List = x -> List.of(x + 1);
		Function<Integer,List<Integer>> mul3List = x -> List.of(x * 3);

		Integer x = 10;

		System.out.println("add1 result: " + add1.apply(x));
		System.out.println("mul3 result: " + mul3.apply(x));
		System.out.println("add1List result: " + add1List.apply(x));
		System.out.println("mul3List result: " + mul3List.apply(x));

		System.out.println("add1 then mul3: " + add1.andThen(mul3).apply(x));
	}


	//////////////////////////////////////////////////
	// Lambda scope
	//////////////////////////////////////////////////
	static class Clazz1 {
		private int memberOffset = 2;
		public BiFunction<String, Integer, Integer> foo() {
			final var localOffset = 7;
			BiFunction<String, Integer, Integer> f1 = (str, add) -> Integer.parseInt(str) + add + localOffset + memberOffset;
			return f1;
		}
	}

	public static void foo20( String[] args ) {
		Clazz1 obj = new Clazz1();
		BiFunction<String, Integer, Integer> myfunc = obj.foo();
		System.out.println(myfunc.apply("3", 1 )); // 3 + 1 + 7 + 2 = 13
	}

	
}