package com.translate.online.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtil {
    public static Matcher getMatcher(String patternStr, String text) {
        Pattern pattern = Pattern.compile(patternStr);
        return pattern.matcher(text);
    }

    public static boolean find(String patternStr, String text) {
        Matcher matcher = MatcherUtil.getMatcher(patternStr, text);
        return matcher.find();
    }

    public static void printGroup(Matcher matcher) {
        matcher.reset();
        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(matcher.group(i));
            }
        }
    }

    public static String getGroup(Matcher matcher) {
        matcher.reset();
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                sb.append(matcher.group(i));
            }
        }
        return sb.toString();
    }

    public static void getGroup(Matcher matcher, List<String> list) {
        matcher.reset();
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                list.add(matcher.group(i));
            }
        }
    }

    public static String getGroup(Matcher matcher, Integer index) {
        matcher.reset();
        String str = null;
        while (matcher.find()) {
            str = matcher.group(index);
            break;
        }
        return str;
    }
}
