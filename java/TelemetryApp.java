package com.telemetryserver.client;


import com.telemetryserver.Instrumentation.*;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class TelemetryApp
{
    public static void main(String[] args)
    {
        startTestServer(2018);
    }


    public static void startTestServer(int port)
    {
        try
        {
            Server server = new Server(port);
            ServletContextHandler context = new ServletContextHandler();
            context.setContextPath("/");
            server.setHandler(context);

            //Expose our Instrumented servlet.
            context.addServlet(new ServletHolder(Instrumented_Class.getInstance()), "/");

            //Prometheus Metrics Servlet
            context.addServlet(new ServletHolder(new MetricsServlet()), "/metrics");

            // Add metrics about CPU, JVM memory etc.
            //DefaultExports.initialize();

            // Start the webserver.
            server.start();
            server.join();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
 }
