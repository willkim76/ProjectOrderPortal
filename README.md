# Lucas and the Willies Design Document

## Instructions

*Save a copy of this template for your team in the same folder that contains
this template.*

*Replace italicized text (including this text!) with details of the design you
are proposing for your team project. (Your replacement text shouldn't be in
italics)*

*You should take a look at the example design document in the same folder as
this template for more guidance on the types of information to capture, and the
level of detail to aim for.*

## *Juicy Burger Online Ordering* Design

## 1. Problem Statement

[comment]: <> (*Explain clearly what problem you are trying to solve.*)
The client, Juicy Burgers, have been serving their community for years as a simple walk in service. With the
shifting technological landscape, Juicy Burger desires to modernize their business with an online ordering
service with the goal of streamlining the ordering experience while increasing their online presence.

The goal is to reduce over crowding during peak operating hours that may hamper potential customers from accessing 
Juicy Burgers products as well as increasing the number of potential customers in general.

## 2. Top Questions to Resolve in Review

*List the most important questions you have about your design, or things that
you are still debating internally that you might like help working through.*

1. Is the shopping cart handled by the Front End or the Back End?  
2. Will the menu initially be hard coded on the Front End or a call to the database?
3. How detailed should the data models be? How many data models do we need?
4. How many data tables do we need?

## 3. Use Cases

*This is where we work backwards from the customer and define what our customers
would like to do (and why). You may also include use cases for yourselves, or
for the organization providing the product to customers.*

U1. *As a Juciy Burger customer, I want to be able to view the menu when I go to the website*

U2. *As a Juicy Burger customer, I want to be able to add, update, and delete menu items in my shopping cart*
    
U3. *As a Juicy Burger customer, I want to be able to place an order*

U4. *As a Juicy Burger customer, I want to be able to view a previously placed order*

## 4. Project Scope

*Clarify which parts of the problem you intend to solve. It helps reviewers know
what questions to ask to make sure you are solving for what you say and stops
discussions from getting sidetracked by aspects you do not intend to handle in
your design.*

### 4.1. In Scope

- View the Juicy Burger menu
- Add menu items to a shopping cart
- Update items in a shopping cart
- Delete items from a shopping cart
- Place an Order
- Retrieve an Order

### 4.2. Out of Scope

- Substitutions on menu items
- Creating a user account
- Delivery service
- End points for Business integration at physical location

# 5. Proposed Architecture Overview

*Describe broadly how you are proposing to solve for the requirements you
described in Section 2.*

1) Viewing the menu items
   - The front end Javascript will make a get call to a .json file that will populate and display the menu 
   to the customer.
2) Adding menu items to a shopping cart 
   - The front end will build an order with menu items, and display the order to the customer as a shopping cart.
3) Updating items within a shopping cart
   - The frontend will have functionality to update the order, either by quantity or by menu item.
4) Deleting items from a shopping cart
   - The frontend will have functionality to delete items within a shopping cart.
5) Placing an order with a shopping cart
   - The front end generates a PlaceOrderRequest for the backend, where an Order is generated within a Lambda 
   function. The Order is stored within a dedicated Order table in DynamoDB, and a copy of the Order is returned 
   to the customer.
   

*This may include class diagram(s) showing what components you are planning to
build.*

*You should argue why this architecture (organization of components) is
reasonable. That is, why it represents a good data flow and a good separation of
concerns. Where applicable, argue why this architecture satisfies the stated
requirements.*

This model represents a simple but complete solution for every use case we proposed. Each function is constrained 
to a specific task, and there are no overlapping functions.

# 6. API

## 6.1. Public Models

*Define the data data.types.models your service will expose in its responses via your
*`-Model`* package. These will be equivalent to the *`PlaylistModel`* and
*`SongModel`* from the Unit 3 project.*

OrderModel

MenuItemModel

## 6.2.1. *Place Order Activity*

*Describe the behavior of the first endpoint you will build into your service
API. This should include what data it requires, what data it returns, and how it
will handle any known failure cases. You should also include a sequence diagram
showing how a user interaction goes from user to website to service to database,
and back. This first endpoint can serve as a template for subsequent endpoints.
(If there is a significant difference on a subsequent endpoint, review that with
your team before building it!)*

The customer places an order. The Javascript creates an Order object with the MenuItems the customer selected and 

sends it to the backend, where the lambda function checks for invalid order attributes. It returns if the order is 

invalid, else it sends the Order to the database. The database sens back the Order data, which is displayed

to the customer.

![](../project_documents/PlaceOrderActivity-SD.png)

*(You should have a separate section for each of the endpoints you are expecting
to build...)*

## 6.2.2 *Get Order Activity*

*(repeat, but you can use shorthand here, indicating what is different, likely
primarily the data in/out and error conditions. If the sequence diagram is
nearly identical, you can say in a few words how it is the same/different from
the first endpoint)*

The same path is used that was used for Place Order Activity, and the same thing is returned. The database checks for 

a valid orderId (which is returned to the customer as an attribute of the Order when the Order is placed) and returns 

an invalid order notification if the orderId is invalid. Else it returns the Order information. 


![](../project_documents/GetOrderActivity-SD.png)

# 7. Tables

*Define the DynamoDB tables you will need for the data your service will use. It
may be helpful to first think of what objects your service will need, then
translate that to a table structure, like with the *`Playlist` POJO* versus the
`playlists` table in the Unit 3 project.*

(out of scope) MenuTable - holds menu items

OrderHistoryTable - holds previously placed Orders

# 8. Pages

*Include mock-ups of the web pages you expect to build. These can be as
sophisticated as mockups/wireframes using drawing software, or as simple as
hand-drawn pictures that represent the key customer-facing components of the
pages. It should be clear what the interactions will be on the page, especially
where customers enter and submit data. You may want to accompany the mockups
with some description of behaviors of the page (e.g. “When customer submits the
submit-dog-photo button, the customer is sent to the doggie detail page”)*


When the customer presses the "Order" button, they are sent to the Menu page, where the 
MenuItems are displayed for them. They then chose which items to include in their order
by selecting the boxes next to the menu item names displayed on the page. When the customer
presses the "Add to cart" button, an Order is built and stored in local memory on thier
machine. When a customer presses the "Edit Order" button, they are sent back to the Menu, 
where they can select a different assortment of items. On the Cart page, when a customer
presses the "Place order" button, the Order is sent to the backend, where it is processed
and stored to the Order History table in the DynamoDB.


![](../project_documents/front page.png)
![](../project_documents/menu.png)
![](../project_documents/cart.png)
![](../project_documents/Juicy_Burger_Wireframe.png)
