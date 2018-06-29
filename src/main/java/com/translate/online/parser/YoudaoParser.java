package com.translate.online.parser;

import com.translate.online.util.ParserUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

public class YoudaoParser extends Parser {
    @Override
    protected String type() {
        return "有道";
    }

    @Override
    protected String link(String entry) {
        return "https://dict.youdao.com/w/eng/" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        String url = "https://dict.youdao.com/w/eng/" + entry;
        String html = ParserUtil.getUrl(url);
        StringBuffer sb = new StringBuffer();
        Document doc = ParserUtil.getNoPrettyDoc(html);
        // 词头
        doc.select("a.addExp").remove();
        sb.append(ParserUtil.wrapDiv(doc.select("#results-contents #phrsListTab .wordbook-js").text()));
        // 简单释义
        Elements para = doc.select("#results-contents #phrsListTab .trans-container ul");
        Iterator<Element> paraIter = para.iterator();
        while (paraIter.hasNext()) {
            Element ele = paraIter.next();
            for (Element chil : ele.children())
                sb.append(ParserUtil.wrapDiv(chil.text()));
        }
        // 网络翻译
        Elements webTrans = doc.select("#tWebTrans .wt-container .title>span");
        if (webTrans.size() > 0)
            sb.append("网络：");
        int webTransFlag = 0;
        for (Element webTran : webTrans) {
            sb.append(webTran.text().trim());
            if (webTransFlag != webTrans.size() - 1)
                sb.append("；");
            webTransFlag++;
        }
        // 汉汉释义
        Elements hanhan = doc.select("#hhDictTrans");
        Iterator<Element> hanhanIter = hanhan.iterator();
        while (hanhanIter.hasNext()) {
            Element ele = hanhanIter.next();
            ParserUtil.removeComments(ele);
            sb.append(ParserUtil.wrapDiv(ParserUtil.removeAttr(ele).html()));
        }
        // 长句翻译
        Elements sentence = doc.select("#fanyiToggle .trans-container");
        Iterator<Element> sentenceIter = sentence.select("p").iterator();
        while (sentenceIter.hasNext()) {
            Element ele = sentenceIter.next();
            if (ele.html().indexOf("以上为机器翻译结果") != -1)
                ele.remove();
            else
                sb.append(ParserUtil.wrapDiv(ele.text()));
        }
        return sb.toString();
    }
}
