# Calendar

A calendar and scheduling application in Java. Only a portion of it is implemented here. Specifically, the implementation covers counting how many days there are in a meeting. Meeting parameters can be provided in a csv file as in 
```
   # input.csv
   # start, end, day of week
   2018-05-02, 2018-12-31, Wednesday
   2019-01-01, 2019-12-31, Thursday
```
Optionally, vacation and holiday dates can be provided to exclude counting meetings that fall on those dates. Please see ```IMeetingOccurrences.java``` and its implementations for further details on where to place this and the meeting parameters file.

## Requirements and Installation
JDK 8 or higher is required. The project can be set up as a Java project in Eclise IDE.

## Future Enhancements
User set up, authentication, authorization, online availability, accessibility from mobile devices and the features below:
```
Enter parameters in a form
Select dates from a UI calendar widget
Select meeting days from checkboxes
Select start time and duration of meeting
Select invitees from data base / repository
Show invitees conflicts and allow editing and re-submission of the meeting request
Meeting invitation should be sent to invitees in their time zone
Display time adjusted for day light savings time where applicable
Cancel a meeting
```
