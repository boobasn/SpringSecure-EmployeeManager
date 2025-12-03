import { Employee } from './employee';

export interface Department {

  id?: string;

  name: string;
  description?: string;

  // ✅ relation OneToMany
  employes?: Employee[];
}
