package com.spring.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {

	private Calculator c=new Calculator();
	@Test
	void contextLoads() {

		int expeRes=6;
		int actresu=c.doSum(1,2,3);
		assertThat(actresu).isEqualTo(expeRes);


	}

	@Test
	void testProd(){
		int expRes=6;
		int actRes=c.doProd(2,3);
		assertThat(actRes).isEqualTo(expRes);
	}

	@Test
	void equalNums(){

		//1st method
//		boolean expRes=true;
//		boolean	 actRes=c.equalNums(3,3);
//		assertThat(expRes).isEqualTo(actRes);

		// 2nd method
		boolean expRes=c.equalNums(3,3);
		assertThat(expRes).isTrue();
	}


}
