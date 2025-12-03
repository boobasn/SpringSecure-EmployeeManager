import { Employee } from './employee';

export type TypeContrat =
  | 'CDI'
  | 'CDD'
  | 'STAGE'
  | 'FREELANCE';

export interface Contrat {

  id?: string;

  typeContrat: TypeContrat;

  dateDebut: string;
  dateFin?: string;

  salaire: number;
  statut: string;

  // ✅ relation OneToOne
  employee?: Employee;
}
