INSERT INTO organization
(name, description, domain)
VALUES('Ensar', 'Ensar desc', 'agile-domain');

INSERT INTO forecast_dash_board
(organization_id, dash_board_id)
VALUES(1, '');

INSERT INTO `user`
( first_name, last_name, email, password, role_name, organization_id)
VALUES('Bhakta', 'Reddy', 'bhakta@ensarsolutions.com', '$2a$10$32I5VkFY4VlyB2CZpn/NWu1ysZ4kObVOyIhnSRSOub7flTZ9rp2PG', 'ROLE_SUPER_ADMIN', 1);

INSERT INTO projects
(name)
VALUES('SiteIQ');

INSERT INTO releases
(name)
VALUES('1.0');

INSERT INTO releases
(name)
VALUES('2.0');

INSERT INTO clients
(name, contact)
VALUES('SiteIQ', 'Kishore');

INSERT INTO clients
(name, contact)
VALUES('Ensar', 'Bhakta');

INSERT INTO clients
(name, contact)
VALUES('Internal', 'Ensar');

INSERT INTO products
(name,user_id,client_id)
VALUES('Agile',1,2);

INSERT INTO products
(name,user_id,client_id)
VALUES('HR',1,2);

INSERT INTO features
(name, product_id)
VALUES('Requirements',1);
INSERT INTO features
(name, product_id)
VALUES('Epics',1);
INSERT INTO features
(name, product_id)
VALUES('Stories',1);

INSERT INTO teams
(name)
VALUES('SiteIQ');


INSERT INTO epics
(name,description,accptance_criteria,points,original_estimate,remainng_estimate,start_date,end_date,due_date,hours_worked,risk_level,priority,release_id,feature_id,user_id,team_id)
VALUES('Need a UI to capture requirements','Need a UI to capture requirements','Able to add/delete/update requirements',5,5,3,'2021-11-05','2021-11-05','2021-11-05',5,0,'high',1,1,1,1 );

INSERT INTO stories
(name,description,accptance_criteria,points,original_estimate,remainng_estimate,start_date,end_date,due_date,hours_worked,risk_level,priority,epic_id,release_id,feature_id,user_id,team_id)
VALUES('Need a UI to capture requirements','Need a UI to capture requirements','Able to add/delete/update requirements',5,5,3,'2021-11-05','2021-11-05','2021-11-05',5,0,'high',1,1,1,1,1 );



INSERT INTO comments
(name, epic_id)
VALUES('Dashboard Comment',1);
INSERT INTO comments
(name, epic_id)
VALUES('Dashboard Comment2',1);

INSERT INTO comments
(name, story_id)
VALUES('Dashboard Story Comment',1);
INSERT INTO comments
(name, epic_id)
VALUES('Dashboard Story Comment2',1);


INSERT INTO status
(summary)
VALUES('Today I developed Summary Table');


INSERT INTO testcases_modules
(name,description,project_id)
VALUES('UI','UI Testing',1);	  
	  
INSERT INTO testcases
(test_id,name,description,expected_results, actual_results, status, user_id)
VALUES('ZERV-1','ZERV-1','Testing Mobile','Mobile should work','Mobile not working','pass',1);

	  
INSERT INTO testcases_steps
(description,expected_results, actual_results, `order`,testcase_id)
VALUES('Testing Mobile','Mobile should work','Mobile not working',1,1);


INSERT INTO tickets
(name, type, status, priority, user_id)
VALUES('SiteIQ','HR','open','low',1);

INSERT INTO timesheet_projects
(name, client_id)
VALUES('Sippio Azure B2C',1);

INSERT INTO timesheet_projects
(name)
VALUES('Angular');

INSERT INTO timesheet_sub_projects
(name, timesheet_projects_id)
VALUES('Front End',1);

INSERT INTO timesheet_sub_projects
(name, timesheet_projects_id)
VALUES('Angular Complete Reference',2);


INSERT INTO timesheet_tasks
(name)
VALUES('High Level Design');

INSERT INTO timesheet_tasks
(name)
VALUES('Learning');


INSERT INTO timesheet_hours
(timesheet_projects_id, timesheet_sub_projects_id, date, user_id,start_at,end_at,comment)
VALUES(2,2,'2021-11-05',1,'08:00:00','10:00:00','SiteIQ Development');
