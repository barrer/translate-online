package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

public class UrbanParser extends Parser {
    public static final String TYPE = "Urban";

    @Override
    protected String link(String entry) {
        return "https://www.urbandictionary.com/define.php?term=" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        return ParserUtil.wrapA(link(entry), TYPE);
    }
}
