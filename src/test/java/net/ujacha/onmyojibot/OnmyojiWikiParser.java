package net.ujacha.onmyojibot;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OnmyojiWikiParser {

    private final static String HOST = "https://onmyoji.fandom.com";

    @Test
    public void testGetList() throws IOException {

        // TODO ~~~headless browser -> parse~~~
        // 전체 목록 페이지 호출
        // 시간있을 때 헤드리스로도 구현

        // - list

        String listUrl = HOST + "/wiki/Shikigami/List/All";

        Document doc = Jsoup.connect(listUrl).get();
        log.debug("{}", doc.title());
        Elements tables = doc.select("table.article-table");
        Element table = tables.first();
        Elements trs = table.select("tbody tr");
        log.debug("{}", trs.size());
        for (Element tr : trs) {
//            log.debug("{}", tr.outerHtml());
            Elements tds = tr.children();
//            log.debug("tds:{}", tds.size());
//            log.debug("td[0]:{}", tds.get(0).outerHtml());  // No.
//            log.debug("td[1]:{}", tds.get(1).outerHtml());  // Image
//            log.debug("td[2]:{}", tds.get(2).outerHtml());  // Name
//            log.debug("td[3]:{}", tds.get(3).outerHtml());  // Voice Actor
//            log.debug("td[4]:{}", tds.get(4).outerHtml());  // Rarity

            String url, name, rarity;

            url = tds.get(2).select("a").attr("href");
            name = tds.get(2).select("a").attr("title");
            rarity = tds.get(4).attr("data-sort-value");
            if (StringUtils.isEmpty(rarity)) {
                rarity = tds.get(4).text();
            }
            log.debug("{}\tNAME:{}\tRARITY:{}\tURL:{}",
                    tds.get(0).text(),
                    name,
                    rarity,
                    url
            );
        }

//        for (Element headline : newsHeadlines) {
//            log.debug("{}\n\t{}",
//                    headline.attr("title"), headline.absUrl("href"));
//        }

    }

    @Test
    public void testGetItem() throws IOException {

        String itemUrl = HOST + "/wiki/Momo";

        Document doc = Jsoup.connect(itemUrl).get();
        log.debug("{}", doc.title());

        String name, rarity, defaultSkin;
        Map<String, String> art = new HashMap<>();

        name = doc.select("#PageHeader h1.page-header__title").text();
        rarity = doc.select("#PageHeader div.page-header__categories>.page-header__categories-links").text();

        log.debug("NAME: {}", name);
        log.debug("RARITY: {}", rarity);

        defaultSkin = doc.select("#mw-content-text > div.hidden.oinfoboxgallery > div > div > a > img").attr("data-src");

        log.debug("SKIN: {}", defaultSkin);


        doc = Jsoup.connect(itemUrl + "/Gallery").get();

        Element gallary0 = doc.select("#gallery-0").first();

        Elements items = gallary0.select(".wikia-gallery-item");

        items.forEach(item -> {

//            log.debug("ITEM: {}", item.outerHtml());
            log.debug("caption: {}", item.select(".lightbox-caption").text());
            log.debug("image name: {}", item.select("img").attr("data-image-name"));
            log.debug("image url: {}", item.select("img").attr("data-src"));

        });

    }

}
