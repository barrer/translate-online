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

        StringBuffer out = new StringBuffer();
        // 输出列表
        StringBuffer parserHtml = new StringBuffer();
        for (String s : Parser.entryParser.keySet()) {
            if (type.length() > 0 && entry.length() > 0 && s.equals(type.toString()))// 选中
                parserHtml.append(ParserUtil.wrapA(entry.toString(),
                        ParserUtil.style_translateOnlineType + ParserUtil.style_translateOnlineTypeChecked, s));
            else// 未选中
                parserHtml.append(ParserUtil.wrapA("type=" + s + "entry=" + entry, ParserUtil.style_translateOnlineType, s));
        }
        out.append(ParserUtil.wrapDiv(ParserUtil.style_translateOnlineTypeParent, parserHtml.toString()));

        // 输出正文
        if (type.length() > 0 && entry.length() > 0) {
            out.append(Parser.get(type.toString(), entry.toString()));
        }
        System.out.println(ParserUtil.wrapDiv(ParserUtil.style_translateBase, out.toString()));
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
