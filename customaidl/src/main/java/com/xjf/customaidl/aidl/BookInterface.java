package com.xjf.customaidl.aidl;

import android.os.*;
import android.util.Log;

import java.util.List;

/**
 * description: 自定义aidl.java
 *
 * @author xuejingfei
 * create at 19-6-18
 */
public interface BookInterface extends IInterface {

    public static abstract class Stub extends android.os.Binder implements BookInterface {
        private static String DESCRIPTOR = "com.xjf.customaidl.aidl.BookInterface";
        static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static BookInterface asInterface(IBinder binder) {
            if (binder == null) {
                return null;
            }
            //从本进程中查找
            IInterface iInterface = binder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof BookInterface) {
                return (BookInterface) iInterface;
            }
            return new Proxy(binder);
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(DESCRIPTOR);
                    return true;
                case TRANSACTION_addBook:
                    Log.d("xjf","Stub.addBook()");
                    data.enforceInterface(DESCRIPTOR);
                    Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    this.addBook(_arg0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getBookList:
                    Log.d("xjf","Stub.getBooks()");
                    data.enforceInterface(DESCRIPTOR);
                    java.util.List<Book> _result = this.getBooks();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {

        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            return null;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }


        public static class Proxy implements BookInterface {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                mRemote = iBinder;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                Log.d("xjf","Proxy.addBook()");
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                _data.writeInterfaceToken(DESCRIPTOR);
                if (book != null) {
                    _data.writeInt(1);
                    book.writeToParcel(_data, 0);
                }
                mRemote.transact(Stub.TRANSACTION_addBook, _data, _reply, 0);
                _reply.readException();
                Log.d("xjf","Proxy.addBook()1");
            }

            @Override
            public List<Book> getBooks() throws RemoteException {
                Log.d("xjf","Proxy.getBooks()");
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                List<Book> books;
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(Stub.TRANSACTION_getBookList, _data, _reply, 0);
                _reply.readException();
                books = _reply.createTypedArrayList(Book.CREATOR);
                Log.d("xjf","Proxy.getBooks()1");
                return books;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }
        }
    }

    public void addBook(Book book) throws RemoteException;

    public List<Book> getBooks() throws RemoteException;
}
