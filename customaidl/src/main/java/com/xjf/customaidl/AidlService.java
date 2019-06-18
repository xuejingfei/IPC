package com.xjf.customaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.xjf.customaidl.aidl.Book;
import com.xjf.customaidl.aidl.BookInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 专家个人信息界面
 *
 * @author xuejingfei
 * create at 19-6-17
 */
public  class AidlService extends Service {
    private final String TAG = AidlService.class.getSimpleName();
    private List<Book> books = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCrate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy(){
        Log.d(TAG,"onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BookService();
    }

    public class BookService extends BookInterface.Stub{

        @Override
        public void addBook(Book book) throws RemoteException {
            books.add(book);
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            return books;
        }
    }
}