package com.stockmonitor.abstracts;

import com.stockmonitor.interfaces.IApplication;

public abstract class AbstractApplication implements IApplication {
    @Override
    public abstract void run();

    // Common application functionality can be added here
}