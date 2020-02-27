package com.shaunlu.github.hibernate.type;

import com.shaunlu.github.hibernate.model.CustomerInfo;
import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class CustomerInfoType extends AbstractSingleColumnStandardBasicType<CustomerInfo>
        implements DiscriminatorType<CustomerInfo> {

    public static final CustomerInfoType INSTANCE = new CustomerInfoType();

    public CustomerInfoType() {
        super(VarcharTypeDescriptor.INSTANCE, CustomerInfoTypeDescriptor.INSTANCE);
    }

    @Override
    public CustomerInfo stringToObject(String xml) throws Exception {
        // this method will use CustomerInfoTypeDescriptor.fromString as implementation
        return fromString(xml);
    }

    @Override
    public String objectToSQLString(CustomerInfo value, Dialect dialect) throws Exception {
        // this method will use CustomerInfoTypeDescriptor.toString as implementation
        return toString(value);
    }

    // custom BasicType name
    @Override
    public String getName() {
        return "cus_info_type";
    }
}
