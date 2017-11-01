package net.wohlfart.mercury.security.oauth;


import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;


/**
 * this class is threadsafe
 */
@Component
public class AccountFactory {

    private static final String[] USER_ID_KEYS = {
            "id", // github, facebook, google
    };
    private static final String[] USER_FULL_NAME_KEYS = {
            "name", // github, facebook, google
    };
    private static final String[] USER_EMAIL_KEYS = {
            "email", // google
    };
    private static final String[] USER_LOGIN_KEY = {
            "login",  // github
    };

    private static final String[] LOGIN_OR_ID_KEY = { "login", "id" };


    @Autowired
    UserService userService;

    public User create(String provider, HashMap tokenValues, HashMap userValues) {
        String name = provider + ":" + this.findFirst(LOGIN_OR_ID_KEY, userValues).get();
        String email = this.findFirst(USER_EMAIL_KEYS, userValues).orElse(this.findFirst(LOGIN_OR_ID_KEY, userValues).get() + "@" + provider);
        User user = User.builder().name(name).email(email).build();
        // TODO: add token
        return userService.save(user);
    }

    private Optional<String> findFirst(String[] keys, HashMap values) {
        for (String key : keys) {
            if (values.containsKey(key) && values.get(key) != null) {
                return Optional.of((String) values.get(key));
            }
        }
        return Optional.empty();
    }

    /*
##### gihub:
tokenValues = {HashMap@12011}  size = 3
 0 = {HashMap$Node@12164} "access_token" -> "..."
 1 = {HashMap$Node@12165} "scope" -> "read:user"
 2 = {HashMap$Node@12166} "token_type" -> "bearer"

userValues = {HashMap@12012}  size = 37
 0 = {HashMap$Node@12058} "gists_url" -> "https://api.github.com/users/mwohlf/gists{/gist_id}"
 1 = {HashMap$Node@12059} "repos_url" -> "https://api.github.com/users/mwohlf/repos"
 2 = {HashMap$Node@12060} "two_factor_authentication" -> "false"
 3 = {HashMap$Node@12061} "following_url" -> "https://api.github.com/users/mwohlf/following{/other_user}"
 4 = {HashMap$Node@12062} "bio" -> "null"
 5 = {HashMap$Node@12063} "created_at" -> "2011-06-02T17:41:39Z"
 6 = {HashMap$Node@12064} "login" -> "mwohlf"
 7 = {HashMap$Node@12065} "type" -> "User"
 8 = {HashMap$Node@12066} "blog" ->
 9 = {HashMap$Node@12067} "private_gists" -> "0"
 10 = {HashMap$Node@12068} "total_private_repos" -> "0"
 11 = {HashMap$Node@12069} "subscriptions_url" -> "https://api.github.com/users/mwohlf/subscriptions"
 12 = {HashMap$Node@12070} "updated_at" -> "2017-10-29T19:22:27Z"
 13 = {HashMap$Node@12071} "site_admin" -> "false"
 14 = {HashMap$Node@12072} "disk_usage" -> "166148"
 15 = {HashMap$Node@12073} "collaborators" -> "0"
 16 = {HashMap$Node@12074} "company" -> "null"
 17 = {HashMap$Node@12075} "owned_private_repos" -> "0"
 18 = {HashMap$Node@12076} "id" -> "825865"
 19 = {HashMap$Node@12077} "public_repos" -> "14"
 20 = {HashMap$Node@12078} "gravatar_id" ->
 21 = {HashMap$Node@12079} "plan" -> " size = 4"
 22 = {HashMap$Node@12080} "email" -> "null"
 23 = {HashMap$Node@12081} "organizations_url" -> "https://api.github.com/users/mwohlf/orgs"
 24 = {HashMap$Node@12082} "hireable" -> "null"
 25 = {HashMap$Node@12083} "starred_url" -> "https://api.github.com/users/mwohlf/starred{/owner}{/repo}"
 26 = {HashMap$Node@12084} "followers_url" -> "https://api.github.com/users/mwohlf/followers"
 27 = {HashMap$Node@12085} "public_gists" -> "0"
 28 = {HashMap$Node@12086} "url" -> "https://api.github.com/users/mwohlf"
 29 = {HashMap$Node@12087} "received_events_url" -> "https://api.github.com/users/mwohlf/received_events"
 30 = {HashMap$Node@12088} "followers" -> "1"
 31 = {HashMap$Node@12089} "avatar_url" -> "https://avatars3.githubusercontent.com/u/825865?v=4"
 32 = {HashMap$Node@12090} "events_url" -> "https://api.github.com/users/mwohlf/events{/privacy}"
 33 = {HashMap$Node@12091} "html_url" -> "https://github.com/mwohlf"
 34 = {HashMap$Node@12092} "following" -> "0"
 35 = {HashMap$Node@12093} "name" -> "Michael Wohlfart"
 36 = {HashMap$Node@12094} "location" -> "null"




#### facebook:

tokenValues = {HashMap@12009}  size = 3
 0 = {HashMap$Node@12068} "access_token" -> "..."
 1 = {HashMap$Node@12069} "token_type" -> "bearer"
 2 = {HashMap$Node@12070} "expires_in" -> "5127475"
userValues = {HashMap@12010}  size = 2
 0 = {HashMap$Node@12058} "name" -> "Michael Wohlfart"
 1 = {HashMap$Node@12059} "id" -> "1884727795176026"


#### google:
tokenValues = {HashMap@12004}  size = 5
 0 = {HashMap$Node@12051} "access_token" -> "..."
 1 = {HashMap$Node@12052} "refresh_token" -> "1/...."
 2 = {HashMap$Node@12053} "id_token" -> "..-
 3 = {HashMap$Node@12054} "token_type" -> "Bearer"
 4 = {HashMap$Node@12055} "expires_in" -> "3600"
userValues = {HashMap@12005}  size = 10
 0 = {HashMap$Node@12068} "gender" -> "male"
 1 = {HashMap$Node@12069} "name" -> "Michael Wohlfart"
 2 = {HashMap$Node@12070} "link" -> "https://plus.google.com/+MichaelWohlfart"
 3 = {HashMap$Node@12071} "id" -> "103209724357383632506"
 4 = {HashMap$Node@12072} "verified_email" -> "true"
 5 = {HashMap$Node@12073} "given_name" -> "Michael"
 6 = {HashMap$Node@12074} "locale" -> "en"
 7 = {HashMap$Node@12075} "family_name" -> "Wohlfart"
 8 = {HashMap$Node@12076} "email" -> "mwhlfrt@gmail.com"
 9 = {HashMap$Node@12077} "picture" -> "https://lh3.googleusercontent.com/-dEOUb9n0PwM/AAAAAAAAAAI/AAAAAAAAAAA/-bkB2w-CJMc/photo.jpg"


     */


}
