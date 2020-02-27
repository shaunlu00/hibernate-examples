package com.shaunlu.github.hibernate.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaunlu.github.hibernate.model.CustomerInfo;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.io.IOException;

public class CustomerInfoTypeDescriptor extends AbstractTypeDescriptor<CustomerInfo> {

    public static final CustomerInfoTypeDescriptor INSTANCE = new CustomerInfoTypeDescriptor();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected CustomerInfoTypeDescriptor() {
        super(CustomerInfo.class);
    }

    @Override
    public String toString(CustomerInfo customerInfo) {
        String str = null;
        try {
            str = objectMapper.writeValueAsString(customerInfo);
        } catch (JsonProcessingException e) {
        }
        return str;
    }

    @Override
    public CustomerInfo fromString(String string) {
        CustomerInfo customerInfo = null;
        try {
            customerInfo = objectMapper.readValue(string, CustomerInfo.class);
        } catch (IOException e) {
        }
        return customerInfo;
    }


    @Override
    // The unwrap method is used when passing a CustomerInfoType as a PreparedStatement bind parameter,
    public <X> X unwrap(CustomerInfo value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (CustomerInfoTypeDescriptor.class.isAssignableFrom(type)) {
            return (X) value;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }
        throw unknownUnwrap(type);
    }

    @Override
    // The wrap method is used to transform the JDBC column value object (e.g. String in our case) to the actual mapping object type
    public <X> CustomerInfo wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return fromString((String) value);
        }
        if (value instanceof CustomerInfo) {
            return (CustomerInfo) value;
        }
        throw unknownWrap(value.getClass());
    }
}
