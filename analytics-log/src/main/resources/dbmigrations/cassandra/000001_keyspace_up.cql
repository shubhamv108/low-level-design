CREATE KEYSPACE IF NOT EXISTS analytics_logs
WITH durable_writes = true AND replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };