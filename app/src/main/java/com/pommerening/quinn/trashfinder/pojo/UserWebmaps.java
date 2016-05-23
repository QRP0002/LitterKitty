package com.pommerening.quinn.trashfinder.pojo;

import android.graphics.Bitmap;

import com.esri.core.portal.PortalItem;

/**
 * Created by Quinn Pommerening on 5/23/2016.
 */
public class UserWebmaps {

    public PortalItem item;
    public Bitmap itemThumbNail;

    public UserWebmaps(PortalItem item, Bitmap bitmap) {
        this.item = item;
        this.itemThumbNail = bitmap;
    }
}
