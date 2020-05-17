package com.lc.df.studentapp.testbase;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

/*
 * Ravi's Creation
 * Date of Creation 17/05/2020
 */
public class TestBase {
    @BeforeClass
    public static void inIt() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/student";
    }
}
