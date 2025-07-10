import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class Rest_EndPoint_Test {
     @Test
    public void testGetLabseqValido() {
        RestAssured.given()
            .when().get("/labseq/5")
            .then()
            .statusCode(200)
            .body(is("1"));
    }

    @Test
    public void testGetLabseqNegativo() {
        RestAssured.given()
            .when().get("/labseq/-1")
            .then()
            .statusCode(400)
            .body(is("{\"error\": \"n deve ser um inteiro não-negativo\"}"));
    }
}
