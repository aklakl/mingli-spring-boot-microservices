package com.mingli.rwholding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.robustwealth.microservices.rwholding.model.HoldingDTO;

public class Test {

//	public static void main(String[] args) {
//		test1();
//	}
	
	public  static void test1() {
		List<HoldingDTO> holdingList = new ArrayList<HoldingDTO>();
		for(int i=0;i<10;i++) {
			HoldingDTO holding = new HoldingDTO();
			holding.setAccount_id("account_id"+i);
			holdingList.add(holding);
		}
		Stream<?> stream  = holdingList.stream().map( p -> {
			  return p.getAccount_id();
		});
		//List<String> accountIDs = (List<String>) stream.collect(Collectors.toList());
		List<Object> accountIDs =  Arrays.asList(stream.collect(Collectors.toList()));
		testGetFunctionName();
		
		test0(accountIDs);
		
	}

	public static void testGetFunctionName() {  
        String funcName2 = new Throwable().getStackTrace()[1].getMethodName();  
        System.out.println(funcName2);  
        String clazzName4 = Thread.currentThread().getStackTrace()[2].getMethodName();  
        System.out.println(clazzName4);   
    } 
	
	public  static  void test0(List list) {
		if (list == null || list.size() ==0) {
			for(int i=0;i<10;i++) {
				list.add(i);
			}
		}
		
		String string = list.toString();
		System.out.println("list.toString():"+string);
		
		System.out.println("list.toString():"+string.replace("[", "").replace("]", ""));
		
	}
	
}
