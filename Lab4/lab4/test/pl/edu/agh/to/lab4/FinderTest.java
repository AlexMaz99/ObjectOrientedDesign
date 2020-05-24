package pl.edu.agh.to.lab4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.to.lab4.searching.NameSearchStrategy;
import pl.edu.agh.to.lab4.suspects.CracowCitizen;
import pl.edu.agh.to.lab4.suspects.Prisoner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FinderTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private PrintStream originalOut;

    private Collection<CracowCitizen> allCracowCitizens = new ArrayList<CracowCitizen>();

    private Map<String, Collection<Prisoner>> allPrisoners = new HashMap<String, Collection<Prisoner>>();

    private Finder suspectFinder = new Finder(allCracowCitizens, allPrisoners);

    @Test
    public void testDisplayingNotJailedPrisoner() {
        addPrisoner("Wiezeienie stanowe", new Prisoner("Jan", "Kowalski", "802104543357", 2000, 1, 30));
        suspectFinder.display(new NameSearchStrategy("Jan"));
        assertContentIsDisplayed("Jan Kowalski");
    }

    @Test
    public void testDisplayingSuspectedPerson() {
        allCracowCitizens.add(new CracowCitizen("Jan", "Kowalski", 20));
        suspectFinder.displayAllSuspectsWithName("Jan");
        assertContentIsDisplayed("Jan Kowalski");
    }

    @Test
    public void testNotDisplayingTooYoungPerson() {
        allCracowCitizens.add(new CracowCitizen("Jan", "Kowalski", 15));
        suspectFinder.displayAllSuspectsWithName("Jan");
        assertContentIsNotDisplayed("Jan Kowalski");
    }

    @Test
    public void testNotDisplayingJailedPrisoner() {
        allCracowCitizens.add(new CracowCitizen("Jan", "Kowalski", 20));
        addPrisoner("Wiezeienie stanowe", new Prisoner("Jan", "Kowalski2", "802104543357", 2000, 20, 30));
        suspectFinder.displayAllSuspectsWithName("Jan");
        assertContentIsNotDisplayed("Jan Kowalski2");
    }

    private void assertContentIsDisplayed(String expectedContent) {
        assertTrue("Application did not contain expected content: " + outContent.toString(), outContent.toString()
                .contains(expectedContent));
    }

    private void assertContentIsNotDisplayed(String expectedContent) {
        assertFalse("Application did contain expected content although it should not: " + outContent.toString(), outContent.toString()
                .contains(expectedContent));
    }

    @Before
    public void redirectSystemOut() {
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void resetSystemOut() {
        System.setOut(originalOut);
    }

    private void addPrisoner(String category, Prisoner news) {
        if (!allPrisoners.containsKey(category))
            allPrisoners.put(category, new ArrayList<Prisoner>());
        allPrisoners.get(category).add(news);
    }
}
