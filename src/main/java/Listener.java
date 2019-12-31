import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class Listener implements StatusListener {

    private String path;
    private String nomeConexao;
    private FileWriter fw;

    public Listener(String path, String nomeConexao) {
        this.path = path;
        this.nomeConexao = nomeConexao;
        configFile();
    }

    private void configFile() {
        try {
            File arquivo = new File(this.path);
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            fw = new FileWriter(arquivo, true);
            fw.write(this.nomeConexao + "\n\n\n");
        } catch (IOException ex) {
            System.out.println("Caminho inválido!!");;
        }
    }

    @Override
    public void onStatus(Status status) {
        try {
            fw.write(status.getText() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
    }

    @Override
    public void onTrackLimitationNotice(int i) {
    }

    @Override
    public void onScrubGeo(long l, long l1) {
    }

    @Override
    public void onStallWarning(StallWarning sw) {
    }

    @Override
    public void onException(Exception excptn) {
        excptn.printStackTrace(); //To change body of generated methods, choose Tools | Templates.
    }

}
