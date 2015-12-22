# MapReduceInJava

MapReduce algorithm simualted on a Facebook Dataset using the Java Virtual Machine's multithreadding facilities.

21.12.2015

MULTICORE IMPLEMENTATION OF MAPREDUCE ALGORITHM

FINDING COMMON FRIENDS IN A SOCIAL NETWORK DATASET

Arya Volkan Şen

1)INTRODUCTION

As multi-core chips become ubiquitous, we need parallel programs that can exploit more than one processor. Our project is about multi-core implementation of mapreduce algorithm and we are finding common friends in a social network dataset.

2)MAPREDUCE OVERVIEW
	
MapReduce is a programming model and an associated implementation for processing and generating large data sets with a parallel, distributed algorithm on a cluster. MapReduce algorithm has two functions that are Map and Reduce. Map function extracts something you care about and group by key. The intermediate output is a set of <key , value> pairs. The reduce function is applied to all intermediate output with the same key. Reduce function aggregates and summarizes them and write the result.

The following pseudocode shows the basic structure of a MapReduce program that lists the common friends of users. The map function emits the <key, value> intermediate pairs. Reduce function gives the list of common friends. 

// input: Social Media User List
// intermediate output: key=(A,B); value=(friend list)

Map(input User u, friendList){ 
for each User x in friendList 		//Applied to each input element
EmitIntermediate((u,x), list(friendsOf(u));	// <key, val> intermediate pairs
}

// intermediate output: key=(A,B), value=friend list
//output: key=(A,B); value=matches

Reduce(input key, values1[ ] values2[ ]){
for each v in values1
if(v exists in values2)			//Applied to all pairs with the same key
resultList.append(v);

Emit(key, resultList);				//Final output sorted by key
}

3) GENERAL STRUCTURE AND CONTROL FLOW

Figure 1 shows general structure and the basic data flow for the runtime system.
The scheduler creates and manages the threads that run all Map and Reduce tasks. After initialization, the scheduler determines the number of cores to use for this computation. For each core, it spawns a worker thread that is dynamically assigned some number of Map and Reduce tasks. There are four stages in general structure. After initialization in the first stage splitter divides input data to equally sized units for processing in the Map stage.For every Map task splitter is being called to return pointer to the data. Each task applies map function to an input chunks and emits intermediate <key,value> pairs. The Partition function splits the intermediate pairs into units for the Reduce tasks. All values of the same key goes to the same unit. Reduce stage starts after map task is complete.Reduce task are also completed as a map reduce, but the difference is that all the key values with the same key are assigned in one task. The output of the Reduce stage is already sorted by keys, and in the Merge stage all pairs with sorted keys are merged in one buffer.


Figure 1

4) IMPLEMENTATION
We have implemented the project with Java using the thread utilization of JVM. In our implementation we simulated MapReduce algorithm with its intermediate steps and outputs. Locality and cache affinity is dependant on the block size so in our implementation we gave the option to the end-user. In our experiments large block size like 500 had around 6 times slower performance where block size of 25 had got the job done around 7 minutes. We couldn’t implement dynamic load balancing just yet but using the thread-pool libraries it is only a few hours work. 
	

Our dataset is coming from Facebook and includes more than 4 thousand people with their identities kept secret. Final output of our algorithm gives the common friends of those 4 thousand people if they are friends already. This output is close to 400 mb of data uncompressed and text based. Considering we only used an Intel i5, with 4 cores; this amount of data had pretty good processing time.

In our implementation we tried to utilize the shared memory approach and only used the disk to give intermediate outputs. Even though in this application we didn’t need the disk to swap the data in and out, if we had larger dataset we could have implemented it the same way, only it would take significantly longer to process it due to cache misses and page faults.

5)RESOURCES

MapReduce: Simpliﬁed Data Processing on Large Clusters - JeffreyDean and Sanjay Ghemawat - Google, Inc.

MapReduce - Steve Krenzel - http://stevekrenzel.com/finding-friends-with-mapreduce

Evaluating MapReduce for Multicore and Multiprocessor Systems - Colby Ranger, Ramanan Raghuraman, Arun Penmetsa, Gary Bradski, Christos Kozyrakis; Computer Systems Laboratory - Stanford University

Phoenix Project - https://github.com/kozyraki/phoenix

