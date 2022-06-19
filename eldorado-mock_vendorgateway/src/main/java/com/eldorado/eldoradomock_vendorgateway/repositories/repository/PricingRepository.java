package com.eldorado.eldoradomock_vendorgateway.repositories.repository;

import com.eldorado.eldoradomock_vendorgateway.repositories.entity.PricingDbDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingRepository extends JpaRepository<PricingDbDTO, Integer> {

}
