CREATE DATABASE IF NOT EXISTS db_sistema CHARACTER SET utf8mb4;
USE db_sistema;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    senha_hash VARCHAR(128) NOT NULL
);

SET @ajuste = IF(
    EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'usuarios'
          AND column_name = 'senha_hash'
    ),
    'SELECT ''Coluna senha_hash pronta''',
    'ALTER TABLE usuarios ADD COLUMN senha_hash VARCHAR(128) NULL'
);
PREPARE adicionar_coluna FROM @ajuste;
EXECUTE adicionar_coluna;
DEALLOCATE PREPARE adicionar_coluna;
