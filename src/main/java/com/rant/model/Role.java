package com.rant.model;

import java.util.Date;

/**
 * This class represents an Author's Role.<br>
 * <br>
 * Default Roles:
 * <li>0 = Author - Create/Edit their own posts. Edit their own Profile page.</li>
 * <li>1 = Editor - Create/Edit any posts. See all hidden posts. Edit any Profile page.</li>
 * <li>2 = Owner - Create/Edit any posts. Edit their own Profile page. Add/Delete users.</li>
 * <li>3 = Admin - Edit any posts. See all hidden posts. Edit any profile pages. No profile page.
 * Add/Delete users.</li>
 * 
 * @author Austin Delamar
 * @created 7/04/2017
 *
 */
public class Role {

    private int id;
    private String name;
    private String description;

    private Date createDate;
    private Date modifyDate;

    private boolean isPublic;
    private boolean isPostsCreate;
    private boolean isPostsEdit;
    private boolean isPostsEditOthers;
    private boolean isPostsSeeHidden;
    private boolean isPostsDelete;
    private boolean isUsersCreate;
    private boolean isUsersEdit;
    private boolean isUsersEditOthers;
    private boolean isUsersDelete;
    private boolean isRolesCreate;
    private boolean isRolesEdit;
    private boolean isRolesDelete;
    private boolean isPagesCreate;
    private boolean isPagesEdit;
    private boolean isPagesDelete;
    private boolean isCommentsCreate;
    private boolean isCommentsEdit;
    private boolean isCommentsEditOthers;
    private boolean isCommentsDelete;
    private boolean isSettingsCreate;
    private boolean isSettingsEdit;
    private boolean isSettingsDelete;

    public Role(int id) {
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isPostsCreate() {
        return isPostsCreate;
    }

    public void setPostsCreate(boolean isPostsCreate) {
        this.isPostsCreate = isPostsCreate;
    }

    public boolean isPostsEdit() {
        return isPostsEdit;
    }

    public void setPostsEdit(boolean isPostsEdit) {
        this.isPostsEdit = isPostsEdit;
    }

    public boolean isPostsEditOthers() {
        return isPostsEditOthers;
    }

    public void setPostsEditOthers(boolean isPostsEditOthers) {
        this.isPostsEditOthers = isPostsEditOthers;
    }

    public boolean isPostsSeeHidden() {
        return isPostsSeeHidden;
    }

    public void setPostsSeeHidden(boolean isPostsSeeHidden) {
        this.isPostsSeeHidden = isPostsSeeHidden;
    }

    public boolean isPostsDelete() {
        return isPostsDelete;
    }

    public void setPostsDelete(boolean isPostsDelete) {
        this.isPostsDelete = isPostsDelete;
    }

    public boolean isUsersCreate() {
        return isUsersCreate;
    }

    public void setUsersCreate(boolean isUsersCreate) {
        this.isUsersCreate = isUsersCreate;
    }

    public boolean isUsersEdit() {
        return isUsersEdit;
    }

    public void setUsersEdit(boolean isUsersEdit) {
        this.isUsersEdit = isUsersEdit;
    }

    public boolean isUsersEditOthers() {
        return isUsersEditOthers;
    }

    public void setUsersEditOthers(boolean isUsersEditOthers) {
        this.isUsersEditOthers = isUsersEditOthers;
    }

    public boolean isUsersDelete() {
        return isUsersDelete;
    }

    public void setUsersDelete(boolean isUsersDelete) {
        this.isUsersDelete = isUsersDelete;
    }

    public boolean isRolesCreate() {
        return isRolesCreate;
    }

    public void setRolesCreate(boolean isRolesCreate) {
        this.isRolesCreate = isRolesCreate;
    }

    public boolean isRolesEdit() {
        return isRolesEdit;
    }

    public void setRolesEdit(boolean isRolesEdit) {
        this.isRolesEdit = isRolesEdit;
    }

    public boolean isRolesDelete() {
        return isRolesDelete;
    }

    public void setRolesDelete(boolean isRolesDelete) {
        this.isRolesDelete = isRolesDelete;
    }

    public boolean isPagesCreate() {
        return isPagesCreate;
    }

    public void setPagesCreate(boolean isPagesCreate) {
        this.isPagesCreate = isPagesCreate;
    }

    public boolean isPagesEdit() {
        return isPagesEdit;
    }

    public void setPagesEdit(boolean isPagesEdit) {
        this.isPagesEdit = isPagesEdit;
    }

    public boolean isPagesDelete() {
        return isPagesDelete;
    }

    public void setPagesDelete(boolean isPagesDelete) {
        this.isPagesDelete = isPagesDelete;
    }

    public boolean isCommentsCreate() {
        return isCommentsCreate;
    }

    public void setCommentsCreate(boolean isCommentsCreate) {
        this.isCommentsCreate = isCommentsCreate;
    }

    public boolean isCommentsEdit() {
        return isCommentsEdit;
    }

    public void setCommentsEdit(boolean isCommentsEdit) {
        this.isCommentsEdit = isCommentsEdit;
    }

    public boolean isCommentsEditOthers() {
        return isCommentsEditOthers;
    }

    public void setCommentsEditOthers(boolean isCommentsEditOthers) {
        this.isCommentsEditOthers = isCommentsEditOthers;
    }

    public boolean isCommentsDelete() {
        return isCommentsDelete;
    }

    public void setCommentsDelete(boolean isCommentsDelete) {
        this.isCommentsDelete = isCommentsDelete;
    }

    public boolean isSettingsCreate() {
        return isSettingsCreate;
    }

    public void setSettingsCreate(boolean isSettingsCreate) {
        this.isSettingsCreate = isSettingsCreate;
    }

    public boolean isSettingsEdit() {
        return isSettingsEdit;
    }

    public void setSettingsEdit(boolean isSettingsEdit) {
        this.isSettingsEdit = isSettingsEdit;
    }

    public boolean isSettingsDelete() {
        return isSettingsDelete;
    }

    public void setSettingsDelete(boolean isSettingsDelete) {
        this.isSettingsDelete = isSettingsDelete;
    }
}
