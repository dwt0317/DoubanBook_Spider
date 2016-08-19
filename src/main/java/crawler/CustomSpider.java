package crawler;

import java.io.Closeable;
import java.io.IOException;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class CustomSpider extends Spider{
	
	public CustomSpider(PageProcessor pageProcessor) {
		super(pageProcessor);
		// TODO Auto-generated constructor stub
	}
	
	public static CustomSpider create(PageProcessor pageProcessor) {
        return (CustomSpider)new CustomSpider(pageProcessor);
    }
	
	
    private void destroyEach(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public void stopThread(){	
		super.stat.set(STAT_STOPPED);    
	}
	
	@Override
    public void close() {
		threadPool.shutdown();
		while(threadPool.getThreadAlive()!=0){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
        destroyEach(downloader);
        destroyEach(pageProcessor);
        destroyEach(scheduler);
        for (Pipeline pipeline : pipelines) {
            destroyEach(pipeline);
        }       
    }
}
