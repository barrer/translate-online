package com.translate.online;

import com.translate.online.parser.Parser;
import com.translate.online.util.MatcherUtil;
import com.translate.online.util.ParserUtil;

import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) throws Exception {
        // 拼接参数
        StringBuffer command = new StringBuffer();
        int argFlag = 0;
        for (String arg : args) {
            command.append(arg);
            if (argFlag != args.length - 1)
                command.append(" ");
            argFlag++;
        }
        String type = getType(command);
        String entry = getEntry(command);
        if (type == null || entry == null)
            entry = command.toString();// 非正文内调用

        // 输出列表
        String style = "display:inline-block;" +
                "font-weight:bold;" +
                "line-height:120%;" +
                "margin:.2em .3em;" +
                "padding:1px 3px;" +
                "background-color:#36c;" +
                "border-radius:2px;" +
                "color:#fff;" +
                "font-size:90%;";
        StringBuffer parserHtml = new StringBuffer();
        for (String s : Parser.entryParser.keySet()) {
            parserHtml.append("<a " +
                    "style=\"" + style + "\"" +
                    "href=\"type=" + s +
                    "entry=" + entry + "\">" + s + "</a>");
        }
        System.out.println(ParserUtil.wrapDiv(parserHtml.toString()));

        // 输出entry
        System.out.println("<div>" +
                "<a style=\"font-style:italic;color:#f09;\" href=\"" + entry + "\">" + entry + "</a>" +
                "</div>");

        // 输出正文
        if (type != null && entry != null) {
            System.out.println(Parser.get(type, entry));
        }
    }

    /**
     * 得到类型
     */
    private static String getType(StringBuffer command) {
        Matcher matcher = MatcherUtil.getMatcher("^type=(.*?)entry=(.*?)$", command.toString());
        if (matcher.matches())
            return MatcherUtil.getGroup(matcher, 1);
        return null;
    }

    /**
     * 得到entry
     */
    private static String getEntry(StringBuffer command) {
        Matcher matcher = MatcherUtil.getMatcher("^type=(.*?)entry=(.*?)$", command.toString());
        if (matcher.matches())
            return MatcherUtil.getGroup(matcher, 2);
        return null;
    }
}
