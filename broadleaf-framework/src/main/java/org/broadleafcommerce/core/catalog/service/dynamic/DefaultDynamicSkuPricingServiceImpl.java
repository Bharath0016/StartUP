/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.catalog.service.dynamic;

import java.util.HashMap;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.catalog.domain.ProductOptionValueImpl;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuBundleItem;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jfischer
 * 
 */
@Service("blDynamicSkuPricingService")
public class DefaultDynamicSkuPricingServiceImpl implements
        DynamicSkuPricingService {

    @Override
    public DynamicSkuPrices getSkuPrices(Sku sku,
            @SuppressWarnings("rawtypes") HashMap skuPricingConsiderations) {
        // the default behavior is to ignore the pricing considerations and
        // return the retail and sale price from the sku
        //TODO add the adjustments here 
        DynamicSkuPrices prices = new DynamicSkuPrices();
        prices.setRetailPrice(sku.getRetailPrice());
        prices.setSalePrice(sku.getSalePrice());
        prices.setPriceAdjustment(sku.getProductOptionValueAdjustments());
        return prices;
    }

  

    @Override
    public DynamicSkuPrices getSkuBundleItemPrice(SkuBundleItem skuBundleItem,
            HashMap skuPricingConsiderations) {
        DynamicSkuPrices prices = new DynamicSkuPrices();
        prices.setSalePrice(skuBundleItem.getSalePrice());
        return prices;
    }





    @Override
    public DynamicSkuPrices getPriceAdjustment(
            ProductOptionValueImpl productOptionValueImpl,
            Money priceAdjustment, HashMap skuPricingConsiderationContext) {
        DynamicSkuPrices prices = new DynamicSkuPrices();
        
        prices.setPriceAdjustment(priceAdjustment);
        return prices;
    }



  
}
