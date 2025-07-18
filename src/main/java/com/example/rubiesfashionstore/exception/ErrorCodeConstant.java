package com.example.rubiesfashionstore.exception;

public class ErrorCodeConstant {
    //400 - BAD REQUEST
    public static final String INVALID_PRICE = "400101";
    public static final String INVALID_SKU_FORMAT = "400102";
    public static final String INVALID_SIZE = "400103";
    public static final String CATEGORY_NAME_ALREADY_EXISTS = "400104";
    public static final String COLOR_NAME_ALREADY_EXISTS = "400104";
    public static final String HEX_CODE_ALREADY_EXISTS = "400105";
    public static final String EMAIL_ALREADY_EXISTS = "400106";

    //401 - UNAUTHORIED
    public static final String INVALID_LOGIN_CREDENTIALS = "401001"; // Thông tin đăng nhập không chính xác

    //403 - FORBIDDE
    public static final String USER_INACTIVE = "403001";              // Tài khoản bị khoá

    //404 - NOT FOUND
    public static final String PRODUCT_NOT_FOUND_BY_ID = "404101";
    public static final String CATEGORY_NOT_FOUND_BY_ID = "404102";
    public static final String COLOR_NOT_FOUND_BY_ID = "404103";

    public static final String USER_NOT_FOUND = "404104";

    //409 - CONFLICT
    public static final String DUPLICATE_SKU = "409101";
    public static final String DUPLICATE_CATEGORY_NAME = "409102";

    // 500 - SERVER ERROR
    public static final String SERVER_ERROR = "500001";
}
