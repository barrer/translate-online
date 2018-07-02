package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Parser {
    public static Map<String, Class> entryParser = new LinkedHashMap<String, Class>();

    static {
        entryParser.put(BingParser.TYPE, BingParser.class);
        entryParser.put(YoudaoParser.TYPE, YoudaoParser.class);
        entryParser.put(GoogleParser.TYPE, GoogleParser.class);
    }

    /**
     * 返回entry对应的Parser处理后的html
     */
    public static String get(String type, String entry) {
        try {
            Parser parser = (Parser) entryParser.get(type).newInstance();
            return parser.parse(entry) + ParserUtil.wrapDiv("<a href=\"" + parser.link(entry) + "\">☞ 查看</a>");
        } catch (Exception e) {
            return "parse error!" + "<br>"
                    + "type: " + type + "<br>"
                    + "entry: " + entry + "<br>"
                    + ParserUtil.exceptionToString(e);
        }

    }

    protected abstract String link(String entry);

    protected abstract String parse(String entry) throws Exception;
}
