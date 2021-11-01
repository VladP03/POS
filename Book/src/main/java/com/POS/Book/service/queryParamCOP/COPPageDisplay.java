package com.POS.Book.service.queryParamCOP;

import com.POS.Book.service.queryParamCOP.chain.PriceAvailable;
import com.POS.Book.service.queryParamCOP.chain.TitleAvailable;
import com.POS.Book.service.queryParamCOP.chain.YearAvailable;
import com.POS.Book.service.queryParamCOP.chainPageDisplay.ItemsAvailable;
import com.POS.Book.service.queryParamCOP.chainPageDisplay.PageAndItemsAvailable;
import com.POS.Book.service.queryParamCOP.chainPageDisplay.PageAvailable;

public class COPPageDisplay {

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
