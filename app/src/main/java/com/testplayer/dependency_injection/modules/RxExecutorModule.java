package com.testplayer.dependency_injection.modules;

import com.testplayer.rx.JobExecutor;
import com.testplayer.rx.RxExecutor;
import com.testplayer.rx.RxExecutorImpl;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public class RxExecutorModule {

    @Provides
    RxExecutor provideRxExecutor(final Executor jobExecutor, final Scheduler scheduler){
        return new RxExecutorImpl(jobExecutor, scheduler);
    }

    @Provides
    @Singleton
    Executor provideThreadExecutor(){
        return new JobExecutor();
    }

    @Provides
    @Singleton
    Scheduler providePostExecutionScheduler(){
        return AndroidSchedulers.mainThread();
    }
}
