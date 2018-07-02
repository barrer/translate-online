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
        StringBuffer type = new StringBuffer();
        StringBuffer entry = new StringBuffer();
        initParam(command, type, entry);

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
        if (type.length() > 0 && entry.length() > 0) {
            System.out.println(Parser.get(type.toString(), entry.toString()));
        }
    }

    /**
     * 初始化参数
     */
    private static void initParam(StringBuffer command, StringBuffer type, StringBuffer entry) {
        Matcher matcher = MatcherUtil.getMatcher("^type=(.*?)entry=(.*?)$", command.toString());
        if (matcher.matches()) {
            type.append(MatcherUtil.getGroup(matcher, 1));
            entry.append(MatcherUtil.getGroup(matcher, 2));
        }
        if (type.length() == 0 || entry.length() == 0)
            entry.append(command.toString());// 非正文内调用（首次调用）
    }
}
