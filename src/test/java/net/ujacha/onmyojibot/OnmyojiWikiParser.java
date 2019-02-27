package net.ujacha.onmyojibot;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class OnmyojiWikiParser {

    @Test
    public void test() throws IOException {

        // TODO headless browser -> parse
        // - list
        // - item
        // - image





        //
//        Document doc = Jsoup.connect("https://onmyoji.fandom.com/wiki/Shikigami/List").get();
//        log.debug("{}", doc.title());
//        Elements tables = doc.select("table");
//
//        tables.forEach(table -> {
//            log.debug("table: {}", table.html());
//
//        });


//        Elements trs = table.select("tbody tr");
//        for(Element tr : trs){
//            Elements tds = tr.children();
//            log.debug("{}\t{}\t{}",
//                    tds.get(4).attr("title"),
//                    tds.get(2).attr("title"),
//                    tds.get(2).attr("href")
//            );
//        }

//        for (Element headline : newsHeadlines) {
//            log.debug("{}\n\t{}",
//                    headline.attr("title"), headline.absUrl("href"));
//        }

    }

}
