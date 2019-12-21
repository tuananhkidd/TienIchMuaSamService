package com.kidd.shopping.firebase.chat;

import com.google.cloud.firestore.annotation.ServerTimestamp;

import java.util.Date;

public class EmptyMessage {
    @ServerTimestamp
    private Date createdDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
