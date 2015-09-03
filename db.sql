CREATE TABLE events (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  created_at DATETIME NOT NULL,
  event_name TEXT NOT NULL
);

CREATE TABLE samples (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  sample_type INT UNSIGNED NOT NULL,
  sample_value VARCHAR(255) NOT NULL
);

INSERT INTO samples
(sample_type, sample_value)
VALUES
(1,'Lorem ipsum'),
(1,'dolor sit amet'),
(2,'consectetur adipiscing elit'),
(3,'sed do eiusmod tempor'),
(3,'incididunt'),
(3,'ut labore et dolore magna'),
(4,'aliqua'),
(4,'Ut enim ad minim veniam'),
(4,'quis nostrud exercitation ullamco'),
(5,'laboris nisi ut aliquip ex ea commodo consequat');
