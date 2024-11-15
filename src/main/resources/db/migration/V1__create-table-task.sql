CREATE TABLE tasks (
  id int AUTO_INCREMENT PRIMARY KEY,
  name varchar(80) not null unique,
  price decimal(12, 2) not null,
  position int not null,
  expire_at datetime not null,
  created_at datetime not null
);

CREATE INDEX idx_tasks_position ON tasks(position);