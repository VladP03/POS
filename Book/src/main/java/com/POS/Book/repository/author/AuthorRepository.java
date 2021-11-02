package com.POS.Book.repository.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findById(Long id);

    @Query("SELECT author FROM Author author WHERE author.firstName = :name OR author.lastName = :name")
    Author findByNameEquals(@Param("name") String name);

    @Query("SELECT author FROM Author author WHERE author.firstName LIKE %:name% OR author.lastName LIKE %:name%")
    List<Author> findByNamePartially(@Param("name") String name);
}
