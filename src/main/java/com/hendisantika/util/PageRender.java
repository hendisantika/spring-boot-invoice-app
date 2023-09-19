package com.hendisantika.util;

import org.springframework.data.domain.Page;

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
public class PageRender<T> {

    private final String url;
    private final Page<T> page;
    private final int totalPages;
    private final int itemsPerPage;
    private final int currPage;
    private final List<PageItem> pages;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages = new ArrayList<>();

        this.itemsPerPage = page.getSize();
        this.totalPages = page.getTotalPages();
        this.currPage = page.getNumber() + 1;

        int from;
        int until;
        if (totalPages <= itemsPerPage) {
            from = 1;
            until = totalPages;
        } else {
            if (currPage <= (itemsPerPage / 2)) {
                from = 1;
                until = itemsPerPage;
            } else if (currPage >= totalPages - itemsPerPage / 2) {
                from = totalPages - itemsPerPage + 1;
                until = itemsPerPage;
            } else {
                from = currPage - (itemsPerPage / 2);
                until = itemsPerPage;
            }
        }

        for (int i = 0; i < until; i++) {
            pages.add(new PageItem(from + i, currPage == from + i));
        }
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }

    public String getUrl() {
        return url;
    }

    public Page<T> getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }

}
