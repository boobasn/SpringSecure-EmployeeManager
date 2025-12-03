export interface User {
  id?: string;
  username: string;
  password?: string;   // souvent facultatif à la lecture
  role: string;        // ADMIN | MANAGER | EMPLOYE | POINTEUR
}
