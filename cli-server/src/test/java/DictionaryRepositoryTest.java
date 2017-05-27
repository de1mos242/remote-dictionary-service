import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryRepositoryTest {

    private DictionaryRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new DictionaryRepository();
    }

    @Test
    public void testAddTranslation() {
        repository.addTranslation("hello", "здравствуйте");
        assertEquals(1, repository.getTranslations("hello").size());
        assertEquals(true, repository.getTranslations("hello").contains("здравствуйте"));
    }

    @Test
    public void testAddTwoTranslationsToOneWord() {
        repository.addTranslation("hello", "здравствуйте");
        repository.addTranslation("hello", "привет");
        assertEquals(2, repository.getTranslations("hello").size());
        assertEquals(true, repository.getTranslations("hello").contains("здравствуйте"));
        assertEquals(true, repository.getTranslations("hello").contains("привет"));
    }

    @Test
    public void testAddTwoTranslationPairs() {
        repository.addTranslation("hello", "здравствуйте");
        repository.addTranslation("bye", "пока");
        assertEquals(1, repository.getTranslations("hello").size());
        assertEquals(true, repository.getTranslations("hello").contains("здравствуйте"));
        assertEquals(1, repository.getTranslations("bye").size());
        assertEquals(true, repository.getTranslations("bye").contains("пока"));
    }

    @Test
    public void testRemoveOneOfTranslations() {
        repository.addTranslation("hello", "здравствуйте");
        repository.addTranslation("hello", "привет");
        repository.removeTranslation("hello", "здравствуйте");
        assertEquals(1, repository.getTranslations("hello").size());
        assertEquals(true, repository.getTranslations("hello").contains("привет"));
    }

    @Test
    public void testRemoveLastTranslation() {
        repository.addTranslation("hello", "здравствуйте");
        repository.removeTranslation("hello", "здравствуйте");
        assertEquals(0, repository.getTranslations("hello").size());
    }

    @Test
    public void testDictionaryShouldBeCaseInsensitive() {
        repository.addTranslation("hEllo", "ЗДРавСтвуйте");
        assertEquals(1, repository.getTranslations("HellO").size());
        assertEquals(true, repository.getTranslations("HellO").contains("здравствуйте"));
        repository.removeTranslation("heLLO", "здравСтвУйте");
        assertEquals(0, repository.getTranslations("hEllo").size());
    }
}