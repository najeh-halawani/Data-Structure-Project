# Project Title: Garage Center
## Team Members: Najeh Halawani - Mustafa Khodor - Ralph Antoun

-  First of all, we used a Doubly Linked List instead of a singly linked list or other data 
structures because it allows us to iterate in both directions which facilitates the 
traversal between cars and has a dynamic size. 

- Secondly, we used a Binary Search Tree for its efficiency in searching and inserting 
the data in the nodes in a sorted way with low time complexity. Therefore, we used it 
to create a hierarchy between the customers of the garage according to the amount 
of money spent at the garage. 

-  Then, the Stack helped us to manage our data by benefiting from the last-in-first-out 
fashion. We used stacks to track the recent activity performed by either the person or 
the garage and we displayed this recent activity in tables. 

-  We also Implemented Priority Queues for having quick access to the highest priority 
item with low time complexity; We used them to store the people who rented cars 
from our garage according to the duration of the rent. 

-  Taking the advantage of Array Lists for their dynamic size and easy access, we 
stored the info of every person that logged into our garage. Also, for the Arrays, we 
limit the number of cars owned by each person. 

-  Finally, to make our program more interactive, we used a customer engagement 
platform called Twilio to build a unique personalized experience for our customers by 
informing them by SMS when their services at our Garage are done, and what is their 
new balance after every purchase at our garage. 



-  Project Description Draft:
   - Registration panel of users to enter the garage, from asking the user his basic information to checking if he has one or two cars.
   - Dashboard Panel containing sub-panels including but not limited to Dashboard,Buy/Rent Car, Sell Car, History, And Logout.
    
   - The dashboard contains a pie chart of all the available cars in the garage, and the recent activities of the garage (Buy, Rent, Sell, Wash);
    
   - Buy/Rent Cars contains car details, navigation of the cars forward and backward, buy car functionality, rent car functionality based on the days count,and for sure       handling all the possible cases.
    
   - Sell Car contains navigation of the owned cars of the registered person. And offer the garage to buy the specific car.
    
   - History contains the recent activity of the registered person,  two tables, one for the recently bought cars by descending order, and one for the rented cars by         the rented days' order.
    
   - A logout button to close the panel and show the login panel again.

    
-  Used Data Structures
    - [ ] Binary Search Tree ( For people who buy a car according to the price of car)
    - [ ] Priority Queue ( For people who rent a car prioroty: duration )
    - [ ] Stack ( For the activities acomplished by the person and the Garage ) 
    - [ ] ArrayList ( For all people )
    - [ ] Double LinkedList ( For the cars available in the garage ) 
    - [ ] Singly LinkedList
    - [ ] Array
