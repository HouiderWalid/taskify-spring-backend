/**/
/* Table Creation */
/**/
CREATE TABLE permissions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE roles(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);
CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id)
);
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT,
    full_name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE user_permissions (
    user_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, permission_id)
);
CREATE TABLE projects (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    creator_id BIGINT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    due_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id BIGINT NOT NULL,
    assigned_to_user_id BIGINT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status ENUM('todo', 'in_progress', 'done') DEFAULT 'todo',
    priority ENUM('low', 'medium', 'high') DEFAULT 'medium',
    due_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
/**/
/* Foreign Keys */
/**/
ALTER TABLE role_permissions
ADD CONSTRAINT role_permission_permission_fk FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE;
ALTER TABLE role_permissions
ADD CONSTRAINT role_permission_role_fk FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE;
ALTER TABLE users
ADD CONSTRAINT user_role_fk FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE
SET NULL;
ALTER TABLE user_permissions
ADD CONSTRAINT user_permission_permission_fk FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE;
ALTER TABLE user_permissions
ADD CONSTRAINT user_permission_user_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE projects
ADD CONSTRAINT project_creator_fk FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE
SET NULL;
ALTER TABLE tasks
ADD CONSTRAINT task_project_fk FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE;
ALTER TABLE tasks
ADD CONSTRAINT task_assigned_to_user_fk FOREIGN KEY (assigned_to_user_id) REFERENCES users(id) ON DELETE
SET NULL;
/**/
/* Data Insert */
/**/
INSERT INTO roles (name)
values ('admin'),
    ('manager'),
    ('member');
INSERT INTO permissions (name)
VALUES ('view-overview'),
    ('view-projects'),
    ('create-project'),
    ('update-project'),
    ('delete-project'),
    ('view-tasks'),
    ('create-task'),
    ('update-task'),
    ('delete-task'),
    ('update-task-status'),
    ('view-users'),
    ('create-user'),
    ('update-user'),
    ('view-chat'),
    ('view-settings'),
    ('update-settings');
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id,
    p.id
FROM roles r
    CROSS JOIN permissions p
WHERE r.name = 'admin'
    AND p.name IN (
        'view-overview',
        'view-projects',
        'create-project',
        'update-project',
        'delete-project',
        'view-tasks',
        'create-task',
        'update-task',
        'delete-task',
        'view-users',
        'create-user',
        'update-user',
        'view-chat',
        'view-settings',
        'update-settings',
        'update-task-status'
    );
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id,
    p.id
FROM roles r
    CROSS JOIN permissions p
WHERE r.name = 'manager'
    AND p.name IN (
        'view-overview',
        'view-projects',
        'create-project',
        'update-project',
        'delete-project',
        'view-tasks',
        'create-task',
        'update-task',
        'delete-task',
        'view-chat',
        'view-settings',
        'update-settings',
        'update-task-status'
    );
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id,
    p.id
FROM roles r
    CROSS JOIN permissions p
WHERE r.name = 'member'
    AND p.name IN (
        'view-overview',
        'view-projects',
        'view-tasks',
        'view-chat',
        'view-settings',
        'update-settings',
        'update-task-status'
    );
INSERT INTO users (role_id, full_name, email, password)
SELECT r.id,
    'houider walid',
    'houiderwalid@gmail.com',
    '$2a$12$QkBSSiS7PYxqaiio/6aJW.01RzSCjAFtF1Q7a7aX1mHyvpXvyoobu'
FROM roles r
WHERE r.name = 'admin';
INSERT INTO user_permissions (user_id, permission_id)
SELECT u.id,
    p.id
FROM users u
    CROSS JOIN permissions p
WHERE u.email = 'houiderwalid@gmail.com'
    AND p.name IN (
        'view-overview',
        'view-projects',
        'create-project',
        'update-project',
        'delete-project',
        'view-tasks',
        'create-task',
        'update-task',
        'delete-task',
        'view-users',
        'create-user',
        'update-user',
        'view-chat',
        'view-settings',
        'update-settings',
        'update-task-status'
    )
    /* dotenv -e .env -- mvn flyway:migrate */