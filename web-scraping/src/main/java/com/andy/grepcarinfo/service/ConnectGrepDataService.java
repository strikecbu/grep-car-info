package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.CarView;
import org.jsoup.nodes.Document;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/20 AndyChen,new
 * </ul>
 * @since 2021/2/20
 */
public interface ConnectGrepDataService {

    Flux<CarView> scrapWebData(String url) throws IOException;
}
