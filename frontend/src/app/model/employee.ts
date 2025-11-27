export interface Employee {
  id?: number;            // facultatif pour le create
  nom: string;
  prenom: string;
  email: string;
  telephone?: string;
  dateNaissance?: string;
  adresse?: string;
  departement?: string;
  poste?: string;
  dateEmbauche?: string;
}
