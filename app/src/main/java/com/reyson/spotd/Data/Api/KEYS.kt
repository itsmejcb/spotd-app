package com.reyson.spotd.Data.Api

class KEY {
    companion object{
        const val EMAIL_PATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$"
        const val NAME_PATTERN = "[^0-9_'\"<>!@#$%^&*()=+|;:,.?{}×÷€£¥₩`~▪︎○●□■♤♡◇♧☆⊙°•¤《》¡¿]+"
        // const val NAME_PATTERN = "^[\\p{L}'\\\\-]+(?: [\\\\p{L}'\\\\-]+)*\$"
        const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        const val EMAIL = "primary_email"
        const val AUTH = "auth"
        const val UID = "uid"
        const val USERNAME = "username"
        const val PROFILE_PICTURE = "profile_picture"
        const val FULL_NAME = "full_name"
        const val USER_DEFAULT_COUNTRY_CODE = "default_country"
        const val SET_POST_STATUS = "set_rb"
        const val SET_COMMENT_STATUS = "set_cm"
        const val USER_UID = "user_uid"
        const val POST_PUSH_KEY = "ppk"
    }
}


