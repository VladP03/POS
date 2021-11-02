package com.POS.Book.service.BookQueryParam;

import com.POS.Book.service.BookQueryParam.chainPageDisplay.ItemsAvailable;
import com.POS.Book.service.BookQueryParam.chainPageDisplay.PageAndItemsAvailable;
import com.POS.Book.service.BookQueryParam.chainPageDisplay.PageAvailable;

class COPPageDisplay {

    private final Chain firstChain;

    public COPPageDisplay() {

        firstChain = new PageAndItemsAvailable();
        Chain secondChain = new ItemsAvailable();
        Chain thirdChain = new PageAvailable();

        firstChain.setNextChain(secondChain);
        secondChain.setNextChain(thirdChain);
        thirdChain.setNextChain(null);
    }

    public Chain getFirstChain() {
        return firstChain;
    }
}
