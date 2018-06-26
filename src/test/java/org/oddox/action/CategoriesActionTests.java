package org.oddox.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.objects.Category;

/**
 * Unit tests for CategoriesAction
 */
@RunWith(JUnit4.class)
public class CategoriesActionTests {

    @InjectMocks
    private CategoriesAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void variables() {
        handle.setCategories(null);
        assertNull(handle.getCategories());

        Category cat = new Category();
        cat.setName("Meta");
        List<Category> list = new ArrayList<Category>();
        list.add(cat);
        handle.setCategories(list);
        assertEquals("Meta", handle.getCategories()
                .get(0)
                .getName());
    }
}
