package co.edu.escuelaing.entrypoints.utils;

import co.edu.escuelaing.entrypoints.Route;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlerUtils {
    public static boolean matchMethod(Route route, APIGatewayProxyRequestEvent event) {
        String routePath = route.path();
        String routeMethod = route.method();

        String httpMethod = event.getHttpMethod();
        String resourcePath = event.getPath();

        if (httpMethod.equals(routeMethod)) {
            Pattern pattern = Pattern.compile("^" + routePath.replaceAll("\\{\\w+\\}", "(\\\\w+)") + "$");
            Matcher matcher = pattern.matcher(resourcePath);
            return matcher.matches();
        }
        return false;
    }
}
