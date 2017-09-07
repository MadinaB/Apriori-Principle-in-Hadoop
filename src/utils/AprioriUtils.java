package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import list.ItemSet;
import list.Transaction;

/*
 * Contains utility methods for Apriori algorithm
 */
public class AprioriUtils
{

// Returns a transaction object for the input txn record.
    public static Transaction getTransaction(int id, String txnRecord) {
        String currLine = txnRecord.trim();
        String[] words = currLine.split(" ");
        Transaction transaction = new Transaction(id);

        for (int i = 0; i < words.length; i++) {
            transaction.add(Integer.parseInt(words[i].trim()));
        }

        return transaction;
    }


// Determines if an item with the specified frequency has minimum support or not.
    public static boolean hasMinSupport(double minSup, int numTxns, int itemCount) {
        
    	double currentSup=itemCount*1.0/numTxns;
    	if(Double.compare(currentSup,minSup)>=0){return true;}
    	return false;
    	
   }


//    L_k : frequent itemSets of length k.
//    C_k : candidate frequent itemSets of length k.

//  1) Self-join L_k to create itemSets of size k+1.
//  2) For each subset of the above itemSets, we check if it is in L_k; we do this check approximately by keeping hashcode of itemsets in L_k in a set and check against this set.
//     If all of the subsets are in L_k, then we add the itemSet to C_(k+1).   (pruning)
//  3) Add the itemSets in C_(k+1) into the Trie (implemented in the previous assignment).

/*
  In self-joining step, you can only generate the (k+1)-itemSet with k-itemSets having the same itemSet up to (k-1) items
  For example,
      Case 1
        Suppose we have 2 itemSet that have length 3 (list[0..2])
          [1, 2, 3], [1, 2, 4]

        In this case, you should check that the itemSets are the same from index 0 to index 1 and generate 4-newItemSet and pruning.

      Case 2
        Suppose we have 2 itemSet the have length 3 (list[0..2])
          [1, 2, 3], [2, 3, 4]

       In this case, it should only check index 0 and break.
       it doesn't need to generate the (k+1) itemSet

        consider generating (k+1)-newItemSet only for the same thing up to (k-1) consecutively.


*/

    public static List<ItemSet> getCandidateItemSets(List<ItemSet> prevPassItemSets, int itemSetSize) {
        List<ItemSet> candidateItemSets = new ArrayList<>();
        Map<Integer, ItemSet> itemSetMap = generateItemSetMap(prevPassItemSets);
        Collections.sort(prevPassItemSets);
        int prevPassItemSetsSize = prevPassItemSets.size();

       


        for(int index1 = 0; index1 < prevPassItemSetsSize; index1++){
        	for(int index2 = index1 + 1; index2 < prevPassItemSetsSize; index2++) {
        		boolean not_equal = false;
        		for(int c = 0; c < itemSetSize  - 1; c++) {
        			if(prevPassItemSets.get(index1).get(c) != prevPassItemSets.get(index2).get(c)) {
        				not_equal = true;
        				break;
        			}
        		}
        		if(not_equal == false) {
        			ItemSet newCreateItemSet = new ItemSet();
        			if(itemSetSize > 1){
        				for(int i1=0;i1<itemSetSize-1;i1++){
        					newCreateItemSet.add(prevPassItemSets.get(index1).get(i1));
        				}
        			}
        			newCreateItemSet.add(prevPassItemSets.get(index1).get(itemSetSize - 1));
        			newCreateItemSet.add(prevPassItemSets.get(index2).get(itemSetSize - 1));
        			if(prune(itemSetMap, newCreateItemSet) == false) continue;
        			candidateItemSets.add(newCreateItemSet);
        		}
        		else break;
        	}
        }


        return candidateItemSets;
    }


// Generates a map of hashcode and the corresponding ItemSet. Since multiple entries can
// have the same hashcode, there would be a list of ItemSets for any hashcode.
// It is used to verify that the subset of C_(K+1) belongs to L_K during the pruning process.

    public static Map<Integer, ItemSet> generateItemSetMap(List<ItemSet> itemSets) {
        Map<Integer, ItemSet> itemSetMap = new HashMap<>();

        for (ItemSet itemSet : itemSets) {
            int hashCode = itemSet.hashCode();
            if (!itemSetMap.containsKey(hashCode)) {
                itemSetMap.put(hashCode, itemSet);
            }
        }
        return itemSetMap;
    }

// This method checks that the subset of C_(K+1) belongs to L_K.
// If all of the subsets are in L_K, then it return true, otherwise false.
// 

    static boolean prune(Map<Integer, ItemSet> itemSetsMap, ItemSet newItemSet) {
        List<ItemSet> subsets = getSubSets(newItemSet);

        for (ItemSet subItemSet : subsets) {
            int hashCodeToSearch = subItemSet.hashCode();
            if (!itemSetsMap.containsKey(hashCodeToSearch)) {
                return false;
            }
        }
        return true;
    }

//  Generates all possible subsets with length k-1 for ItemSet (preserves order)
    static List<ItemSet> getSubSets(ItemSet itemSet) {
        List<ItemSet> subSets = new ArrayList<>();
      
        ItemSet newItemSet=new ItemSet(itemSet.size()-1);
        for(int item :itemSet){
        	newItemSet.clear();
        	for(int item2 :itemSet){
            	if(item!=item2){newItemSet.add(item2);}
            }
        	ItemSet newItemSet2 = new ItemSet();
        	newItemSet2.addAll(newItemSet);
        	subSets.add(newItemSet2);
        }
   


        return subSets;
    }
}
