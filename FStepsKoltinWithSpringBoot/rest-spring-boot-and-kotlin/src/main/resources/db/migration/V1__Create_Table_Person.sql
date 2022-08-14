-- Copiando estrutura para tabela rest_with_spring_boot_pedro.person
CREATE TABLE IF NOT EXISTS `person` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `address` varchar(100) NOT NULL,
    `first_name` varchar(80) NOT NULL,
    `gender` varchar(10) NOT NULL,
    `last_name` varchar(80) NOT NULL,
    PRIMARY KEY (`id`)
    )

