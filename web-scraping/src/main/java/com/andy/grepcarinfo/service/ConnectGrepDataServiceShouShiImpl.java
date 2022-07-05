package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.Price;
import com.andy.grepcarinfo.model.UpdateInfo;
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
import java.util.stream.Collectors;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/20 AndyChen,new
 * </ul>
 * @since 2021/2/20
 */
@Service
public class ConnectGrepDataServiceShouShiImpl implements ConnectGrepDataService {

    final private static Logger LOGGER = LoggerFactory.getLogger(ConnectGrepDataServiceShouShiImpl.class);

    final private UpdateInfo updateInfo;

    public ConnectGrepDataServiceShouShiImpl(UpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }

    @Override
    public void setLatestUpdateDate(Document pageDoc) throws IOException {
        for (Element element : pageDoc.getElementsByTag("meta")) {
            String attr = element.attr("name");
            if(!"keywords".equals(attr))
                continue;
            String content = element.attr("content");
            String reg = "(\\d{4}/\\d{2}/\\d{2})\\s更新出售中車輛";
            Matcher matcher = Pattern.compile(reg).matcher(content);
            if (matcher.find()) {
                final String date = matcher.group(1);
                LOGGER.info("更新最後更新日期： {}", date);
                updateInfo.setShiouShiDate(date);
            }
        }
    }

    @Override
    public List<Car> grepCarData(String url) throws IOException {
        final Document doc = Jsoup.connect(url).timeout(60000).get();
        final Element content = doc.getElementsByAttributeValue("itemprop", "articleBody").get(0);
        boolean listStart = false;
        List<List<Element>> list = new ArrayList<>();
        List<Element> infos = new ArrayList<>();
        for (Element child : content.children()) {
            final String html = child.html();
            if (html.contains("現有車輛資訊表")) {
                listStart = true;
                continue;
            }
            if (html.contains("新 進 車"))
                break;

            if (!listStart)
                continue;

            if (child.tag().getName().equals("hr")) {
                if(infos.size() == 0) // avoid when start get info but hit hr first
                    continue;
                list.add(infos);
                infos = new ArrayList<>();
                continue;
            }
            infos.add(child);
        }

        final List<Car> collect = list.stream()
                .map(elements -> {
                    final Car car = new Car();
                    for (Element element : elements) {
                        final Elements aTag = element.getElementsByTag("a");
                        String aTagUrl = null;
                        if (aTag.size() > 0) {
                            aTagUrl = aTag.get(0).attr("href");
                        }
                        String text = element.text();
                        transTextToCar(car, aTagUrl, text);
                        car.setVendor("小施");
                    }
                    return car;
                }).collect(Collectors.toList());
        LOGGER.info("共找到{}台汽車", collect.size());
        setLatestUpdateDate(doc);

        return collect;
    }

    public void transTextToCar(Car car, String aTagUrl, String text) {
        String regEx = "(\\d{4}/\\d{4})\\s.*";
        final Matcher matcher = Pattern.compile(regEx).matcher(text);
        if (matcher.find()) {
            final String year = matcher.group(1);
            text = text.replace(year, "").trim();
            car.setYear(year);
            final String takeOrderKeyWord = "已收訂";
            if(text.contains(takeOrderKeyWord)) {
                final String name = text.substring(0, text.indexOf(takeOrderKeyWord));
                car.setSold(true);
                car.setName(name);
            } else {
                final Matcher priceMatcher = Pattern.compile("(.*)\\s(\\d+\\.?\\d*\\s?)萬\\s賞車按我").matcher(text);
                if (priceMatcher.find()) {
                    final String name = priceMatcher.group(1).trim();
                    car.setName(name);
                    final String priceStr = priceMatcher.group(2).trim();
                    final Price price = new Price();
                    price.setCar(car);
                    price.setPrice(Double.parseDouble(priceStr));
                    final List<Price> prices = car.getPrices();
                    prices.add(price);
                    //賞車按我
                    car.setPicUrl(aTagUrl);
                } else {
                    throw new RuntimeException("Should be found price, but can not match.");
                }
            }
        } else {
            final String regex = "(規格配備)?按我參考";
            text = text.replaceAll(regex, "").trim();
            if(car.getDescription() == null)
                car.setDescription(text);
            //新車按我參考
            if(car.getNewCarUrl() == null)
                car.setNewCarUrl(aTagUrl);

        }
    }
}
