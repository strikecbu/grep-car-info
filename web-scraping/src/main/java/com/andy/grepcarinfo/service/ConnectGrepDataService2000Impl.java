package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.Price;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/3/9 AndyChen,new
 * </ul>
 * @since 2021/3/9
 */
@Service
public class ConnectGrepDataService2000Impl {

    final private static Logger LOGGER = LoggerFactory.getLogger(ConnectGrepDataServiceShouShiImpl.class);


    public List<Car> grepCarData(String url) throws IOException {
        final Document doc = Jsoup.connect(url)
                .timeout(60000)
                .get();
        final Elements elements = doc.getElementById("article-content-inner")
                .getElementsByTag("p");
        final ArrayList<Car> cars = new ArrayList<>();
        for (Element element : elements) {
            final String text = element.text();
            if (text.contains("感謝新車主支持"))
                break;
            //已售車輛
            String soldRegularStr = "^(\\d{4}年)\\s(.*)\\s賞車點此\\s(.*)感謝收訂$";
            final Matcher soldMatch = Pattern.compile(soldRegularStr)
                    .matcher(text);
            if (soldMatch.find()) {
                final Car car = new Car();
                car.setVendor("兩千");
                final String years = soldMatch.group(1);
                final String name = soldMatch.group(2);
                final String description = soldMatch.group(3);
                car.setSold(true);
                car.setYear(years);
                car.setName(name);
                car.setDescription(description);
                cars.add(car);
            }
            //等待銷售
            String regularStr = "^(\\d{4}年)\\s(.*)\\s(.*)萬\\s賞車點此\\s(.*)$";
            final Matcher matcher = Pattern.compile(regularStr)
                    .matcher(text);
            if (matcher.find()) {
                final Car car = new Car();
                final String picUrl = element.getElementsByTag("a")
                        .attr("href");
                car.setVendor("兩千");
                final String years = matcher.group(1);
                final String name = matcher.group(2);
                final String price = matcher.group(3);
                final String description = matcher.group(4);
                car.setYear(years);
                car.setName(name);
                final Price webPrice = new Price();
                webPrice.setCar(car);
                webPrice.setPrice(Double.parseDouble(price));
                final List<Price> prices = car.getPrices();
                prices.add(webPrice);
                car.setDescription(description);
                car.setPicUrl(picUrl);
                cars.add(car);
            }
        }
        LOGGER.info("共找到{}台汽車", cars.size());
//        setLatestUpdateDate(doc);
        return cars;
    }
}
