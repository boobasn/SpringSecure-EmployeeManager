-- ========================
-- TABLE: Department
-- ========================
INSERT INTO departments (id, name, description)
VALUES 
(1, 'Informatique', 'Département de développement logiciel et maintenance des systèmes.'),
(2, 'Ressources Humaines', 'Gestion des employés et des recrutements.'),
(3, 'Comptabilité', 'Gestion financière et comptable de l’entreprise.');

-- ========================
-- TABLE: Employee
-- ========================
INSERT INTO employees (id, first_name, last_name, email, telephone, adresse, date_embauche, salaire, departement_id)
VALUES 
(1, 'Babou', 'Sene', 'babou.sene@entreprise.com', '771234567', 'Dakar, Sénégal', '2023-01-15', 450000, 1),
(2, 'Awa', 'Diop', 'awa.diop@entreprise.com', '770987654', 'Thiès, Sénégal', '2022-11-20', 350000, 2),
(3, 'Moussa', 'Ba', 'moussa.ba@entreprise.com', '780456789', 'Saint-Louis, Sénégal', '2021-09-01', 600000, 1),
(4, 'Fatou', 'Ndiaye', 'fatou.ndiaye@entreprise.com', '760789012', 'Dakar, Sénégal', '2024-03-12', 300000, 3),
(5, 'Babou', 'Sene', 'babou.sene@entreprise.com', '771234567', 'Dakar, Sénégal', '2023-01-15', 450000, 2),
(6, 'Fatou', 'Ndiaye', 'fatou.ndiaye@entreprise.com', '760789012', 'Dakar, Sénégal', '2024-03-12', 300000, 2);

-- ========================
-- TABLE: contracts
-- ========================
INSERT INTO contrats (id, type, date_debut, date_fin, salaire, statut, employee_id)
VALUES
(1, 'CDI', '2023-01-15', NULL, 450000, 'ACTIF', 1),
(2, 'CDD', '2022-11-20', '2024-11-20', 350000, 'ACTIF', 2),
(3, 'CDI', '2021-09-01', NULL, 600000, 'ACTIF', 3),
(4, 'STAGE', '2024-03-12', '2024-09-12', 300000, 'TERMINE', 4);

-- ========================
-- TABLE: conge (Leave)
-- ========================
INSERT INTO conge (id, type, date_debut, date_fin, status, employee_id)
VALUES
(1, 'MALADIE', '2024-05-01', '2024-05-05', 'REFUSE', 1),
(2, 'ANNUELLE', '2024-08-10', '2024-08-20', 'REFUSE', 2),
(3, 'AUTRE', '2024-06-15', '2024-06-18', 'REFUSE', 3),
(4, 'ANNUELLE', '2024-09-01', '2024-09-10', 'REFUSE', 4);

-- ========================
-- TABLE: postes
-- ========================
INSERT INTO postes (id, titre, description)
VALUES
(1, 'Développeur Backend', 'Responsable du développement des API et de la logique serveur.'),
(2, 'Chargé RH', 'Gère les recrutements, contrats et la relation avec les employés.'),
(3, 'Comptable', 'S’occupe des comptes, bilans et rapports financiers.'),
(4, 'Technicien Support', 'Assure le support technique et la maintenance du matériel.');

