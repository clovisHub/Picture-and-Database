package com.example.w3d5ex01;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    private FeedReaderContract(){

    }

    public static class FeedEntries implements BaseColumns {

        public static final String TABLE_NAME ="Jason";
        public static final String COLUMN_NAME ="First";
        public static final String COLUMN_LAST ="Last";
        public static final String COLUMN_EMAIL ="Email";
        public static final String COLUMN_CITY ="City";
        public static final String COLUMN_IMAGE="Image path";
    }



}
