package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

public class WiktionaryParser extends Parser {
    public static final String TYPE = "Wiktionary";

    @Override
    protected String link(String entry) {
        return "https://en.wiktionary.org/wiki/" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        return ParserUtil.wrapA(link(entry), TYPE);
    }
}
