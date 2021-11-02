package com.POS.Book.service.AuthorQueryParam;

import lombok.Getter;

@Getter
public class ChainOfResponsability {

    private Chain firstChain;

    public ChainOfResponsability() {
        firstChain = new NameAndMatchAvailable();
        Chain secondChain = new NameAvailable();

        firstChain.setNextChain(secondChain);
        secondChain.setNextChain(null);
    }
}
