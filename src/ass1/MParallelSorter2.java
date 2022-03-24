package ass1;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/** Benefits of this algorithm compared with others:
 *  List in notes from lab reading about the copious benefits of completablefutures vs futures
 *  also marcos explanation from the lcecture slides
 */

/** What I learned from implementing this algorithm
 *
 */

public class MParallelSorter2 implements Sorter {

  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
    int size = list.size();
    if(size <= 1){ return list; }
    if(list.size() <= 20) { return new MSequentialSorter().sort(list); }
    int mid = size/2;

    CompletableFuture<List<T>> leftSide = CompletableFuture.supplyAsync(() -> sort(list.subList(0, mid)));
    CompletableFuture<List<T>> rightSide = CompletableFuture.supplyAsync(() -> sort(list.subList(mid, size)));
    return (leftSide.thenCombine(rightSide, MSequentialSorter::merge)).join();
  }

}