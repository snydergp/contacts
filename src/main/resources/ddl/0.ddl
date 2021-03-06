CREATE SCHEMA contacts_data;

CREATE TABLE contacts_data.contact (
	contact_id INT NOT NULL IDENTITY PRIMARY KEY,
	info TEXT
);

CREATE TABLE contacts_data.person (
	contact_id INT NOT NULL PRIMARY KEY REFERENCES contacts_data.contact ON DELETE CASCADE,
	title_cd SMALLINT,
	first_name VARCHAR(128),
	middle_initial CHAR(1),
	last_name VARCHAR(128),
);

CREATE TABLE contacts_data.business (
	contact_id INT NOT NULL PRIMARY KEY REFERENCES contacts_data.contact ON DELETE CASCADE,
	name VARCHAR(128)
);

CREATE TABLE contacts_data.address (
	address_id INT NOT NULL IDENTITY PRIMARY KEY,
	contact_id INT NOT NULL REFERENCES contacts_data.contact ON DELETE CASCADE,
	address_type VARCHAR(32),
	line_1 VARCHAR(128),
	line_2 VARCHAR(128),
	line_3 VARCHAR(128),
	city VARCHAR(64),
	state_province CHAR(2),
	postal_code VARCHAR(10),
	country VARCHAR(32),
	additional_info VARCHAR(128)
);

CREATE INDEX contacts_data.address_contact_id ON contacts_data.address (contact_id);

CREATE TABLE contacts_data.phone_number (
	phone_number_id INT NOT NULL IDENTITY PRIMARY KEY,
	contact_id INT NOT NULL REFERENCES contacts_data.contact ON DELETE CASCADE,
	phone_type VARCHAR(32),
	number VARCHAR(15) -- http://en.wikipedia.org/wiki/E.164
); 

CREATE INDEX contacts_data.phone_number_contact_id ON contacts_data.phone_number (contact_id);

CREATE TABLE contacts_data.email (
	email_id INT NOT NULL IDENTITY PRIMARY KEY,
	contact_id INT NOT NULL REFERENCES contacts_data.contact ON DELETE CASCADE,
	email_type VARCHAR(32),
	email VARCHAR(1024)
);

CREATE INDEX contacts_data.email_contact_id ON contacts_data.email (contact_id);