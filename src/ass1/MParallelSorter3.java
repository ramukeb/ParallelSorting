package ass1;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/** Benefits of this algorithm vs the others:
 *
 */

/** What I learned from implementing this algorithm:
 *
 *
 */

public class MParallelSorter3 implements Sorter {
  static final ForkJoinPool mainPool = new ForkJoinPool();

  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list) { return mainPool.invoke(new Sorter<>(list)); }

  private static class Sorter<T extends Comparable<? super T>> extends RecursiveTask<List<T>> {
    private final List<T> list;

    public Sorter(List<T> list){ this.list = list;}

    @Override
    protected List<T> compute() {
      int size = list.size();
      if(size <= 1){ return list; }
      if(size <= 20) { return new MSequentialSorter().sort(list); }
      int mid = size / 2;

      Sorter<T> sorterA = (new Sorter<>(list.subList(0, mid)));
      Sorter<T> sorterB = new Sorter<>(list.subList(mid, size));
      invokeAll(sorterA, sorterB);
      return MSequentialSorter.merge(sorterA.join(), sorterB.join());
    }
  }
}
