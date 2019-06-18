package com.xjf.customaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.xjf.customaidl.aidl.Book;
import com.xjf.customaidl.aidl.BookInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 管理
 *
 * @author xuejingfei
 * create at 19-6-17
 */
public class BookServiceManager {
    private static volatile BookServiceManager mInstance;

    private BookInterface mBookInterface;


    public static BookServiceManager getInstance() {
        if (mInstance == null) {
            synchronized (BookServiceManager.class) {
                if (mInstance == null) {
                    mInstance = new BookServiceManager();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("xjf", "service connect");
            mBookInterface = BookInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("xjf", "service onServiceDisconnected");

        }
    };

    public void bindService(Context context) {
        Intent intent = new Intent(context, AidlService.class);
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    public void startService(Context context) {
        Intent intent = new Intent(context, AidlService.class);
        context.startService(intent);
    }


    public void unBindService(Context context) {
        context.unbindService(mConnection);
        Intent intent = new Intent(context, AidlService.class);
        context.stopService(intent);
    }

    /**
     * 添加书本
     *
     * @param book
     */
    public void addBook(Book book) {
        try {
            mBookInterface.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取书本列表
     */
    public List<Book> getBooks() {
        try {
            return mBookInterface.getBooks();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }


}
