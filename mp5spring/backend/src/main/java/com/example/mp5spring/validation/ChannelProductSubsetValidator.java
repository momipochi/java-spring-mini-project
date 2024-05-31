package com.example.mp5spring.validation;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.mp5spring.model.Channel;
import com.example.mp5spring.model.Product;

public class ChannelProductSubsetValidator implements ConstraintValidator<ChannelProductSubsetConstraint,Channel>{

    @Override
    public void initialize(ChannelProductSubsetConstraint subset) {
    }

    @Override
    public boolean isValid(Channel channel, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        System.out.println("Validating channel....");
        Set<Product> privateList = channel.getOwnedProductsPrivate();
        for (Product product : privateList) {
            if(!channel.getOwnedProducts().contains(product)){
                System.out.println("Tought luck bud");
                return false;
            }
        }
        System.out.println("Youre good to go");
        return true;
    }
    
}
