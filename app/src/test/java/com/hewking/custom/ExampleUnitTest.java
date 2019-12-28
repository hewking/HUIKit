package com.hewking.custom;

import androidx.core.util.PatternsCompat;

import org.junit.Test;

import java.util.regex.Matcher;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRegex(){
        String url = "http://tool.oschina.net/apidocs/apidoc?api=jdk-zh";
        String url2 = "  baid.com";
        String text = "heheda " + url + url2;

        Matcher matcher = PatternsCompat.WEB_URL.matcher(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            System.out.println("start : " + start + " end : " + end + "testRegex : " + matcher.group());
        }


    }

}