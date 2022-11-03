package ru.zulvit;

import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(FactoryService.class).to(LibraryFactory.class);
    }
}
