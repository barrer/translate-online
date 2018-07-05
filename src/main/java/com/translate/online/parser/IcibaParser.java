package com.translate.online.parser;

import com.translate.online.util.MatcherUtil;
import com.translate.online.util.ParserUtil;

import java.util.regex.Matcher;

public class IcibaParser extends Parser {
    public static final String TYPE = "金山";

    @Override
    protected String link(String entry) {
        return "http://www.iciba.com/" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        String url = "http://open.iciba.com/huaci_new/dict.php?word=" + entry;
        String html = ParserUtil.getUrl(url);
        html = html.replaceAll("<a[^>]*?>([^<>]*?)</a>", "$1")
                .replace("<p", "<div")
                .replace("</p>", "</div>");
        Matcher matcher = MatcherUtil.getMatcher("dict.innerHTML='([\\s\\S].*?)';\n" + "   \tvar loading", html);
        StringBuffer sb = new StringBuffer();
        sb.append(MatcherUtil.getGroup(matcher, 1));
        return sb.toString();
    }
}
