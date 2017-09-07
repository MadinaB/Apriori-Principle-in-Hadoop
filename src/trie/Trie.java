package trie;

import java.util.ArrayList;
import java.util.Arrays;

import list.ItemSet;
import list.Transaction;

/**
 * Trie are used for efficiently searching for a pattern of items in a transaction in frequent
 * itemset mining algorithms. This represents the structure of a Trie.
 */

public class Trie
{
    TrieNode rootNode;
    final int height;

    public Trie(int height) {
        rootNode = new TrieNode();
        this.height = height;
    }

    public boolean add(ItemSet itemSet) {
      
    	TrieNode root=this.rootNode;
        for(int i=0;i<height;i++){
            int key=itemSet.get(i);
            if(!root.containsKey(key)){
                TrieNode newNode=new TrieNode();
                root.put(key,newNode);
            }
            root=root.get(key);
        }
        if(root.isLeafNode()){return false;}
        root.setLeafNode(true);
        root.add(itemSet);
        return true;
    	 
    }

    public boolean contains(ItemSet itemSet) {
      
    	 boolean contain=false;
         TrieNode root=this.rootNode;
         for(int i=0;i<itemSet.size();i++){
             int key=itemSet.get(i);
             if(!root.containsKey(key)){
                 contain=false;
                 return contain;
             }
             root=root.get(key);
         }

         if(root.isLeafNode()){
             contain=true;}
         return contain;
    	
       
    }

    public TrieNode getRootNode() {
        return rootNode;
    }

    public void findItemSetsInRecurse(ArrayList<ItemSet> matchedItemSet, TrieNode root, ArrayList<Integer> transaction,int height, int curHeight,ItemSet itemSet ) {
        if(root.isLeafNode()){
                matchedItemSet.add(root.getItemSet());
        }
        else {

            for(int i=curHeight;i<transaction.size();i++){
                int key=transaction.get(i);
                if(root.containsKey(key)) {
                	findItemSetsInRecurse(matchedItemSet, root.get(key), transaction, height, curHeight + 1, itemSet );
                }
            }

        }

    }
    public void findItemSets(ArrayList<ItemSet> matchedItemSet, Transaction transaction) {
       
    	int curHeight=0;
        ItemSet itemSet=new ItemSet();
        findItemSetsInRecurse(matchedItemSet, rootNode, transaction,height,curHeight,itemSet);
    	
    }
}

