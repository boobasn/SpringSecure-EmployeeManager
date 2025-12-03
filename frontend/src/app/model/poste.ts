import { Employee } from './employee';

export interface Poste {

  id?: string;

  titre: string;
  description?: string;

  // ✅ relation OneToMany
  employes?: Employee[];
}
