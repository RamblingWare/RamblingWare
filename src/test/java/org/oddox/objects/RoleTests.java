package org.oddox.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.oddox.config.Utils;
import org.oddox.objects.Role;

/**
 * Unit tests for Role Object
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class RoleTests {

    private String dateTime;
    private String readableDate;
    private String readableDateTime;

    @Before
    public void beforeEachTest() {
        dateTime = Utils.getDateIso8601();
        readableDate = Utils.formatReadableDate(Utils.convertStringToDate(dateTime));
        readableDateTime = Utils.formatReadableDateTime(Utils.convertStringToDate(dateTime));
    }

    @Test
    public void role() {
        Role role = new Role("author");
        role.setName("Author");
        assertEquals("Author", role.getName());
        role.set_Id("author");
        assertEquals("author", role.get_Id());
        role.set_Rev("1");
        assertEquals("1", role.get_Rev());
        role.setDescription("Hello World");
        assertEquals("Hello World", role.getDescription());
        role.setPublic(false);
        role.setPostsCreate(false);
        role.setPostsEdit(true);
        role.setPostsEditOthers(true);
        role.setPostsSeeHidden(true);
        role.setPostsDelete(true);
        role.setUsersCreate(true);
        role.setUsersEdit(true);
        role.setUsersEditOthers(true);
        role.setUsersDelete(true);
        role.setRolesCreate(true);
        role.setRolesEdit(true);
        role.setRolesDelete(true);
        role.setPagesCreate(true);
        role.setPagesEdit(true);
        role.setPagesDelete(true);
        role.setCommentsCreate(false);
        role.setCommentsEdit(false);
        role.setCommentsEditOthers(false);
        role.setCommentsDelete(false);
        role.setSettingsCreate(true);
        role.setSettingsEdit(true);
        role.setSettingsDelete(true);
        assertTrue(!role.isPublic());
        assertTrue(!role.isPostsCreate());
        assertTrue(role.isPostsEdit());
        assertTrue(role.isPostsEditOthers());
        assertTrue(role.isPostsSeeHidden());
        assertTrue(role.isPostsDelete());
        assertTrue(role.isUsersCreate());
        assertTrue(role.isUsersEdit());
        assertTrue(role.isUsersEditOthers());
        assertTrue(role.isUsersDelete());
        assertTrue(role.isRolesCreate());
        assertTrue(role.isRolesEdit());
        assertTrue(role.isRolesDelete());
        assertTrue(role.isPagesCreate());
        assertTrue(role.isPagesEdit());
        assertTrue(role.isPagesDelete());
        assertTrue(!role.isCommentsCreate());
        assertTrue(!role.isCommentsEdit());
        assertTrue(!role.isCommentsEditOthers());
        assertTrue(!role.isCommentsDelete());
        assertTrue(role.isSettingsCreate());
        assertTrue(role.isSettingsEdit());
        assertTrue(role.isSettingsDelete());
        role.setCreateDate(dateTime);
        assertEquals(dateTime, role.getCreateDate());
        assertEquals(readableDate, role.getCreateDateReadable());
        assertEquals(readableDateTime, role.getCreateDateTimeReadable());
        role.setModifyDate(dateTime);
        assertEquals(dateTime, role.getModifyDate());
        assertEquals(readableDate, role.getModifyDateReadable());
        assertEquals(readableDateTime, role.getModifyDateTimeReadable());
        assertNotNull(role.toString());

        Role role2 = new Role("author");
        role2.setName("Author");
        assertEquals("Author", role2.getName());

        assertTrue(role.compareTo(role2) == 0);

        Role role3 = new Role("admin");
        role3.setName("Admin");
        assertEquals("Admin", role3.getName());

        assertTrue(role.compareTo(role3) != 0);
    }

}
