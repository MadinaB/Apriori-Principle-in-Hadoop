package apriori;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import utils.AprioriUtils;

import java.io.IOException;

/*
 * Reducer for all phases would collect the emitted itemId keys from all the mappers
 * and aggregate it to return the count for each itemId.
 */

public class AprioriReducer extends Reducer<Text, IntWritable, Text, IntWritable>
{
    public void reduce(Text itemSet, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        
        IntWritable result = new IntWritable();
        Double minSup = Double.parseDouble(context.getConfiguration().get("minSup"));
        Integer numTxns = context.getConfiguration().getInt("numTxns", 2);
    	int itemCount = 0;
        for (IntWritable val : values) {
        	itemCount+= val.get();
        }
        if( AprioriUtils.hasMinSupport( minSup, numTxns, itemCount)){
        	 result.set(itemCount);
        	 context.write(itemSet, result);
        }
       

    }
}
