package crawler;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class DoubanProcessor implements PageProcessor {

	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(Constants.sleepTime);

    public void process(Page page) {
    	List<String> contentPages = page.getHtml().xpath("//div[@class='paginator']").links().all();
 	
    	//目录页
    	if(contentPages.size()!=0){
    		page.setSkip(true);		
    		page.addTargetRequests(page.getHtml().xpath("//div[@class='info']/h2").links().all());
    		page.addTargetRequests(contentPages);
    	}
    	//内容页
    	else{
    		System.out.println(page.getHtml().xpath("//meta[@name='keywords']/@content").toString());
            page.putField("keywords", page.getHtml().xpath("//meta[@name='keywords']/@content").toString());
            if (page.getResultItems().get("keywords")==null){
                //skip this page
                page.setSkip(true);
            }else{
            	page.putField("info", page.getHtml().xpath("//div[@class='intro']/allText()"));
            }
    	}
    	     
       
    }

    public Site getSite() {
        return site;
    }


}
