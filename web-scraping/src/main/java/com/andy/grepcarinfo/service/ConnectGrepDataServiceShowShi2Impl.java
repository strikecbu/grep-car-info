package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.exception.FetchDataException;
import com.andy.grepcarinfo.model.CarView;
import com.andy.grepcarinfo.model.VendorType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ConnectGrepDataServiceShowShi2Impl implements ConnectGrepDataService {

    @Override
    public Flux<CarView> scrapWebData(String url) throws IOException {

        return getAllQueryUrl(url)
                .flatMap(
                        queryUrl -> {
                            Mono<Document> tMono = Mono.fromCallable(() -> {
                                        try {
                                            return Jsoup.connect(queryUrl)
                                                    .timeout(10000)
                                                    .get();
                                        } catch (IOException e) {
                                            throw new FetchDataException(e, queryUrl);
                                        }
                                    })
                                    .publishOn(Schedulers.parallel());

                            return tMono.flatMapMany(document -> {
                                log.info("Query url: {}", queryUrl);
                                return Flux.fromStream(document.select(".product-small.box")
                                        .stream()
                                        .parallel()
                                        .map(parentEle -> {
                                            Element element = parentEle.selectFirst(".box-text-products");
                                            Element imgEle = parentEle.selectFirst("img");
                                            String imageUrl = imgEle.attr("src");

                                            Elements aEle = element.select("a");
                                            String detailUrl = aEle.attr("href");
                                            String title = aEle.text();
                                            String[] splitPrice = element.select("bdi")
                                                    .text()
                                                    .split(" ");
                                            String originPrice = splitPrice.length == 2 ? splitPrice[1] : splitPrice[3];
                                            String price = originPrice.replaceAll(",", "");
                                            String description = element.child(2)
                                                    .text();
                                            int year = Integer.parseInt(title.split(" ")[0].substring(0, 4));
                                            String brand = queryUrl.split("pwb-brand=")[1];
                                            return CarView.builder()
                                                    .title(title)
                                                    .description(description)
                                                    .detailUrl(detailUrl)
                                                    .price(Double.parseDouble(price))
                                                    .year(year)
                                                    .brand(brand)
                                                    .imageUrl(imageUrl)
                                                    .queryTime(LocalDateTime.now())
                                                    .vendorType(VendorType.SHOU_SHI)
                                                    .build();
                                        }));
                            });
                        })
                .onErrorContinue((e, o) -> {
                    if (e instanceof FetchDataException fetchDataException) {
                        log.error("Scraping {} fail, ex: {}",
                                fetchDataException.getFetchUrl(),
                                fetchDataException.getMessage());
                    } else {
                        log.error("Scraping fail, ex: {}", e.getMessage());
                    }
                });
    }

    public Flux<String> getAllQueryUrl(String url) throws IOException {
        final Document doc = Jsoup.connect(url)
                .timeout(60000)
                .get();
        Stream<String> brandStream = doc.select(".prdctfltr_checkboxes")
                .first()
                .children()
                .stream()
                .map(element -> {
                    return element.className()
                            .replace("prdctfltr_ft_", "");
                })
                .filter(str -> !"none".equalsIgnoreCase(str));

        Stream<String> cardTypeStream = doc.select(".prdctfltr_checkboxes")
                .get(3)
                .children()
                .stream()
                .map(element -> element.className()
                        .replace("prdctfltr_ft_", ""))
                .filter(str -> !"none".equalsIgnoreCase(str));


        Flux<String> brands$ = Flux.fromStream(brandStream);
        Flux<String> carTypes$ = Flux.fromIterable(cardTypeStream.collect(Collectors.toList()));

        return brands$.flatMap(
//                brand -> Flux.range(0, 1)
                brand -> carTypes$
//                        .map(type -> String.format("pwb-brand=%s", brand))
                        .map(type -> String.format("pa_species=%s&pwb-brand=%s", type, brand))
                        .map(queryStr -> url.concat("?")
                                .concat(queryStr))
        );
    }
}
