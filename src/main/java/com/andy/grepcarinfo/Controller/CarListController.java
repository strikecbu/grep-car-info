package com.andy.grepcarinfo.Controller;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.Price;
import com.andy.grepcarinfo.repository.CarRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
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
        final List<Car> collect = StreamSupport.stream(all.spliterator(), false)
                .sorted((o1, o2) -> {
                    final String year1 = o1.getYear().split("/")[0];
                    final String year2 = o2.getYear().split("/")[0];
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
                        final LocalDate initDate = LocalDate.ofInstant(car.getInitTime().toInstant(), ZoneId.of("UTC" +
                                "+8"));
                        final LocalDate updateDate = LocalDate.ofInstant(car.getUpdateTime().toInstant(), ZoneId.of(
                                "UTC+8"));
                        final long days = ChronoUnit.DAYS.between(initDate, updateDate);
                        car.setDayOfSold(days);
                    }
                }).collect(Collectors.toList());
        model.addAttribute("carList", collect);

        return "list";
    }
}
