package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

public class SearchParser extends Parser {
    public static final String TYPE = "Search";

    @Override
    protected String link(String entry) {
        return "https://www.google.com/ncr#q=" + entry + "&safe=off";
    }

    @Override
    protected String parse(String entry) throws Exception {
        return ParserUtil.wrapDiv(ParserUtil.wrapA(link(entry), "Google")) +
                ParserUtil.wrapDiv(ParserUtil.wrapA("https://www.baidu.com/#wd=" + entry, "百度搜索")) +
                ParserUtil.wrapDiv(ParserUtil.wrapA("https://www.bing.com/search?q=" + entry, "Bing 搜索")) +
                "";
    }
}
