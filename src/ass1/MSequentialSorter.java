package ass1;

import java.util.ArrayList;
import java.util.List;

/** Benefits of sequential sorting compared to other (parallelised) sorters:
 *  While adding paralellism to a problem often significantly increases the performance of the algorithm, this is not always true.
 *  In the case that the dataset is very small, or the case that the computer that the code is operating on lacks the appropriate number of calls, sequentially implementing merge sort can be more efficient.
 *  I have seen this in the implementation of my code on my laptop (4 cores). I was (at first) confused as to why my sequential code was consistently faster than the parallel on my laptop.
 *  However, after running the code on a lab computer I have seen the increased performance that paralellised code offers (given the right number of cores).
 *  This is because there is a fixed start-up cost associated with establishing threads, and if there are not enough cores to make this start-up cost worth it (or the dataset is too small to justify it), the resulting performance will actually be worse than that of the sequential solution.
 *  Sequential sorting (in this case) also has the added benefit of being easier to implement and de-bug.
 */

/** What I learned in implementing this sort:
 * The main thing that I learned in this implementation was the importance of first totally optimizing code sequentially, before moving on to parallelising the code.
 * I was struggling to understand why my parallel implementations of the code were so much slower than my sequential solution, until I understood that I was unnecessarily complicating the sequential solution with far too many computationally complex/expensive operations.
 * These operations were mostly many unnecessary list copys, and once I optimized the sequential code my parallel solutions followed in the increased performance.
 *
 */
public class MSequentialSorter implements Sorter {

  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
    int size = list.size();
    if(size <= 1){ return list; }
    int mid = size/2;
    List<T> firstHalf = sort(list.subList(0, mid)); //recursively sort the first half list of list
    List<T> secondHalf = sort(list.subList(mid, size));
    return merge(firstHalf, secondHalf);
  }

  public static <T extends Comparable<? super T>> List<T> merge(List<T> firstHalf, List<T> secondHalf){
    List<T> done = new ArrayList<>();
    int currIndexFirst = 0;
    int currIndexSecond = 0;

    //while each index is still within the bounds of each half list
    while(currIndexFirst < firstHalf.size() && currIndexSecond < secondHalf.size()){
      if(firstHalf.get(currIndexFirst).compareTo(secondHalf.get(currIndexSecond)) < 0){ //means element from first half is less than second, add to list
        done.add(firstHalf.get(currIndexFirst));
        currIndexFirst ++;
      }else{ //else 2nd half result was smaller, add to list first instead (as we are sorting small to high)
        done.add(secondHalf.get(currIndexSecond));
        currIndexSecond ++;
      }
    }

    //At this point one of the lists has exhausted its elements, so we iterate through the remaining half list until it too has exhausted
    while((currIndexFirst < firstHalf.size()) || (currIndexSecond < secondHalf.size())){
      if(currIndexFirst < currIndexSecond) {
        done.add(firstHalf.get(currIndexFirst));
        currIndexFirst++;
      }else{
        done.add(secondHalf.get(currIndexSecond));
        currIndexSecond++;
      }
    }
    return done;
  }
}