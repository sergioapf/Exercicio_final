import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sergio.Rest;

public class Rest_Test {
    private Rest rest;

    @BeforeEach
    public void setup() {
        rest = new Rest();
    }

    @Test
    public void testLabseqValoresBase() {
        assertEquals(BigInteger.ZERO, rest.labseq(0));
        assertEquals(BigInteger.ONE, rest.labseq(1));
        assertEquals(BigInteger.ZERO, rest.labseq(2));
        assertEquals(BigInteger.ONE, rest.labseq(3));
    }

    @Test
    public void testLabseqValoresGerais() {
        
        assertEquals(BigInteger.ONE, rest.labseq(4)); 
        assertEquals(BigInteger.ONE, rest.labseq(5)); 
        assertEquals(BigInteger.valueOf(21), rest.labseq(20));
    }

    @Test
    public void testLabseqValorGrande() {

        BigInteger result = rest.labseq(1000);
        assertNotNull(result);
        assertTrue(result.compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testLabseqComNumeroNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            rest.labseq(-1);
        });
    }
}
