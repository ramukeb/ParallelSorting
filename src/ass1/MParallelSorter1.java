package ass1;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** Benefits of this implementation of parallelism compared to the others:
 * talk about the benefits of futures vs comparableFutures - https://blog.knoldus.com/future-vs-completablefuture-1/
 * Can also look at lecure slide where marco talks about the benefits of each of these
 */

/** Things I learnt when implementing this algorithm:
 * Main thing was the importance of a workstealingPool vs a fixed/cached threadpool. Need to sure up this difference by talking to marco
 */

public class MParallelSorter1 implements Sorter {
//  private static final ExecutorService pool = Executors.newCachedThreadPool();
  ExecutorService pool = Executors.newWorkStealingPool();

  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
    int size = list.size();
    if(size <= 1){ return list; }
    if(list.size() <= 20) { return new MSequentialSorter().sort(list); }
    int mid = size/2;

    Future<List<T>> leftSide = pool.submit(() -> sort(list.subList(0, mid)));
    List<T> rightSide = sort(list.subList(mid, size));
    return MSequentialSorter.merge(get(leftSide), rightSide);
  }

  public static <T> T get(Future<T> future){
    try{ return future.get(); }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new Error(e);
    }
    catch (ExecutionException e) {
      Throwable t = e.getCause();
      if(t instanceof RuntimeException rt) { throw rt; }
      if(t instanceof Error et) { throw et; }
      throw new Error("Unexpected Checked Exception", t);
    }
  }
}