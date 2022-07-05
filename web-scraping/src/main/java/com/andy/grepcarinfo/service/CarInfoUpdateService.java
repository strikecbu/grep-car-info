package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.VendorType;

import java.io.IOException;

public interface CarInfoUpdateService {

    void updateCarInfo(VendorType vendor) throws IOException;
}
