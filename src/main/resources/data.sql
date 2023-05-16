INSERT INTO usuario (nome, email, senha, cpf, tipo_usuario, is_ativo, is_banido, data_criacao, ultimo_login) VALUES
('João da Silva', 'joao.silva@email.com', 'd3JpdGVydmFsdWVnYXRla25ld3N1cnByaXNlaG9wZXN0cnVnZ2xldXBvbm1pbGtzZXQ', '49308791845', 1, 0, 0, '2023-04-14 10:30:00', '2023-04-14 10:30:00'),
('John Doe', 'john@doe.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', '49308791855', 1, 1, 0, '2023-04-14 10:30:00', '2023-04-14 10:30:00'),
('Maria da Silva', 'maria@silva.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', '49308791865', 1, 1, 0, '2023-04-14 10:30:00', '2023-04-14 10:30:00'),
('Ana Souza', 'ana@souza.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', '49308791875', 1, 0, 0, '2023-04-14 10:30:00', '2023-04-14 10:30:00'),
('Pedro Alves', 'pedro@alves.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', '49308791885', 0, 0, 0, '2023-04-14 10:30:00', '2023-04-14 10:30:00'),
('Mariana Santos', 'mariana@santos.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', '49308791895', 0, 0, 0, '2023-04-14 10:30:00', '2023-04-14 10:30:00');


INSERT INTO endereco (is_validado, logradouro, numero, bairro, uf, cep, latitude, longitude) VALUES
(1, 'Rua A', 100, 'Centro', 'SP', '12345678', -23.567890, -46.789012),
(0, 'Rua B', 200, 'Vila Madalena', 'SP', '12345678', -23.567890, -46.789012),
(1, 'Rua C', 300, 'Moema', 'SP', '12345678', -23.567890, -46.789012),
(1, 'Rua D', 400, 'Pinheiros', 'SP', '12345678', -23.567890, -46.789012),
(1, 'Rua E', 500, 'Ipanema', 'RJ', '12345678', -22.567890, -43.789012),
(0, 'Rua F', 600, 'Barra da Tijuca', 'RJ', '12345678', -22.567890, -43.789012);


INSERT INTO buffet (nome, descricao, tamanho, preco_medio_diaria, qtd_pessoas, caminho_comprovante, residencia_comprovada, is_visivel, id_usuario, id_endereco) VALUES
('Buffet do Fulano', 'Buffet completo para festas', 500, 2000.00, 200, '/caminho/comprovante/fulano.pdf', 1, 1, 1, 1),
('Buffet do Ciclano', 'Buffet simples para eventos pequenos', 100, 800.00, 50, '/caminho/comprovante/ciclano.pdf', 0, 1, 1, 2),
('Buffet do Beltrano', 'Buffet para casamentos e eventos formais', 1000, 5000.00, 500, '/caminho/comprovante/beltrano.pdf', 1, 1, 2, 3),
('Buffet da Ana', 'Buffet para festas infantis', 200, 1500.00, 100, '/caminho/comprovante/ana.pdf', 1, 1, 4, 4),
('Buffet do Pedro', 'Buffet para eventos corporativos', 300, 2500.00, 150, '/caminho/comprovante/pedro.pdf', 1, 1, 5, 5),
('Buffet da Mariana', 'Buffet para casamentos e eventos formais', 800, 5000.00, 300, '/caminho/comprovante/mariana.pdf', 0, 1, 6, 6);


INSERT INTO mensagem (mensagem, mandado_por, data, id_usuario, id_buffet) VALUES
('Gostaria de solicitar um orçamento para uma festa de aniversário', 1, NOW(), 1, 2),
('Temos pacotes especiais para eventos corporativos, entre em contato para saber mais', 2, NOW(), 2, 1),
('Nossos preços variam de acordo com o tipo de evento e quantidade de pessoas, entre em contato para mais informações', 1, NOW(), 3, 1),
('Gostaria de agendar uma visita para conhecer melhor o buffet', 2, NOW(), 4, 3),
('Qual o valor para um evento de 200 pessoas?', 4, NOW(), 5, 2),
('Estou interessado no serviço de decoração, vocês têm opções personalizadas?', 5, NOW(), 6, 1);


INSERT INTO faixa_etaria (descricao) VALUES
('1 a 5 anos'),
('6 a 10 anos'),
('11 a 15 anos'),
('16 a 20 anos'),
('21 a 25 anos'),
('26 a 30 anos'),
('31 a 35 anos'),
('36 a 40 anos'),
('41 a 49 anos'),
('50 anos ou mais');

INSERT INTO tipo_evento (descricao) VALUES
('Casamento'),
('Aniversário'),
('Empresarial'),
('Happy Hour'),
('Comemoração'),
('Infantil'),
('Outros');

INSERT INTO servico (descricao) VALUES
('Comida'),
('Decoração'),
('Música'),
('Garçom'),
('Segurança'),
('Limpeza'),
('Estacionamento'),
('Fotografia');

INSERT INTO imagem (caminho, nome, tipo, is_ativo, data_upload, id_buffet) VALUES
('/images/buffet1/', 'imagem1', 'jpg', 1, '2023-04-14 10:30:00', 1),
('/images/buffet2/', 'imagem2', 'png', 0, '2023-04-14 12:45:00', 2),
('/images/buffet3/', 'imagem3', 'jpg', 1, '2023-04-14 10:30:00', 3),
('/images/buffet4/', 'imagem4', 'png', 1, '2023-04-14 12:45:00', 4),
('/images/buffet5/', 'imagem5', 'png', 1, '2023-04-14 12:45:00', 5),
('/images/buffet6/', 'imagem6', 'jpg', 1, '2023-04-14 10:30:00', 6);

INSERT INTO imagem_chat (caminho, nome, tipo, is_ativo, data_upload, id_mensagem) VALUES
('/images/chat1/', 'imagem1', 'jpg', 1, '2023-04-14 09:30:00', 1),
('/images/chat2/', 'imagem2', 'png', 1, '2023-04-14 09:30:00', 2),
('/images/chat3/', 'imagem3', 'jpg', 1, '2023-04-14 09:30:00', 3),
('/images/buffet5/', 'imagem5', 'png', 1, '2023-04-14 12:45:00', 5),
('/images/buffet6/', 'imagem6', 'jpg', 1, '2023-04-14 10:30:00', 6);

INSERT INTO buffet_tipo_evento VALUES
(1, 1),
(2, 1),
(3, 1),
(1, 2),
(4, 3),
(5, 2),
(6, 1);

INSERT INTO buffet_servico VALUES
(1,1),
(2,2),
(3,3),
(4, 3),
(5, 2),
(6, 1);

INSERT INTO buffet_faixa_etaria VALUES
(1,1),
(2,4),
(3,5),
(4, 3),
(5, 5),
(6, 6);