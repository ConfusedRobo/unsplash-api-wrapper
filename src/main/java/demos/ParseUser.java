package demos;

import models.UnsplashImageDateTimeData;
import models.UnsplashImageUserAccountInfo;
import models.model_utils.Location;
import utils.paths.TopPaths;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static models.UnsplashImageUserAccountInfo.ProfileImageSizes.*;
import static models.UnsplashImageUserAccountInfo.SocialKeys.*;

public class ParseUser {

    public static void main(String... args) throws IOException {
        var account = new UnsplashImageUserAccountInfo("_7ldoV2Ti0g", "sayannath");
        account.addImageSize(SMALL, "https://images.unsplash.com/profile-fb-1465045918-291eef4d0211.jpg?ixlib=rb-1.2" +
                                    ".1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32");
        account.addImageSize(LARGE, "https://images.unsplash.com/profile-fb-1465045918-291eef4d0211.jpg?ixlib=rb-1.2" +
                                    ".1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128");
        account.addImageSize(MEDIUM, "https://images.unsplash.com/profile-fb-1465045918-291eef4d0211.jpg?ixlib=rb-1.2" +
                                     ".1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64");

        account.addSocialLink(FOLLOWERS, "https://api.unsplash.com/users/sayannath/followers");
        account.addSocialLink(PORTFOLIO, "https://api.unsplash.com/users/sayannath/portfolio");
        account.addSocialLink(FOLLOWING, "https://api.unsplash.com/users/sayannath/following");
        account.addSocialLink(SELF, "https://api.unsplash.com/users/sayannath");
        account.addSocialLink(HTML, "https://unsplash.com/@sayannath");
        account.addSocialLink(PHOTOS, "https://api.unsplash.com/users/sayannath/photos");
        account.addSocialLink(LIKES, "https://api.unsplash.com/users/sayannath/likes");

        account.setLocation(new Location());
        account.getLocation().setCity("New Delhi");

        account.setBio("Follow me on Instagram @sayannath");
        account.setAcceptedUnsplashTOS(true);
        account.setHireable(true);
        account.setTwitterUsername(null);
        account.setInstagramUsername("sayannath");
        account.setPortfolioURL(new URL("https://www.youtube.com/channel/UCUeZjck1nzms3DFO8PITv6Q"));
        account.setTotalLikes(2);
        account.setName("Sayan Nath");
        account.setLastName("Nath");
        account.setUpdatedAt(new UnsplashImageDateTimeData("2021-07-01T02:24:39-04:00"));
        account.setFirstName("Sayan");
        account.setTotalPhotoCollections(2);
        account.setTotalPhotosPosted(644);

        try (var writer = new BufferedWriter(new FileWriter(TopPaths.TEMP_PATH + "/sample-user-parsed.json"))) {
            writer.write(account.packUserJSON().toString(2));
        }
    }
}
