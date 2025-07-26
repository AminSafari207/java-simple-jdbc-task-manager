create database task_manager;

set search_path to public;

create table employee (
    id serial primary key,
    first_name varchar,
    last_name varchar
);

create table task (
    id serial primary key,
    title varchar,
    description varchar,
    estimated_hours int
);

create table assigned_tasks (
    id serial primary key,
    employee_id int references employee(id) on delete cascade,
    task_id int references task(id) on delete cascade
);