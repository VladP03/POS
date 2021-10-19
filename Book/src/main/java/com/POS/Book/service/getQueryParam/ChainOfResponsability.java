package com.POS.Book.service.getQueryParam;

import com.POS.Book.service.getQueryParam.chain.IsbnAvailable;
import com.POS.Book.service.getQueryParam.chain.PriceAvailable;
import com.POS.Book.service.getQueryParam.chain.TitleAvailable;
import com.POS.Book.service.getQueryParam.chain.YearAvailable;

public class ChainOfResponsability {

    private final Chain firstChain;

    public ChainOfResponsability() {
        firstChain = new IsbnAvailable();

        Chain secondChain = new PriceAvailable();
        Chain thirdChain = new TitleAvailable();
        Chain forthChain = new YearAvailable();

        firstChain.setNextChain(secondChain);
        secondChain.setNextChain(thirdChain);
        thirdChain.setNextChain(forthChain);
        forthChain.setNextChain(null);
    }

    public Chain getFirstChain() {
        return firstChain;
    }
}
