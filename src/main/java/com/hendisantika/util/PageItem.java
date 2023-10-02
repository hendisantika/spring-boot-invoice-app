package com.hendisantika.util;

import lombok.Getter;

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
public record PageItem(int number, boolean current, List<PageItem> pages) {}
