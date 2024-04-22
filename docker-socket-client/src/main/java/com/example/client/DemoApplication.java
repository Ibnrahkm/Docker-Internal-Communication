package com.example.client;

import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .readTimeout(50000, TimeUnit.MILLISECONDS)
                        .build();

                Request request = new Request.Builder()
                        .url("ws://" + System.getenv("SERVER_CONTAINER_NAME_WITH_PORT") + "/websocket")
                        .build();
                client.newWebSocket(request, new WebSocketListener() {
                    @Override
                    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                        super.onClosed(webSocket, code, reason);
                    }

                    @Override
                    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                        super.onClosing(webSocket, code, reason);
                    }

                    @Override
                    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                        super.onFailure(webSocket, t, response);
                    }

                    @Override
                    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                        super.onMessage(webSocket, text);
                        System.out.println("message received:: " + text);
                    }

                    @Override
                    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                        super.onOpen(webSocket, response);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                webSocket.send("hello this is: " + new Date().toString());
                            }
                        }, 0, 1000);
                    }
                });

            }
        }).start();

        // Trigger shutdown of the dispatcher's executor so this process exits immediately.
        //  client.dispatcher().executorService().shutdown();
    }

}
