package com.springexamples.urlshortener.service;

@Service
public interface UrlService {
  public url generateShortLink(UrlDto urlDto);
  public url persistShortLink(Url url);
  public url getEncodedUrl(String url);
  public void deleteShortLink(Url url);
}
