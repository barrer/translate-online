package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

public class SogouParser extends Parser {
    public static final String TYPE = "搜狗";

    @Override
    protected String link(String entry) {
        if (ParserUtil.hasChineseByRange(entry))
            return "https://fanyi.sogou.com/#auto/en/" + entry;
        else
            return "https://fanyi.sogou.com/#auto/zh-CHS/" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        return ParserUtil.wrapA(link(entry), TYPE);
    }
}
