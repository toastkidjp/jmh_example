package jp.toastkid.name.reactor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import jp.toastkid.name.NameInformation;
import reactor.core.publisher.Flux;

/**
 * Name loader.
 * @author Toast kid
 *
 */
public class NameLoader implements Callable<Collection<NameInformation>>{

    /** ObjecctReader's holder. */
    private static final ThreadLocal<ObjectReader> READER_HOLDER
        = ThreadLocal.withInitial(() -> new ObjectMapper().readerFor(NameInformation.class));

    /** names. */
    private final Collection<NameInformation> names;

    /** name's nationalities. */
    private final Collection<String> nationalities;

    /** /path/to/file. */
    private final String targetPath;

    /**
     * init with file path.
     * @param filePath path
     */
    public NameLoader(final String filePath) {
        this.targetPath = filePath;
        nationalities = Sets.mutable.empty();
        names         = Lists.mutable.empty();
    }

    @Override
    public Collection<NameInformation> call() {
        try (final BufferedReader fileReader = Files.newBufferedReader(Paths.get(targetPath))) {
            final Flux<NameInformation> cache
                = Flux.<NameInformation>create(emitter ->
                    fileReader.lines().onClose(emitter::complete).forEach(str -> {
                    try {
                        emitter.next(READER_HOLDER.get()
                                .readValue(str.getBytes(StandardCharsets.UTF_8.name())));
                    } catch (final Exception e) {
                        emitter.fail(e);
                    }
                })).cache();
            cache.subscribe(names::add);
            cache.subscribe(info -> nationalities.add(info.getNationality()));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    /**
     * return nationalities list.
     * @return nationalities list.
     */
    public Collection<String> getNationalities() {
        return nationalities;
    }

}