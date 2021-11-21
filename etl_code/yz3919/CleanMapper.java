import org.json.JSONObject;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
* map json file to destination format
* review_id | business_id | review_rating | business_rating | review_text
*/



public class CleanMapper extends Mapper<LongWritable, Text, Text, Text> {
    
 
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
       try{ 
            JSONObject jo = new JSONObject(value.toString());
            if (jo.length()==9 || jo.length()==14){
                context.write(new Text(jo.getString("business_id")), value);
            }  
        }
	catch(Exception e){
           return;
        }
        
    }
}
