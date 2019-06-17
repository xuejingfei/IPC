package com.xjf.ipc;

import com.xjf.ipc.Book;


oneway interface NotifyCallBackInterface {
    void notifyNewBook(in Book book);
}