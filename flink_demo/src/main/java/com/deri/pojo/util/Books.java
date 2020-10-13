package com.deri.pojo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: Books
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/20 10:52
 * @Version: v1.0
 **/
public class Books {

    public static BookPojo getBook(){
        List<BookPojo> bookPojos = new ArrayList<>();
        {
            BookPojo book1 = new BookPojo();
            book1.setId(1);
            book1.setType("cs");
            book1.setPrice(80);
            bookPojos.add(book1);
        }
        {
            BookPojo book1 = new BookPojo();
            book1.setId(2);
            book1.setType("math");
            book1.setPrice(70);
            bookPojos.add(book1);
        }
        {
            BookPojo book1 = new BookPojo();
            book1.setId(3);
            book1.setType("ph");
            book1.setPrice(60);
            bookPojos.add(book1);
        }
        {
            BookPojo book1 = new BookPojo();
            book1.setId(4);
            book1.setType("cs");
            book1.setPrice(50);
            bookPojos.add(book1);
        }
        {
            BookPojo book1 = new BookPojo();
            book1.setId(5);
            book1.setType("math");
            book1.setPrice(40);
            bookPojos.add(book1);
        }

        {
            BookPojo book1 = new BookPojo();
            book1.setId(6);
            book1.setType("ph");
            book1.setPrice(30);
            bookPojos.add(book1);
        }
        return bookPojos.get(new Random().nextInt(6));
    }
}
