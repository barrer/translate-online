package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

public class MWParser extends Parser {
    public static final String TYPE = "MW";

    @Override
    protected String link(String entry) {
        return "https://www.merriam-webster.com/dictionary/" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        return ParserUtil.wrapA(link(entry), TYPE);
    }
}
