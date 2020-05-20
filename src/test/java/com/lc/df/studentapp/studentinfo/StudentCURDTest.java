package com.lc.df.studentapp.studentinfo;

import com.lc.df.studentapp.model.StudentPojo;
import com.lc.df.studentapp.testbase.TestBase;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.lc.df.studentapp.utils.TestUtils.getRandomValue;
import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertThat;

/*
 * Ravi's Creation
 * Date of Creation 17/05/20
 */

@RunWith(SerenityRunner.class)
public class StudentCURDTest extends TestBase {

    // variable created with random method
    static String firstName = "RAM" + getRandomValue();
    static String lastName = "LAKHAN";
    static String programme = "primer testing 3";
    static String email = "RAM"+ getRandomValue() + "@satyug.com";

    static int studentId;

    @Title("This test creates new student record")
    @Test
    public void test001(){

        List<String> courses = new ArrayList<>();
        courses.add("serenity");
        courses.add("CURD test");
        // object created
        StudentPojo studentPojo = new StudentPojo();

        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setCourses(courses);
        studentPojo.setId(studentId);

        SerenityRest.rest()
                .given()
                .header("Content-Type","application/json")
                .log()
                .all()
                .body(studentPojo)
                .post()
                .then()
                .log().all()
                .statusCode(201);
    }

    @Title("This test get all the student list")
    @Test
    public void test002(){
        // string path p1 & p2 separated  and concatenate after extraction for verification
        String p1 = "findAll{it.firstName=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> value = SerenityRest.rest()
                .given()
                .header("Content-Type","application/json")
                .when()
                .get("/list")

                .then()
                .statusCode(200)
                .extract()
                .path(p1 + firstName + p2); //concatenation
        assertThat(value,hasValue(firstName));
        studentId = (int) value.get("id");
    }
    @Title("Update user information and verify updated information")
    @Test
    public void test003(){

        String p1 = "findAll{it.firstName=='";
        String p2 = "'}.get(0)";

        firstName = firstName + "_change";

        List<String> courses = new ArrayList<>();
        courses.add("python");
        courses.add("pearl");
        courses.add("ruby");

        StudentPojo studentPojo = new StudentPojo();

        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setCourses(courses);

        SerenityRest.rest()
                .given()
                .header("Content-Type","application/json")
                .log().all()
                .when()
                .body(studentPojo)
                .put("/"+studentId)
                .then()
                .log().all()
                .statusCode(200);

        // assert method to verify that value has been added to

        HashMap<String ,Object> value = SerenityRest.rest().given()
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .extract()
                .path(p1+firstName+p2);
        System.out.println(value);
        assertThat(value,hasValue(firstName));

    }

    @Title("Delete the student and verify if the student is deleted")
    @Test
    public void test004() {

        SerenityRest.rest()
                .given()
                .header("Content-Type","application/json")
                .when()
                .delete("/"+studentId)
                .then()
                .statusCode(204);

        SerenityRest.rest()
                .given()
                .when()
                .get("/"+studentId)
                .then().statusCode(404);

    }

    }


