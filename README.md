# Apriori-Principle-in-Hadoop
Apriori algorithm finds all itemsets with supports larger than or equal to minimum support.  

Apriori algorithm makes multiple passes over the transaction data. 

In the first pass, the support of individual items is counted and frequent items are determined (based on minimum support). 

In each subsequent pass, a seed set of itemsets found to be frequent in the previous pass is used for generating new potentially frequent itemsets, called candidate itemsets.  

Then their actual support is counted while making a pass over the transaction data. 

At the end of the pass, those that satisfy the minimum support constraint are collected, that is, frequent itemsets are determined, and they become the seed for the next pass. 

This process is repeated until no new frequent itemsets are found. 
