package com.translate.online.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParserUtil {
    /**
     * 请求user-agent
     */
    private static final String DEFAULT_USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36";

    /**
     * 根据url获取响应网页html
     *
     * @param url              网址
     * @param maxBodySizeBytes 最大响应允许大小（0：无限）
     * @param followRedirects  是否跟随重定向
     * @param method           POST 或 GET
     */
    public static String getUrl(String url,
                                int maxBodySizeBytes,
                                int timeout,
                                boolean followRedirects,
                                Connection.Method method) throws IOException {
        Connection connection = Jsoup.connect(url)
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .userAgent(DEFAULT_USER_AGENT)
                .maxBodySize(maxBodySizeBytes)
                .timeout(timeout)
                .followRedirects(followRedirects)
                .method(method);

        Connection.Response response = connection.execute();
        return response.body();
    }

    public static String getUrl(String url) throws IOException {
        return getUrl(url, 0, 30000, true, Connection.Method.GET);
    }


    /**
     * 得到不美化输出的Document
     */
    public static Document getNoPrettyDoc(String html) {
        Document doc = Jsoup.parse(html);
        doc.outputSettings().prettyPrint(false);
        return doc;
    }

    /**
     * 移除注释
     */
    public static void removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size(); ) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
    }

    /**
     * 移除属性
     * <pre>{@code
     * Map<String, String> mAttr = new LinkedHashMap<String, String>();
     * mAttr.put("^src$", ".*");
     * exclude.put("^img$", mAttr);
     * }</pre>
     *
     * @param exclude 排除列表（key：节点名字，value：属性名正则与属性值正则）
     */
    public static Element removeAttr(Element element, Map<String, Map<String, String>> exclude) {
        Elements elements = element.getAllElements();
        for (Element ele : elements) {// 删除属性
            String nodeName = ele.nodeName();
            Attributes attributes = ele.attributes();
            Iterator<Attribute> attrIter = attributes.iterator();
            while (attrIter.hasNext()) {
                Attribute attr = attrIter.next();
                String key = attr.getKey();
                String value = attr.getValue();

                boolean flag = true;// 匹配所有规则，一旦有一个匹配到就不删
                for (String mNode : exclude.keySet()) {
                    Map<String, String> mAttr = exclude.get(mNode);
                    for (String mKey : mAttr.keySet()) {
                        String mVal = mAttr.get(mKey);
                        if (MatcherUtil.find(mNode, nodeName) && MatcherUtil.find(mKey, key) && MatcherUtil.find(mVal, value)) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag)
                    ele.removeAttr(key);
            }
        }
        return element;
    }

    /**
     * 删除所有属性
     */
    public static Element removeAttr(Element element) {
        return removeAttr(element, new HashMap<String, Map<String, String>>());
    }

    /**
     * 包装div指定class
     */
    public static String wrapDiv(String className, String innerHtml) {
        return "<div class=\"" + className + "\">" + innerHtml + "</div>";
    }

    /**
     * 包装div
     */
    public static String wrapDiv(String innerHtml) {
        return "<div>" + innerHtml + "</div>";
    }

    /**
     * 包装span指定class
     */
    public static String wrapSpan(String className, String innerHtml) {
        return "<span class=\"" + className + "\">" + innerHtml + "</span>";
    }

    /**
     * 包装span
     */
    public static String wrapSpan(String innerHtml) {
        return "<span>" + innerHtml + "</span>";
    }

    /**
     * 是否包含汉字<br>
     * 根据汉字编码范围进行判断<br>
     * CJK统一汉字（不包含中文的，。《》（）“‘’”、！￥等符号）<br>
     * ref: https://segmentfault.com/a/1190000004408403
     *
     * @param str
     * @return
     */
    public static boolean hasChineseByRange(String str) {
        if (str == null) {
            return false;
        }
        char[] ch = str.toCharArray();
        for (char c : ch) {
            if (c >= 0x4E00 && c <= 0x9FBF) {
                return true;
            }
        }
        return false;
    }
}
