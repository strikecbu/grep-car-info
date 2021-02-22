package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.Car;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2021/2/20 AndyChen,new
 * </ul>
 * @since 2021/2/20
 */
@Service
public class ConnectGrepDataServiceImpl implements ConnectGrepDataService {

    @Override
    public List<Car> grepCarData(String url) throws IOException {
        final URL urlO = new URL(url);
        final URLConnection connection = urlO.openConnection();
        final StringBuilder stringBuilder = new StringBuilder();
        try (
            final InputStream inputStream = connection.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        };


        return null;
    }


}
