package jp.toastkid.name;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 * Fake main method.
 * @author Toast kid
 *
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class LoaderBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void reactor() {
        final jp.toastkid.name.reactor.App m = new jp.toastkid.name.reactor.App();
        System.out.print("rec.1 names: " + m.firstNames.size());
        System.out.print("rec.2 names: " + m.familyNames.size());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void rxjava() {
        final jp.toastkid.name.rxjava.App m = new jp.toastkid.name.rxjava.App();
        System.out.print("rxj.1 names: " + m.firstNames.size());
        System.out.print("rxj.2 names: " + m.familyNames.size());
    }

}
