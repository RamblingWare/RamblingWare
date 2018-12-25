package com.amdelamar.action.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.amdelamar.objects.Category;
import com.amdelamar.objects.Post;
import com.amdelamar.objects.Tag;
import com.amdelamar.objects.Year;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for ArchiveInterceptor
 */
@RunWith(JUnit4.class)
public class ArchiveInterceptorTests {

    @InjectMocks
    private ArchiveInterceptor handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void getters() {
        assertEquals(0, ArchiveInterceptor.getArchiveTotal());
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
