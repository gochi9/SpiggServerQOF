package com.deadshotmdf.SpiggServerQOF.BlockChat.Objects;

public class BlockProfile {

    private boolean chatAllowed;
    private boolean privateChatAllowed;

    public BlockProfile(boolean chatAllowed, boolean privateChatAllowed) {
        this.chatAllowed = chatAllowed;
        this.privateChatAllowed = privateChatAllowed;
    }

    public boolean isChatAllowed() {
        return chatAllowed;
    }

    public void setChatAllowed(boolean chatAllowed) {
        this.chatAllowed = chatAllowed;
    }

    public boolean isPrivateChatAllowed() {
        return privateChatAllowed;
    }

    public void setPrivateChatAllowed(boolean privateChatAllowed) {
        this.privateChatAllowed = privateChatAllowed;
    }

}
