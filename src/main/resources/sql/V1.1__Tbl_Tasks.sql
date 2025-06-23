
IF NOT EXISTS(SELECT
  *
FROM sys.objects
WHERE object_id = OBJECT_ID(N'tasks') AND type IN (N'U'))

CREATE TABLE tasks
(
  id INT IDENTITY(1,1) CONSTRAINT pk_tasks PRIMARY KEY,
  title VARCHAR(100),
  description VARCHAR(350),
  status VARCHAR(10),
  due DATETIME,
  created_on DATETIME NOT NULL DEFAULT GETDATE(),
  created_by VARCHAR(70) NOT NULL,
  last_updated_by VARCHAR(70),
  last_updated_on DATETIME
)
GO
