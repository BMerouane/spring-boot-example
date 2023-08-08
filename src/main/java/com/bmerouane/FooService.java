package com.bmerouane;

import org.springframework.stereotype.Service;

@Service
public class FooService {

    private final Main.Foo foo;

    public FooService(Main.Foo foo) {
        this.foo = foo;
        System.out.println(foo);
    }

    public Main.Foo getFoo() {
        return foo;
    }
}
