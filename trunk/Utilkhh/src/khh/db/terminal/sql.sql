CREATE TABLE example (
         id INT,
         data VARCHAR(100)
       );
       
       insert into example values(1,'1');
       insert into example values(1,'1-1');
       insert into example values(1,'1-2');
       insert into example values(1,'1-3');
       
      insert into example values(2,'2');
       insert into example values(2,'2-1');
       insert into example values(2,'2-2');
       insert into example values(2,'2-3');
       
       select * from example;
       
       update example set data=concate(data,'--') where id='2'