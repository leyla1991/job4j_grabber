package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

public class AlertRabbit {


    public static void main(String[] args) {
        try (Connection connection = init()){
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            int timePrint = Integer.parseInt(rabbitProperties().getProperty("rabbit.interval"));
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(timePrint)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection init() throws ClassNotFoundException, IOException, SQLException {
        Class.forName(rabbitProperties().getProperty("driver"));
        String url = rabbitProperties().getProperty("url");
        String login = rabbitProperties().getProperty("login");
        String pass = rabbitProperties().getProperty("password");
        Connection connection = DriverManager.getConnection(url, login, pass);
        return connection;
    }

    public static Properties rabbitProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            properties.load(in);
        }
        return properties;
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            Connection connection =(Connection) context.getJobDetail().getJobDataMap().get("connection");
           try (PreparedStatement preparedStatement = init().prepareStatement("insert into rabbit(created_date) values (?);",
                    Statement.RETURN_GENERATED_KEYS)) {
               preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
               preparedStatement.execute();
            } catch (SQLException e) {
               throw new RuntimeException(e);
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
        }
    }
}
