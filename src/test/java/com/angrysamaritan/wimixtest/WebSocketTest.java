package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repos.UserRepo;
import com.angrysamaritan.wimixtest.service.JWTService;
import com.angrysamaritan.wimixtest.service.NotificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.concurrent.TimeUnit.SECONDS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class WebSocketTest {

    static final String WEBSOCKET_URI = "http://localhost:8080/websocket";
    static final String WEBSOCKET_TOPIC = "/topic/notifications";

    BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();
    private WebSocketStompClient stompClient;
    private String token;
    private User user;


    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepo userRepo;

    @Before
    public void prepare() {
        addUser();
        generateToken();
        stompClient = new WebSocketStompClient(new SockJsClient(
                Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    public void generateToken() {
        token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), new ArrayList<>()));
    }

    public void addUser() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("test@mail.test");
        user.setUsername("testUsername");
        user.getProfile().setFirstName("A");
        user.getProfile().setLastName("A");
        user.setPassword(encoder.encode("12345"));
        user = userRepo.save(user);
    }

    @Autowired
    private NotificationService notificationService;

    @Test
    public void testSubscribe() throws ExecutionException, InterruptedException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        StompSession stompSession = stompClient.connect(WEBSOCKET_URI, new WebSocketHttpHeaders(httpHeaders),
                new StompSessionHandlerAdapter(){}).get();

        stompSession.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        stompSession.send("/app/echo", "Lox".getBytes());

        Assert.assertEquals("Test", blockingQueue.poll(3, SECONDS));
        Assert.assertEquals("Test2", blockingQueue.poll(3, SECONDS));
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + new String((byte[]) o));
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}
