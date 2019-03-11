package net.sharksystem.identity.android;

import android.content.Context;

import identity.IdentityStorage;

public interface SharkIdentityStorage extends IdentityStorage {
    CharSequence getOwnerID();
    CharSequence getOwnerName();
    void setOwnerName(CharSequence name);
    void setOwnerID(CharSequence ownerID);
}
