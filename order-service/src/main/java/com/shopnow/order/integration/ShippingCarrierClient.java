package com.shopnow.order.integration;

import com.shopnow.order.generic.CarrierTrackingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shippingCarrierClient", url = "${integration.shipping-carrier.url:http://localhost:9091}")
public interface ShippingCarrierClient {

    @GetMapping("/carrier/track/{trackingNumber}")
    CarrierTrackingResponse getTracking(@PathVariable("trackingNumber") String trackingNumber);
}
