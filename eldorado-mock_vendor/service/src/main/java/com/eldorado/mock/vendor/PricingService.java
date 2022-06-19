package com.eldorado.mock.vendor;

import com.eldorado.mock.vendor.models.PricingDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PricingService {

    public List<PricingDTO> getPrices(Optional<String> symbol) {
        return Collections.emptyList();
    }
}
