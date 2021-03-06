package com.amdelamar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.amdelamar.action.RedirectAction;
import com.amdelamar.config.AppConfig;
import com.amdelamar.config.Application;
import com.amdelamar.config.Utils;
import com.amdelamar.database.CouchDb;
import com.amdelamar.database.CouchDbSetup;
import com.amdelamar.database.Database;
import io.vertx.core.net.JksOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Single;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.CompositeFuture;
import io.vertx.reactivex.core.Future;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.core.net.SelfSignedCertificate;
import io.vertx.reactivex.ext.web.Router;

/**
 * OddoxVerticle for Vertx
 *
 * @author amdelamar
 * @date 05/28/2018
 */
public class OddoxVerticle extends AbstractVerticle {

    // Global vals
    public final static String VERSION = "0.1.0";
    public final static int PORT = 8080;
    public final static int HTTPS_PORT = 8443;
    public final static String APP_PROP_FILE = "/app.properties";
    public final static String DB_PROP_FILE = "/db.properties";
    public final static String TEMPLATES_DIR = System.getProperty("user.dir") + "/webroot/templates/";

    // Internal vars
    private static int httpPort = PORT;
    private static int httpsPort = HTTPS_PORT;
    private static boolean httpsEnabled = false;
    private static HttpServer httpServer;
    private static Logger logger = LoggerFactory.getLogger(OddoxVerticle.class);

    public static void main(String[] args) {
        // Vertx core
        final Vertx vertx = Vertx.vertx();

        // Deploy Verticle        
        final Single<String> deployment = vertx.rxDeployVerticle(OddoxVerticle.class.getName());

        // Observe deploy
        deployment.subscribe(successResult -> {
            logger.trace("Verticle Deployed: " + successResult);
        }, errorResult -> {
            logger.error("FATAL: Deploy failed.", errorResult);
        });
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void start(io.vertx.core.Future<Void> startFuture) throws Exception {

        logger.info("Starting\r\n" + "           _     _           \r\n" + "          | |   | |          \r\n"
                + "  ___   __| | __| | ___  __  __\r\n" + " / _ \\ / _` |/ _` |/ _ \\ \\ \\/ /\r\n"
                + "| (_) | (_) | (_) | (_) | >  < \r\n" + " \\___/ \\____|\\____|\\___/ /_/\\_\\.org (v" + VERSION
                + ")\r\n" + "-----------------------------------------------");

        try {
            // HTTPS_ENABLED env check
            httpsEnabled = Boolean.parseBoolean(System.getenv("HTTPS_ENABLED"));
        } catch (Exception e) {
            httpsEnabled = false;
        }

        boolean httpRedirectEnabled = false;
        try {
            // HTTP_REDIRECT_ENABLED env check
            httpRedirectEnabled = Boolean.parseBoolean(System.getenv("HTTP_REDIRECT_ENABLED"));
        } catch (Exception e) {
        }

        // Check all prerequisites
        final ArrayList<Future> futures = new ArrayList<Future>();
        futures.addAll(Arrays.asList(checkWebroot(), loadSettings(), loadDatabase()));

        if (httpsEnabled) {
            futures.add(startHttpsServer());
        } else {
            futures.add(startHttpServer());
        }

        if (httpRedirectEnabled) {
            futures.add(startHttpRedirectServer());
        }

        // await all futures
        CompositeFuture.all(futures)
                .setHandler(ar -> {
                    if (ar.succeeded()) {
                        // All futures succeeded
                        logger.info("Oddox is ready to serve traffic.");

                        // Set OkHttpClient logging level
                        // java.util.logging.Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);

                        startFuture.complete();
                    } else {
                        // At least one future failed
                        startFuture.fail(ar.cause());
                        vertx.close();
                    }
                });
    }

    @Override
    public void stop(io.vertx.core.Future<Void> stopFuture) throws Exception {
        if (httpsEnabled) {
            logger.info("Stopped listening on ports: " + httpPort + ", " + httpsPort);
        } else {
            logger.info("Stopped listening on port: " + httpPort);
        }
        stopFuture.complete();
    }

    /**
     * Check if Webroot exists
     *
     * @return Future
     */
    @SuppressWarnings("rawtypes")
    private Future checkWebroot() {
        final Future future = Future.future();

        File webroot = new File(System.getProperty("user.dir") + "/webroot");
        if (!webroot.exists() || !webroot.isDirectory()) {
            logger.error("Webroot is not found or can't read. Expected it here: (user.dir) "
                    + System.getProperty("user.dir"));
            future.fail("Webroot is not found or invalid");
        } else {
            future.complete();
        }
        return future;
    }

    @SuppressWarnings("rawtypes")
    private Future startHttpRedirectServer() {
        final Future future = Future.future();

        try {
            // PORT env check
            httpPort = Integer.parseInt(System.getenv("PORT"));
        } catch (Exception e) {
            logger.warn("Env PORT not found or not valid. Defautling to: " + PORT);
            httpPort = PORT;
        }

        // Create HTTP server
        httpServer = vertx.createHttpServer(new HttpServerOptions().setLogActivity(true));

        // Redirect HTTP requests to HTTPS
        httpServer.requestHandler(new RedirectAction());

        // Start listening
        httpServer.listen(httpPort, asyncResult -> {
            if (asyncResult.succeeded()) {
                logger.info("Listening on port: " + httpPort);
                future.complete();
            } else {
                logger.error("Failed to bind on port " + httpPort + ". Is it being used?");
                future.fail(asyncResult.cause());
            }
        });
        return future;
    }

    @SuppressWarnings("rawtypes")
    private Future startHttpServer() {
        final Future future = Future.future();

        try {
            // PORT env check
            httpPort = Integer.parseInt(System.getenv("PORT"));
        } catch (Exception e) {
            logger.warn("Env PORT not found or not valid. Defautling to: " + PORT);
            httpPort = PORT;
        }

        // Create HTTP server
        httpServer = vertx.createHttpServer(new HttpServerOptions().setLogActivity(true));

        // Map Web Routes
        Router mainRouter = WebRoutes.newRouter(vertx);

        // Set Router
        httpServer.requestHandler(mainRouter::accept);

        // Start listening
        httpServer.listen(httpPort, asyncResult -> {
            if (asyncResult.succeeded()) {
                logger.info("Listening on port: " + httpPort);
                future.complete();
            } else {
                logger.error("Failed to bind on port " + httpPort + ". Is it being used?");
                future.fail(asyncResult.cause());
            }
        });
        return future;
    }

    @SuppressWarnings("rawtypes")
    private Future startHttpsServer() {
        final Future future = Future.future();

        try {
            // HTTPS_PORT env check
            httpsPort = Integer.parseInt(System.getenv("HTTPS_PORT"));
        } catch (Exception e) {
            logger.warn("Env HTTPS_PORT not found or not valid. Defautling to: " + HTTPS_PORT);
            httpsPort = HTTPS_PORT;
        }

        Boolean http2Enabled = false;
        try {
            // HTTP2_ENABLED env check
            http2Enabled = Boolean.parseBoolean(System.getenv("HTTP2_ENABLED"));
        } catch (Exception e) {
            http2Enabled = false;
        }

        // HTTPS_KEYSTORE env check
        String httpsKeystore = System.getenv("HTTPS_KEYSTORE");
        String httpsKeystorePassword = System.getenv("HTTPS_KEYSTORE_PASSWORD");

        // Check if provided keystore and password
        HttpServer httpsServer;
        if (httpsKeystore != null &&
                !httpsKeystore.isEmpty() &&
                httpsKeystorePassword != null &&
                !httpsKeystorePassword.isEmpty()) {
            // Use Keystore provided for https

            // Create HTTPS server
            httpsServer = vertx.createHttpServer(new HttpServerOptions().setLogActivity(true)
                    .setUseAlpn(http2Enabled) // HTTP/2 only supported on JDK 9+
                    .setSsl(true)
                    .setKeyStoreOptions(
                            new JksOptions().setPassword(httpsKeystorePassword)
                                    .setPath(System.getProperty("user.dir") + httpsKeystore)));
        } else {
            // Generate the certificate for https
            SelfSignedCertificate cert = SelfSignedCertificate.create();

            // Create HTTPS server
            httpsServer = vertx.createHttpServer(new HttpServerOptions().setLogActivity(true)
                    .setUseAlpn(http2Enabled) // HTTP/2 only supported on JDK 9+
                    .setSsl(true)
                    .setKeyCertOptions(cert.keyCertOptions()));
        }

        // Map Web Routes
        Router mainRouter = WebRoutes.newRouter(vertx);

        // Set Router
        httpsServer.requestHandler(mainRouter::accept);

        httpsServer.listen(httpsPort, asyncResult -> {
            if (asyncResult.succeeded()) {
                logger.info("Listening on port: " + httpsPort);
                future.complete();
            } else {
                logger.error("Failed to bind on port " + httpsPort + ". Is it being used?");
                future.fail(asyncResult.cause());
            }
        });
        return future;
    }

    @SuppressWarnings("rawtypes")
    private Future loadSettings() {
        final Future future = Future.future();
        try {
            // Load settings from File
            Application.setAppConfig(Application.loadSettingsFromFile(APP_PROP_FILE));
            future.complete();
        } catch (Exception e) {
            logger.error("FATAL: Properties file not found or failed to load properly.");
            future.fail(e.getMessage());
        }
        return future;
    }

    @SuppressWarnings("rawtypes")
    private Future loadDatabase() {
        final Future future = Future.future();
        try {
            // Setup Database
            final Database db = Application.loadDatabase(System.getenv(), DB_PROP_FILE);

            // cleanup url
            db.setUrl(Utils.removeUserPassFromURL(db.getUrl()));

            logger.info("Using Database:\r\n" + db.toString());
            Application.setDatabaseSetup(new CouchDbSetup(db));

            if (!Application.getDatabaseSetup()
                    .setup()) {
                future.fail("Database invalid");
            }

            // Load settings from Database
            Application.setDatabaseService(new CouchDb(db));
            Application.setAppFirewall(Application.getDatabaseService()
                    .getAppFirewall());
            Application.setAppHeaders(Application.getDatabaseService()
                    .getAppHeaders());

            final AppConfig configdb = Application.getDatabaseService()
                    .getAppConfig();
            if (configdb != null) {
                logger.info("Found app settings in the database. Using that instead of " + APP_PROP_FILE);
                final AppConfig appConfig = Application.getAppConfig();
                appConfig.getSettings()
                        .putAll(configdb.getSettings());
                Application.setAppConfig(appConfig);
            }
            future.complete();
        } catch (Exception e) {
            logger.error("FATAL: Database not found or failed to load properly.");
            future.fail(e.getMessage());
        }
        return future;
    }
}
