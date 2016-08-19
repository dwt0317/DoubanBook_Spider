package crawler;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class CustomPipeline implements Pipeline, Closeable{
	private String dirPath;
	private AtomicLong count;
	private long fileIndex;
	private String path;
	private BufferedWriter bw;

	
	public CustomPipeline(String dirPath){
		this.dirPath = dirPath;
		count = new AtomicLong(0L);
		fileIndex = 0L;
		path = this.dirPath + "/"+ fileIndex ;
		try {
			bw = new BufferedWriter(new FileWriter(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void process(ResultItems rst, Task task) {
		if(rst==null)
			return;
		
		if(fileIndex < count.getAndIncrement()/Constants.fileRecordNum){
			try {
				bw.close();
				path = this.dirPath + "/"+ (++fileIndex) ;
				bw = new BufferedWriter(new FileWriter(path));
				
			} catch (IOException e) {
				System.err.println("open file error");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		try {			 
			bw.write(JSON.toJSONString(rst.getAll()));
			bw.write("\n");
				
		} catch (IOException e) {
			System.err.println("write file error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		bw.close();
	}

}
