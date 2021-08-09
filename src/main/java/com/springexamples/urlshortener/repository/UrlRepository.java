package com.springexamples.urlshortener.repository;

import com.springexamples.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UrlRepository extends JpaRepository<Url, Long>
{
  public Url findByShortLink(String shortLink);
}
