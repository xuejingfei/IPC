package com.xjf.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

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
    private RemoteCallbackList<NotifyCallBackInterface> mRemoteListeners = new RemoteCallbackList<>();
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
            int size = mRemoteListeners.beginBroadcast();
            for(int i =0; i<size;i++) {
                mRemoteListeners.getBroadcastItem(i).notifyNewBook(book);
            }
            Log.d("xjf","listenerSize" + size);
            mRemoteListeners.finishBroadcast();
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return books;
        }

        @Override
        public void registerListener(NotifyCallBackInterface callBack) throws RemoteException {
            mRemoteListeners.register(callBack);
        }

        @Override
        public void unregisterListener(NotifyCallBackInterface callBack) throws RemoteException {
            mRemoteListeners.unregister(callBack);
        }
    }
}