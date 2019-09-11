package net.ibokette.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class Java8To12LanguageFeatures {
	public static void main( String[] args ) { }
	
	//////////////////////////////////////////////////
	// Simple Lamba Expression
	//////////////////////////////////////////////////
	static JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	static void foo20() {
		List<Long> gidList = jdbcTemplate.query("select attrb_gid as gid from srm.attrb", 
				new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("gid");
			}
		});
		System.out.println(gidList);
	}
	
	static void foo21() {
		List<Long> gidList = jdbcTemplate.query("select attrb_gid as gid from srm.attrb", 
			(rs, rowNum) -> {
				return rs.getLong("gid");
		});
		System.out.println(gidList);
	}
	
	//////////////////////////////////////////////////
	// Streams
	//////////////////////////////////////////////////
	static void foo22() {
		List<String> list = new ArrayList<>(); list.add("1"); list.add("11"); list.add("111");

		List<Integer> moreThanTenList1 = new ArrayList<>();
		for (String str : list) {
			int i = Integer.parseInt(str);
			if (i > 10) {
				moreThanTenList1.add(i);
			}
		}

		List<Integer> moreThanTenList2 = list.stream()
				.map(Integer::parseInt)
				.filter(i -> i > 10)
				.collect(Collectors.toList());

		System.out.println(moreThanTenList2);
	}
	
	//////////////////////////////////////////////////
	// Default Methods
	//////////////////////////////////////////////////
	public interface ITestInterface {
		default public void sayHello() {
			System.out.println("Hi World!");
		}
	}
	
	//////////////////////////////////////////////////
	// Optional
	//////////////////////////////////////////////////
	static void foo23() {
        Optional<String> name = Optional.empty();
        //
        // Do some stuff that might do something with name
        //
        name.ifPresentOrElse(validName -> {
        	System.out.println("Her name is " + validName);
        }, () -> {
        	System.out.println("She has no name!!! oh no!!!!!");
        });
	}
	
	//////////////////////////////////////////////////
	// Immutable List/Set/Map
	//////////////////////////////////////////////////
    static void func1() {
    	
    	List<String> list = List.of("a");
    	
    	Set<Long> set = Set.of(12L, 24L, 48L);
    	
    	Map<String, Integer> map = Map.of("a", 1, "b", 2);
    	
    	System.out.println(list);
    	System.out.println(set);
    	System.out.println(map);
    }
    
	//////////////////////////////////////////////////
    // Local variable type inference
	//////////////////////////////////////////////////
    static void func2() {
    	
    	var list = List.of("a");
    	
    	var set = Set.of(12L, 24L, 48L);
    	
    	var map = Map.of("a", 1, "b", 2);
    	
    	System.out.println(list);
    	System.out.println(set);
    	System.out.println(map);
    }
}