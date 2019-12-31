import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrawlerTwitter {

    private List<String> connectionName, consumerKey, consumerSecret, accessToken,
            accessTokenSecret, boundingBox, searchWord, pathFiles;

    public CrawlerTwitter() throws FileNotFoundException, IOException, Exception {
        executar();
    }

    public void executar() throws IOException, Exception {
        configConnectionName();
        configConsumerKey();
        configConsumerSecret();
        configAccessToken();
        configAccessTokenSecret();
        configBoundingBox();
        configPathFiles();
        configSearchWord();
        createPoolConnections();
    }

    private BufferedReader getArquivoLeitura() throws FileNotFoundException {
        File fileConfig = new File("arquivoConfig.txt");
        FileReader fileReader = new FileReader(fileConfig);
        return new BufferedReader(fileReader);
    }

    private void createPoolConnections() throws Exception {
        for (int i = 0; i < this.accessToken.size(); i++) {
            new TwitterApiScrap(connectionName.get(i), consumerKey.get(i),
                    consumerSecret.get(i), accessToken.get(i),
                    accessTokenSecret.get(i), searchWord.get(i), boundingBox.get(i), pathFiles.get(i)).run();
        }
    }

    private void configAccessToken() throws IOException {
        this.accessToken = new ArrayList<>();
        BufferedReader br = this.getArquivoLeitura();
        while (br.ready()) {
            String a = br.readLine();
            if (a.contains("connection.access_token=")) {
                this.accessToken.add(a.substring(24).trim());
            }
        }
        br.close();
    }

    private void configAccessTokenSecret() throws IOException {
        this.accessTokenSecret = new ArrayList<>();
        BufferedReader br = this.getArquivoLeitura();
        while (br.ready()) {
            String a = br.readLine();
            if (a.contains("connection.access_token_secret")) {
                this.accessTokenSecret.add(a.substring(31).trim());
            }
        }
        br.close();
    }

    private void configConnectionName() throws IOException {
        this.connectionName = new ArrayList<>();
        BufferedReader br = this.getArquivoLeitura();
        while (br.ready()) {
            String a = br.readLine();
            if (a.contains("connection.user")) {
                this.connectionName.add(a.substring(16).trim());
            }
        }
        br.close();
    }

    private void configSearchWord() throws IOException {
        this.searchWord = new ArrayList<>();
        BufferedReader br = this.getArquivoLeitura();
        while (br.ready()) {
            String a = br.readLine();
            if (a.contains("connection.search_word")) {
                this.searchWord.add(a.substring(23));
            }
        }
        br.close();
    }

    private void configConsumerKey() throws IOException {
        this.consumerKey = new ArrayList<>();
        BufferedReader br = this.getArquivoLeitura();
        while (br.ready()) {
            String a = br.readLine();
            if (a.contains("connection.consumer_key")) {
                this.consumerKey.add(a.substring(24).trim());
            }
        }
        br.close();
    }

    private void configConsumerSecret() throws IOException {
        this.consumerSecret = new ArrayList<>();
        BufferedReader br = this.getArquivoLeitura();
        while (br.ready()) {
            String a = br.readLine();
            if (a.contains("connection.consumer_secret")) {
                this.consumerSecret.add(a.substring(27).trim());
            }
        }
        br.close();
    }

    private void configBoundingBox() throws IOException {
        this.boundingBox = new ArrayList<>();
        BufferedReader br = this.getArquivoLeitura();
        while (br.ready()) {
            String a = br.readLine();
            if (a.contains("connection.bounding_box")) {
                this.boundingBox.add(a.substring(24).trim());
            }
        }
        br.close();
    }

    private void configPathFiles() throws IOException {
        this.pathFiles = new ArrayList<>();
        BufferedReader br = this.getArquivoLeitura();
        while (br.ready()) {
            String a = br.readLine();
            if (a.contains("path.save.data")) {
                this.pathFiles.add(a.substring(15).trim());
            }
        }
        br.close();
    }
}
