package demos;

import models.ImageDateTime;
import models.UserAccount;
import models.model_utils.Location;
import utils.paths.TopPaths;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static models.UserAccount.ProfileSizes.*;
import static models.UserAccount.SocialKeys.*;

public class UserMain {

    public static void main(String... args) throws IOException {
        var account = new UserAccount();
        account.username = "sayannath";
        account.id = "_7ldoV2Ti0g";
        account.addProfileSize(SMALL, "https://images.unsplash.com/profile-fb-1465045918-291eef4d0211.jpg?ixlib=rb-1.2" +
                                      ".1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32");
        account.addProfileSize(LARGE, "https://images.unsplash.com/profile-fb-1465045918-291eef4d0211.jpg?ixlib=rb-1.2" +
                                      ".1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128");
        account.addProfileSize(MEDIUM, "https://images.unsplash.com/profile-fb-1465045918-291eef4d0211.jpg?ixlib=rb-1.2" +
                                       ".1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64");

        account.addLink(FOLLOWERS, "https://api.unsplash.com/users/sayannath/followers");
        account.addLink(PORTFOLIO, "https://api.unsplash.com/users/sayannath/portfolio");
        account.addLink(FOLLOWING, "https://api.unsplash.com/users/sayannath/following");
        account.addLink(SELF, "https://api.unsplash.com/users/sayannath");
        account.addLink(HTML, "https://unsplash.com/@sayannath");
        account.addLink(PHOTOS, "https://api.unsplash.com/users/sayannath/photos");
        account.addLink(LIKES, "https://api.unsplash.com/users/sayannath/likes");

        account.location = new Location();
        account.location.setCity("New Delhi");

        account.bio = "Follow me on Instagram @sayannath";
        account.acceptedUnsplashTOS = true;
        account.hireable = true;
        account.twitterUsername = null;
        account.instagramUsername = "sayannath";
        account.portfolioURL = new URL("https://www.youtube.com/channel/UCUeZjck1nzms3DFO8PITv6Q");
        account.totalLikes = 2;
        account.name = "Sayan Nath";
        account.lastName = "Nath";
        account.updatedAt = new ImageDateTime("2021-07-01T02:24:39-04:00");
        account.firstName = "Sayan";
        account.totalPhotoCollections = 2;
        account.totalPhotosPosted = 644;

        try (var writer = new BufferedWriter(new FileWriter(TopPaths.TEMP_PATH + "/sample-user-parsed.json"))) {
            writer.write(account.toJSON().toString(2));
        }
    }
}
