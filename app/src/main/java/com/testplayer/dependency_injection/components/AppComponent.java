package com.testplayer.dependency_injection.components;

import android.content.Context;

import com.testplayer.rx.RxExecutor;
import com.testplayer.dependency_injection.modules.ContextModule;
import com.testplayer.dependency_injection.modules.RxExecutorModule;

import javax.inject.Singleton;
import dagger.Component;

@Component(modules = {ContextModule.class, RxExecutorModule.class})
@Singleton
public interface AppComponent {

    Context getContext();

    RxExecutor getRxExecutor();
}
