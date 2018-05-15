package org.oddox.action.interceptor;

import static org.junit.Assert.assertNotNull;
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
import org.oddox.objects.Post;
import org.oddox.objects.Tag;
import org.oddox.objects.Year;

/**
 * Unit tests for ArchiveInterceptor
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class ArchiveInterceptorTests {

    @InjectMocks
    private ArchiveInterceptor interceptor;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructor() {
        assertTrue(interceptor != null);
    }

    @Test
    public void variables() {
        interceptor.init();
        interceptor.destroy();
    }

    @Test
    public void featured() {
        Post featured = new Post("test");
        List<Post> featuredList = new ArrayList<Post>();
        featuredList.add(featured);
        ArchiveInterceptor.setArchiveFeatured(featuredList);
        assertNotNull(ArchiveInterceptor.getArchiveFeatured());
        assertTrue(ArchiveInterceptor.getArchiveFeatured()
                .contains(featured));
    }

    @Test
    public void categories() {
        Category cat = new Category();
        List<Category> catList = new ArrayList<Category>();
        catList.add(cat);
        ArchiveInterceptor.setArchiveCategories(catList);
        assertNotNull(ArchiveInterceptor.getArchiveCategories());
        assertTrue(ArchiveInterceptor.getArchiveCategories()
                .contains(cat));
    }

    @Test
    public void tags() {
        Tag tag = new Tag();
        List<Tag> tagList = new ArrayList<Tag>();
        tagList.add(tag);
        ArchiveInterceptor.setArchiveTags(tagList);
        assertNotNull(ArchiveInterceptor.getArchiveTags());
        assertTrue(ArchiveInterceptor.getArchiveTags()
                .contains(tag));
    }

    @Test
    public void years() {
        Year year = new Year();
        List<Year> yearList = new ArrayList<Year>();
        yearList.add(year);
        ArchiveInterceptor.setArchiveYears(yearList);
        assertNotNull(ArchiveInterceptor.getArchiveYears());
        assertTrue(ArchiveInterceptor.getArchiveYears()
                .contains(year));
    }

    @Test
    public void nullTests() {
        ArchiveInterceptor.setArchiveFeatured(null);
        assertNull(ArchiveInterceptor.getArchiveFeatured());

        ArchiveInterceptor.setArchiveTags(null);
        assertNull(ArchiveInterceptor.getArchiveTags());

        ArchiveInterceptor.setArchiveCategories(null);
        assertNull(ArchiveInterceptor.getArchiveCategories());

        ArchiveInterceptor.setArchiveYears(null);
        assertNull(ArchiveInterceptor.getArchiveYears());
    }
}
