# MultiThreadExample


This project is very basic sample that shows how to write multi-threaded codes using Java in Android. 

It is really important to know how to use multiple threads; especially when you develop multi-threading architecture, such as multimedia and network. 

This project provides complete control over multithreaded program, which can be be suspended, resumed, or stopped completely based on your events.


## What is Multithreading?


The process of executing multiple threads simultaneously is known as multithreading.





## Why is it important?


Multithreaded programs contain two or more threads that can run concurrently and each thread defines a separate path of execution. This means that a single program can perform two or more tasks simultaneously. For example, one thread is writing content on a file at the same time another thread is performing spelling check.

Basically, when you develop a multithreaded program (a program that deals with multiple threads), you have to controll threads well to execute which one executes first and which one executes right after.  




## References that are helpful for me to understand the concept of multi-threading


[Java Multithreading Steeplechase: Stopping Threads](https://10kloc.wordpress.com/2013/03/03/java-multithreading-steeplechase-stopping-threads/)

[Multithreading in java with examples](http://beginnersbook.com/2013/03/multithreading-in-java/)

[Java Thread: notify() and wait() examples](http://www.programcreek.com/2009/02/notify-and-wait-example/)



## Structure of this Project (Screenshot of .xml below)

![xml_preview](https://user-images.githubusercontent.com/20435620/29009109-40bb975c-7b5b-11e7-9585-27b23e4dfcb2.PNG)


There are two threads (Thread A and Thread B). 
Each thread has its own while loop that counts numbers infinitely.

There are a total number of "9 buttons" that are very much self-explanatory if you take a look at the .xml file.

#1 Button: START ALL

#2 Button: PAUSE THREAD A

#3 Button: RESUME THREAD A

#4 Button: STOP THREAD A

#5 Button: PAUSE THREAD B 

#6 Button: RESUME THREAD B

#7 Button:  STOP THREAD B

#8 Button: RESTART ALL

#9 Button: PAUSE ALL


**Clone it and check it out! I hope this helps you to know how to use threads in Android**


## LICENSE

```

The MIT License (MIT)

Copyright (c) 2016 Tae-hwan

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

```
