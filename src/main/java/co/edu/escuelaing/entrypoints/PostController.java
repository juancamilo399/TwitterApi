package co.edu.escuelaing.entrypoints;

import co.edu.escuelaing.entrypoints.utils.HandlerUtils;
import co.edu.escuelaing.post.domain.model.Post;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HashMap<Long, Post> memory = new HashMap<>();


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        LambdaLogger logger = context.getLogger();
        Method[] methods = this.getClass().getMethods();

        //Handle to the correct method
        for (Method method : methods) {
            if (method.isAnnotationPresent(Route.class)) {
                Route route = method.getAnnotation(Route.class);
                if (matchMethod(route, event)) {
                    try {
                        return (APIGatewayProxyResponseEvent) method.invoke(this, event, context);
                    } catch (Exception e) {
                        logger.log(e.getMessage());
                    }
                }
            }
        }
        return mapApiGatewayResponse(400, "not found");

    }

    private boolean matchMethod(Route route, APIGatewayProxyRequestEvent event) {
        return HandlerUtils.matchMethod(route, event);
    }

    @Route(method = "GET", path = "/posts")
    public APIGatewayProxyResponseEvent getPosts(APIGatewayProxyRequestEvent event, Context context) throws JsonProcessingException {
        List<Post> users = new ArrayList<>(memory.values());
        return mapApiGatewayResponse(200, objectMapper.writeValueAsString(users));
    }

    @Route(method = "POST", path = "/posts")
    public APIGatewayProxyResponseEvent saveUser(APIGatewayProxyRequestEvent event, Context context) throws JsonProcessingException {
        Post post = objectMapper.readValue(event.getBody(), Post.class);
        memory.put(post.getId(),post);
        return mapApiGatewayResponse(200,objectMapper.writeValueAsString(post));
    }

    private APIGatewayProxyResponseEvent mapApiGatewayResponse(int statusCode, String body) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return new APIGatewayProxyResponseEvent()
                .withBody(body)
                .withIsBase64Encoded(false)
                .withStatusCode(statusCode)
                .withHeaders(headers);
    }

}
