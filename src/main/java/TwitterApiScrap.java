
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterApiScrap implements Runnable {

    private String connectionName, consumerKey, consumerSecret,
            accessToken, accessTokenSecret, boundingBox, searchWord, pathFiles;
    private FilterQuery filtre;
    private ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    boolean bb = false, sw = false;
    TwitterStream twitterStream;

    public TwitterApiScrap(String connectionName, String consumerKey, String consumerSecret, String accessToken,
            String accessTokenSecret, String searchWord, String boundingBox, String pathFiles) {
        this.connectionName = connectionName;
        this.accessTokenSecret = accessTokenSecret;
        this.accessToken = accessToken;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.searchWord = searchWord;
        this.boundingBox = boundingBox;
        this.pathFiles = pathFiles;
        this.filtre = new FilterQuery();
    }

    @Override
    public void run() {
        configurationBuilder.setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
        twitterStream.addListener(new Listener(this.pathFiles, this.connectionName));
        configFilterWords();
        configFilterBB();
        callApi();
    }

    private void callApi() {
        if (this.bb || this.sw) {
            twitterStream.filter(this.filtre);
        } else {
            twitterStream.sample();
        }
    }

    private void configFilterWords() {
        if (this.searchWord.length() > 0) {
            this.filtre.track(this.searchWord.split(","));
            this.sw = true;
        }
    }

    private void configFilterBB() {
        if (this.boundingBox.length() > 0) {
            if (this.boundingBox.split(",").length > 3) {
                String[] cordenadas = this.boundingBox.split(",");
                try {
                    double[][] cods = {{Double.parseDouble(cordenadas[0].trim()), Double.parseDouble(cordenadas[1].trim())},
                    {Double.parseDouble(cordenadas[2].trim()), Double.parseDouble(cordenadas[3].trim())}};
                    this.filtre.locations(cods);
                } catch (Exception e) {
                    System.out.println("Filtro de Bounding Box está incorreto!!");;
                    return;
                }
                this.bb = true;
            }
        }
    }

}
