package com.translate.online.parser;

import com.translate.online.util.ParserUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

public class BingParser extends Parser {
    public static final String TYPE = "必应";

    @Override
    protected String link(String entry) {
        return "https://cn.bing.com/dict/search?q=" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        String url = "https://cn.bing.com/dict/search?q=" + entry;
        String html = ParserUtil.getUrl(url);
        html = html.replaceAll("<script[\\s\\S]*?</script>", "")
                .replaceAll("<style[\\s\\S]*?</style>", "")
                .replaceAll("<meta[\\s\\S]*?/>", "")
                .replaceAll("<link[\\s\\S]*?/>", "")
                .replaceAll("<!--[\\s\\S]*?-->", "");
        StringBuffer sb = new StringBuffer();
        Document doc = ParserUtil.getNoPrettyDoc(html);
        // 词头
        sb.append(ParserUtil.wrapDiv(doc.select(".qdef .hd_area").text()));
        // 简单释义
        Elements para = doc.select(".qdef ul,.qdef .hd_div1");
        Iterator<Element> paraIter = para.iterator();
        while (paraIter.hasNext()) {
            Element ele = paraIter.next();
            for (Element pos : ele.select(".pos")) {
                if (pos.hasClass("web"))
                    pos.append("：");
                else
                    pos.append("&nbsp;");
            }
            for (Element chil : ele.children())
                sb.append(ParserUtil.wrapDiv(chil.text()));
        }
        // 长句翻译
        Elements sentence = doc.select(".lf_area");
        if (sentence.html().indexOf("计算机翻译") != -1) {
            Iterator<Element> sentenceIter = sentence.select(".p1-10,.p1-11").iterator();
            while (sentenceIter.hasNext()) {
                Element ele = sentenceIter.next();
                sb.append(ParserUtil.wrapDiv(ele.text()));
            }
        }
        return sb.toString();
    }
}
