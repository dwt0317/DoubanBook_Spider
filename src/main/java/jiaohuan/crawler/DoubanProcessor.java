package jiaohuan.crawler;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class DoubanProcessor implements PageProcessor {
    public static void main(String[] args) {
        Spider.create(new DoubanProcessor()).addUrl("https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4?start=0&type=T").thread(1).run();
    }
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
    	List<String> contentPages = page.getHtml().xpath("//div[@class='paginator']").links().all();
    	page.addTargetRequests(contentPages);
        page.putField("name", page.getHtml().xpath("//span[@property='v:itemreviewed']/text()").toString());
        System.out.println(page.getResultItems().get("name"));
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("content", page.getHtml().xpath("//div[@class='intro']/text()"));
       
    }

    public Site getSite() {
        return site;
    }


}
