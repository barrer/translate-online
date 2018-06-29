package com.translate.online.parser;

import org.junit.Test;

public class BingParserTest {
    @Test
    public void parse() throws Exception {
        String type = "必应";
        System.out.println(Parser.get(type, "test"));
        System.out.println(Parser.get(type, "take off"));
        System.out.println(Parser.get(type, "His theories have never really been put to the test."));
        System.out.println(Parser.get(type, "测试"));
    }

}