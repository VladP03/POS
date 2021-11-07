package com.POS.Book.service.BookQueryParam;

import com.POS.Book.service.BookQueryParam.chainGenreYear.GenreAndYearAvailable;
import com.POS.Book.service.BookQueryParam.chainGenreYear.GenreAvailable;
import com.POS.Book.service.BookQueryParam.chainGenreYear.DefaultAvailable;
import com.POS.Book.service.BookQueryParam.chainGenreYear.YearAvailable;

public class ChainOfResponsability {

    private final Chain firstChain;

    public ChainOfResponsability() {

        firstChain = new GenreAndYearAvailable();
        Chain secondChain = new GenreAvailable();
        Chain thirdChain = new YearAvailable();
        Chain forthChain = new DefaultAvailable();

        firstChain.setNextChain(secondChain);
        secondChain.setNextChain(thirdChain);
        thirdChain.setNextChain(forthChain);
    }

    public Chain getFirstChain() {
        return firstChain;
    }
}
