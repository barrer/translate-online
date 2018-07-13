package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Parser {
    public static final String LINE_BREAK = "line-break";
    public static Map<String, Class> entryParser = new LinkedHashMap<String, Class>();

    static {
        entryParser.put(BingParser.TYPE, BingParser.class);
        entryParser.put(YoudaoParser.TYPE, YoudaoParser.class);
        entryParser.put(GoogleParser.TYPE, GoogleParser.class);
        entryParser.put(IcibaParser.TYPE, IcibaParser.class);
        entryParser.put(SogouParser.TYPE, SogouParser.class);
        entryParser.put(BaiduParser.TYPE, BaiduParser.class);
        entryParser.put(LINE_BREAK, null);
        entryParser.put(SearchParser.TYPE, SearchParser.class);
        entryParser.put(WikiParser.TYPE, WikiParser.class);
        entryParser.put(ODEParser.TYPE, ODEParser.class);
        entryParser.put(MWParser.TYPE, MWParser.class);
        entryParser.put(WiktionaryParser.TYPE, WiktionaryParser.class);
        entryParser.put(UrbanParser.TYPE, UrbanParser.class);
    }

    /**
     * 返回entry对应的Parser处理后的html
     */
    public static String get(String type, String entry) {
        try {
            Parser parser = (Parser) entryParser.get(type).newInstance();
            return ParserUtil.wrapDiv(parser.parse(entry)) +
                    ParserUtil.wrapDiv(ParserUtil.wrapA(parser.link(entry), ParserUtil.style_translateOnlineSee, "☞"));
        } catch (Exception e) {
            return ParserUtil.wrapDiv(ParserUtil.style_translateOnlineError,
                    "parse error!" + "<br>" + "type: " + type + "<br>" + "entry: " + entry + "<br>" + ParserUtil.exceptionToString(e));
        }
    }

    protected abstract String link(String entry);

    protected abstract String parse(String entry) throws Exception;
}
