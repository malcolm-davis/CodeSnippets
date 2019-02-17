package code.undertow.wsorchestrator;


public interface WorkerListener {

    void onFileReceived(String payloadText);

    void onMessageReceived(String text);

}
