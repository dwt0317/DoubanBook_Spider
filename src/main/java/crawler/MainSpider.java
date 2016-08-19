package crawler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.BloomFilterDuplicateRemover;

public class MainSpider {
	
    public static void main(String[] args) throws InterruptedException {
    	CustomSpider spi = CustomSpider.create(new DoubanProcessor());
    	spi.addUrl("https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4?start=0&type=T")
    	    .addPipeline(new CustomPipeline("./douban"))
    	    .setScheduler(new QueueScheduler()
    	    		.setDuplicateRemover(new BloomFilterDuplicateRemover(1000))) //1000是估计的页面数量
    	    .thread(3);
        
        spi.start();
        while(spi.getPageCount()<Constants.pageNum){
        	Thread.sleep(200);
        }
        
        spi.stopThread();
    }
}
