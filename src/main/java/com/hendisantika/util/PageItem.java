package com.hendisantika.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/09/21
 * Time: 05.20
 */
public class PageItem {
    private final int number;
    private final boolean current;
    private final List<PageItem> pages;

    public PageItem(int number, boolean current) {
        this.number = number;
        this.current = current;
        this.pages = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public boolean isCurrent() {
        return current;
    }
}
