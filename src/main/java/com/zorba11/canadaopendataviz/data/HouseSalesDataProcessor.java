package com.zorba11.canadaopendataviz.data;

import com.zorba11.canadaopendataviz.model.SoldHouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class HouseSalesDataProcessor implements ItemProcessor<SoldHouseInput, SoldHouse> {

    private static final Logger log = LoggerFactory.getLogger(HouseSalesDataProcessor.class);

    @Override
    public SoldHouse process(final SoldHouseInput soldHouseInput) throws Exception {

        SoldHouse soldHouse = new SoldHouse();

        soldHouse.setId(soldHouseInput.getId());

        soldHouse.setPrice(soldHouseInput.getPrice());

        soldHouse.setLat(soldHouseInput.getLat());
        soldHouse.setLng(soldHouseInput.getLng());

        String address = soldHouseInput.getAddress();
        String addressFormatted = address.replace(',', ' ');

        soldHouse.setAddress(addressFormatted);

        return soldHouse;
    }

}
