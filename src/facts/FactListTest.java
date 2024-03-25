package facts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import facts.FactList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class FactListTest {
    private FactList factList;

    @Before
    public void setUp() {
        factList = new FactList();
        // Populate the factList with some test data
        Fact fact1 = new Fact();
        fact1.setAuthor("Author1");
        fact1.setType("Type1");
        fact1.setText("Text1");

        Fact fact2 = new Fact();
        fact2.setAuthor("Author2");
        fact2.setType("Type2");
        fact2.setText("Text2");

        Fact fact3 = new Fact();
        fact3.setAuthor("Author3");
        fact3.setType("Type3");
        fact3.setText("Text3");

        factList.set(fact1);
        factList.set(fact2);
        factList.set(fact3);
    }

    @Test
    public void testSearchByAuthor() {
        FactList searchResult = factList.search("Author1", FactSearchMode.AUTHOR_VAL);
        assertEquals(1, searchResult.getSize());
        assertEquals("Author1", searchResult.get(0).getAuthor());
    }

    @Test
    public void testSearchByText() {
        FactList searchResult = factList.search("Text2", FactSearchMode.TEXT_VAL);
        assertEquals(1, searchResult.getSize());
        assertEquals("Text2", searchResult.get(0).getText());
    }

    @Test
    public void testSearchByType() {
        FactList searchResult = factList.search("Type3", FactSearchMode.TYPE_VAL);
        assertEquals(1, searchResult.getSize());
        assertEquals("Type3", searchResult.get(0).getType());
    }

    @Test
    public void testSearchByAll() {
        FactList searchResult = factList.search("Text", FactSearchMode.ALL_VAL);
        assertEquals(3, searchResult.getSize());
    }

    @Test
    public void testSearchNonExisting() {
        FactList searchResult = factList.search("NonExisting", FactSearchMode.AUTHOR_VAL);
        assertEquals(0, searchResult.getSize());
    }

    @After
    public void tearDown() {
        // Clean up after tests if needed
        factList = null;
    }
}
