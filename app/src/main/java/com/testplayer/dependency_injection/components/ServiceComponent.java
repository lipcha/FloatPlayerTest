package com.testplayer.dependency_injection.components;

import com.testplayer.PlayerService;
import com.testplayer.dependency_injection.modules.ServiceModule;
import com.testplayer.dependency_injection.scope.ServiceScope;

import dagger.Component;

@ServiceScope
@Component(dependencies = AppComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(PlayerService playerService);
}
