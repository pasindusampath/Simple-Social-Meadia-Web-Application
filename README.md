# Lab 6

## Scenario

Code&Code is a small company dedicated to Software Development. Their engineering team, to which you belong, is working on writing a Web Application as an MVP for a new customer.

The code name for this App is “Loggy”, which is meant to offer functionality for a personal journal where users can log their daily activities through text, voice and video.

The first step will be to write the main functionality, which is essentially a Micro-blogging System where all the posts are automatically annotated with voice, video or text.

As part of the first iteration you have to create a web application that will be the foundation for the Microblogging System under the following assumptions:

a. Users submit their logging activity through a web page to a single thread in the same way Twitter users submit their posts. A form containing an input box and submit button is shown at the top and under that, a list with all the previous submissions ordered by the timestamp in descending order.

b. A short title (60chars) and a short content description (120 chars) is required. After that, the user attaches the actual content, which can be a picture, audio, video or text file.

c. The file is processed in the server and a thumbnail is shown right below the description.


## Tasks

### MVC Pattern

The application renders HTML directly from the Servlet. While this approach was good enough for a quick prototype, it would not scale.

1. Apply the MVC pattern by templating the View using JSP pages.

2. Customize the CSS styling with a design of your choice.


**Remember that although the scenario and resulting model may be used for future activities, the main goal is to practice what you have learned in this module, so do not be worried about finding the perfect solution for this case. And keep in mind that System.out.println() will be enough for the purposes of illustrating your model**.
