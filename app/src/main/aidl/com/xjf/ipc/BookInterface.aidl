package com.xjf.ipc;

import com.xjf.ipc.Book;
import com.xjf.ipc.NotifyCallBackInterface;

interface BookInterface {

    void addBook(in Book book);

    List<Book> getBookList();

    void registerListener(NotifyCallBackInterface callBack);

    void unregisterListener(NotifyCallBackInterface callBack);

}
