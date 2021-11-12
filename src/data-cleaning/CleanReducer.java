import java.util.*;
import java.io.IOException;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.math.BigDecimal;
import org.json.JSONObject;

public class CleanReducer extends Reducer<Text, Text, NullWritable, Text> {


/*
* map json file to destination format
* review_id | business_id | review_rating | business_rating | review_text
* join the review records with business records using the business_id
*/
 
   @Override
   public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

      String reviewId = "";
      String businessId = "";
      String review_text = "";
      Float review_rating = 0f;
      Float business_rating = 0f;
      
      for (Text t:values){
         
            JSONObject jo = new JSONObject(t.toString());
            if (jo.length()==9){
               reviewId = jo.getString("review_id");
               businessId = jo.getString("business_id");
               review_text = jo.getString("text");
               review_rating = BigDecimal.valueOf(jo.getDouble("stars")).floatValue();
            }
            else {
               business_rating = BigDecimal.valueOf(jo.getDouble("stars")).floatValue();
            }
         }
         
            
                
      

      Text result = new Text(String.format("%s|%s|%s|%s|%s", reviewId, businessId, review_rating.toString(), business_rating.toString(), review_text));
      context.write(NullWritable.get(), result);
   }
}
