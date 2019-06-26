package fraktikant.tflcstefan.hybrit.app.util;

import fraktikant.tflcstefan.hybrit.app.entity.NotificationType;
import fraktikant.tflcstefan.hybrit.app.web.dto.JobDTO;
import fraktikant.tflcstefan.hybrit.app.web.dto.NotificationDTO;

import java.util.List;

public class Builders {

    public static String cronBuilder(NotificationDTO notificationDTO) {

        String cron = null;

        Integer minutes = notificationDTO.getNotificationTime().getMinute();
        Integer hours = notificationDTO.getNotificationTime().getHour();

        if(notificationDTO.getNotificationType() == NotificationType.DAILY){
            cron = "0 " + minutes + " " + hours + " ? * SUN,MON,TUE,WED,THU,FRI,SAT";
        } else if(notificationDTO.getNotificationType() == NotificationType.WEEKLY){
            cron = "0 " + minutes + " " + hours + " ? * WED";
        } else if(notificationDTO.getNotificationType() == NotificationType.MONTHLY){
            cron = "0 " + minutes + " " + hours + " 15 JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC ?";
        } else {
            cron = "0 0 10 ? * SUN,MON,TUE,WED,THU,FRI,SAT";
        }
        return cron;
    }

    public static String newJobsMailBody(List<JobDTO> jobs) {

        String body = "<table border=\"1\">" +
                        "<tr>" +
                            "<th>Title</th>" +
                            "<th>Job Type</th>" +
                            "<th>Company</th>" +
                            "<th>Location</th>" +
                            "<th>How to apply</th>" +
                        "</tr>";

        //for (int i = 0; i < jobs.size(); i++) {
        //    body += jobs.get(i).toTableRow();
        //}

        //java.lang.ClassCastException: java.util.LinkedHashMap
        // cannot be cast to
        // fraktikant.tflcstefan.hybrit.app.web.dto.JobDTO

        //body += jobs.get(0).toTableRow();

        body += "</table>";

        System.out.println(body);
        return body;
    }
}
