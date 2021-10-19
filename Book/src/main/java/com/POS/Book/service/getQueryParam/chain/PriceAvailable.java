package com.POS.Book.service.getQueryParam.chain;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.Book;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.getQueryParam.Chain;

import java.util.List;
import java.util.Optional;

public class PriceAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    // TODO : PRICE LOGIC


    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        if (bookFilter.getPrice() != null) {
            Optional<List<Book>> optionalBookList = Optional.ofNullable(bookRepository.findByPrice(bookFilter.getPrice()));

            return optionalBookList.map(books -> BookAdapter.toDTOList(books))
                    .orElseThrow(() -> new RuntimeException());
        } else {
            return nextChain.run(bookFilter, bookRepository);
        }
    }
}
