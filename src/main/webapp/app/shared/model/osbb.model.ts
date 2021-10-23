import { ITrashCompany } from 'app/shared/model/trash-company.model';

export interface IOsbb {
  id?: number;
  address?: string | null;
  geo?: string | null;
  name?: string | null;
  trashCompany?: ITrashCompany | null;
}

export const defaultValue: Readonly<IOsbb> = {};
