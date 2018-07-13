package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

public class WikiParser extends Parser {
    public static final String TYPE = "Wiki";

    @Override
    protected String link(String entry) {
        return "https://en.wikipedia.org/wiki/" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        return ParserUtil.wrapA(link(entry), TYPE);
    }
}
