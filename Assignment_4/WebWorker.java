import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebWorker extends Thread {
    private String urlString;
    private WebFrame frame;
    private int index;
    private Semaphore semaphore;
    private int bytes;
    private long startTime;
    private String stat;
    private long endTime;

    public WebWorker(String urlString, int index, WebFrame frame, Semaphore semaphore){
        this.urlString = urlString;
        this.frame = frame;
        this.index = index;
        this.semaphore = semaphore;
        stat = "";
        bytes = 0;
    }
    @Override
    public void run(){
        frame.incrementRun();
        startTime = System.currentTimeMillis();
        download();
        endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        if(stat.isEmpty()) {
            stat = createStatus(timeElapsed);
        }
        frame.stopWorking(index, stat);
    }

    private String createStatus(long elapsedTime){
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        String result = formattedDate + " ";
        result += elapsedTime + "ms ";
        result += Integer.toString(bytes) + " bytes";
        return result;
    }

    void download() {
        InputStream input = null;
        StringBuilder contents = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            // Set connect() to throw an IOException
            // if connection does not succeed in this many msecs.
            connection.setConnectTimeout(5000);

            connection.connect();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            char[] array = new char[1000];
            int len;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                contents.append(array, 0, len);
                bytes += len;
                Thread.sleep(100);
            }
        }
        // Otherwise control jumps to a catch...
        catch (MalformedURLException ignored) {
            bytes = 0;
            stat = "err";
        } catch (InterruptedException exception) {
            bytes = 0;
            stat = "interrupted";
        } catch (IOException ignored) {
            stat = "err";
            bytes = 0;
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {
            }
        }
    }
}
