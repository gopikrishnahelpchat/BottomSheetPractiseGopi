package com.gopi.bottomsheet.bottomsheetpractisegopi.utils;

import android.util.Log;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by gopikrishna on 6/8/16.
 */
public class RxJavaFactory {

    public static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(func.call());
                    subscriber.onCompleted();
                } catch (Exception ex) {
                    Log.e("RxJava ", "Error reading from the database", ex);
                    subscriber.onError(ex);
                }
            }
        });
    }

}
