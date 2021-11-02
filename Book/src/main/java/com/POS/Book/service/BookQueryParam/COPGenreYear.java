package com.POS.Book.service.BookQueryParam;

import com.POS.Book.service.BookQueryParam.chainGenreYear.GenreAndYearAvailable;
import com.POS.Book.service.BookQueryParam.chainGenreYear.GenreAvailable;
import com.POS.Book.service.BookQueryParam.chainGenreYear.YearAvailable;

class COPGenreYear {

    private final Chain firstChain;

    protected COPGenreYear() {

        firstChain = new GenreAndYearAvailable();
        Chain secondChain = new GenreAvailable();
        Chain thirdChain = new YearAvailable();

        firstChain.setNextChain(secondChain);
        secondChain.setNextChain(thirdChain);
        thirdChain.setNextChain(null);
    }

    public Chain getFirstChain() {
        return firstChain;
    }
}
