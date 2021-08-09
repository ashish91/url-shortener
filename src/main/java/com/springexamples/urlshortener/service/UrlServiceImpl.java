package com.springexamples.urlshortener.service;

import com.google.common.hash.Hashing;
package com.springexamples.urlshortener.model.Url;
package com.springexamples.urlshortener.model.UrlDto;
package com.springexamples.urlshortener.repository.UrlRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService {
  private static final Logger logger = LoggerFactory.getLogger(UrlServiceImpl.class);
  @Autowired
  private UrlRepository urlRepository;

  @Override
  public Url generateShortLink(UrlDto urlDto) {
    String encodedUrl = encodeUrl(urlDto.getUrl());
    Url urlToPersist = new Url();
    urlToPersist.setCreationDate(LocalDateTime.now());
    urlToPersist.setOriginalUrl(urlDto.getUrl());
    urlToPersist.setShortLink(encodedUrl);
    urlToPersist.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(),urlToPersist.getCreationDate()));

    Url urlToRet = persistShortLink(urlToPersist);

    return urlToRet;
  }

  private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate)
  {
      if(StringUtils.isBlank(expirationDate))
      {
          return creationDate.plusSeconds(60);
      }
      LocalDateTime expirationDateToRet = LocalDateTime.parse(expirationDate);
      return expirationDateToRet;
  }

  private String encodeUrl(String url) {
    String encodedUrl = "";
    LocalDateTime time = LocalDateTime.now();
    encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
    return encodedUrl;
  }

  @Override
    public Url persistShortLink(Url url) {
        Url urlToRet = urlRepository.save(url);
        return urlToRet;
    }

    @Override
    public Url getEncodedUrl(String url) {
        Url urlToRet = urlRepository.findByShortLink(url);
        return urlToRet;
    }

    @Override
    public void deleteShortLink(Url url) {

        urlRepository.delete(url);
    }
}
