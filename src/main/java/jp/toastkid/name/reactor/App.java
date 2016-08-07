package jp.toastkid.name.reactor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import jp.toastkid.name.NameInformation;

/**
 * Fake main method.
 * @author Toast kid
 *
 */
public class App {

    /** /path/to/firstName. */
    private static final String FIRST_NAME_PATH  = "src/main/resources/first.txt";

    /** /path/to/familyName. */
    private static final String FAMILY_NAME_PATH = "src/main/resources/family.txt";

    /** contains nationalities. */
    public MutableList<String> nationalities;

    /** contains first names. */
    public MutableList<NameInformation> firstNames;

    /** contains family names. */
    public MutableList<NameInformation> familyNames;

    public App() {
        nationalities = Lists.mutable.empty();
        final int numOfTasks = 2;
        final CountDownLatch latch = new CountDownLatch(numOfTasks);
        final ExecutorService es = Executors.newFixedThreadPool(numOfTasks);
        es.execute(() -> {
            final NameLoader loader = new NameLoader(FIRST_NAME_PATH);
            firstNames = Lists.mutable.ofAll(loader.call())
                    .sortThis().distinct();
            synchronized (this) {
                nationalities.addAll(loader.getNationalities());
            }
            latch.countDown();
        });
        es.execute(() -> {
            final NameLoader loader = new NameLoader(FAMILY_NAME_PATH);
            familyNames = Lists.mutable.ofAll(loader.call())
                    .sortThis().distinct();
            synchronized (this) {
                nationalities.addAll(loader.getNationalities());
            }
            latch.countDown();
        });

        nationalities.distinct().sortThis();
        try {
            latch.await();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        es.shutdown();
    }

}
