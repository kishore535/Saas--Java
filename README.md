## Software as a Service: Google Cloud Vision API using Java

# Saas--Java
#Application
Application Url: https://gaeproject-276401.appspot.com/
About Application: Our application firstly asks user to upload an image on server. After uploading the image, Google Cloud Vision API is invoked for analysing the image to detect labels. And finally the results are returned in well formatted table containing detected labels of the image. Our application is basically divided into 3 parts: Frontend, GAE and Backend
State of application: Application is successfully implemented and is in fully working condition.

#Explanation

Front-end
We have created two simple HTML forms. One form is used by the user for uploading the image and second form is used for returning results in table format. As mentioned, we used HTML and it's functionalities for creating both the forms. The image upload form looks like:

https://github.com/kishore535/Saas--Java/raw/master/Images/saas9.PNG
Server -side
Two server side files:-
1. index.html file :
This file implements the part of the application which displays the image browse and image upload buttons to the user. It makes user to easily select and upload the image of his/her choice.
https://github.com/kishore535/Saas--Java/raw/master/Images/saas7.PNG

2. upload.java file:
This java servlet converts the image into byte code and then points to the Google Cloud Vision API to fetch the info about the image uploaded.

https://github.com/kishore535/Saas--Java/raw/master/Images/saas8.PNG

GAE
For downloading and setting up Google Cloud Vision Client Library, we followed the instructions provided here - Google Documentation

https://github.com/kishore535/Saas--Java/raw/master/Images/saas1.PNG
https://github.com/kishore535/Saas--Java/raw/master/Images/saas2.PNG
https://github.com/kishore535/Saas--Java/raw/master/Images/saas3.PNG
https://github.com/kishore535/Saas--Java/raw/master/Images/saas4.PNG
https://github.com/kishore535/Saas--Java/raw/master/Images/saas5.PNG
https://github.com/kishore535/Saas--Java/raw/master/Images/saas6.PNG

#STATUS OF APPLICATION :

The application is successfully implemented and it is in fully working condition.
ISSUES :
Intially met with little ambuguity of setting up the Google cloud Vision API but it was very soon resolved after working on it.
Encountered a problem getting the output for the image uploaded. Then tried resolving the base problem of it by giving the path of image in static way.
Later worked on the the actual working of application by adding the dynamic behavior to it.
