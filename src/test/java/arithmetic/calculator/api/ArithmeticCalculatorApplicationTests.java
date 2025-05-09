package arithmetic.calculator.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArithmeticCalculatorApplicationTests {
	@Test
	void contextLoads() {
		ArithmeticCalculatorApplication.main(new String[]{});
	}
}
