package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Parser {
    public static Map<String, Parser> entryParser = new LinkedHashMap<String, Parser>();

    static {
        if (true) {
            Parser parser = new BingParser();
            entryParser.put(parser.type(), parser);
        }
        if (true) {
            Parser parser = new YoudaoParser();
            entryParser.put(parser.type(), parser);
        }
        if (true) {
            Parser parser = new GoogleParser();
            entryParser.put(parser.type(), parser);
        }
    }

    /**
     * 返回entry对应的Parser处理后的html
     */
    public static String get(String type, String entry) {
        try {
            return entryParser.get(type).parse(entry)
                    + ParserUtil.wrapDiv("<a href=\"" + entryParser.get(type).link(entry) + "\">☞ 查看</a>");
        } catch (Exception e) {
            e.printStackTrace();
            return "parse error!";
        }

    }

    protected abstract String type();

    protected abstract String link(String entry);

    protected abstract String parse(String entry) throws Exception;
}
