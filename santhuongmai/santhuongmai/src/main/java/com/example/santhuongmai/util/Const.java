package com.example.santhuongmai.util;

public class Const {
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static class ORDER_STATUS {
        public final static String WAITING = "WAITING";
        public final static String WAITING_DELIVERY = "WAITING_DELIVERY";
        public final static String DELIVERED = "DELIVERED";
        public final static String CANCELED = "CANCELED";
        public final static String RETURNS = "RETURNS";
        public final static String ERROR = "ERROR";
        public final static String ON_DELIVERY = "ON_DELIVERY";
        public final static String DONE = "DONE";
    }
    public final static class GHN {
        public final static String PREFIX_ADDRESS_API = "https://online-gateway.ghn.vn/shiip/public-api/master-data";
        public final static String PREFIX_SHIPPING_API = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order";
        public final static String TOKEN = "cff0fcca-5ddf-11ed-ad26-3a4226f77ff0";
    }
}
