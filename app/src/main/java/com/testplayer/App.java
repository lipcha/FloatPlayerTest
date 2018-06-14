package com.testplayer;

import android.app.Application;
import android.content.Context;

import com.testplayer.dependency_injection.components.AppComponent;
import com.testplayer.dependency_injection.components.DaggerAppComponent;
import com.testplayer.dependency_injection.components.DaggerDownloaderComponent;
import com.testplayer.dependency_injection.components.DaggerServiceComponent;
import com.testplayer.dependency_injection.components.DownloaderComponent;
import com.testplayer.dependency_injection.components.ServiceComponent;
import com.testplayer.dependency_injection.modules.ContextModule;
import com.testplayer.dependency_injection.modules.DownloaderModule;

public class App extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildAppComponent();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    private void buildAppComponent(){
        sAppComponent = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static ServiceComponent getServiceComponent(){
        return DaggerServiceComponent
                .builder()
                .appComponent(getAppComponent())
                .build();
    }

    public static DownloaderComponent getDownloaderComponent(final Context context){
        return DaggerDownloaderComponent
                .builder()
                .downloaderModule(new DownloaderModule(context))
                .build();
    }
}
