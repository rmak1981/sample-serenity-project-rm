package com.lc.df.studentapp.utils;

import java.util.Random;

/*
 * Ravi's Creation
 * Date of Creation 17/05/2020
 */
public class TestUtils {

    public static String getRandomValue(){
        Random random = new Random();
        int randomInt = random.nextInt(100000);
        return Integer.toString(randomInt);
    }
}
