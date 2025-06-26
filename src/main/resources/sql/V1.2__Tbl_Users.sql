IF NOT EXISTS(SELECT
  *
FROM sys.objects
WHERE object_id = OBJECT_ID(N'users') AND type IN (N'U'))

CREATE TABLE users
(
  id INT IDENTITY(1,1) CONSTRAINT pk_users PRIMARY KEY,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  email VARCHAR(15) NOT NULL CONSTRAINT uq_users_email UNIQUE,
  password VARCHAR(255),
  role VARCHAR(7),
  created_on DATETIME NOT NULL DEFAULT GETDATE(),
  created_by VARCHAR(70) NOT NULL,
  last_updated_by VARCHAR(70),
  last_updated_on DATETIME,
  deleted_on DATETIME,
  deleted_by VARCHAR(70)
)
GO
