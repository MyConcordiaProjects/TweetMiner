package controllers;

import com.google.inject.Singleton;

import play.mvc.Controller;
import actors.MainActor;
import play.libs.F;
import play.libs.streams.ActorFlow;
import akka.actor.*;
import akka.stream.*;
import play.mvc.*;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;

//@Singleton
public class MainController extends Controller {
	private final Logger logger = org.slf4j.LoggerFactory.getLogger("controllers.MainController");

    // Websocket interface
    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public MainController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public WebSocket socket() {

        return WebSocket.Text.acceptOrResult(request -> {
            if (sameOriginCheck(request)) {
                return CompletableFuture.completedFuture(
                        F.Either.Right(ActorFlow.actorRef(MainActor::props,
                                actorSystem, materializer)));
            } else {
                return CompletableFuture.completedFuture(F.Either.Left(forbidden()));
            }
        });
    }

   
    private boolean sameOriginCheck(Http.RequestHeader rh) {
        final Optional<String> origin = rh.header("Origin");

        if (! origin.isPresent()) {
            logger.error("originCheck: rejecting request because no Origin header found");
            return false;
        } else if (originMatches(origin.get())) {
            logger.debug("originCheck: originValue = " + origin);
            return true;
        } else {
            logger.error("originCheck: rejecting request because Origin header value " + origin + " is not in the same origin");
            return false;
        }
    }

    private boolean originMatches(String origin) {
        return origin.contains("localhost:9000") || origin.contains("localhost:19001");
    }
}
