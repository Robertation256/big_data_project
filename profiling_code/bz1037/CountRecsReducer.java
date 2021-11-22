import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class CountRecsReducer
 extends Reducer<Text, IntWritable, Text, IntWritable> {

 @Override
 public void reduce(Text key, Iterable<IntWritable> values, Context context)
 throws IOException, InterruptedException {


      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      IntWritable result = new IntWritable(sum);
      context.write(new Text("Total number of lines: "), result);
}
}