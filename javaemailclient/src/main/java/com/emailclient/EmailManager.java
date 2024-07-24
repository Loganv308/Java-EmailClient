package com.emailclient;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Folder;

import com.emailclient.controller.services.FetchFoldersService;
import com.emailclient.controller.services.FolderUpdaterService;
import com.emailclient.model.EmailAccount;
import com.emailclient.model.EmailMessage;
import com.emailclient.model.EmailTreeItem;

import javafx.scene.control.TreeItem;

public class EmailManager {

    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;

    private FolderUpdaterService folderUpdaterService;
    private List<Folder> folderList = new ArrayList<Folder>();

    // Folder handler
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");
    
    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }
    
    public List<Folder> getFolderList() {
        return this.folderList;
    }

    public EmailManager(){
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public void addEmailAccount(EmailAccount emailAccount) {
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }

    public void setRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnread() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        // Says to the server the message was deleted
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
