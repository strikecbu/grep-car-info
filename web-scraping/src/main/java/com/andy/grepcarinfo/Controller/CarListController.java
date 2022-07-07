package com.andy.grepcarinfo.Controller;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.Price;
import com.andy.grepcarinfo.repository.CarRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/23 AndyChen,new
 * </ul>
 * @since 2021/2/23
 */
@Controller
public class CarListController {

    final private CarRepository repository;

    public CarListController(CarRepository repository) {
        this.repository = repository;
    }


    @RequestMapping("/list")
    public String list(Model model) {
        final Iterable<Car> all = repository.findAll();

        final ArrayList<Car> soldCars = new ArrayList<>();
        final ArrayList<Car> stillSellCars = new ArrayList<>();
        StreamSupport.stream(all.spliterator(), false)
                .sorted((o1, o2) -> {
                    String regularStr = "^(\\d{4})[/年].*";
                    final Pattern pattern = Pattern.compile(regularStr);
                    final Matcher matcher1 = pattern.matcher(o1.getYear());
                    final Matcher matcher2 = pattern.matcher(o2.getYear());
                    matcher1.find();
                    matcher2.find();
                    final String year1 = matcher1.group(1);
                    final String year2 = matcher2.group(1);
                    return -1 * Integer.compare(Integer.parseInt(year1), Integer.parseInt(year2));

                }).peek(car -> {
                    //最新價錢
                    final Optional<Price> max = car.getPrices().stream()
                            .max(Comparator.comparing(Price::getUpdateTime));
                    if (max.isPresent()) {
                        final Price price = max.get();
                        car.setLatestPrice(price.getPrice());
                    }

                    //賣出花費天數
                    if (car.isSold()) {
                        long hours =
                                TimeUnit.HOURS.convert(car.getUpdateTime().getTime() - car.getInitTime().getTime(), TimeUnit.MILLISECONDS);
                        long days = (long) Math.ceil((float) hours / 24);
                        car.setDayOfSold(days);
                    }
                }).forEach(car -> {
                    final ArrayList<Car> cars;
                    if (car.isSold()) {
                        soldCars.add(car);
                    } else {
                        stillSellCars.add(car);
                    }
                });
        final ArrayList<Object> shouShiCars = new ArrayList<>();
        final ArrayList<Object> twoThousandCars = new ArrayList<>();
        stillSellCars.forEach(car -> {
            if ("小施".equals(car.getVendor())) {
                shouShiCars.add(car);
            } else {
                twoThousandCars.add(car);
            }
        });
        model.addAttribute("SOLD_CAR", soldCars);
        model.addAttribute("SHOU_SHI_CAR", shouShiCars);
        model.addAttribute("TWO_THOUSAND_CAR", twoThousandCars);
//        model.addAttribute("updateDate", updateInfo.getShiouShiDate());
        return "list";
    }
}
