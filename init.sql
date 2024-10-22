DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'access_doc') THEN
      CREATE DATABASE access_doc;
   END IF;
END $$;

