import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class AutocompleteTest {
//instances that we are supposed to use for the testcases in problem 2.
	Term[] instances =
			new Term[] {new Term("ape", 6), 
			new Term("app", 4), 
			new Term("ban", 2),
			new Term("bat", 3),
			new Term("bee", 5),
			new Term("car", 7),
			new Term("cat", 1)};
	String[] names= {"ape", "app", "ban", "bat", "bee", "car", "cat"};
	double[] weights = {6, 4, 2, 3, 5, 7, 1};

   public Autocomplete.Autocompletor getInstance(String[] names, double[] weights){
		return new Autocomplete.TrieAutocomplete(names, weights);
	}

	public Autocomplete.Autocompletor getInstance(){
		return getInstance(names, weights);
	}
   
	public class Autoincompletor extends Autocomplete.TrieAutocomplete{

		public Autoincompletor(String[] instances, double[] weights) {
			super(instances, weights);
		}

		@Override
		public Iterable<String> topMatches(String prefix, int k){
			return new LinkedList<String>();
		}

	}

	public ArrayList<ArrayList<Term>> allPermutations(ArrayList<Term> tempArray){
		if (tempArray.size() == 1){
			ArrayList<ArrayList<Term>> output = new
					ArrayList<ArrayList<Term>>();
			output.add(tempArray);
			return output;
		}
		ArrayList<ArrayList<Term>> output = 
				new ArrayList<ArrayList<Term>>();
		for(int i = 0; i < tempArray.size(); i++){
			ArrayList<Term> tempArraycopy = new ArrayList<Term>(tempArray);
			tempArraycopy.remove(i);
			ArrayList<ArrayList<Term>> subArray = allPermutations(tempArraycopy);
			for(ArrayList<Term> element: subArray)
				element.add(tempArray.get(i));
			output.addAll(subArray);
		}
		return output;
	}
	

	
	private String[] ArrayIterator(Iterable<String> it) {
		ArrayList<String> list = new ArrayList<String>();
		for (String element: it)
			list.add(element);
		return list.toArray(new String[0]);
	}

	//Test case for the TopMatches method
   @Test()
	public void testTopKMatches() {
		Autocomplete.Autocompletor test = getInstance();
		String[] queries = {"", "", "", "", "a", "ap", "b", "ba", "d"};
		int[] intArray = {8, 1, 2, 3, 1, 1, 2, 2, 100};
		String[][] results = {
				{"car", "ape", "bee", "app", "bat", "ban", "cat"},
				{"car"}, 
				{"car", "ape"}, 
				{"car", "ape", "bee"}, 
				{"ape"}, 
				{"ape"},
				{"bee", "bat"},
				{"bat", "ban"},
				{}
		};
		for(int i = 0; i < queries.length; i=i+2){
			String temp = queries[i];
			String[] X = ArrayIterator(test.topMatches(temp, intArray[i]));
			String[] Y = results[i];
			assertArrayEquals(Y, X);
		}
	}
   
   //Test case for the TopMatch() method
	@Test()
	public void testTopMatch() {
		Autocomplete.Autocompletor test = getInstance();
		String[] queries = {"", "a", "ap", "b", "ba", "c", "ca", "cat", "d", " "};
		String[] results = {"car", "ape", "ape", "bee", "bat", "car", "car", "cat", "", ""};
		for(int i = 0; i < queries.length; i=i+2){
			String temp = queries[i];
			String X = test.topMatch(temp);
			String Y = results[i];
			assertEquals(Y, X);
		}
	}

}