package jp.toastkid.name.rxjava;

import static org.junit.Assert.assertEquals;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.junit.Test;

import jp.toastkid.name.NameInformation;

/**
 * check {@link App}'s behavior.
 * @author Toast kid
 *
 */
public class MainTest {

    @Test
    public void test() {
        final App m = new App();
        checkFirstNames(m.firstNames);
        checkFamilyNames(m.familyNames);
    }

    /**
     * check first names.
     * @param firstNames
     */
    private void checkFirstNames(final MutableList<NameInformation> firstNames) {
        assertEquals(2381, firstNames.size());
        final MutableListMultimap<String,NameInformation> groupBy
            = firstNames.groupBy(name -> name.getNationality());
        assertEquals(14,   groupBy.keysView().size());
        assertEquals(223,  groupBy.get("スペイン語圏").size());
        assertEquals(13,   groupBy.get("ロシア").size());
        assertEquals(99,  groupBy.get("エストニア").size());
        assertEquals(69,   groupBy.get("リトアニア").size());
        assertEquals(2,    groupBy.get("インド").size());
        assertEquals(103,  groupBy.get("フィンランド").size());
        assertEquals(75,  groupBy.get("ラトビア").size());
        assertEquals(596, groupBy.get("英語圏").size());
        assertEquals(18,   groupBy.get("グルジア").size());
        assertEquals(773,  groupBy.get("ドイツ語圏").size());
        assertEquals(9,    groupBy.get("ルーマニア").size());
        assertEquals(137,  groupBy.get("フランス語圏").size());
        assertEquals(65,   groupBy.get("ポルトガル語圏").size());
        assertEquals(199,  groupBy.get("イタリア").size());
    }

    /**
     * check family names.
     * @param familyNames
     */
    private void checkFamilyNames(final MutableList<NameInformation> familyNames) {
        assertEquals(2691, familyNames.size());
        final MutableListMultimap<String,NameInformation> groupBy
            = familyNames.groupBy(name -> name.getNationality());
        assertEquals(14,   groupBy.keysView().size());
        assertEquals(242,  groupBy.get("スペイン語圏").size());
        assertEquals(12,   groupBy.get("ロシア").size());
        assertEquals(100,  groupBy.get("エストニア").size());
        assertEquals(98,   groupBy.get("リトアニア").size());
        assertEquals(1,    groupBy.get("インド").size());
        assertEquals(108,  groupBy.get("フィンランド").size());
        assertEquals(104,  groupBy.get("ラトビア").size());
        assertEquals(1198, groupBy.get("英語圏").size());
        assertEquals(22,   groupBy.get("グルジア").size());
        assertEquals(367,  groupBy.get("ドイツ語圏").size());
        assertEquals(8,    groupBy.get("ルーマニア").size());
        assertEquals(121,  groupBy.get("フランス語圏").size());
        assertEquals(52,   groupBy.get("ポルトガル語圏").size());
        assertEquals(258,  groupBy.get("イタリア").size());
    }

}
