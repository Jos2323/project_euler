//Euler Problem 54 (http://projecteuler.net/problem=54	)

/* Problem:	
	Poker hands

In the card game poker, a hand consists of five cards and are ranked, from lowest to highest, in the following way:

High Card: Highest value card.
One Pair: Two cards of the same value.
Two Pairs: Two different pairs.
Three of a Kind: Three cards of the same value.
Straight: All cards are consecutive values.
Flush: All cards of the same suit.
Full House: Three of a kind and a pair.
Four of a Kind: Four cards of the same value.
Straight Flush: All cards are consecutive values of same suit.
Royal Flush: Ten, Jack, Queen, King, Ace, in same suit.
The cards are valued in the order:
2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace.

If two players have the same ranked hands then the rank made up of the highest value wins; 
for example, a pair of eights beats a pair of fives (see example 1 below). 
But if two ranks tie, for example, both players have a pair of queens, 
then highest cards in each hand are compared (see example 4 below); 
if the highest cards tie then the next highest cards are compared, and so on.

Consider the following five hands dealt to two players:

Hand	 	Player 1	 	Player 2	 	Winner
1	 	5H 5C 6S 7S KD
Pair of Fives
 	2C 3S 8S 8D TD
Pair of Eights
 	Player 2
2	 	5D 8C 9S JS AC
Highest card Ace
 	2C 5C 7D 8S QH
Highest card Queen
 	Player 1
3	 	2D 9C AS AH AC
Three Aces
 	3D 6D 7D TD QD
Flush with Diamonds
 	Player 2
4	 	4D 6S 9H QH QC
Pair of Queens
Highest card Nine
 	3D 6D 7H QD QS
Pair of Queens
Highest card Seven
 	Player 1
5	 	2H 2D 4C 4D 4S
Full House
With Three Fours
 	3C 3D 3S 9S 9D
Full House
with Three Threes
 	Player 1
The file, poker.txt, contains one-thousand random hands dealt to two players. 
Each line of the file contains ten cards (separated by a single space): 
the first five are Player 1's cards and the last five are Player 2's cards. 
You can assume that all hands are valid (no invalid characters or repeated cards), 
each player's hand is in no specific order, and in each hand there is a clear winner.

How many hands does Player 1 win?



*/

/* Lösung (Nr. 23882, 2016/07/24):

	376

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Euler54 {
	
	// card values
	static final char[] cards = new char[]{'0','0','2','3','4','5','6','7','8','9','T','J','Q','K','A'};
	
	public static void main(String[] args) throws IOException {

		long timer = System.currentTimeMillis();
		
		// count the number of times, a player wins. [0] : Player1, [1] : Player2
		int[] solution = new int[2];
		
		// read from input file and write to output file
		Scanner scanner = new Scanner(new File("res/p054_poker.txt"));
		PrintWriter writer = new PrintWriter(new File("res/p054_output.txt"), "UTF-8");
		
		// rank of a hand (for both players)
		int[] rank = new int[2];

		// true, if flush (for both players)
		boolean[] flush = new boolean[2];
		// [0] true, if straight, [1] true, if straight is from A-5 (for both players)
		boolean[][] straight = new boolean[2][2];
		// [0] : number of equal cards (-> 2=pair, etc.), [1] value of equal cards, [2]~ : positions of equal cards (for both players) 
		int[][] equals = new int[2][6];
		// used for a second set of equal cards, if one equal set has already been found (for both players) 
		int[][] equals2 = new int[2][6];

		// loop over input file
		while (scanner.hasNextLine()) {
			
			String winner = "";
		
			String line = scanner.nextLine();
			
			// read line and split into two arrays
			String[] hands_line = line.split("\\s",10);
			String[][] hands = new String[2][];
			hands[0] = Arrays.copyOfRange(hands_line, 0, 5);
			hands[1] = Arrays.copyOfRange(hands_line, 5, 10);
			
			// get rank of hands for both players
			for (int i = 0; i <= 1; i++)
			{
				flush[i] = testForFlush(hands[i]);
				straight[i] = testForStraight(hands[i]);
				if (flush[i])
				{
					if (straight[i][0])
						// hand is straight flush
						rank[i] = 9;
					else
						// hand is flush
						rank[i] = 6;
					continue;
				}
				if (straight[i][0])
				{
					// hand is straight
					rank[i] = 5;
					continue;
				}
				
				// get equals (pair, trips, quads) 
				equals[i] = testForEquals(hands[i],14);
				
				// reset equals2
				equals2[i][0] = equals2[i][1] = 0;
				if (equals[i][0] > 0)
				{
					// if equals exists, get second set of equals (for two pair or full house)
					equals2[i] = testForEquals(hands[i],equals[i][1]-1);
				}
				
				if (equals2[i][0] > 0)
				{
					if (equals[i][0] == 3 || equals2[i][0] == 3)
						// full house
						rank[i] = 7;
					else
						// two pair
						rank[i] = 3;
					continue;
				}
							
				if (equals[i][0] == 4)
					// quads
					rank[i] = 8;
				
				if (equals[i][0] == 3)
					// trips
					rank[i] = 4;
				
				if (equals[i][0] == 2)
					// one pair
					rank[i] = 2;
				
				if (equals[i][0] == 0)
					// high card
					rank[i] = 1;
				
			}
			
			// if both hands have the same rank, compare card values
			if (rank[0] == rank[1])
			{
				switch(rank[0]) {
				case 1 :
				case 6 :
					// if high card or flush, compare high card values normally
				{
					int comp_res = compareForHighCard(hands[0],hands[1]);
					solution[comp_res]++;
					if (comp_res >= 0)
						winner = (comp_res == 0) ? "Player1" : "Player2"; 
				}
				break;
				
				case 5 :
				case 9 :
					// if straight or straight flush, compare high card values normally, but rank A-5 lowest
				{
					// if only one of the hands is A-5, than the other hand is higher
//					if (straight[0][1])
					if (straight[0][1] && !straight[1][1])
					{
						solution[1]++;
						winner = "Player2";
					}
//					else if (straight[1][1])
					else if (straight[1][1] && !straight[0][1])
					{
						solution[0]++;
						winner = "Player1";
					}
					else
					{
						// compare high card values normally
						int comp_res = compareForHighCard(hands[0],hands[1]);
						solution[comp_res]++;
						if (comp_res >= 0)
							winner = (comp_res == 0) ? "Player1" : "Player2"; 
					}
				}
				break;
				
				case 3 :
					// if two pair, compare the two pair values normally, then the remaining high card value
				{
					int twopair1_max = Math.max(equals[0][1], equals2[0][1]);
					int twopair2_max = Math.max(equals[1][1], equals2[1][1]);
					int twopair1_min = Math.min(equals[0][1], equals2[0][1]);
					int twopair2_min = Math.min(equals[1][1], equals2[1][1]);
					// compare the value of the higher pair. if differ, the higher value wins
					if (twopair1_max > twopair2_max)
					{
						solution[0]++;
						winner = "Player1";
					}
					else if (twopair1_max < twopair2_max)
					{
						solution[1]++;
						winner = "Player2";
					}
					else
					{
						// compare the value of the lower pair. if differ, the higher value wins
						if (twopair1_min > twopair2_min)
						{
							solution[0]++;
							winner = "Player1";
						}
						else if (twopair1_min < twopair2_min)
						{
							solution[1]++;
							winner = "Player2";
						}
						else
						{
							// if both pair values are the same, compare remaining high card values normally
							int comp_res = compareForHighCard(getRemainingCards(getRemainingCards( 
									hands[0], Arrays.copyOfRange(equals[0],2,4) ), Arrays.copyOfRange(equals2[0],2,4) ),
									getRemainingCards(getRemainingCards( 
											hands[1], Arrays.copyOfRange(equals[1],2,4) ), Arrays.copyOfRange(equals2[1],2,4) ));
							solution[comp_res]++;
							if (comp_res >= 0)
								winner = (comp_res == 0) ? "Player1" : "Player2"; 
						}
					}
				}
				break;
				
				case 7 :
				{
					// if full house, compare the trips values normally, then the pair value value
					int fullhouse1 = equals[0][0] == 3 ? equals[0][1] : equals2[0][1];
					int fullhouse2 = equals[1][0] == 3 ? equals[1][1] : equals2[1][1];
					// if the trips value differ, the higher value wins
					if (fullhouse1 > fullhouse2)
					{
						solution[0]++;
						winner = "Player1";
					}
					else if (fullhouse1 < fullhouse2)
					{
						solution[1]++;
						winner = "Player2";
					}
					else
					{
						// if the trips value are the same, the higher pair value wins
						fullhouse1 = equals[0][0] == 2 ? equals[0][1] : equals2[0][1];
						fullhouse2 = equals[1][0] == 2 ? equals[1][1] : equals2[1][1];
						if (fullhouse1 > fullhouse2)
						{
							solution[0]++;
							winner = "Player1";
						}
						else if (fullhouse1 < fullhouse2)
						{
							solution[1]++;
							winner = "Player2";
						}
					}
				}
				break;
	
				case 2 :
				case 4 :
				case 8 :
				{
					// if pair, trips or quads, the higher value of those set wins. if equal, compare the remaining high cards normally
					if (equals[0][1] > equals[1][1])
					{
						solution[0]++;
						winner = "Player1";
					}
					else if (equals[0][1] < equals[1][1])
					{
						solution[1]++;
						winner = "Player2";
					}
					else
					{						
						// if set value is the same, compare remaining high card values normally
						int comp_res = compareForHighCard(getRemainingCards(hands[0], Arrays.copyOfRange(equals[0],2,2+equals[0][0])),
								getRemainingCards(hands[1], Arrays.copyOfRange(equals[1],2,2+equals[1][0])));
						solution[comp_res]++;
						if (comp_res >= 0)
							winner = (comp_res == 0) ? "Player1" : "Player2"; 
					}
				}
				break;
				}
			}
			else
			{
				// if the ranks of the hands differ, the higher ranked hand wins
				if(rank[0] > rank[1])
				{
					winner = "Player1";
					solution[0]++;
				}
				else
				{
					winner = "Player2";
					solution[1]++;
				}
			}
			
			// write result to output file
			writer.println(Arrays.toString(hands_line) + " -> Winner: " + winner + " Ranks: " + rank[0] + " " + rank[1]);
		
		}
		
		// close reader and writer
		scanner.close();
		writer.close();
				
		System.out.println("Result: " + Arrays.toString(solution));	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	// look at a set of cards (same amount), which hand has the better high card (go down to the last, if needed)
	// 0 : hand1, 1 : hand2, -1 : equal, -2 : error
	public static int compareForHighCard(String[] hand1, String[] hand2) {
		if (hand1.length != hand2.length)
			return -2;
		
		boolean[] flag = new boolean[2];
		
		for (int i = 14; i > 1; i--)
		{
			flag[0] = flag[1] = false;
			for (int j = 0; j < hand1.length; j++)
			{
				if (hand1[j].charAt(0) == cards[i])
					flag[0] = true;
				if (hand2[j].charAt(0) == cards[i])
					flag[1] = true;
			}
			if (flag[0] != flag[1])
				return flag[0] ? 0 : 1;
		}
		
		return -1;
	}
	
	// true, if Flush
	public static boolean testForFlush(String[] hand) {
		for (int i = 1; i < hand.length; i++)
		{
			if (hand[i].charAt(1) != hand[i-1].charAt(1))
				return false;
		}
		
		return true;
	}
	
	// first return true, if Straight. second return true, if A-5 Straight
	public static boolean[] testForStraight(String[] hand) {
		
		boolean[] res = new boolean[2];
		
		boolean[] cardsCount = new boolean[15];
		for (int j = 0; j < hand.length; j++)
		{
			for (int i = 14; i > 1; i--)
			{
				if(hand[j].charAt(0) == cards[i])
				{	
					if (cardsCount[i])
						return res;
					else
					{
						cardsCount[i] = true;
						if (i == 14)
							cardsCount[1] = true;
						break;
					}
				}
			}	
		}
		for (int i = 1; i < 11; i++)
		{
			if (cardsCount[i])
			{
				int j = i;
				while (j < 15 && cardsCount[j])
				{
					j++;
				}
				if (j-i > 4)
				{
					res[0] = true;
					if (i == 1)
						res[1] = true;
					return res;
				}
			}
		}
		return res;
	}
	
	// test for equal cards, lower than the value of startCard
	// [0] : amount of equal cards (2 -> pair, 3 -> trips, 4 -> quads)
	// [1] : value of equal cards
	// [2]~ : positions of equal cards
	public static int[] testForEquals(String[] hand, int startCard) {
		int[] res = new int[6];
		int counter;
		int index;
		
		for (int i = startCard; i > 1; i--)
		{
			counter = 0;
			index = 2;
			for (int j = 0; j < hand.length; j++)
			{
				if (cards[i] == hand[j].charAt(0))
				{
					counter++;
					res[1] = i;
					res[index++] = j;
				}
			}
			if (counter > 1)
			{
				res[0] = counter;
				return res;
			}
		}
		return res;
	}
	
	// substract cards at positions of taken from hand (used to get remaining cards after 
	public static String[] getRemainingCards(String[] hand, int[] taken) {
		String[] res = new String[hand.length - taken.length];
		int index = 0;
		
		label:
		for (int i = 0; i < hand.length; i++)
		{
			for (int j = 0; j < taken.length; j++)
			{
				if (i == taken[j])
					continue label;
			}
			res[index] = hand[i];
			index++;
		}
		
		return res;
	}
	
}






/* Erklärung:
	1. Grundüberlegungen:
		- Teste erst auf Kategorien und dann innerhalb der Kategorien
	2. Brute Force Prinzip:
		a) 
	3. Optimierungen: 
		- 
	4. Problematik:
		- testet nicht auf Gleichheit von Haenden!
		

*/
