package co.edu.escuelaing.entrypoints;

import co.edu.escuelaing.entrypoints.utils.HandlerUtils;
import co.edu.escuelaing.user.domain.model.User;
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


public class UserController implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HashMap<Long, User> memory = new HashMap<>();


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

    @Route(method = "GET", path = "/users")
    public APIGatewayProxyResponseEvent getUsers(APIGatewayProxyRequestEvent event, Context context) throws JsonProcessingException {
        List<User> users = new ArrayList<>(memory.values());
        return mapApiGatewayResponse(200, objectMapper.writeValueAsString(users));
    }

    @Route(method = "GET", path = "/users/{userId}")
    public APIGatewayProxyResponseEvent getUser(APIGatewayProxyRequestEvent event, Context context) throws JsonProcessingException {
        long userId = Long.parseLong(event.getPathParameters().get("userId"));
        return mapApiGatewayResponse(200, objectMapper.writeValueAsString(memory.get(userId)));
    }

    @Route(method = "POST", path = "/users")
    public APIGatewayProxyResponseEvent saveUser(APIGatewayProxyRequestEvent event, Context context) throws JsonProcessingException {
        User user = objectMapper.readValue(event.getBody(), User.class);
        memory.put(user.getUserId(),user);
        return mapApiGatewayResponse(200,objectMapper.writeValueAsString(user));
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
