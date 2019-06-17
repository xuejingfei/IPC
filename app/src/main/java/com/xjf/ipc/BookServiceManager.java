package com.xjf.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

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

    private List<NotifyCallBackInterface> mListeners = new ArrayList<>();

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
            try {
                for (NotifyCallBackInterface callBackInterface : mListeners) {
                    mBookInterface.registerListener(callBackInterface);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("xjf", "service onServiceDisconnected");
            try {
                for (NotifyCallBackInterface callBackInterface : mListeners) {
                    mBookInterface.unregisterListener(callBackInterface);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    public void bindService(Context context) {
        Intent intent = new Intent(context, AidlService.class);
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
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
            return mBookInterface.getBookList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 注册接口
     *
     * @param notifyCallBackInterface
     */
    public void registerListener(NotifyCallBackInterface notifyCallBackInterface) {

        try {
            if (mBookInterface != null) {
                mBookInterface.registerListener(notifyCallBackInterface);
            } else {
                mListeners.add(notifyCallBackInterface);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解除注册
     *
     * @param notifyCallBackInterface
     */
    public void unRegisterListener(NotifyCallBackInterface notifyCallBackInterface) {
        try {
            if (mBookInterface != null) {
                mBookInterface.unregisterListener(notifyCallBackInterface);
            } else {
                mListeners.remove(notifyCallBackInterface);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
