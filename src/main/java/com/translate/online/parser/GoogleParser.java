package com.translate.online.parser;

import com.translate.online.util.ParserUtil;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleParser extends Parser {
    public static final String TYPE = "谷歌";

    @Override
    protected String link(String entry) {
        if (ParserUtil.hasChineseByRange(entry))
            return "https://translate.google.cn/#auto/en/" + entry;
        else
            return "https://translate.google.cn/#auto/zh-CN/" + entry;
    }

    @Override
    protected String parse(String entry) throws Exception {
        // ref: https://greasyfork.org/zh-CN/scripts/35251-%E6%99%BA%E8%83%BD%E5%88%92%E8%AF%8D%E7%BF%BB%E8%AF%91/code
        String url = "https://translate.google.cn/translate_a/single?client=gtx&dt=t&dt=bd&dj=1&source=input&hl=zh-CN&sl=auto&tl=";
        if (ParserUtil.hasChineseByRange(entry))
            url += "en&q=" + entry;
        else
            url += "zh-CN&q=" + entry;
        String json = ParserUtil.getUrl(url);
        StringBuffer sb = new StringBuffer();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray sentences = jsonObject.getJSONArray("sentences");
        for (Object sen : sentences) {
            JSONObject jSen = (JSONObject) sen;
            sb.append(ParserUtil.wrapDiv(jSen.get("orig").toString()));
            sb.append(ParserUtil.wrapDiv(jSen.get("trans").toString()));
        }
        return sb.toString();
    }
}
