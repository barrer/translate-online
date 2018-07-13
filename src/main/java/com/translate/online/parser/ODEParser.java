package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

public class ODEParser extends Parser {
    public static final String TYPE = "ODE";

    @Override
    protected String link(String entry) {
        return "https://en.oxforddictionaries.com/definition/" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        return ParserUtil.wrapA(link(entry), TYPE);
    }
}
