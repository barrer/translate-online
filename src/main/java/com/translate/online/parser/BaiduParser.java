package com.translate.online.parser;

import com.translate.online.util.ParserUtil;

public class BaiduParser extends Parser {
    public static final String TYPE = "百度";

    @Override
    protected String link(String entry) {
        if (ParserUtil.hasChineseByRange(entry))
            return "https://fanyi.baidu.com/#zh/en/" + entry;
        else
            return "https://fanyi.baidu.com/#en/zh/" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        return ParserUtil.wrapA(link(entry), TYPE);
    }
}
