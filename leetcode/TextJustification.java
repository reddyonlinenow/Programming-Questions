package leetcode;

/*
 * Given a sequence of words, and a limit on the number of characters that can be put 
 * in one line (line width). Put line breaks in the given sequence such that the 
 * lines are printed neatly
 * 
 * Solution:
 * Badness - We define badness as square of empty spaces in every line. So 2 empty space
 * on one line gets penalized as 4 (2^2) while 1 each empty space on 2 lines gets
 * penalized as 2(1 + 1). So we prefer 1 empty space on different lines over 2 empty space on
 * one line.
 * 
 * For every range i,j(words from i to j) find the cost of putting them on one line. If words 
 * from i to j cannot fit in one line cost will be infinite. Cost is calculated as square of
 * empty space left in line after fitting words from i to j.
 * 
 * Then apply this formula to get places where words need to be going on new line.
 * minCost[i] = minCost[j] + cost[i][j-1]
 * Above formula will try every value of j from i to len and see which one gives minimum 
 * cost to split words from i to len.
 * 
 * Space complexity is O(n^2)
 * Time complexity is O(n^2)
 * 
 */
public class TextJustification {
public String justify(String words[], int width) {
        
        int cost[][] = new int[words.length][words.length];
        
        // next 2 for loops are used to calculate cost of putting words from
        // i to j in one line. If words don't fit in one line then we put
        // Integer.MAX_VALUE there.
        for(int i=0 ; i < words.length; i++){
            cost[i][i] = width - words[i].length();	// number of spaces left after entering first word
            for(int j=i+1; j < words.length; j++)
            	// spaces left after entering 
            	// current word = spaces left 
            	// after previous word - length of current word
            	// - 1 [for the space between words]
                cost[i][j] = cost[i][j-1] - words[j].length() - 1; 
            
        }
        
        for(int i=0; i < words.length; i++) 
            for(int j=i; j < words.length; j++)
            	cost[i][j] = (cost[i][j] < 0) ? Integer.MAX_VALUE : (int)Math.pow(cost[i][j], 2);
        
        // minCost from i to len is found by trying
        // j between i to len and checking which
        // one has min value
        int minCost[] = new int[words.length];
        int result[] = new int[words.length];
        for(int i = words.length-1; i >= 0 ; i--){
            minCost[i] = cost[i][words.length-1];
            result[i] = words.length;
            for(int j=words.length-1; j > i; j--){
                if(cost[i][j-1] == Integer.MAX_VALUE)
                    continue;
                
                if(minCost[i] > minCost[j] + cost[i][j-1]) {
                    minCost[i] = minCost[j] + cost[i][j-1];
                    result[i] = j;
                }
            }
        }
        int i = 0;
        int j;
        
        System.out.println("Minimum cost is " + minCost[0]);
        System.out.println("\n");
        
        // finally put all words with new line added in 
        // string buffer and print it.
        StringBuilder builder = new StringBuilder();
        do {
            j = result[i];
            for(int k=i; k < j; k++)
                builder.append(words[k] + " ");
            
            builder.append("\n");
            i = j;
        } while(j < words.length);
        
        return builder.toString();
    }
    
    public static void main(String args[]){
    	String st = "Debosmit Ray likes to code in his free time";
        TextJustification awl = new TextJustification();
        System.out.println(awl.justify(st.split(" "), 12));
    }
}
