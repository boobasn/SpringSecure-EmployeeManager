import { User } from './user';
import { Department } from './departement';
import { Poste } from './poste';
import { Contrat } from './contrat';
import { Leave } from './leave';

export interface Employee {

  id?: string;

  firstName: string;
  lastName: string;
  email: string;
  telephone: string;
  salaire: number;
  matricule: string;
  dateNaissance?: string;
  dateEmbauche?: string;
  sexe?: string;
  adresse?: string;

  // ✅ Relations JPA respectées
  departement?: Department;
  poste?: Poste;
  contrat?: Contrat;

  leaves?: Leave[];

  // ✅ Lien utilisateur
  user: User;
}
