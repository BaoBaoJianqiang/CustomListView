package com.example.jianqiang.testlistview.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtils
{
    public static void copy(Context context, String content)
    {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        clipboardManager.setPrimaryClip(ClipData.newPlainText("title", content));
    }
}
