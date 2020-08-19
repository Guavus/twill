package org.apache.twill.internal.utils;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class Utility {
    public static ListenableFuture<Service.State> getListenableFutureFromService(Callable<Service.State> callable){
        return MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor()).submit(callable);
    }
}
