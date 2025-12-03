import { Employee } from './employee';

export type TypeConge =
  | 'MALADIE'
  | 'ANNUELLE'
  | 'AUTRE';

export type Status =
  | 'ATTENTE'
  | 'APPROUVE'
  | 'REFUSE';

export interface Leave {

  id?: string;

  typeConge: TypeConge;

  dateDebut: string;
  dateFin: string;

  status: Status;

  motif?: string;
  commentaireManager?: string;

  dateDemande?: string;

  // ✅ relation ManyToOne (côté Angular on met juste Employee)
  employee?: Employee;
}
