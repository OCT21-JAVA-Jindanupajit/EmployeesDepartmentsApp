package com.jindanupajit.plugins.org.springframework.boot.binder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Component;

@Component
public interface Binder extends CommandLineRunner {

    @Override
    default void run(String... args) {
        String name = this.getClass().getName();

        String format = AnsiOutput.toString(
                AnsiColor.BRIGHT_YELLOW, " * ",
                AnsiColor.WHITE,"Binder ",
                AnsiColor.BRIGHT_YELLOW,"[",
                AnsiColor.BRIGHT_MAGENTA,"%s",
                AnsiColor.BRIGHT_YELLOW,"] ",
                AnsiColor.BRIGHT_GREEN,"Registered.\n",
                AnsiColor.DEFAULT);

        System.out.printf(format, name);

    }
}
