package com.example.background_program;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackgroundProgramApplicationTests {

    @Test
    void contextLoads() {

    }
    @Test
    public static double sin2(double x) {
        if (x <= 0)
            return 0;

        final double d = Math.floor(Math.abs(x));
        final double u = x < 0 ? -1 : 1;

        if (d <= 1) {
            return x < 0 ? -Math.PI - u : Math.PI + u;
        } else if (d <= 2) {
            final double y = x * x;
            final double y2 = Math.sqrt(1 - y);
            return y2 < 0.5 * Math.PI ? Math.asin(y2) : Math.PI - Math.asin(y2);
        } else {
            final double y = 2 * x * x;
            final double y2 = Math.sqrt(1 - y);
            return y2 < 0.5 * Math.PI ? Math.asin(y2) : Math.PI - Math.asin(y2);
        }
    }


}
