-- 🚀 Initialisation des données pour l'application RH (PostgreSQL)

-- ========================
-- TABLE: departments
-- ========================
INSERT INTO departments (id, name, description)
VALUES 
('a89ea774-eabd-430c-8bf6-5db819127cda', 'Informatique', 'Département de développement logiciel et maintenance des systèmes.'),
('f1a34dee-0964-4f46-992d-757e865e5efa', 'Ressources Humaines', 'Gestion des employés et des recrutements.'),
('d102937c-d4d5-498e-a040-3762f2dfb884', 'Comptabilité', 'Gestion financière et comptable de l’entreprise.');

---

-- ========================
-- TABLE: employees
-- ========================
INSERT INTO employees (id, first_name, last_name, email, telephone, adresse, date_embauche, salaire, departement_id)
VALUES 
('47ae43f6-d495-460e-80a6-2db50c65b965', 'Babou', 'Sene', 'babou.sene@entreprise.com', '771234567', 'Dakar, Sénégal', '2023-01-15', 450000, 'a89ea774-eabd-430c-8bf6-5db819127cda'),
('006cc7ac-86b9-4101-b141-1c0326f5244b', 'Awa', 'Diop', 'awa.diop@entreprise.com', '770987654', 'Thiès, Sénégal', '2022-11-20', 350000, 'f1a34dee-0964-4f46-992d-757e865e5efa'),
('a1b2c3d4-e5f6-7890-ab12-cd34ef567890', 'Moussa', 'Ba', 'moussa.ba@entreprise.com', '780456789', 'Saint-Louis, Sénégal', '2021-09-01', 600000, 'a89ea774-eabd-430c-8bf6-5db819127cda'),
('d4e5f6a7-b890-12cd-34ef-567890a1b2c3', 'Fatou', 'Ndiaye', 'fatou.ndiaye@entreprise.com', '760789012', 'Dakar, Sénégal', '2024-03-12', 300000, 'd102937c-d4d5-498e-a040-3762f2dfb884'),
('e5f6a7b8-9012-3cde-45f6-7890a1b2c3d4', 'Babou', 'Sene', 'babou.sene@entreprise.com', '771234567', 'Dakar, Sénégal', '2023-01-15', 450000, 'f1a34dee-0964-4f46-992d-757e865e5efa'),
('f6a7b890-123c-d45f-6789-0a1b2c3d4e5f', 'Fatou', 'Ndiaye', 'fatou.ndiaye@entreprise.com', '760789012', 'Dakar, Sénégal', '2024-03-12', 300000, 'd102937c-d4d5-498e-a040-3762f2dfb884');

---

-- ========================
-- TABLE: contrats
-- ========================
INSERT INTO contrats (id, type, date_debut, date_fin, salaire, statut, employee_id)
VALUES
('83c65891-122e-4179-905d-c820c956d9cf', 'CDI', '2023-01-15', NULL, 450000, 'ACTIF', '47ae43f6-d495-460e-80a6-2db50c65b965'),
('af92a71a-9378-4518-b32a-8027f69b6707', 'CDD', '2022-11-20', '2024-11-20', 350000, 'ACTIF', '006cc7ac-86b9-4101-b141-1c0326f5244b'),
('dcf89725-b100-49c8-8abd-4cc035aa87d6', 'CDI', '2021-09-01', NULL, 600000, 'ACTIF', 'a1b2c3d4-e5f6-7890-ab12-cd34ef567890'),
('7403c178-9b57-409e-9a1c-c99b417acb3c', 'STAGE', '2024-03-12', '2024-09-12', 300000, 'TERMINE', 'd4e5f6a7-b890-12cd-34ef-567890a1b2c3');

---

-- ========================
-- TABLE: conge
-- ========================
INSERT INTO conge (id, type, date_debut, date_fin, status, employee_id)
VALUES
('f68fc875-2609-4f12-bdad-63e9dfa763d0', 'MALADIE', '2024-05-01', '2024-05-05', 'REFUSE', '47ae43f6-d495-460e-80a6-2db50c65b965'),
('0d056829-eeda-40d8-954e-c1a29027ef14', 'ANNUELLE', '2024-08-10', '2024-08-20', 'REFUSE', '006cc7ac-86b9-4101-b141-1c0326f5244b'),
('1f82095f-d23e-44af-9a17-6c6de2238452', 'AUTRE', '2024-06-15', '2024-06-18', 'REFUSE', 'a1b2c3d4-e5f6-7890-ab12-cd34ef567890'),
('b34ccfe1-183e-4983-b8c5-2a902825302c', 'ANNUELLE', '2024-09-01', '2024-09-10', 'REFUSE', 'd4e5f6a7-b890-12cd-34ef-567890a1b2c3');

---

-- ========================
-- TABLE: postes
-- ========================
INSERT INTO postes (id, titre, description)
VALUES
('a45bc3fe-b81a-4edd-a708-efa2c69886fa', 'Développeur Backend', 'Responsable du développement des API et de la logique serveur.'),
('4ec31020-e0e5-47cb-a12c-9d40acf595e4', 'Chargé RH', 'Gère les recrutements, contrats et la relation avec les employés.'),
('1e31570e-91a2-4c96-87da-c969ea91ba22', 'Comptable', 'S’occupe des comptes, bilans et rapports financiers.'),
('e6d246d9-e1bb-42e7-b477-97159cb05054', 'Technicien Support', 'Assure le support technique et la maintenance du matériel.');