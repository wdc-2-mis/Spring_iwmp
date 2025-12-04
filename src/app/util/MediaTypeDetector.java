package app.util;

public class MediaTypeDetector {

    public static String detectPlatform(String url) {
        url = url.toLowerCase();
        if (url.contains("youtube.com") || url.contains("youtu.be")) return "YOUTUBE";
        if (url.contains("instagram.com")) return "INSTAGRAM";
        if (url.contains("facebook.com")) return "FACEBOOK";
        if (url.contains("twitter.com") || url.contains("x.com")) return "TWITTER";
        if (url.contains("linkedin.com")) return "LINKEDIN";
        return "UNKNOWN";
    }

    public static String detectMediaType(String url) {
        url = url.toLowerCase();

        if (url.contains("youtube.com") || url.contains("youtu.be") || url.contains("/shorts/"))
            return "VIDEO";

        if (url.contains("instagram.com/reel/")) return "VIDEO";
        if (url.contains("instagram.com/p/")) return "IMAGE";
        if (url.contains("instagram.com/")) return "IMAGE"; // generic profile image

        if (url.contains("facebook.com/watch") || url.contains("/videos/")) return "VIDEO";
        if (url.contains("facebook.com/photo")) return "IMAGE";

         if (url.contains("x.com/") || url.contains("twitter.com/")) {
            if (url.contains("/video/") || url.contains("status/") && (url.contains("t=") || url.contains("?s=")))
                return "VIDEO";
            return "IMAGE";
        }

         if (url.contains("/video/")) return "VIDEO";
        if (url.contains("/posts/") || url.contains("/feed/")) return "IMAGE";

        return "NA"; // not matched
    }
}



