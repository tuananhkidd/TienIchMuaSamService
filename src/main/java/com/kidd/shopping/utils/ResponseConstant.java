package com.kidd.shopping.utils;

public class ResponseConstant {
    public static final String VI = "vi";
    public static final String EN = "en";

    public static final String MSG_OK = "Ok";

    public static class Vi {
        public static final String WRONG_EMAIL_OR_PASSWORD = "Sai Email hoặc mật khẩu";
        public static final String OLD_PASSWORD_MISMATCH = "Mật khẩu cũ không khớp";
        public static final String EMAIL_EXIST = "Email đã tồn tại";
    }



    public static class ErrorMessage {
        public static final String NOT_FOUND = "not found";
        public static final String RESOURCE_EXIST = "resource exist";
        public static final String INVALID_EMAIL = "invalid email";
        public static final String ACCOUNT_NOT_VERIFIED = "account has't been verified";
        public static final String ACCOUNT_VERIFED = "account has been verified";
        public static final String INTERNAL_SERVER_ERROR = "internal server error";
        public static final String PASSWORD_TOO_SHORT = "Password need to be at lease 6 character length";
        public static final String INVALID_FIELDS = "invalid_field";
        public static final String USER_IS_NOT_STUDENT = "user_is_not_student";
        public static final String HOUSE_OWNER_NOT_FOUND = "house_owner_not_found";
        public static final String ROOM_NOT_OWN_OF_HOUSE_OWNER = "room_not_own_of_house_owner";
        public static final String REGION_NOT_SUPPORTED = "region_not_supported";
        public static final String POST_IS_NOT_EXPIRED = "post_is_not_expired";
        public static final String ROOM_NOT_FOUND = "room_not_found";
        public static final String STUDENT_NOT_FOUND = "student_not_found";
        public static final String OFFER_EXIST = "offer_exist";
        public static final String ROOM_SAVED = "room_saved";
        public static final String JOINED_ROOM_NOT_FOUND = "joined_room_not_found";
        public static final String USER_IS_NOT_HOUSE_OWNER = "USER_IS_NOT_HOUSE_OWNER";
        public static final String ACCOUNT_NOT_EXIST = "ACCOUNT_NOT_EXIST";
        public static final String NOTIFICATION_NOT_FOUND = "NOTIFICATION_NOT_FOUND";
        public static final String PENDING_POST_NOT_FOUND = "PENDING_POST_NOT_FOUND";
    }
}