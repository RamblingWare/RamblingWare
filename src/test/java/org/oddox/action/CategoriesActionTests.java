package org.oddox.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.action.CategoriesAction;
import org.oddox.objects.Category;

/**
 * Unit tests for CategoriesAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class CategoriesActionTests {

    @InjectMocks
    private CategoriesAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructor() {
        assertTrue(action != null);
    }

    @Test
    public void variables() {
        action.setCategories(null);
        assertNull(action.getCategories());

        Category cat = new Category();
        cat.setName("Meta");
        List<Category> list = new ArrayList<Category>();
        list.add(cat);
        action.setCategories(list);
        assertEquals("Meta", action.getCategories()
                .get(0)
                .getName());

        action.setServletRequest(null);
        action.setServletResponse(null);
        if(action.servletRequest != null) {
            fail("servletRequest is not expected value");
        }
    }
}
