package pl.edu.agh.internetshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.agh.internetshop.util.CustomAssertions.assertBigDecimalCompareValue;

import java.math.BigDecimal;


public class ProductTest {

	
    private static final String NAME = "Mr. Sparkle";
    private static final BigDecimal PRICE = BigDecimal.valueOf(1);
    private static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.76);

	@Test
    public void testProductName() throws Exception{
        //given
    	
        // when
        Product product = new Product(NAME, PRICE);
        
        // then
        assertEquals(NAME, product.getName());
    }
    
    @Test
    public void testProductPriceWithoutDiscount() throws Exception{
        //given
    	
        // when
        Product product = new Product(NAME, PRICE);
        
        // then
        assertBigDecimalCompareValue(product.getPrice(), PRICE);
    }

    @Test
    public void testProductPriceWithDiscount() throws Exception{
        //given

        // when
        Product product = new Product(NAME, PRICE, DISCOUNT);

        // then
        assertBigDecimalCompareValue(product.getPrice(), PRICE.multiply(DISCOUNT));
    }

    @Test
    public void testProductDiscount() throws Exception{
        //given

        // when
        Product product = new Product(NAME, PRICE, DISCOUNT);

        // then
        assertEquals(product.getProductDiscount(), DISCOUNT);
    }
}