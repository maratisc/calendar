# Calendar

A calendar and scheduling application in Java. Presently only a portion of the requirements are addressed, i.e., given the meeting parameters provide the number of days the meeting occurs. Meeting parameters can be provided in a CSV file as in 
```
   # input.csv
   # start, end, day of week
   2018-05-04, 2018-12-31, Wednesday
   2019-01-01, 2019-12-31, Thursday
```
The parameters can also be provided as JSON string. Optionally, vacation and holiday dates can be provided to exclude counting meetings that fall on those dates. Please see ```IMeetingOccurrences.java``` and its implementations for further details.

## Requirements and Installation
JDK 8 or higher is required. The project can be set up as a Java Maven project in Eclipse IDE.

## Future Enhancements
User set up, authentication, authorization, online availability, accessibility from mobile devices and the features below:
```
Enter meeting parameters in a form online
Select dates from a UI calendar widget
Select meeting days in a week from check-boxes
Select start time and duration of meeting
Select invitees from a lookup
Show invitees conflicts and allow editing and re-submission
Submission sends meeting to invitees
Invitees can accept or reject a meeting 
Invitees see meeting in their local time zone
Display time adjusted for day light savings time where applicable
Cancel a meeting
```
