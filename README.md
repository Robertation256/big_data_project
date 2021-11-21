# Intro

This project analyzes the public dataset provided by [Yelp](https://www.yelp.com/dataset) and contains mainly two parts: topic extraction and sentiment analysis on user reviews. 



# Files and Directories

/ana_code

- clean.jar: MapReduce job for cleaning the yelp dataset
- profile.jar: MapReduce job for analyzing the distribution of ratings
- topic_extraction.sc: Scala commands to be run in Spark interactive shell for topic extraction
- ./org: org.json, the package we used for handling JSON file. You will need to have it if you want to compile the jars by yourself. 

/data_ingestion

- commands.txt: commands for populating the original dataset into HDFS

/etl_code/yz3919

- *.java: source code of clean.jar

/profiling_code/yz3919

- *.java: source code for profile.jar

# How to Run

### Data Ingestion

1. Download full dataset from [Yelp](https://www.yelp.com/dataset)
2. Populate the data into HDFS (see /data_ingestion/commands.txt for detail commands)

### Data Cleaning

This MapReduce job extracts review.json and business.json from the aggregated dataset and join them using the "business_id" field. 

- **You can directly run the clean.jar file by**

```shell
hadoop jar clean.jar Clean {path of your original dataset on hdfs} {your desired output path on hdfs}
```

- **Or you can compile the jar file by yourself and then run it**

1. In your working directory, make sure your you have "/etl_code/yz3919/*.java" and "ana_code/org"

   ![image-20211121103521042](./screenshots/readme_pics/image-20211121103521042.png)

2. Do

   ```shell
   javac -cp `yarn classpath`:. CleanMapper.java
   ```

   ```shell
   javac -cp `yarn classpath`:. CleanReducer.java
   ```

   ```shell
   javac -cp `yarn classpath`:. Clean.java
   ```

   ```shell
   jar -cvf clean.jar *.class org/json/*.class
   ```

3. Cleaned data is ready for use as input for topic extraction.

### Data Profiling

- **You can directly run the profile.jar file by**

```shell
hadoop jar profile.jar Profile {path of your original dataset on hdfs} {your desired output path on hdfs}
```

- **Or you can compile the jar file by yourself and then run it**

1. In your working directory, make your you have "/profile_code/yz3919/*.java" and "ana_code/org" (similar to data cleaning above)

   

2. Do

   ```shell
   javac -cp `yarn classpath`:. ProfileMapper.java
   ```

   ```shell
   javac -cp `yarn classpath`:. ProfileReducer.java
   ```

   ```shell
   javac -cp `yarn classpath`:. Profile.java
   ```

   ```shell
   jar -cvf profile.jar *.class org/json/*.class
   ```

3.  Output shows the distribution of ratings

   ![image-20211121104834286](./screenshots/readme_pics/image-20211121104834286.png)



### Topic Extraction

Analyze topics of all reviews as well as reviews of good (rating = 1 - 2.5) and bad (rating = 4.5 - 5) businesses.

1. Download a stop word file

   ```
   wget http://ir.dcs.gla.ac.uk/resources/linguistic_utils/stop_words -O {your path on peel}
   ```

2. Open spark interactive shell

   ```
   spark-shell --deploy-mode client
   ```

3. Make sure you change the file paths to your own

   - stop word file path

     ![image-20211121110630835](./screenshots/readme_pics/image-20211121110630835.png)

   - input file path

     ![image-20211121110605109](./screenshots/readme_pics/image-20211121110605109.png)

4. Select all code in topic_extraction.sc and copy paste into shell. (When the last print command is reached, you may hit enter to run it)

5. Outputs are printed directly in the sequence of all reviews topics, good business review topics and bad business review topics.

