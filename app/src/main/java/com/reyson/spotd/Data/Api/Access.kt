package com.reyson.spotd.Data.Api

class Access {
    companion object {
        const val FULL_ACCESS = "184d44c6-8414-4003-9a4a6f85a5c0-32b9-40cf"

        // read only
        const val READ_ONLY = "8b507c7d-0e52-4861-be0ac05dff38-7265-4a7a"

        // storage zone name
        const val STORAGE_ZONE_NAME = "jdevv1"

        // pull zone name
        const val PULL_ZONE_NAME = "jdevv1"
        val URL_BUCKET_PROFILE =
            "https://storage.bunnycdn.com/" + Access.STORAGE_ZONE_NAME + "/profile/"
        val URL_BUCKET_POST =
            "https://storage.bunnycdn.com/" + Access.STORAGE_ZONE_NAME + "/posts/"
        val URL_BUCKET_COVER =
            "https://storage.bunnycdn.com/" + Access.STORAGE_ZONE_NAME + "/cover/"
        val URL_BUCKET_HIGHLIGHTS =
            "https://storage.bunnycdn.com/" + Access.STORAGE_ZONE_NAME + "/highlights/"
        val URL_BUCKET_MESSAGE =
            "https://storage.bunnycdn.com/" + Access.STORAGE_ZONE_NAME + "/message/"
        val URL_BUCKET_PROMOTION =
            "https://storage.bunnycdn.com/" + Access.STORAGE_ZONE_NAME + "/promotion/"
        val URL_PULL_ZONE_PROFILE = "https://" + Access.PULL_ZONE_NAME + ".b-cdn.net/profile/"
        val URL_PULL_ZONE_POST = "https://" + Access.PULL_ZONE_NAME + ".b-cdn.net/posts/"
        val URL_PULL_ZONE_COVER = "https://" + Access.PULL_ZONE_NAME + ".b-cdn.net/cover/"
        val URL_PULL_ZONE_HIGHLIGHTS =
            "https://" + Access.PULL_ZONE_NAME + ".b-cdn.net/highlights/"
        val URL_PULL_ZONE_MASSAGE = "https://" + Access.PULL_ZONE_NAME + ".b-cdn.net/massage/"
        val URL_PULL_ZONE_PROMOTION =
            "https://" + Access.PULL_ZONE_NAME + ".b-cdn.net/promotion/"

        fun loadTOKEN(): String? {
            return "28760529650b3596cb61eb0bb32080f997ac3bd8512a1f3ea50272ddf0c1af4c8b178c09aada38e4a980f4415c2dda7d"
        }

        val loadToken = loadTOKEN()
        fun loadWebHook(): String? {
            return "https://discordapp.com/api/webhooks/1163338884235677726/Ix01RjzXmaF5eEaT2W4EJQxmSSOmSYhGJocLRxdU-MNTlSLbC7ZjEHSP9vBQvDr0CPID"
        }

        val loadWebHook = loadWebHook()
    }
}